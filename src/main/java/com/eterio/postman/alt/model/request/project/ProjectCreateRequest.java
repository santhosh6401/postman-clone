package com.eterio.postman.alt.model.request.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreateRequest {
    private String name;
    private String projectPath;
    private String gitProjectId;
}
