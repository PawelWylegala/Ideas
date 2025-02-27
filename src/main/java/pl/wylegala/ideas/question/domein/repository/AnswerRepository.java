package pl.wylegala.ideas.question.domein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wylegala.ideas.question.domein.model.Answer;
import pl.wylegala.ideas.question.domein.model.Question;

import java.util.List;
import java.util.UUID;

public interface AnswerRepository extends JpaRepository<Answer, UUID> {
    List<Answer> findByQuestionId(UUID questionId);

    Answer getAnswerById(UUID id);
}
