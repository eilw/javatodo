package hello.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by eirikwiig on 27/07/2017.
 */
@Entity
@Table(name="todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(min = 1, max = 300)
    private String content;

    @NotNull
    private boolean completed = false;

    public Todo() { }

    public Todo(long id) {
        this.id = id;
    }

    public Todo(String content, boolean completed) {
        this.content = content;
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getContent() {

        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(long id) {

        this.id = id;
    }

    public long getId() {

        return id;
    }
}
