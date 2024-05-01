package com.eterio.postman.alt.model.response.profile;

import com.eterio.postman.alt.model.response.CommonResponse;
import lombok.Data;

@Data
public class ProfileSigninResponse extends CommonResponse {

    private String clientToken;
}
