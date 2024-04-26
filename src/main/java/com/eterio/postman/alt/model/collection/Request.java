package com.eterio.postman.alt.model.collection;

import lombok.Data;

import java.util.List;

@Data
public class Request {
    private String method;
    private List<Header> header;
    private Body body;
    private Url url;
}
