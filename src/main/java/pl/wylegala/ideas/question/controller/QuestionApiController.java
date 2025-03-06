package pl.wylegala.ideas.question.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wylegala.ideas.question.domein.model.Question;
import pl.wylegala.ideas.question.dto.QuestionDto;
import pl.wylegala.ideas.question.service.QuestionMapper;
import pl.wylegala.ideas.question.service.QuestionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/questions")
public class QuestionApiController {

	private final QuestionService questionsService;
	private final QuestionMapper questionMapper;

	public QuestionApiController(QuestionService questionsService, QuestionMapper questionMapper) {
		this.questionsService = questionsService;
        this.questionMapper = questionMapper;
    }

	@GetMapping
	List<QuestionDto> getQuestions(){
		return questionsService.getQuestions();
	}

	@GetMapping("{id}")
	QuestionDto getQuestion(@PathVariable UUID id){
		return questionsService.getQuestion(id);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	QuestionDto createQuestion(@RequestBody QuestionDto questionDto) {
		return questionsService.createQuestion(questionDto);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	QuestionDto updateQuestion(@PathVariable UUID id, @RequestBody QuestionDto question){
	return questionsService.updateQuestion(id, question);

	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	void deleteQuestion(@PathVariable UUID id){
		questionsService.deleteQuestion(id);
	}
}
