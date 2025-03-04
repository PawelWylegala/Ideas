package pl.wylegala.ideas.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wylegala.ideas.question.domein.model.Answer;
import pl.wylegala.ideas.question.domein.model.Question;
import pl.wylegala.ideas.question.domein.repository.AnswerRepository;
import pl.wylegala.ideas.question.domein.repository.QuestionRepository;
import pl.wylegala.ideas.question.dto.AnswerDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final AnswerMapper answerMapper;


    @Transactional(readOnly = true)
    public List<AnswerDto> getAnswers(UUID questionId) {
        return answerRepository.findByQuestionId(questionId)
                .stream()
                .map(answerMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AnswerDto getAnswer(UUID id) {
        Answer answer = answerRepository.getReferenceById(id);
        return answerMapper.mapToDto(answer);
    }

    @Transactional
    public AnswerDto updateAnswer(UUID answerId, AnswerDto answerRequest) {
        Answer answer = answerRepository.getReferenceById(answerId);
        answer.setName(answerRequest.getName());

        Answer savedAnswer = answerRepository.save(answer);

        return answerMapper.mapToDto(savedAnswer);
    }

    @Transactional
    public AnswerDto createAnswer(UUID questionId, AnswerDto answerRequest) {
        Answer answer = new Answer();
        answer.setName(answerRequest.getName());

        Question question = questionRepository.getReferenceById(questionId);
        question.addAnswer(answer);

        Answer savedAnswer = answerRepository.save(answer);

        questionRepository.save(question);
        return answerMapper.mapToDto(savedAnswer);
    }

    @Transactional
    public void deleteAnswer(UUID id) {
        answerRepository.deleteById(id);

    }
}
