package com.tw.personalfinancetracker.util;

public class UserUtil {
    public static boolean isNotOwner(String ownerId, String userId) {
        return !userId.equals(ownerId);
    }
}
