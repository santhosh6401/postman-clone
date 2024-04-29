package com.eterio.postman.alt.service.implementation;

import com.eterio.postman.alt.model.common.IdName;
import com.eterio.postman.alt.model.entity.ProfileEntity;
import com.eterio.postman.alt.model.entity.WorkspaceEntity;
import com.eterio.postman.alt.model.request.profile.ApproveWorkspaceRequest;
import com.eterio.postman.alt.model.request.profile.ProfileSignupRequest;
import com.eterio.postman.alt.model.response.profile.*;
import com.eterio.postman.alt.repository.ProfileRepository;
import com.eterio.postman.alt.repository.WorkspaceRepository;
import com.eterio.postman.alt.service.ProfileService;
import com.eterio.postman.alt.utils.HelperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.eterio.postman.alt.constant.AppConstant.FAILED;
import static com.eterio.postman.alt.constant.AppConstant.SUCCESS;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final HelperUtils helperUtils;
    private final ProfileRepository profileRepository;
    private final WorkspaceRepository workspaceRepository;

    @Override
    public ProfileSignupResponse profileSignup(String interactionId, ProfileSignupRequest request) {

        ProfileEntity entity = new ProfileEntity();
        ProfileSignupResponse response = new ProfileSignupResponse();

        entity.setProfileId(helperUtils.generateId("PR"));
        entity.setEmail(request.getEmail());
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setPassword(request.getPassword());
        entity.setAudit(helperUtils.createAudit(interactionId));
        profileRepository.save(entity);

        BeanUtils.copyProperties(entity, response);
        //need to prepare clientToken
        response.setClientToken("");
        //need to call the create workspace
        return response;
    }

    @Override
    public ProfileSigninResponse profileSignin(String email, String password) {
        Optional<ProfileEntity> entity = profileRepository.findByEmailAndPassword(email, password);
        ProfileSigninResponse response = new ProfileSigninResponse();
        if (entity.isPresent()) {
            //need to prepare clent token
            response.setClientToken("");
            response.setResponse(SUCCESS);
            response.setDescription("signin successfully");
        } else {
            response.setResponse(FAILED);
            response.setDescription("profile not found");
        }
        return response;
    }

    @Override
    public GetProfileResponse getProfile(String clientToken, String email, int page, int size) {
        GetProfileResponse response = new GetProfileResponse();
        List<GetProfile> getProfileList = new ArrayList<>();
        GetProfile getProfile = new GetProfile();
        if (ObjectUtils.isEmpty(email) && !ObjectUtils.isEmpty(clientToken)) {
            //TODO need to extract client token
            Optional<ProfileEntity> entity = profileRepository.findByProfileId(""); //TODO need to pass profile id extracted from client token
            if (entity.isPresent()) {
                getProfile.setEmail(entity.get().getEmail());
                getProfile.setFirstName(entity.get().getFirstName());
                getProfile.setLastName(entity.get().getLastName());
//            getProfile.setWorkspaces("");   TODO need to integrate for this field
                getProfileList.add(getProfile);
                response.setProfiles(getProfileList);
                response.setResponse(SUCCESS);
                response.setDescription("profile retrieved successfully");
            } else {
                response.setResponse(FAILED);
                response.setDescription("profile not found");
            }
        } else if ((!ObjectUtils.isEmpty(email) && ObjectUtils.isEmpty(clientToken)) ||
                (!ObjectUtils.isEmpty(email) && !ObjectUtils.isEmpty(clientToken))) {
            Optional<ProfileEntity> entity = profileRepository.findByEmail(email);
            if (entity.isPresent()) {
                getProfile.setEmail(entity.get().getEmail());
                getProfile.setFirstName(entity.get().getFirstName());
                getProfile.setLastName(entity.get().getLastName());
//            getProfile.setWorkspaces("");   TODO need to integrate for this field
                getProfileList.add(getProfile);
                response.setProfiles(getProfileList);
                response.setResponse(SUCCESS);
                response.setDescription("profile retrieved successfully");
            } else {
                response.setResponse(FAILED);
                response.setDescription("profile not found");
            }
        } else {
            List<ProfileEntity> profileEntityList = profileRepository.findAll();
            if (!ObjectUtils.isEmpty(profileEntityList)) {
                for (ProfileEntity entity : profileEntityList) {
                    getProfile.setEmail(entity.getEmail());
                    getProfile.setFirstName(entity.getFirstName());
                    getProfile.setLastName(entity.getLastName());
//            getProfile.setWorkspaces("");   TODO need to integrate for this field
                    getProfileList.add(getProfile);
                }
                int fromIndex = (page - 1) * size;
                int toIndex = Math.min(fromIndex + size, getProfileList.size());
                boolean hasNextPage = ((page + 1) * size) < getProfileList.size();
                boolean hasPreviousPage = page > 0;
                response.setProfiles(getProfileList.subList(fromIndex, toIndex));
                response.setHasNext(hasNextPage);
                response.setHasPrevious(hasPreviousPage);
                response.setResponse(SUCCESS);
                response.setDescription("profile retrieved successfully");
            } else {
                response.setResponse(FAILED);
                response.setDescription("profile not found ");
            }
        }
        return response;
    }

    @Override
    public ProfileDashboardResponse dashboard(String clientToken, int page, int size) {

        //TODO need to extract client token to get profileId
        ProfileDashboardResponse response = new ProfileDashboardResponse();
        Pageable pageable = PageRequest.of(page, size);
        Optional<ProfileEntity> entity = profileRepository.findByProfileId("");
        if (entity.isPresent()) {
            response.setFirstName(entity.get().getFirstName());
            response.setLastName(entity.get().getLastName());
            response.setEmail(entity.get().getEmail());
            int fromIndex = (page - 1) * size;
            int toIndex = Math.min(fromIndex + size, entity.get().getWorkspaces().size());
            boolean hasNextPage = ((page + 1) * size) < entity.get().getWorkspaces().size();
            boolean hasPreviousPage = page > 0;
            response.setWorkspaces(entity.get().getWorkspaces().subList(fromIndex, toIndex));
            response.setHasNext(hasNextPage);
            response.setHasPrevious(hasPreviousPage);
            response.setResponse(SUCCESS);
            response.setDescription("workspace retrieved successfully");

        } else {
            response.setResponse(FAILED);
            response.setDescription("profile not found");
        }
        return response;
    }

    @Override
    public ApproveWorkspaceResponse approveWorkspace(ApproveWorkspaceRequest request) {
        ApproveWorkspaceResponse response = new ApproveWorkspaceResponse();
        //TODO need to extract client token
        Optional<ProfileEntity> optionalProfile = profileRepository.findByProfileId("");
        Optional<WorkspaceEntity> optionalWorkspace = workspaceRepository.findByWorkspaceId(request.getWorkspaceId());

        if (optionalProfile.isPresent() && optionalWorkspace.isPresent()) {
            if (optionalProfile.get().getProfileId().equalsIgnoreCase(optionalWorkspace.get().getCreator())) {
                IdName idName = new IdName();
                idName.setId(request.getApproverId());
                idName.setName(optionalWorkspace.get().getName());
                optionalProfile.get().getWorkspaces().add(idName);
                profileRepository.save(optionalProfile.get());
                response.setResponse(SUCCESS);
                response.setDescription("workspace added to the profile");
            } else {
                response.setResponse(FAILED);
                response.setDescription("creator profile not match");
            }
        } else {
            response.setResponse(FAILED);
            response.setDescription("profile not found");
        }

        return response;
    }
}
