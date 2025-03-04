package pl.wylegala.ideas.question.service;

import org.springframework.stereotype.Component;
import pl.wylegala.ideas.question.domein.model.Answer;
import pl.wylegala.ideas.question.dto.AnswerDto;

@Component
public class AnswerMapper {

    public AnswerDto mapToDto(Answer answer) {
        AnswerDto answerDto = new AnswerDto();
        answerDto.setId(answer.getId());
        answerDto.setName(answer.getName());
        return answerDto;
    }


    public Answer mapToDto(AnswerDto answerDto) {
        Answer answer = new Answer();
        answer.setId(answerDto.getId());
        answer.setName(answerDto.getName());
        return answer;
    }
}
