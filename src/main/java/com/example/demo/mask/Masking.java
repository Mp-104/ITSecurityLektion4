package com.example.demo.mask;

import io.micrometer.common.util.StringUtils;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Masking {

    public static String maskEmailAddress(final String email) {
        final String mask = "*****";
        final int at = email.indexOf("@");
        if (at > 2) {
            final int maskLen = Math.min(Math.max(at / 2, 2), 4);
            final int start = (at - maskLen) / 2;
            return email.substring(0, start) + mask.substring(0, maskLen) + email.substring(start + maskLen);
        }
        return email;
    }


    public static String mask(String email) {
        int atIndex = email.indexOf('@');
        String repeatedString = IntStream.range(0, atIndex - 2).mapToObj(i -> "*").collect(Collectors.joining());
        String maskedPart = email.substring(0, atIndex - repeatedString.length()) + repeatedString;
        String maskedEmail = maskedPart + email.substring(atIndex);

        return maskedEmail;
    }

    public static String maskEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) {
            // If the email is too short to mask properly
            return email;
        }

        String domain = email.substring(atIndex);
        String username = email.substring(0, atIndex);

        if (username.length() <= 2) {
            // If the username is too short to mask properly
            return email;
        }

        StringBuilder maskedUsername = new StringBuilder();
        maskedUsername.append(username.charAt(0)); // Keep the first character
        for (int i = 1; i < username.length() - 1; i++) {
            maskedUsername.append('*'); // Mask all characters except the first and last
        }
        maskedUsername.append(username.charAt(username.length() - 1)); // Keep the last character

        return maskedUsername.toString() + domain;
    }

//    public static String maskEmail(String fieldValue) {
//        if (StringUtils.isBlank(fieldValue)) {
//            return "";
//        }
//        int pos = fieldValue.indexOf('@');
//        if (pos == -1) {
//            return "".repeat(fieldValue.length());
//        }
//        int chars = Math.min(pos-1, 3);
//        String pattstr = "(^[^@]{%d}|(?!^)\\G)[^@]".formatted(chars);
//        return fieldValue.replaceAll(pattstr, "$1");
//    }


}
