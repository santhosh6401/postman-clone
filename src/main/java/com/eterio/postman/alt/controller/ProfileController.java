package com.eterio.postman.alt.controller;

import com.eterio.postman.alt.model.request.profile.ProfileSignupRequest;
import com.eterio.postman.alt.model.response.profile.GetProfileResponse;
import com.eterio.postman.alt.model.response.profile.ProfileSigninResponse;
import com.eterio.postman.alt.model.response.profile.ProfileSignupResponse;
import com.eterio.postman.alt.service.ProfileService;
import com.eterio.postman.alt.utils.GenerateAndValidateToken;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static javax.management.remote.JMXConnectionNotification.FAILED;

@Api(tags = "Profile Management", value = "Profile Management")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    private final GenerateAndValidateToken tokenUtils;

    @PostMapping("/signup")
    public ProfileSignupResponse profileSignup(@RequestHeader String interactionId,
                                               @RequestBody ProfileSignupRequest request) {
        return profileService.profileSignup(interactionId, request);

    }

    @PostMapping("/sign-in")
    public ProfileSigninResponse profileSignIn(@RequestHeader String interactionId,
                                               @RequestHeader String email,
                                               @RequestHeader String password) {
        return profileService.profileSignIn(interactionId, email, password);
    }

    @GetMapping("/get-profile")
    public GetProfileResponse getProfile(@RequestHeader String interactionId,
                                         @RequestHeader String clientToken,
                                         @RequestParam(required = false) String email) {

        GetProfileResponse response = new GetProfileResponse();

        if (Objects.isNull(clientToken) || !tokenUtils.validateToken(clientToken)) {
            response.setResponse(FAILED);
            response.setDescription("token invalid ... :(");
            return response;
        }

        response = profileService.getProfile(interactionId, tokenUtils.decodeToken(clientToken).get("profileId").toString(), email);

        return response;
    }


    @PutMapping("/update")
    public ProfileSignupResponse profileEdit(@RequestHeader String interactionId,
                                             @RequestBody ProfileSignupRequest request,
                                             @RequestHeader String clientToken) {

        ProfileSignupResponse response = new ProfileSignupResponse();

        if (Objects.isNull(clientToken) || !tokenUtils.validateToken(clientToken)) {
            response.setResponse(FAILED);
            response.setDescription("token invalid ... :(");
        }

        response = profileService.updateProfile(interactionId, tokenUtils.decodeToken(clientToken).get("profileId").toString(), request);

        return response;

    }

}
