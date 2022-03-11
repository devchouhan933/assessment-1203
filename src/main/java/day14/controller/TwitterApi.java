package day14.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
public class TwitterApi {

    private static final List<Tweet> tweets = new ArrayList<>();
    private final Map<String, User> userProfile = new HashMap<>();
    private final Map<String, List<String>> following = new HashMap<>();
    @Autowired
    private UserDao userDao;
    @Autowired
    private FollowerDao followerDao;
    @Autowired
    private TweetDao tweetDao;

 //   @Autowired
    public TwitterApi(UserDao userDao) {
        List<User> list = userDao.readAll();
        for (User user : list) {
            userProfile.put(user.getEmail(), user);
        }
    }

    @GetMapping("/getTweets")
    public ModelAndView fetchTweets(@RequestParam String email) {
        List<Tweet> tweetList = tweetDao.readByEmail(email);
        List<User> list = userDao.readByEmail(email);
        String password = "";
        String name = "";
        for (User user : list) {
            password = user.getPassword();
            name = user.getName();
        }
        System.out.println(tweetList);
        ModelAndView modelAndView = new ModelAndView("tweets");
        modelAndView.getModel().put("tweets", tweetList);
        modelAndView.getModel().put("email", email);
      //  modelAndView.getModel().put("password", password);
        modelAndView.getModel().put("name", name);
        return modelAndView;
    }

    @GetMapping("/displayUserDetails")
    public ModelAndView getUserDetails(@RequestParam String email) {
        ModelAndView modelAndView = new ModelAndView("users");
        if (userProfile.isEmpty()) allAccDetails();
        List<User> users = new ArrayList<>();
        for (Map.Entry entry : userProfile.entrySet()) {
            users.add((User) entry.getValue());
        }
        modelAndView.getModel().put("User", users);
        modelAndView.getModel().put("userEmail", email);
        return modelAndView;
    }

