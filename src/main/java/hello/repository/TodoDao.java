package hello.repository;

import hello.models.Todo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by eirikwiig on 27/07/2017.
 */
@Repository
@Transactional
public class TodoDao {

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(Todo todo) {
        getSession().save(todo);
    }

    public void delete(Todo todo) {
        getSession().delete(todo);
    }

    @SuppressWarnings("unchecked")
    public List<Todo> getAll() {
        return getSession().createQuery("from Todo").list();
    }

    public Todo getByContent(String content) {
        return (Todo) getSession().createQuery(
                "from Todo where content = :content")
                .setParameter("content", content)
                .uniqueResult();
    }

    public Todo getById(long id) {
            return (Todo) getSession().get(Todo.class, id);
    }

    public void update(Todo todo) {
        getSession().update(todo);
    }
}
