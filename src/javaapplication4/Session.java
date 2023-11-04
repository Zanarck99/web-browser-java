package javaapplication4;

import java.net.URL;
import java.util.ArrayDeque;

/**
 * Representa uma sessão de navegação.
 * Guarda dados que serão utilizados durante a navegação.
 */
public class Session {
    // Atributos
    private static URL currentPageURL;
    private static ArrayDeque<URL> backStack = new ArrayDeque<>();
    private static ArrayDeque<URL> forwardStack = new ArrayDeque<>();
    private static String currentSourceCode;
    private static StringBuilder responseRecord = new StringBuilder();

    // Getters E Setters
    public static URL getCurrentPageURL() {
        return Session.currentPageURL;
    }

    public static void setCurrentPageURL(URL currentPageURL) {
        Session.currentPageURL = currentPageURL;
    }

    public static ArrayDeque<URL> getBackStack() {
        return Session.backStack;
    }

    public static void setBackStack(ArrayDeque<URL> backStack) {
        Session.backStack = backStack;
    }

    public static ArrayDeque<URL> getForwardStack() {
        return Session.forwardStack;
    }

    public static void setForwardStack(ArrayDeque<URL> forwardStack) {
        Session.forwardStack = forwardStack;
    }

    public static String getCurrentSourceCode() {
        return Session.currentSourceCode;
    }

    public static void setCurrentSourceCode(String currentSourceCode) {
        Session.currentSourceCode = currentSourceCode;
    }

    public static StringBuilder getResponseRecord() {
        return Session.responseRecord;
    }

    public static void setResponseRecord(StringBuilder responseRecord) {
        Session.responseRecord = responseRecord;
    }
}
