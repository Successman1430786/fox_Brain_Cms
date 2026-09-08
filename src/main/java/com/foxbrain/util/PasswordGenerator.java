package com.foxbrain.util;

public class PasswordGenerator {

    public static void main(String[] args) {

        String password = "Admin@123";

        String hash = PasswordHashUtil.hashPassword(password);

        System.out.println("Password: " + password);
        System.out.println("Password Hash:");
        System.out.println(hash);
    }
}