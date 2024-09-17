package com.example.demo.generic;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.UUID;

@Data
public abstract class BaseEntity {

    @CreatedDate
    private long createdDate;

    @LastModifiedDate
    private long modifiedDate;

    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID modifiedBy;
}
