package facebook.controller;

import facebook.dao.FriendDao;
import facebook.dao.PostDao;
import facebook.dao.UserDao;
import facebook.entity.Friend;
import facebook.entity.Post;
import facebook.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
public class ControllerApi {
    //private final Map<String, User> userProfile = new HashMap<>();
    @Autowired
    private UserDao userDao;
    @Autowired
    private PostDao postDao;
    @Autowired
    private FriendDao friendDao;


    @GetMapping("/users")
    public ModelAndView getUsers(@RequestParam String email) {
        User user = userDao.readByEmail(email);
        List<User> users = userDao.readAll();
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.getModel().put("users", users);
        modelAndView.getModel().put("userPassword", user.getPassword());
        modelAndView.getModel().put("userEmail", email);
        return modelAndView;
    }

    @PostMapping("/friend")
    public ModelAndView followUsers(@RequestBody MultiValueMap<String, String> requestBody) {
        String email = requestBody.get("userEmail").get(0);
        String friend_email = requestBody.get("friendEmail").get(0);
        String password = requestBody.get("password").get(0);
        System.out.println();
        if (email != null && friend_email != null && !email.equals(friend_email)) {
            User user = userDao.readByEmail(email);
            User userFriend = userDao.readByEmail(friend_email);
            if (user != null && userFriend != null && email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                List<Friend> friendsOfUser = friendDao.readAll(user.getId()).stream().filter(friend -> friend.getFriendId() == userFriend.getId()).collect(Collectors.toList());
                if (friendsOfUser.size() == 0) {
                    Friend friend = new Friend(userFriend.getId(), user);
                    friendDao.create(friend);
                    ModelAndView modelAndView = new ModelAndView("successFriend");
                    return modelAndView;
                }
                return errorMessageModelAndView("You both are friends don't you know ");
            }
            return errorMessageModelAndView("Data mismatch");
        }
        return errorMessageModelAndView("Invalid Data ");
    }


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
    private ModelAndView getPostByEmail(@PathVariable String email, String userEmail) {
        User endUser = userDao.readByEmail(email);
        User user = userDao.readByEmail(userEmail);
        if (endUser != null && user != null) {
            List<Post> allPostByEmail = getAllPostByEmail(email);
            List<Friend> friends = friendDao.readAll(endUser.getId());
            long friendBlocked_status = friends.stream().filter(friend -> friend.getFriendId() == user.getId() && !friend.isBlocked()).count();
            if ("PUBLIC".equals(endUser.getUserPostVisibility()) && (friendBlocked_status > 0 || friends.size() == 0)) {
                if (allPostByEmail.size() > 0) {
                    ModelAndView modelAndView = new ModelAndView("posts");
                    modelAndView.getModel().put("posts", allPostByEmail);
                    return modelAndView;
                }
                return errorMessageModelAndView("not post available yet");
            } else {
                if (friendBlocked_status > 0) {
                    if (allPostByEmail.size() > 0) {
                        ModelAndView modelAndView = new ModelAndView("posts");
                        modelAndView.getModel().put("posts", allPostByEmail);
                        return modelAndView;
                    }
                    return errorMessageModelAndView("your friend didn't posted yet anything");
                }
            }
            return errorMessageModelAndView("you are not allowed ");
        }
        return errorMessageModelAndView("email Not found");
    }

    @PutMapping("/user/block/{email}")
    public ModelAndView blockUser(@PathVariable String email, String userEmail) {
        User endUser = userDao.readByEmail(email);
        User user = userDao.readByEmail(userEmail);
        if (endUser != null && user != null) {
            Optional<Friend> friend = friendDao.readAll(user.getId()).stream().filter(friend_ -> friend_.getFriendId() == endUser.getId()).findFirst();
            if (friend.isPresent()) {
                Friend friendToBlock = friend.get();
                friendDao.blockFriend(friendToBlock.getFriendId());
                errorMessageModelAndView(" friend : " + endUser.getName() + " is blocked");
            }
            return errorMessageModelAndView("you can't block");
        }
        return errorMessageModelAndView("invalid data ");
    }

    private List<Post> getAllPostByEmail(String email) {
        return postDao.findAllPostByEmail(email);
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

