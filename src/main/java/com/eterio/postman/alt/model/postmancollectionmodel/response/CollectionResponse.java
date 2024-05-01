package com.eterio.postman.alt.model.postmancollectionmodel.response;

import com.eterio.postman.alt.model.postmancollectionmodel.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollectionResponse {
    private Collection collection;
    private String response;
    private String description;

}
