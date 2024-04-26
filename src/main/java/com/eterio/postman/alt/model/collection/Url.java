package com.eterio.postman.alt.model.collection;

import lombok.Data;

import java.util.List;

@Data
public class Url {
    private String raw;
    private List<String> host;
    private List<String> path;
    private List<Query> query;
}
