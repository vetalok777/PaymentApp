package com.PaymentApp.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Payment {

    private Integer id;
    private BigDecimal amount;
    private LocalDateTime date;
    private String status;
    private Integer sender_id;
    private Integer receiver_id;

    public Payment(BigDecimal amount, LocalDateTime date, Integer sender_id, Integer receiver_id) {
        this.amount = amount;
        this.date = date;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
    }

    public Payment(Integer id, BigDecimal amount, LocalDateTime date, String status, Integer sender, Integer receiver) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.sender_id = sender;
        this.receiver_id = receiver;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSender_id() {
        return sender_id;
    }

    public void setSender_id(Integer sender_id) {
        this.sender_id = sender_id;
    }

    public Integer getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(Integer receiver_id) {
        this.receiver_id = receiver_id;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "amount=" + amount +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", sender_id=" + sender_id +
                ", receiver_id=" + receiver_id +
                '}';
    }
}
