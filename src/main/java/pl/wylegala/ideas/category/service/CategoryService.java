package pl.wylegala.ideas.category.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wylegala.ideas.category.domain.model.Category;
import pl.wylegala.ideas.category.domain.repository.CategoryRepository;
import pl.wylegala.ideas.category.dto.CategoryDto;
import pl.wylegala.ideas.category.dto.CategoryWithStatisticsDto;


import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional(readOnly = true)
    public Page<CategoryDto> getCategories(Pageable pageable) {
       return getCategories(null,pageable);
    }

    @Transactional(readOnly = true)
    public Page<CategoryDto> getCategories(String search,Pageable pageable) {
        Page<Category> categoryPage;
        if (search == null) {
            categoryPage = categoryRepository.findAll(pageable);
        } else {
            categoryPage = categoryRepository.findByNameContainingIgnoreCase(search,pageable);
        }
        return categoryPage.map(categoryMapper::mapDto);
    }

    @Transactional(readOnly = true)
    public CategoryDto getCategory(UUID id) {
        Category category = categoryRepository.getReferenceById(id);
        return categoryMapper.mapDto(category);
    }

    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDtoRequest) {
        Category category = categoryMapper.mapEntity(categoryDtoRequest);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.mapDto(savedCategory);
    }

    @Transactional
    public CategoryDto updateCategory(UUID id, CategoryDto categoryDtoRequest) {
        Category existingCategory = categoryRepository.getReferenceById(id);
        existingCategory.setName(categoryDtoRequest.getName());
        Category savedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.mapDto(savedCategory);
    }

    @Transactional
    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);

    }

    public List<CategoryWithStatisticsDto> findAllWithStatistics() {
        return categoryRepository.findAllWithStatistics();
    }
}
