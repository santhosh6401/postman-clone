package com.eterio.postman.alt.model.response.project;

import com.eterio.postman.alt.model.response.CommonResponse;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreateResponse extends CommonResponse {
    private String projectId;
    private String name;
}
