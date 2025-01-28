package com.app.timetable.Helpers;

public class StringConverter {

    public static String toCamelCase(String input) {
        StringBuilder output = new StringBuilder();
        String[] inputs=input.split(" ");
        for (String word : inputs) {
            if(!word.trim().isEmpty()) {
                output.append(Character.toUpperCase(word.charAt(0)));
                for (int j = 1; j < word.length(); j++) {
                    output.append(Character.toLowerCase(word.charAt(j)));
                }
                output.append(" ");
            }
        }
        return output.toString().trim();

    }

    public String toUpperCase(String input) {
        return input.toUpperCase();
    }
}
