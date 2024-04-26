package com.eterio.postman.alt.model.profile.request;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String email;
    private String password;
    private String key;
}
