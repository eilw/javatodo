package hello;

import hello.models.Todo;
import hello.models.TodoBuilder;
import hello.repository.TodoDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ToDoControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TodoDao todoDaoMock;

    @Before
    public void setUp() {
        //We have to reset our mock between tests because the mock objects
        //are managed by the Spring container. If we would not reset them,
        //stubbing and verified behavior would "leak" from one test to another.
        MockitoAnnotations.initMocks(this);


        //mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getTodos() throws Exception {
        Todo firstTodo = new TodoBuilder()
                .id(1L)
                .content("Go to store")
                .completed(false)
                .build();
        Todo secondTodo = new TodoBuilder()
                .id(2L)
                .content("Go home")
                .completed(false)
                .build();

        when(todoDaoMock.getAll()).thenReturn(Arrays.asList(firstTodo, secondTodo));
        mvc.perform(MockMvcRequestBuilders.get("/todos").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].content", is("Go to store")));
    }

    @Test
    public void getTodo() throws Exception {
        Todo firstTodo = new TodoBuilder()
                .id(1L)
                .content("Go to store")
                .completed(false)
                .build();
        when(todoDaoMock.getById(1L)).thenReturn(firstTodo);
        mvc.perform(MockMvcRequestBuilders.get("/todos/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", is("Go to store")));
    }

    @Test
    public void createTodo() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/todos").param("content", "Buy cheese").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(todoDaoMock).save(Matchers.any(Todo.class));
    }

    @Test
    public void deleteTodo() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/todos/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(todoDaoMock).delete(Matchers.any(Todo.class));
    }
}