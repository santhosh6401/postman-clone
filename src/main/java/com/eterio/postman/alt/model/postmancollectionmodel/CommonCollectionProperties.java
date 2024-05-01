package com.eterio.postman.alt.model.postmancollectionmodel;

import lombok.Data;

@Data
public class CommonCollectionProperties extends Object{
    private String key;
    private String value;
    private String type;
    private String description;
    private boolean disabled;
    private String warning;
    private String src;
}
