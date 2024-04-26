package com.eterio.postman.alt.model.common.workspace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusLifeCycle {
    private String status;
    private String createdBy;
    private String createdOn;
}
