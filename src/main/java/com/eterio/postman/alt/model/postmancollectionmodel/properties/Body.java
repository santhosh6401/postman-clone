package com.eterio.postman.alt.model.postmancollectionmodel.properties;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Body {
    private String mode;
    private String raw;
    private Option options;
    private List<FormData> formdata = new ArrayList<>();

    private List<UrlEncoded> urlencoded= new ArrayList<>();
}
