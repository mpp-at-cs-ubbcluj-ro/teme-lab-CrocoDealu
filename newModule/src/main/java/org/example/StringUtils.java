package org.example;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import java.util.List;

public class StringUtils {

    public static String padString(String input, int length, char padChar) {
        return Strings.padEnd(input, length, padChar);
    }

    public static List<String> toUpperCaseList(List<String> words) {
        return ImmutableList.copyOf(words.stream()
                .map(String::toUpperCase)
                .toList());
    }
}