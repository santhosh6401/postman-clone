package com.eterio.postman.alt.model.collection.properties;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Url {
    private String raw;
    private String protocol;
    private List<String> host = new ArrayList<>();
    private List<String> path = new ArrayList<>();
    private List<Query> query = new ArrayList<>();
    private List<Variable> variable = new ArrayList<>();
}
