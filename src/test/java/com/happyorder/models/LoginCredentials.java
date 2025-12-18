package com.happyorder.models;

/**
 * Data model representing login credentials for test cases
 * This POJO encapsulates all login-related test data
 *
 * @author HappyOrder QA Team
 * @since 1.0
 */
public class LoginCredentials {

    private String title;
    private String step;
    private String username;
    private String password;
    private String expectedMessage;
    private String result;

    // Default constructor
    public LoginCredentials() {
    }

    // Constructor with all fields
    public LoginCredentials(String title, String step, String username,
                           String password, String expectedMessage, String result) {
        this.title = title;
        this.step = step;
        this.username = username;
        this.password = password;
        this.expectedMessage = expectedMessage;
        this.result = result;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getStep() {
        return step;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getExpectedMessage() {
        return expectedMessage;
    }

    public String getResult() {
        return result;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setExpectedMessage(String expectedMessage) {
        this.expectedMessage = expectedMessage;
    }

    public void setResult(String result) {
        this.result = result;
    }

    // Builder Pattern for flexible object creation
    public static class Builder {
        private String title;
        private String step;
        private String username;
        private String password;
        private String expectedMessage;
        private String result;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder step(String step) {
            this.step = step;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder expectedMessage(String expectedMessage) {
            this.expectedMessage = expectedMessage;
            return this;
        }

        public Builder result(String result) {
            this.result = result;
            return this;
        }

        public LoginCredentials build() {
            return new LoginCredentials(title, step, username, password,
                                       expectedMessage, result);
        }
    }

    @Override
    public String toString() {
        return "LoginCredentials{" +
                "title='" + title + '\'' +
                ", username='" + username + '\'' +
                ", expectedMessage='" + expectedMessage + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
