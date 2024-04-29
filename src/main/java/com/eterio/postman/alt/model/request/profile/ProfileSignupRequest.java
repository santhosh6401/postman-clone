package com.eterio.postman.alt.model.request.profile;

import lombok.Data;

@Data
public class ProfileSignupRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
