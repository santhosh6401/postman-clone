package com.eterio.postman.alt.controller;


import com.eterio.postman.alt.model.request.collection.CollectionRequest;
import com.eterio.postman.alt.model.response.CommonResponse;
import com.eterio.postman.alt.model.response.collection.CollectionResponse;
import com.eterio.postman.alt.service.CollectionService;
import com.eterio.postman.alt.utils.GenerateAndValidateToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.RepositoryFile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@Api(tags = "Collections Management", value = "Collections Management")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/collection")
public class CollectionController {

    private final CollectionService collectionService;

    private final GenerateAndValidateToken tokenUtils;



    @PostMapping
    public CollectionResponse createCollection(@RequestHeader String uniqueInteractionId,
                                               @RequestBody CollectionRequest request,
                                               @RequestHeader(name = "clientToken") String clientToken) throws Exception {
        log.info("interactionId :: [{}] request : {} create the collection ", uniqueInteractionId, request);

        if (Objects.isNull(clientToken) || !tokenUtils.validateToken(clientToken)) {
            throw new Exception("INVALID TOKEN ...:(");
        }

        return collectionService.createCollection(request, uniqueInteractionId, tokenUtils.decodeToken(clientToken).get("profileId").toString());
    }

    @PutMapping
    public CollectionResponse updateCollection(@RequestHeader String uniqueInteractionId,
                                               @RequestBody CollectionRequest request,
                                               @RequestParam(name = "collection-id") String collectionId,
                                               @RequestHeader(name = "clientToken") String clientToken) throws Exception {
        log.info("interactionId :: [{}] request : {} update the collection", uniqueInteractionId, request);

        if (Objects.isNull(clientToken) || !tokenUtils.validateToken(clientToken)) {
            throw new Exception("INVALID TOKEN ...:(");
        }

        return collectionService.updateCollection(request, collectionId, uniqueInteractionId);
    }

    @DeleteMapping
    public CommonResponse deleteCollection(@RequestHeader String uniqueInteractionId,
                                           @RequestParam String collectionId,
                                           @RequestHeader(name = "clientToken") String clientToken) throws Exception {
        log.info("interactionId :: [{}] content-name : {} delete the collection based on id", uniqueInteractionId, collectionId);

        if (Objects.isNull(clientToken) || !tokenUtils.validateToken(clientToken)) {
            throw new Exception("INVALID TOKEN ...:(");
        }

        return collectionService.deleteCollection(collectionId, uniqueInteractionId, tokenUtils.decodeToken(clientToken).get("profileId").toString());
    }

    @GetMapping
    public List<CollectionResponse> getCollection(
            @RequestHeader String uniqueInteractionId,
            @RequestParam(value = "project-id", name = "project-id") @ApiParam(name = "project-id") String projectId,
            @RequestParam(required = false, value = "collection-id", name = "collection-id") @ApiParam(name = "collection-id") String collectionId,
            @RequestParam(required = false, value = "version", name = "version") @ApiParam(name = "version") String version,
            @RequestParam(required = false) String name,
            @RequestHeader(name = "clientToken") String clientToken) throws Exception {

        if (Objects.isNull(clientToken) || !tokenUtils.validateToken(clientToken)) {
            throw new Exception("INVALID TOKEN ...:(");
        }

        return collectionService.getCollection(projectId, collectionId, version, name, uniqueInteractionId);
    }


    @PostMapping("/import")
    public ResponseEntity<Resource> collectionImport(@RequestHeader String uniqueInteractionId,
                                                     @RequestHeader(name = "clientToken") String clientToken,
                                                     @RequestParam(name = "project-id") String projectId,
                                                     @RequestParam(name = "collection-id") String collectionId,
                                                     @RequestParam(name = "version") String version) throws Exception {

        log.info("interactionId :: [{}] export JSON file ", uniqueInteractionId);


        if (Objects.isNull(clientToken) || !tokenUtils.validateToken(clientToken)) {
            throw new Exception("INVALID TOKEN ...:(");
        }

        try {

            RepositoryFile repositoryFile = collectionService.importCollection(projectId , collectionId , version ,tokenUtils.decodeToken(clientToken).get("profileId").toString() ,uniqueInteractionId);

            ByteArrayResource resource = new ByteArrayResource(repositoryFile.getContent().getBytes());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + repositoryFile.getFileName() + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_JSON).body(resource);


        } catch (Exception ex) {
            log.error("interactionId : [{}] :: errorMsg : {}", uniqueInteractionId, ex.getMessage());
            throw ex;
        }
    }

}

