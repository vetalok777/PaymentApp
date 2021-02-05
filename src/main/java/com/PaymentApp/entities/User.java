package com.PaymentApp.entities;

public class User {

    private Integer id;
    private String login;
    private String password;
    private String firstName;
    private Integer status;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

public User(){}

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }


    public User(String login) {
        this.login = login;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '}';
    }
}
