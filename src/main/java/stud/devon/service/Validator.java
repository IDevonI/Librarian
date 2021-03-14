package stud.devon.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static Pattern pattern;
    private static Matcher matcher;
    private static final String namePattern="[A-Z][a-z]+";
    private static final String surnamePattern="[A-Z][a-z]+( [A-Z][a-z]+)*";
    private static final String emailPattern=".+@.+\\.(pl|com)";
    private static final String defaultPattern=".+";
    private static final String authorPattern="[A-Z][a-z]+ [A-Z][a-z]+( [A-Z][a-z]+)*";
    private static final String categoryPattern="\\w+.*\\w";
    private static final String bookIdPattern="[1-9][0-9]{3,5}";
    private static final String userIdPattern="[1-9][0-9]{3,4}";

    public Validator() {};

    public static boolean checkName(String s)
    {
        pattern=Pattern.compile(namePattern);
        matcher=pattern.matcher(s);
        return matcher.matches();
    }
    public static boolean checkSurname(String s)
    {
        pattern=Pattern.compile(surnamePattern);
        matcher=pattern.matcher(s);
        return matcher.matches();
    }
    public static boolean checkEmail(String s)
    {
        pattern=Pattern.compile(emailPattern);
        matcher=pattern.matcher(s);
        return matcher.matches();
    }
    public static boolean checkAddress(String s)
    {
        pattern=Pattern.compile(defaultPattern);
        matcher=pattern.matcher(s);
        return matcher.matches();
    }
    public static boolean checkPasswordEquality(String s1,String s2)
    {
        return s1.equals(s2);
    }
    public static boolean checkPasswordLength(String s1)
    {
        return s1.length()==8;
    }
    public static boolean checkTitle(String s)
    {
        pattern=Pattern.compile(defaultPattern);
        matcher=pattern.matcher(s);
        return matcher.matches();
    }
    public static boolean checkAuthor(String s)
    {
        pattern=Pattern.compile(authorPattern);
        matcher=pattern.matcher(s);
        return matcher.matches();
    }
    public static boolean checkPublisher(String s)
    {
        pattern=Pattern.compile(defaultPattern);
        matcher=pattern.matcher(s);
        return matcher.matches();
    }
    public static boolean checkCategory(String s)
    {
        pattern=Pattern.compile(categoryPattern);
        matcher=pattern.matcher(s);
        return matcher.matches();
    }

    public static boolean checkBookId(String s) {
        pattern=Pattern.compile(bookIdPattern);
        matcher=pattern.matcher(s);
        if(matcher.matches())
        {
            if(DataBase.callFunctionCheckBook(Integer.parseInt(s))==0)
                return false;
            else
                return true;
        }
        return false;
    }

    public static boolean checkWorkerId(String s) {
        pattern=Pattern.compile(userIdPattern);
        matcher=pattern.matcher(s);
        if(matcher.matches())
        {
            if(DataBase.callFunctionCheckWorker(Integer.parseInt(s))==0)
                return false;
            else
                return true;
        }
        return false;
    }

    public static boolean checkClientId(String s) {
        pattern=Pattern.compile(userIdPattern);
        matcher=pattern.matcher(s);
        if(matcher.matches())
        {
            if(DataBase.callFunctionCheckUser(Integer.parseInt(s))==0)
                return false;
            else
                return true;
        }
        return false;
    }
}
