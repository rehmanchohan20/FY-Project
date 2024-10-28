package com.sarfaraz.elearning.model;

import com.sarfaraz.elearning.constants.UserCreatedBy;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.sql.Timestamp;
//By default, properties of the superclass are ignored and not persistent!
// You have to annotate the superclass with @MappedSuperclass to enable embedding of its properties in the concrete
// subclass tables.
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CommonEntity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@CreatedDate
    @Column(name = "created_at")
    private Timestamp createdAt;
    @LastModifiedDate
    @Column(name="update_at")
    private Timestamp updatedAt;

    @CreatedBy
    @Enumerated(EnumType.STRING)
    @Column(name = "created_by", nullable = false)
    private UserCreatedBy createdBy;

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserCreatedBy getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserCreatedBy createdBy) {
        this.createdBy = createdBy;
    }
}
