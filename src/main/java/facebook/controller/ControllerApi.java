package facebook.controller;

import facebook.dao.PostDao;
import facebook.dao.UserDao;
import facebook.entity.Post;
import facebook.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
public class ControllerApi {
    private final Map<String, User> userProfile = new HashMap<>();
    @Autowired
    private UserDao userDao;
    @Autowired
    private PostDao postDao;

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
        if (isValidEmail(email)) {
            User user = userDao.readByEmail(email);
            if (user != null && email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                ModelAndView modelAndView = new ModelAndView("fbProfile");
                modelAndView.getModel().put("name", user.getName());
                modelAndView.getModel().put("email", user.getEmail());
                return modelAndView;
            }
            return errorMessageModelAndView("Invalid credentials");
        }
        return errorMessageModelAndView("not valid email");
    }


    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView registerNew(@RequestBody MultiValueMap<String, String> formData) {
        ModelAndView modelAndView = new ModelAndView("newUsers");
        String email = formData.get("email").get(0);
        String name = formData.get("name").get(0);
        String password = formData.get("password").get(0);
        ModelAndView modelAndView1 = new ModelAndView("error");
        if (userDao.readByEmail(email) == null) {
            if (isValidEmail(email) && containsInvalidChars(name) && isValidPassword(password)) {
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


    @PostMapping(value = "/post", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView addPost(@RequestBody MultiValueMap<String, String> postBody) {
        if (isUserValid(postBody)) {
            String email = postBody.get("email").get(0);
            String postString = postBody.get("post").get(0);
            User user = userDao.readByEmail(email);
            Post post = new Post(postString, Timestamp.from(Instant.now()), user, email);
            postDao.savePost(post);
            ModelAndView modelAndView = new ModelAndView("success-post");
            return modelAndView;
        }
        return errorMessageModelAndView("Data mis match");
    }

    @GetMapping("/posts")
    ModelAndView getPosts() {

        List<Post> allPost = postDao.findAllPost();
        if (allPost.size() > 0) {
            ModelAndView modelAndView = new ModelAndView("posts");

            modelAndView.getModel().put("posts", allPost);
            return modelAndView;
        }
        return errorMessageModelAndView("No pos available yet");
    }

    @GetMapping("/posts/{email}")
    private ModelAndView getPostByEmail(@PathVariable String email) {
        if (userDao.readByEmail(email) != null) {

            List<Post> allPostByEmail = postDao.findAllPostByEmail(email);
            if (allPostByEmail.size() > 0) {
                ModelAndView modelAndView = new ModelAndView("posts");
                modelAndView.getModel().put("posts", allPostByEmail);
                return modelAndView;
            }
            return errorMessageModelAndView("not post available yet");
        }
        return errorMessageModelAndView("email Not found");
    }

    private boolean containsInvalidChars(String name) {
        return ((name != null) && (!name.equals("")) && (name.matches("^[a-zA-Z]*$")));
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&-]+(?:\\." + "[a-zA-Z0-9_+&-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) return false;

        return pat.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";

        Pattern pattern = Pattern.compile(regex);
        return password != null;/*pattern.matcher(password).matches()*/

    }

    private ModelAndView errorMessageModelAndView(String message) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.getModel().put("message", message);
        return modelAndView;
    }

    private boolean isUserValid(MultiValueMap<String, String> map) {
        String email = map.get("email").get(0);
        String password = map.get("password").get(0);
        System.out.println(password);
        User user = userDao.readByEmail(email);
        System.out.println(user);
        return password.equals(user.getPassword());
    }


}