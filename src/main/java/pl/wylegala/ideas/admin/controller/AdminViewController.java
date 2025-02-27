package pl.wylegala.ideas.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wylegala.ideas.question.service.QuestionService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminViewController {

    private final QuestionService questionService;


    @GetMapping
    public String indexView(Model model) {
        model.addAttribute("statistics",questionService.statistics());
        log.info("Admin view index");
        return "admin/index";
    }

}
