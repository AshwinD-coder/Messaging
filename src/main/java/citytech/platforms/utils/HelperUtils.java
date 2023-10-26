package citytech.platforms.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperUtils {
    private HelperUtils(){}
    public static String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890@#";
        return RandomStringUtils.random(8, characters);
    }
    public static boolean matchEmailPattern(String email){
        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9-.]*@gmail[.]com");
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    }
}
