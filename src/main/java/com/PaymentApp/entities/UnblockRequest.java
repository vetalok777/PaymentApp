package com.PaymentApp.entities;

import java.util.Date;

public class UnblockRequest {
    private Integer id;
    private String status;
    private Date creationDate;
    private Integer consideredByAdmin;


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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreation_date(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getConsideredByAdmin() {
        return consideredByAdmin;
    }

    public void setConsideredByAdmin(Integer consideredByAdmin) {
        this.consideredByAdmin = consideredByAdmin;
    }
}
