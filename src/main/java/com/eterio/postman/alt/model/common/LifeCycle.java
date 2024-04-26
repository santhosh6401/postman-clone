package com.eterio.postman.alt.model.common;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LifeCycle {
    private String statusDescription;
    private LocalDateTime createdOn;
}
