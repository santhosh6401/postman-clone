package com.eterio.postman.alt.model.entity;


import com.eterio.postman.alt.model.common.Audit;
import com.eterio.postman.alt.model.common.IdName;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "profile")
public class ProfileEntity {
    @Id
    private String profileId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<IdName> workspaces = new ArrayList<>();
    private Audit audit;
}
