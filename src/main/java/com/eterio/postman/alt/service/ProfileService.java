package com.eterio.postman.alt.service;

import com.eterio.postman.alt.model.request.profile.ApproveWorkspaceRequest;
import com.eterio.postman.alt.model.request.profile.ProfileSignupRequest;

import com.eterio.postman.alt.model.response.profile.*;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ProfileService {

    public ProfileSignupResponse profileSignup(String interactionId, ProfileSignupRequest request);
    public ProfileSigninResponse profileSignin(String email, String password);
    public GetProfileResponse getProfile(String clientToken, String email, int page, int size);
    public ProfileDashboardResponse dashboard(String clientToken, int page, int size);
    public ApproveWorkspaceResponse approveWorkspace(ApproveWorkspaceRequest request);

}
