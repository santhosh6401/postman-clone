package com.eterio.postman.alt.model.response.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectGet {
    private String id;
    private String name;
}
