package com.PaymentApp.entities;

import java.math.BigDecimal;

public class Card {
    private Integer id;
    private String name;
    private String number;
    private BigDecimal balance;
    private Integer status;
    private Integer userId;

   public Card(String name, String number, BigDecimal balance, Integer userId){
        this.name = name;
        this.number = number;
        this.balance = balance;
        this.userId = userId;
    }
    public Card(Integer id, String name, String number, BigDecimal balance, Integer userId, Integer status){
       this.id = id;
        this.name = name;
        this.number = number;
        this.balance = balance;
        this.userId = userId;
        this.status = status;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", balance=" + balance +
                ", status=" + status +
                '}';
    }
}
