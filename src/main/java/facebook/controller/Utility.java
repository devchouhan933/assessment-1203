package facebook.controller;

import facebook.dao.UserDao;
import facebook.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.regex.Pattern;

public class Utility {
    @Autowired
    static UserDao userDao;

    public static boolean containsInvalidChars(String name) {
        return ((name != null) && (!name.equals("")) && (name.matches("^[a-zA-Z]*$")));
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&-]+(?:\\." + "[a-zA-Z0-9_+&-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) return false;

        return pat.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";

        Pattern pattern = Pattern.compile(regex);
        return password != null;/*pattern.matcher(password).matches()*/

    }

    public static ModelAndView errorMessageModelAndView(String message) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.getModel().put("message", message);
        return modelAndView;
    }

    public static boolean isUserValid(MultiValueMap<String, String> map) {
        String email = map.get("email").get(0);
        String password = map.get("password").get(0);
        System.out.println(password);
        User user = userDao.readByEmail(email);

        System.out.println(user);
        return password.equals(user.getPassword());
    }


}
