package com.eterio.postman.alt.model.collection.properties;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Request {
    private String method;
    private List<Header> header = new ArrayList<>();
    private Body body;
    private Url url;
    private String description;
}
