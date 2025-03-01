package pl.wylegala.ideas.question.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.WebContentGenerator;
import pl.wylegala.ideas.category.domain.model.Category;
import pl.wylegala.ideas.category.domain.repository.CategoryRepository;
import pl.wylegala.ideas.question.domein.model.Answer;
import pl.wylegala.ideas.question.domein.model.Question;
import pl.wylegala.ideas.question.domein.repository.AnswerRepository;
import pl.wylegala.ideas.question.domein.repository.QuestionRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@Transactional
@Rollback
class QuestionServiceITest {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private WebContentGenerator webContentGenerator;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @BeforeEach
    void setUp() {
        answerRepository.deleteAll();
        questionRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void shouldGetQuestions() {
        //given
        questionRepository.saveAll(List.of(
                new Question("Question1"),
                new Question("Question2"),
                new Question("Question3")
        ));

        //when
        List<Question> questions = questionService.getQuestions();
        System.out.println(questions.toString());

        //then
        assertThat(questions)
                .hasSize(3)
                .extracting(Question::getName)
                .containsExactlyInAnyOrder("Question1", "Question2", "Question3");
    }

    @Test
    void shouldGetQuestion() {
        //given
        Question question = new Question("Question2");

        questionRepository.saveAll(List.of(
                new Question("Question1"),
                question,
                new Question("Question3")
        ));

        //when
        Question result = questionService.getQuestion(question.getId());
        //then
        assertThat(result.getId()).isEqualTo(question.getId());
    }

    @Test
    void shouldCreateQuestion() {
        //given
        Question question = new Question("Question");

        //when
        Question result = questionService.createQuestion(question);
        //then\
        assertThat(result.getName()).isEqualTo(question.getName());
        assertThat(result.getName()).isEqualTo(questionRepository.getReferenceById(result.getId()).getName());
    }

    @Test
    void shouldUpdateQuestion() {
        //given
        Question question = new Question("Question2");
        question = questionService.createQuestion(question);

        question.setName("updated");
        //when

        Question result = questionService.updateQuestion(question.getId(), question);

        //then
        assertThat(result.getId()).isEqualTo(question.getId());
        assertThat(result.getId()).isEqualTo(questionRepository.getReferenceById(question.getId()).getId());
        assertThat(result.getName()).isEqualTo(question.getName());

    }

    @Test
    void shouldDeleteQuestion() {
        Question question = new Question("Question");
        question = questionService.createQuestion(question);
        UUID id = question.getId();

        // when
        Throwable throwable = catchThrowable(() -> questionService.deleteQuestion(id));

        // then
        assertThat(questionRepository.findById(question.getId())).isEmpty();
    }

    @Test
    void shouldFindAllByCategoryId() {
        //given
        Category category = new Category("Category1");
        categoryRepository.save(category);
        Category category2 = new Category("Category2");
        categoryRepository.save(category2);

        Question question1 = new Question("Question1");
        question1.setCategory(category2);

        Question question2 = new Question("Question2");
        question2.setCategory(category);

        Question question3 = new Question("Question3");
        question3.setCategory(category);
        questionRepository.saveAll(List.of(question1, question2, question3));

        //when

        List<Question> questions = questionService.findAllByCategoryId(category.getId());
        String string = questions.toString();
        System.out.println(string);

        //then
        assertThat(questions)
                .hasSize(2)
                .extracting(Question::getName)
                .containsExactlyInAnyOrder( "Question2", "Question3");
    }

    @Test
    void shouldFindHot() {
        Question question1 = new Question("Question1");
        Question question2 = new Question("Question2");
        Question question3 = new Question("Question3");

        questionRepository.saveAll(List.of(question1, question2, question3));

        Answer answer1 = new Answer("Answer1");
        Answer answer2 = new Answer("Answer2");
        question2.addAnswer(answer1);
        question2.addAnswer(answer2);
        answerRepository.saveAll(List.of(answer1, answer2));

        //when
        Page<Question> result = questionService.findHot(Pageable.unpaged());

        //then

        assertThat(result)
                .hasSize(3)
                .extracting(Question::getName)
                .containsExactlyElementsOf(List.of("Question2", "Question1", "Question3"));


    }

    @Test
    void shouldFindUnanswered() {
        //given
        questionRepository.deleteAll();
        Question question1 = new Question("Question1");
        Question question2 = new Question("Question2");
        Question question3 = new Question("Question3");

        questionRepository.saveAll(List.of(question1, question2, question3));

        Answer answer1 = new Answer("Answer1");
        question2.addAnswer(answer1);
        answerRepository.saveAll(List.of(answer1));

        //when
        Page<Question> unanswered = questionService.findUnanswered(Pageable.unpaged());

        //then
        assertThat(unanswered)
                .hasSize(2)
                .extracting(Question::getName)
                .containsExactlyInAnyOrder("Question1", "Question3");
    }

    @Test
    void shouldFindByQuery() {
        //given
        questionRepository.deleteAll();
        String query = "abc";
        Question question1 = new Question("Question1");
        Question question2 = new Question("Question2-" + query);
        Question question3 = new Question("Question3");

        questionRepository.saveAll(List.of(question1, question2, question3));
        //when
        Page<Question> byQuery = questionService.findByQuery(query, Pageable.unpaged());
        System.out.println(byQuery.toString());

        //then

        assertThat(byQuery)
                .hasSize(1)
                .extracting(Question::getId)
                .containsExactlyInAnyOrder(question2.getId());
    }
}