    @GetMapping("/loginForm")
    public ModelAndView loginForm() {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

    @PostMapping(value = "/tweetnew", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView addTweet(@RequestBody MultiValueMap<String, String> formData) {
        ModelAndView modelAndView = new ModelAndView("newtweet");
        String email = formData.get("email").get(0);
        String password = formData.get("password").get(0);
        String name = formData.get("name").get(0);
        String tweet = formData.get("tweet").get(0);
        if (email != "" && password != "" && tweet != "") {
            User user = userByEmailId(email);
            if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                Tweet tweet1 = new Tweet(tweet, Timestamp.from(Instant.now()), user, email);
                tweetDao.create(tweet1);
                modelAndView.getModel().put("email", email);
                modelAndView.getModel().put("password", password);
                modelAndView.getModel().put("name", name);
                modelAndView.getModel().put("tweet", tweet);
                return modelAndView;
            }
        }
        return modelAndView;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView login(@RequestBody MultiValueMap<String, String> formData) {
        if (!isUserValid(formData)) {
            System.out.println(formData);
            return errorMessageModelAndView("Wrong credentials");
        }
        ModelAndView modelAndView = new ModelAndView("profile");
        String email = formData.get("email").get(0);
        String name = userProfile.get(email).getName();
        modelAndView.getModel().put("name", name);
        modelAndView.getModel().put("email", email);
        return modelAndView;
    }

    private ModelAndView errorMessageModelAndView(String message) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.getModel().put("message", message);
        return modelAndView;
    }

    private boolean isUserValid(MultiValueMap<String, String> map) {
        String email = map.get("email").get(0);
        String password = map.get("password").get(0);
        User user = userProfile.get(email);
        return user.getPassword().equals(password);
    }

    @GetMapping("/fetchUsers")
    Map<String, User> allAccDetails() {
        List<Object[]> list = userDao.read();
        for (int i = 0; i < list.size(); i++) {
            Object[] arr = list.get(i);
            User user = new User(arr[0].toString(), arr[1].toString(), arr[2].toString());
            userProfile.put(arr[1].toString(), user);
        }
        return userProfile;
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

    private User userByEmailId(String email) {
        List<User> resultList = userDao.readByEmail(email);
        if (resultList.size() > 0) {
            User singleResult = resultList.get(0);
            return singleResult;
        }
        return null;
    }

    private List<Tweet> tweetByUserId(Integer id) {
        List<Tweet> resultList = tweetDao.readByUserId(id);
        return resultList;

    }

    @PostMapping(value = "/registerHtml", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView registerHtml() {
        ModelAndView modelAndView = new ModelAndView("registerPage");
        return modelAndView;
    }

    @PostMapping(value = "/registerNew", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView registerNew(@RequestBody MultiValueMap<String, String> formData) {
        ModelAndView modelAndView = new ModelAndView("newUsers");
        System.out.println(formData);
        String email = formData.get("email").get(0);
        String name = formData.get("name").get(0);
        String password = formData.get("password").get(0);

        if (userByEmailId(email) == null) {
            if (isValidEmail(email) && containsInvalidChars(name) && password.length() > 3) {
                User user = new User(name, email, password);
                userDao.create(user);
                modelAndView.getModel().put("email", email);
                modelAndView.getModel().put("name", name);
                modelAndView.getModel().put("password", password);
                return modelAndView;
            }
            return modelAndView;
        }
        return modelAndView;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> requestBodyMap) {
        String email = requestBodyMap.get("email");
        String name = requestBodyMap.get("name");
        String password = requestBodyMap.get("password");
        if (userByEmailId(email) == null) {
            if (isValidEmail(email) && containsInvalidChars(name) && password.length() > 3) {
                User user = new User(name, email, password);
                userDao.create(user);
                return new ResponseEntity<>("User add successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Invalid Input", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User already exist", HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/tweets")
    public ResponseEntity<?> getTweets(@RequestParam String email, @RequestParam String password) {
        if (email != "" && password != "") {
            User user = userByEmailId(email);

            if (user != null && email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                List<Tweet> tweets = tweetByUserId(user.getId());
                return new ResponseEntity<List<Tweet>>(tweets, HttpStatus.BAD_REQUEST);

            }
        }

        return new ResponseEntity<String>("user not match", HttpStatus.BAD_REQUEST);
    }

    /*
    @PutMapping("/update")
    private ResponseEntity<String> updateRecord(@RequestBody Map<String, String> requestBodyMap) {
        String email = requestBodyMap.get("email");
        String name = requestBodyMap.get("name");
        String password = requestBodyMap.get("password");
        if (!containsInvalidChars(name)) {
            return new ResponseEntity<>("name contains invalid characters",
                    HttpStatus.BAD_REQUEST);
        } else if (userProfile.containsKey(email) && userProfile.get(email).getPassword().equals(password)) {
            User user = userProfile.get(email);
            String currName = user.getName();
            if (currName.equals(name)) {
                return new ResponseEntity<>("No change rquired",
                        HttpStatus.OK);
            } else {
                user.setName(name);
                userProfile.put(email, user);
                return new ResponseEntity<>("update successful",
                        HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("User doesn't exist",
                HttpStatus.NOT_FOUND);

    }
*/


    @PostMapping("/tweet")
    public ResponseEntity<String> postTweet(@RequestBody Map<String, String> tweetBody) {
        String email = tweetBody.get("email");
        String password = tweetBody.get("password");
        String tweet = tweetBody.get("tweet");
        if (email != "" && password != "" && tweet != "") {
            User user = userByEmailId(email);
            if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                Tweet tweet1 = new Tweet(tweet, Timestamp.from(Instant.now()), user, email);
                tweetDao.create(tweet1);
                return new ResponseEntity<String>("Tweet is Posted successfully", HttpStatus.OK);
            }
            return new ResponseEntity<String>("UserId Or Password  is not matched", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("UserId Or Password  is not matched", HttpStatus.BAD_REQUEST);
    }
    /*
@GetMapping("/users")
private ResponseEntity<String> followUsers(@RequestBody Map<String,String> requestBody) {

}
*/


    @PostMapping("/followUser")
    private ResponseEntity<String> followUsers(@RequestParam Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String userEmail = requestBody.get("userEmail");
        ResponseEntity<String> responseEntity = null;
        if (!userProfile.containsKey(email)) {
            responseEntity = new ResponseEntity<>("User doesn't exist", HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        if (userEmail.equals(email)) {
            responseEntity = new ResponseEntity<>("You can't follow yourself", HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        if (userProfile.get(userEmail) != null) {
            if (following.containsKey(email)) {
                following.get(email).add(userEmail);
            } else {
                List<String> list = new ArrayList<>();
                list.add(userEmail);
                following.put(email, list);
            }
            Follower follower = new Follower();
            follower.setEmail(email);
            follower.setFollower(userEmail);
            followerDao.create(follower);
            responseEntity = new ResponseEntity<>("User " + userEmail + " followed successfully", HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>("User you are following doesn't exist", HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    //to view following of a user
    @GetMapping("/follow")
    public ModelAndView following(@RequestParam String email) {
        ModelAndView modelAndView = new ModelAndView("followUser");
        List<Follower> followerList = followerDao.readByEmail(email);
        modelAndView.getModel().put("follower", followerList);
        return modelAndView;
    }

    //    User can delete account  -->DELETE
/*
    @DeleteMapping("/delete")
    String deleteRecord(@RequestParam String email) {
        userProfile.remove(email);
        return email + " successfully deleted";
    }
*/

}