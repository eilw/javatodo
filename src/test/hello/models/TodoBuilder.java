package hello.models;

import org.springframework.test.util.ReflectionTestUtils;

/**
 * Created by eirikwiig on 27/07/2017.
 */
public class TodoBuilder {

    private Todo model;

    public TodoBuilder() {
        model = new Todo();
    }

    public TodoBuilder id(Long id) {
        ReflectionTestUtils.setField(model, "id", id);
        return this;
    }

    public TodoBuilder content(String content) {
        ReflectionTestUtils.setField(model, "content", content);
        return this;
    }

    public TodoBuilder completed(boolean completed) {
        ReflectionTestUtils.setField(model, "completed", completed);
        return this;
    }

    public Todo build() {
        return model;
    }
}
