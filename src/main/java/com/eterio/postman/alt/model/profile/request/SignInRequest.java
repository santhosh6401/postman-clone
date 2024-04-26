package com.eterio.postman.alt.model.profile.request;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
