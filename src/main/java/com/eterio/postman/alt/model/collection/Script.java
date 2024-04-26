package com.eterio.postman.alt.model.collection;

import lombok.Data;

import java.util.List;

@Data
public class Script {
    private String type;
    private List<String> exec;
}
