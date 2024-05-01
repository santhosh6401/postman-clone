package com.eterio.postman.alt.model.response.profile;

import com.eterio.postman.alt.model.common.IdName;
import lombok.Data;

import java.util.List;

@Data
public class GetProfile {
    private String firstName;
    private String lastName;
    private String email;
}
