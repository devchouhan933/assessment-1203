/*
package facebook.controller;

import facebook.dao.UserDao;
import facebook.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class UserController {
    //private final Map<String, User> userProfile = new HashMap<>();
    @Autowired
    private UserDao userDao;

    @PostMapping(value = "/registerHtml", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView registerHtml() {
        ModelAndView modelAndView = new ModelAndView("registerPage");
        return modelAndView;
    }

    @GetMapping("/loginForm")
    public ModelAndView loginForm() {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView login(@RequestBody MultiValueMap<String, String> map) {
        String email = map.get("email").get(0);
        String password = map.get("password").get(0);
        if (Utility.isValidEmail(email)) {
            User user = userDao.readByEmail(email);
            if (user != null && email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                ModelAndView modelAndView = new ModelAndView("fbProfile");
                modelAndView.getModel().put("name", user.getName());
                modelAndView.getModel().put("email", user.getEmail());
                return modelAndView;
            }
            return Utility.errorMessageModelAndView("Invalid credentials");
        }
        return Utility.errorMessageModelAndView("not valid email");
    }


    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView registerNew(@RequestBody MultiValueMap<String, String> formData) {
        ModelAndView modelAndView = new ModelAndView("newUsers");
        String email = formData.get("email").get(0);
        String name = formData.get("name").get(0);
        String password = formData.get("password").get(0);
        ModelAndView modelAndView1 = new ModelAndView("error");
        if (userDao.readByEmail(email) == null) {
            if (Utility.isValidEmail(email) && Utility.containsInvalidChars(name) && Utility.isValidPassword(password)) {
                User user = new User(name, email, password);
                userDao.create(user);
                modelAndView.getModel().put("email", email);
                modelAndView.getModel().put("name", name);
                modelAndView.getModel().put("password", password);
                return modelAndView;
            }
            modelAndView1.getModel().put("error", "Invalid data");
            return modelAndView1;
        }
        modelAndView1.getModel().put("error", "User already exist");
        return modelAndView1;
    }

}*/
