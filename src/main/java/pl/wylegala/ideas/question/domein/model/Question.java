package pl.wylegala.ideas.question.domein.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.wylegala.ideas.category.domain.model.Category;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "questions")
@Getter
@Setter
@ToString
public class Question {


    @Id
    private UUID id;

    private String name;

    private LocalDateTime created;

    private LocalDateTime modified;
    @ManyToOne
    private Category category;



    @OneToMany(mappedBy = "question")
    private Set<Answer> answers;

    public Question() {
        this.id = UUID.randomUUID();
    }

    public Question(String name) {
        this();
        this.name = name;

    }

    @PrePersist
    void prePersist() {
        created = LocalDateTime.now();
        modified = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        modified = LocalDateTime.now();
    }

    public Question addAnswer(Answer answer) {
        if (answers == null) {
            answers = new LinkedHashSet<>();
        }
        answer.setQuestion(this);
        answers.add(answer);

        return this;
    }

}
