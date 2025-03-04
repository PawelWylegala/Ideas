package pl.wylegala.ideas.question.service;

import org.springframework.stereotype.Component;
import pl.wylegala.ideas.question.domein.model.Question;
import pl.wylegala.ideas.question.dto.QuestionApiDto;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionMapperApi {

    public QuestionApiDto maptoDto(Question question) {
        QuestionApiDto questionDto = new QuestionApiDto();
        questionDto.setQuestionId(question.getId());
        questionDto.setName(question.getName());
        return questionDto;
    }

    public List<QuestionApiDto> mapDtoList(List<Question> questions) {
        return questions.stream()
                .map(this::maptoDto)
                .collect(Collectors.toList());
    }
}
