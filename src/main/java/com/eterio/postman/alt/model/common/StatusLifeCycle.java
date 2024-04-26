package com.eterio.postman.alt.model.common;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StatusLifeCycle {
    private String status;
    private String createdBy;
    private LocalDateTime createdOn;
}
