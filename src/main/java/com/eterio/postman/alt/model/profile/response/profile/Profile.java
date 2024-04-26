package com.eterio.postman.alt.model.profile.response.profile;

import com.eterio.postman.alt.model.enums.ProfileType;
import lombok.Data;

@Data
public class Profile {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private ProfileType profileType;
}
