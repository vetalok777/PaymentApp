package com.PaymentApp.entities;

import java.time.LocalDateTime;

public class UnblockRequest {
    private Integer id;
    private String status;
    private LocalDateTime creationDate;
    private Integer consideredByAdmin;
    private Integer cardId;

    public UnblockRequest(LocalDateTime creationDate, Integer cardId) {
        this.creationDate = creationDate;
        this.cardId = cardId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreation_date(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getConsideredByAdmin() {
        return consideredByAdmin;
    }

    public void setConsideredByAdmin(Integer consideredByAdmin) {
        this.consideredByAdmin = consideredByAdmin;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }
}
