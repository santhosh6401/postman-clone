package com.eterio.postman.alt.model.collection.properties;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Script {
    private String type;
    private List<String> exec = new ArrayList<>();
}
