package pl.wylegala.ideas.category.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.wylegala.ideas.question.domein.model.Answer;
import pl.wylegala.ideas.question.domein.model.Question;

import java.util.List;
import java.util.Set;
import java.util.UUID;



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


    public Category() {
        this.id = UUID.randomUUID();
    }

    public Category(String name) {
        this();
        this.name = name;

    }

    @OneToMany(
            mappedBy = "category",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    private Set<Question> questions;


}
