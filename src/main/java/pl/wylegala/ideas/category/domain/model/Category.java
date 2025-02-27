package pl.wylegala.ideas.category.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.wylegala.ideas.question.domein.model.Answer;
import pl.wylegala.ideas.question.domein.model.Question;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Entity
@Table(name = "categories")
@Getter
@Setter
@ToString
public class Category {

    @Id
    private UUID id;

    @NotBlank(message = "{ideas.validation.name.NotBlank.message}")
    @Size(min = 3,max = 255)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Question> questions;

    public Category() {
        this.id = UUID.randomUUID();
    }

    public Category(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
    }

    public List<Answer> getAnswers() {
        if (questions == null) {
            return Collections.emptyList();
        }
        return questions.stream()
                .flatMap(q -> q.getAnswers().stream())
                .collect(Collectors.toList());
    }

}
