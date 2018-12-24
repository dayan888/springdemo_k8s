package net.in.dayan.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(@RequestParam Optional<String> error) {
        return new ModelAndView("login", "error", error);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/todo/list");
        return null;
    }

}
