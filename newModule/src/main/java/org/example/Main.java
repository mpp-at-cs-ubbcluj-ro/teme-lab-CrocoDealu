package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        StringUtils s = new StringUtils();
        List<String> l = StringUtils.toUpperCaseList(List.of("123", "abc"));
        l.forEach(System.out::println);
        System.out.println("Hello, World!");
    }
}