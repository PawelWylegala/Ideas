package pl.wylegala.ideas.question.service;


import org.springframework.stereotype.Component;
import pl.wylegala.ideas.question.domein.model.Question;
import pl.wylegala.ideas.question.dto.QuestionDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {

    public QuestionDto mapDto(Question question) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.setName(question.getName());
        questionDto.setCategoryId(question.getCategory().getId());
        questionDto.setCategoryName(question.getCategory().getName());
        questionDto.setAnswers(question.getAnswers() == null ? 0 : question.getAnswers().size());
        questionDto.setCreated(question.getCreated());

        return questionDto;
    }

    public List<QuestionDto> mapDtoList(List<Question> questions) {
        return questions.stream()
                .map(this::mapDto)
                .collect(Collectors.toList());
    }

    public Question mapEntity(QuestionDto questionDto) {
        Question question = new Question();
        question.setId(questionDto.getId());
        question.setName(questionDto.getName());
        question.setCreated(questionDto.getCreated());
       return question;
    }
}
