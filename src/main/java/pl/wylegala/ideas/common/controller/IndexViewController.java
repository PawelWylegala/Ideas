package pl.wylegala.ideas.common.controller;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wylegala.ideas.category.dto.CategoryWithStatisticsDto;
import pl.wylegala.ideas.common.dto.StatisticsDto;
import pl.wylegala.ideas.question.dto.QuestionDto;
import pl.wylegala.ideas.question.service.QuestionService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class IndexViewController extends IdeasCommonViewController {

    private final QuestionService questionService;

    @GetMapping
    public String indexView(
            Model model
    ) {
        addGlobalAttributes(model);

        List<QuestionDto> questionsTop = questionService.findTop(2);
        model.addAttribute("questionsTop", questionsTop);

        List<CategoryWithStatisticsDto> categories = categoryService.findAllWithStatistics();
        model.addAttribute("categories", categories);

        return "index/index";
    }

    public List<QuestionDto> topQuestionsByCategory(UUID categoryId) {
        List<QuestionDto> topQuestions = questionService.findTop(categoryId, 2);
        return topQuestions;
    }

    public List<QuestionDto> randomQuestions() {
        List<QuestionDto> randomQuestions = questionService.findRandom(2);
        return randomQuestions;
    }



}
