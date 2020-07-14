package com.green.springbootjwt.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity implements Serializable {

    protected LocalDateTime dateCreated;
    protected LocalDateTime lastUpdated;
    protected Integer createdBy;
    protected Integer updatedBy;

    @Transient
    protected Integer action;

    @PrePersist
    public void prePersist() {
        dateCreated = LocalDateTime.now();
        lastUpdated = LocalDateTime.now();
        createdBy = getAction();
        updatedBy = getAction();
    }

    @PreUpdate
    public void preUpdates() {
        lastUpdated = LocalDateTime.now();
        updatedBy = getAction();
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void preUpdate() {
        lastUpdated = LocalDateTime.now();
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
