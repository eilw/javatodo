package hello;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by eirikwiig on 26/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ToDoControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void getTodos() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/todos").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)));
    }

    @Test
    public void getTodo() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/todos/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", is("Buy bread")))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void createTodo() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/todos").param("content", "Buy cheese").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect((header().string("Location",  containsString("/todos/"))));
    }

    @Test
    public void deleteTodo() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/todos/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/todos").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..[?(@.id == 1)]").doesNotExist());
    }
}