package com.eterio.postman.alt.model.response.profile;

import com.eterio.postman.alt.model.response.CommonResponse;
import lombok.Data;

import java.util.List;

@Data
public class GetProfileResponse extends CommonResponse {

    private List<GetProfile> profiles;
    private boolean hasNext;
    private boolean hasPrevious;
}
