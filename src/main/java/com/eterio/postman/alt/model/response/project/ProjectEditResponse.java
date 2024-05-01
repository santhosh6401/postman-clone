package com.eterio.postman.alt.model.response.project;

import com.eterio.postman.alt.model.response.CommonResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEditResponse extends CommonResponse {
    private String projectId;
    private String name;
}
