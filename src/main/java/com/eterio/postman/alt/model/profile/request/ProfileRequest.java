package com.eterio.postman.alt.model.profile.request;

import com.eterio.postman.alt.model.enums.ProfileType;
import lombok.Data;

@Data
public class ProfileRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String password;
    private ProfileType profileType;
}
