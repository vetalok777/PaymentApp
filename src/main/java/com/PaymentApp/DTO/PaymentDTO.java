package com.PaymentApp.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentDTO {
    private Integer id;
    private BigDecimal amount;
    private LocalDateTime date;
    private String status;
    private String sender_card;
    private String receiver_card;

    public PaymentDTO(Integer id, BigDecimal amount, LocalDateTime date, String status, String sender, String receiver) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.sender_card = sender;
        this.receiver_card = receiver;

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

    public String getSender_card() {
        return sender_card;
    }

    public void setSender_card(String sender_card) {
        this.sender_card = sender_card;
    }

    public String getReceiver_card() {
        return receiver_card;
    }

    public void setReceiver_card(String receiver_card) {
        this.receiver_card = receiver_card;
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "amount=" + amount +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", sender_card='" + sender_card + '\'' +
                ", receiver_card='" + receiver_card + '\'' +
                '}';
    }
}
