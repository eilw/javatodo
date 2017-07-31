package hello.controller;

import hello.models.Todo;
import hello.repository.TodoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by eirikwiig on 26/07/2017.
 */

@RestController
@RequestMapping("/todos")
public class ToDoController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Todo> getTodos() {
        System.out.println("Trying to get all todos");
        return todoDao.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Todo> getTodo(@PathVariable long id) {
        try {
            System.out.println("Trying to get specific todos");
            return new ResponseEntity<Todo>(todoDao.getById(id), HttpStatus.OK);
        }
        catch (Exception ex) {
            System.out.println("Exception with creating todo" + ex);
            return new ResponseEntity<Todo>(new Todo(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Todo deleteTodo(@PathVariable long id) {
        Todo todo = new Todo(id);
        todoDao.delete(todo);
        return todo;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Todo> createTodo(@RequestParam String content) {
        try {
            System.out.println("Trying to create new todo");
            Todo todo = new Todo(content);
            todoDao.save(todo);
            return new ResponseEntity<Todo>(todo, HttpStatus.CREATED);
        }
        catch (Exception ex) {
            System.out.println("Exception with creating todo" + ex);
            return new ResponseEntity<Todo>(new Todo(), HttpStatus.BAD_REQUEST);
        }
    }


    @Autowired
    private TodoDao todoDao;
}

