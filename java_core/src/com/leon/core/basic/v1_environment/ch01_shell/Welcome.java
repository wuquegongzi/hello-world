package com.leon.core.basic.v1_environment.ch01_shell;

public class Welcome {

    public static void main(String[] args) {
        String[] greeting = new String[3];
         greeting[0] = "Welcome to leon Core Java!";
         greeting[1] = "by Leon chen";
         greeting[2] = "bye bye!";

        for (int i = 0; i < greeting.length; i++) {
            System.out.println(greeting[i]);
        }
    }
}
