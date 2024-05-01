package com.eterio.postman.alt.model.response.project;

import com.eterio.postman.alt.model.response.CommonResponse;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectGetResponse extends CommonResponse {
    private List<ProjectGet> projects;
}
