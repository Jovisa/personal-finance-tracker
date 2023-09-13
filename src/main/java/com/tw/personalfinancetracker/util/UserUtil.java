package com.tw.personalfinancetracker.util;

import java.util.List;

import static com.tw.personalfinancetracker.util.Constants.ROLE_ADMIN;

public class UserUtil {

    public static boolean isNotOwner(String ownerId, String userId) {
        return !userId.equals(ownerId);
    }

    public static boolean isAdmin(List<String> userAuthorities) {
        return hasAuthority(userAuthorities, ROLE_ADMIN);
    }

    private static boolean hasAuthority(List<String> userAuthorities, String authority) {
        return userAuthorities
                .stream()
                .anyMatch(authority::equals);
    }
}
