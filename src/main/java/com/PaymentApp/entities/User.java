package com.PaymentApp.entities;

public class User {

    private Integer id;
    private String login;
    private String password;
    private String firstName;
    private Integer status;


    public User() {
    }

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

    public User(Integer id, String email, String password, String firstName, Integer status) {
        this.id = id;
        this.login = email;
        this.password = password;
        this.firstName = firstName;
        this.status = status;
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
