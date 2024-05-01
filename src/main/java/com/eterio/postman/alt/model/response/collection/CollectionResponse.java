package com.eterio.postman.alt.model.response.collection;

import com.eterio.postman.alt.model.common.CommonResponse;
import lombok.Data;

@Data
public class CollectionResponse extends CommonResponse {
    private String collectionId;
    private String name;
    private String projectId;
    private String creator;
    private String filepath;
    private String version;
}
