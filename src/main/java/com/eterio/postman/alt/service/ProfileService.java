package com.eterio.postman.alt.service;

import com.eterio.postman.alt.model.request.profile.ProfileSignupRequest;

import com.eterio.postman.alt.model.response.profile.*;

import org.springframework.stereotype.Service;

@Service
public interface ProfileService {

    public ProfileSignupResponse profileSignup(String interactionId, ProfileSignupRequest request);
    public ProfileSigninResponse profileSignIn(String interactionId, String email, String password);
    public GetProfileResponse getProfile(String clientToken, String profileId, String email);

    ProfileSignupResponse updateProfile(String interactionId, String profileId ,ProfileSignupRequest request);
}
