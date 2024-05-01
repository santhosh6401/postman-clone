package com.eterio.postman.alt.model.postmancollectionmodel.properties;

import lombok.Data;

@Data
public class ProtocolProfileBehavior {
    private boolean followRedirects;
    private boolean disableBodyPruning;
    private boolean disableUrlEncoding;
}
