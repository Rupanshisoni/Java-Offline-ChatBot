package org.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageParser {
    public static void main(String[] args) {
        String message = "Please tell me the weather in Lucknow.";
        String location = parseLocation(message);
        String response;
        if (location != null) {
            response = "The current weather in " + location + " is sunny.";
        } else {
            response = "I'm sorry, I don't understand what you're asking.";
        }
        System.out.println(response);
    }
    private static String parseLocation(String message) {
        String regex = "weather in (\\w+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
}
