package com.eterio.postman.alt.model.request.collection;

import lombok.Data;

@Data
public class CollectionRequest {

    private String name;
    private String projectId;
    private String filepath;
    private String version;
}
