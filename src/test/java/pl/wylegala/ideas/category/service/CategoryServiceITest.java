package pl.wylegala.ideas.category.service;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import pl.wylegala.ideas.category.domain.model.Category;
import pl.wylegala.ideas.category.domain.repository.CategoryRepository;
import pl.wylegala.ideas.question.domein.repository.AnswerRepository;
import pl.wylegala.ideas.question.domein.repository.QuestionRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@Transactional
@Rollback
class CategoryServiceITest {
    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @BeforeEach
    void setUp() {
        answerRepository.deleteAll();
        questionRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void shouldGetCategories() {


        // given

        categoryRepository.saveAll(List.of(
                new Category("Category 1"),
                new Category("Category 2"),
                new Category("Category 3")
        ));


        // when
        Page<Category> categories = categoryService.getCategories(Pageable.unpaged());

        // then
        assertThat(categories)
                .hasSize(3)
                .extracting(Category::getName)
                .containsExactlyInAnyOrder("Category 1", "Category 2", "Category 3");
    }


    @Test
    void shouldGetCategory() {
        //given
        Category category = categoryRepository.save(new Category("Category 1"));
        UUID categoryId = category.getId();

        //when
        Category result = categoryService.getCategory(categoryId);

        //then
        assertThat(result.getId()).isEqualTo(categoryId);
        assertThat(result.getName()).isEqualTo(category.getName());
    }

    @Test
    void shouldCreateCategory() {
        //given
        Category requestCategory = new Category("Category 1");


        //when
        Category result = categoryService.createCategory(requestCategory);

        //then
        assertThat(result.getName()).isEqualTo(requestCategory.getName());
 assertThat(result.getName()).isEqualTo(categoryRepository.getReferenceById(result.getId()).getName());
    }

    @Test
    void shouldUpdateCategory() {
        //given
        List<Category> categories = List.of(
                new Category("Category1"),
                new Category("Category2")
        );
        UUID id = categories.getFirst().getId();
        Category categoryRequest = categories.getLast();
        categoryRepository.saveAll(categories);


        //when
        Category result  = categoryService.updateCategory(id,categoryRequest);
        //then

        assertThat(result.getName()).isEqualTo(categories.getLast().getName());
        assertThat(result.getId()).isEqualTo(categories.getFirst().getId());
        assertThat(result.getId()).isEqualTo(categoryRepository.getReferenceById(categories.getFirst().getId()).getId());

    }

    @Test
    void shouldDeleteCategory() {
        //given
        Category category = categoryRepository.save(new Category("Category 1"));

        //when
        Throwable throwable = catchThrowable(()-> categoryService.deleteCategory(category.getId()));

        //then

        assertThat(categoryRepository.findById(category.getId())).isEmpty();
    }
}