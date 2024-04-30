package com.eterio.postman.alt.controller;

import com.eterio.postman.alt.model.request.profile.ApproveWorkspaceRequest;
import com.eterio.postman.alt.model.request.profile.ProfileSignupRequest;
import com.eterio.postman.alt.model.response.profile.*;
import com.eterio.postman.alt.service.ProfileService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Profile Management", value = "Profile Management")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping("/signup")
    public ProfileSignupResponse profileSignup(@RequestHeader String interactionId,
                                               @RequestBody ProfileSignupRequest request) {
        ProfileSignupResponse response = profileService.profileSignup(interactionId, request);
        return response;

    }

    @PostMapping("/signin")
    public ProfileSigninResponse profileSignin(@RequestHeader String interactionId,
                                               @RequestHeader String email,
                                               @RequestHeader String password) {
        ProfileSigninResponse response = profileService.profileSignin(email, password);
        return response;
    }

    @GetMapping("/get-profile")
    public GetProfileResponse getProfile(@RequestHeader String interactionId,
                                         @RequestHeader String clientToken,
                                         @RequestHeader String email,
                                         @RequestHeader int page,
                                         @RequestHeader int size) {
        GetProfileResponse response = profileService.getProfile(clientToken, email, page, size);
        return response;
    }

    @PostMapping("/dashboard")
    public ProfileDashboardResponse dashboard(@RequestHeader String interactionId,
                                              @RequestHeader String clientToken,
                                              @RequestHeader int page,
                                              @RequestHeader int size) {
        ProfileDashboardResponse response = profileService.dashboard(clientToken, page, size);
        return response;
    }

    @PostMapping("/approve")
    public ApproveWorkspaceResponse approveWorkspace(@RequestHeader String interactionId,
                                                     @RequestBody ApproveWorkspaceRequest request) {
        ApproveWorkspaceResponse response = profileService.approveWorkspace(request);
        return response;
    }
}
