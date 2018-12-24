package net.in.dayan.springdemo.repository;

import net.in.dayan.springdemo.entity.Todo;
import net.in.dayan.springdemo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TodoRepositoryTest {

    @Autowired
    TodoRepository todoRepository;

    @Test
    public void save() {
        Todo todo = new Todo();
        todo.setTitle("Go Shopping.");
        todo.setStatus(0);
        todo.setDt(new Date());
        todoRepository.save(todo);
    }

    @Test
    public void findByLogin() {
        List<Todo> todoList = todoRepository.findAll();
        for (Todo todo : todoList) {
            System.out.println(todo.getId() + ":" + todo.getTitle() + ":" + todo.getStatus() + ":" + todo.getDt());
        }
    }

    @Test
    public void update() {
        Todo todo = todoRepository.findById(1).orElse(null);
        if (todo != null) {
            todo.setTitle("Go Shopping.");
            todo.setStatus(0);
            todo.setDt(new Date());
            todoRepository.save(todo);
        }
    }


    @Test
    public void delete() {
        Todo todo = todoRepository.findById(1).orElse(null);
        if (todo != null) {
            todoRepository.delete(todo);
        }
    }


}