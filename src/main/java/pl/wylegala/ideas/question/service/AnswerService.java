package pl.wylegala.ideas.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wylegala.ideas.question.domein.model.Answer;
import pl.wylegala.ideas.question.domein.model.Question;
import pl.wylegala.ideas.question.domein.repository.AnswerRepository;
import pl.wylegala.ideas.question.domein.repository.QuestionRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;


    @Transactional(readOnly = true)
    public List<Answer> getAnswers(UUID questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    @Transactional(readOnly = true)
    public Answer getAnswer(UUID id) {
        return answerRepository.getAnswerById(id);
    }

    @Transactional
    public Answer updateAnswer(UUID answerId, Answer answerRequest) {
        Answer answer = answerRepository.getAnswerById(answerId);
        answer.setName(answerRequest.getName());

        return answerRepository.save(answer);
    }

    @Transactional
    public Answer createAnswer(UUID questionId, Answer answerRequest) {
        Answer answer = new Answer();
        answer.setName(answerRequest.getName());

        Question question = questionRepository.getById(questionId);
        question.addAnswer(answer);

        answerRepository.save(answer);
        questionRepository.save(question);
        return answer;
    }

    @Transactional
    public void deleteAnswer(UUID id) {
        answerRepository.deleteById(id);

    }
}
