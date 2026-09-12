package com.edusphere.ui;

public class SessionManager {
    public static boolean isLoggedIn = false;
    public static String currentUsername = "";
    public static String currentUserRole = "";
    public static String pendingRedirectPage = "";

    public static void clearSession() {
        isLoggedIn = false;
        currentUsername = "";
        currentUserRole = "";
        pendingRedirectPage = "";
    }
}
