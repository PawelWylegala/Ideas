package pl.wylegala.ideas.category.service;

import org.springframework.stereotype.Component;
import pl.wylegala.ideas.category.domain.model.Category;
import pl.wylegala.ideas.category.dto.CategoryDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {

    public CategoryDto mapDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public Category mapEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }

    public List<CategoryDto> mapDtoList(List<Category> categories) {
        return categories.stream()
                .map(this::mapDto)
                .toList();

    }

}
