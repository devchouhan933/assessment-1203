/*
package facebook.controller;

import facebook.dao.PostDao;
import facebook.dao.UserDao;
import facebook.entity.Post;
import facebook.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RestController
public class PostController {
    //private final Map<String, User> userProfile = new HashMap<>();
    @Autowired
    private UserDao userDao;
    @Autowired
    private PostDao postDao;

    @PostMapping(value = "/post", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView addPost(@RequestBody MultiValueMap<String, String> postBody) {
        if (Utility.isUserValid(postBody)) {
            String email = postBody.get("email").get(0);
            String postString = postBody.get("post").get(0);
            User user = userDao.readByEmail(email);
            Post post = new Post(postString, Timestamp.from(Instant.now()), user, email);
            postDao.savePost(post);
            ModelAndView modelAndView = new ModelAndView("success-post");
            return modelAndView;
        }
        return Utility.errorMessageModelAndView("Data mis match");
    }

    @GetMapping("/posts")
    ModelAndView getPosts() {

        List<Post> allPost = postDao.findAllPost();
        if (allPost.size() > 0) {
            ModelAndView modelAndView = new ModelAndView("posts");

            modelAndView.getModel().put("posts", allPost);
            return modelAndView;
        }
        return Utility.errorMessageModelAndView("No pos available yet");
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
            return Utility.errorMessageModelAndView("not post available yet");
        }
        return Utility.errorMessageModelAndView("email Not found");
    }

}
*/
