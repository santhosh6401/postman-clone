package com.eterio.postman.alt.model.response.profile;

import com.eterio.postman.alt.model.response.CommonResponse;
import lombok.Data;

@Data
public class ProfileSignupResponse extends CommonResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String clientToken;
}
