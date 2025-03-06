package pl.wylegala.ideas.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wylegala.ideas.category.domain.model.Category;
import pl.wylegala.ideas.category.dto.CategoryDto;
import pl.wylegala.ideas.category.service.CategoryService;


import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/categories")
public class CategoryApiController {
    private final CategoryService categoryService;


    @GetMapping
    Page<CategoryDto> getCategories(Pageable pageable){
        return categoryService.getCategories(pageable);
    }

    @GetMapping("{id}")
    CategoryDto getCategory(@PathVariable UUID id){
        return categoryService.getCategory(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryDto createdCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.createCategory(categoryDto);
    }


    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    CategoryDto updateCategory(@PathVariable UUID id, @RequestBody CategoryDto categoryDto){
        return categoryService.updateCategory(id, categoryDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCategory(@PathVariable UUID id){
        categoryService.deleteCategory(id);
    }
}
