package hello;

import hello.models.Todo;
import hello.models.TodoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sun.text.resources.CollationData;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by eirikwiig on 26/07/2017.
 */

@RestController
@RequestMapping("/todos")
public class ToDoController {
    private HashMap todos = new HashMap();
    private HashMap todo1 = new HashMap();
    private HashMap todo2 = new HashMap();
    private final AtomicLong counter = new AtomicLong();

    public ToDoController() {
        //todo1.put("id", counter.incrementAndGet());
        todo1.put("content", "Buy bread");
        todo1.put("completed", false);
        //todos.put(counter.get(), todo1);

        //todo2.put("id", counter.incrementAndGet());
        todo2.put("content", "Buy milk");
        todo1.put("completed", false);
        //todos.put(counter.get(), todo2);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Todo> getTodos() {
        try {
            System.out.println("Trying to get all todos");
            return _todoDao.getAll();
        }
        catch (Exception ex) {
            System.out.println("Exception with all todos" + ex);
            return new ArrayList<Todo>();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HashMap getTodo(@PathVariable long id) {
        return (HashMap) todos.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteTodo(@PathVariable long id) {
        try {
            Todo todo = new Todo(id);
            _todoDao.delete(todo);
        }
        catch(Exception ex) {
            return ex.getMessage();
        }

        todos.remove(id);
        return "Todo deleted";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createTodo(@RequestParam String content) {
        try {
            System.out.println("Trying to create new todo");
            Todo todo = new Todo(content);
            _todoDao.save(todo);
        }
        catch (Exception ex) {
            System.out.println("Exception with creating todo" + ex);
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(("id")).toUri();
        return ResponseEntity.created(location).build();
    }

    @Autowired
    private TodoDao _todoDao;
}

