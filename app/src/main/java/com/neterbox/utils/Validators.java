package com.neterbox.utils;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dipali.
 */
public class Validators {

    /*
    * Check Password Policy
     */

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "(?i)^(?=.*[a-z])(?=.*\\d).{8,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


    /**
     * Check Mail Is Valid Or Not
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email)
    {
        // TODO Auto-generated method stub

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            return true;
        }
        return false;
    }

    /**
     * Check Phone Is Valid Or Not
     *
     * @param phone
     * @return
     */
    public static boolean isValidPhone(String phone)
    {
        // TODO Auto-generated method stub

        if (Patterns.PHONE.matcher(phone).matches())
        {
            return true;
        }

        return false;
    }

    /**
     * Check data is empty or not
     *
     * @param data
     * @return
     */
    public static boolean isEmpty(String data)
    {
        // TODO Auto-generated method stub

        if (data == null)
        {

            return true;
        }

        if (data.trim().equals(""))
        {
            return true;
        }

        return false;
    }

    /**
     * Check length
     *
     * @param data
     * @return
     */
    public static boolean isLengthMatch(String data, int min_length, int max_length) {
        // TODO Auto-generated method stub

        if (data.length() >= min_length && data.length() <= max_length) {

            return true;
        }

        return false;
    }

    /**
     * Check length
     *
     * @param data
     * @return
     */
    public static boolean isLengthMatch(String data, int required_length)
    {
        // TODO Auto-generated method stub

        if (data.length()>=required_length)
        {

            return true;
        }

        return false;
    }

    /**
     * Check Only Digits
     *
     * @param data
     * @return
     */
    public static boolean checkOnlyDigits(String data)
    {

        if (data.matches("[0-9]+"))
        {
            return true;
        }

        return false;
    }

    /**
     * Check Only Alphabates
     *
     * @param data
     * @return
     */
    public static boolean checkOnlyAlphabets(String data) {

        if (data.matches("[A-Za-z]+")) {

            return true;

        }

        return false;
    }

    /*Address*/

    public static boolean checkAddress(String data){
        if(data.matches("[A-Za-z 0-9\\\\s,\\\\.,\\-]+")){
            return true;
        }
        return false;
    }

    public static boolean mobileNumberCheck(String number){
        String MobilePattern = "[0-9]{10}";
        if(number.matches(MobilePattern)){
            return true;
        }
        return false;
    }
    /**
     * Validation for Password match or not
     *
     * @param password ,confirmPassword
     * @return
     */
    public static boolean isPasswordMatching(String password, String confirmPassword) {

        Pattern pattern = Pattern.compile(password, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(confirmPassword);

        if (matcher.matches())
        {
            return true;
        }

        return false;
    }
}
