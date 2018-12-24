package net.in.dayan.springdemo.controller;

import net.in.dayan.springdemo.entity.Todo;
import net.in.dayan.springdemo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

import static java.nio.file.Files.*;

@Controller
@RequestMapping(value = "/todo")
public class TodoController {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    Environment env;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Todo> list = todoRepository.findAll();
        for (Todo todo : list) {
            if (getFile(todo.getId()) != null) {
                todo.setHasPic(true);
            }
        }
        model.addAttribute("todos", list);
        return "todo/list";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Integer id, Model model) {
        setStatusOptions(model);
        model.addAttribute("todo", todoRepository.findById(id).orElse(null));
        return "todo/edit";
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String create(Model model) {
        setStatusOptions(model);
        model.addAttribute("todo", new Todo());
        return "todo/edit";
    }

    private void setStatusOptions(Model model) {
        model.addAttribute("statusOptions", Todo.statusOptions);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(@ModelAttribute("todo") Todo todo, Model model, HttpServletResponse response) throws IOException {
        if (todo.getId() != null) {
            Todo curTodo = todoRepository.findById(todo.getId()).orElse(null);
            todo.setDt(curTodo.getDt());
        }
        else {
            todo.setDt(new Date());
        }
        todoRepository.save(todo);

        if (todo.getPic() != null) {
            String dir = env.getProperty("app.picDir");
            new File(dir).mkdir();
            String ext = StringUtils.getFilenameExtension(todo.getPic().getOriginalFilename());
            todo.getPic().transferTo(new File(dir + "/" + todo.getId() + "." + ext));
        }

        response.sendRedirect("/todo/list");
        return null;
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Integer id, Model model, HttpServletResponse response) throws IOException {
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo != null) {
            todoRepository.delete(todo);
        }
        File file = getFile(id);
        if (file != null) {
            file.delete();
        }

        response.sendRedirect("/todo/list");
        return null;
    }

    @RequestMapping(value = "pic/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]>  pic(@PathVariable("id") Integer id, Model model) throws IOException {
        File file = getFile(id);
        if (file == null) return null;
        byte[] image = readAllBytes(file.toPath());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    private File getFile(@PathVariable("id") Integer id) {
        String dir = env.getProperty("app.picDir");
        File file =  new File(dir + "/" + id + ".png");
        if (!file.exists()) {
            file =  new File(dir + "/" + id + ".jpg");
        }
        if (!file.exists()) {
            return null;
        }
        return file;
    }
}
