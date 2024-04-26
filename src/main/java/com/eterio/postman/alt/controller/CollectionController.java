package com.eterio.postman.alt.controller;


import com.eterio.postman.alt.model.collection.Collection;
import com.eterio.postman.alt.model.collection.response.CollectionResponse;
import com.eterio.postman.alt.model.common.CommonResponse;
import com.eterio.postman.alt.service.CollectionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;


@Api(tags = "Collections API", value = "Collections API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/collection")
public class CollectionController {             // TODO  client Token validation ...

    private final CollectionService collectionService;

    @PostMapping("/import")
    public CollectionResponse collectionImport(@RequestHeader String uniqueInteractionId,
                                               @RequestHeader(name = "clientToken") String clientToken,
                                               @RequestParam(name = "workspace-id") String workspaceId,
                                               @RequestPart("file") MultipartFile file) {

        log.info("interactionId :: [{}] import JSON file", uniqueInteractionId);

        return collectionService.importCollection(uniqueInteractionId, workspaceId, file);
    }

    @PostMapping("/export")
    public ResponseEntity<Resource> collectionExport(@RequestHeader String uniqueInteractionId,
                                                     @RequestHeader(name = "clientToken") String clientToken,
                                                     @RequestParam(name = "workspace-id") String workspaceId,
                                                     @RequestParam(name = "collection-id") String collectionId) throws JsonProcessingException {

        log.info("interactionId :: [{}] export JSON file ", uniqueInteractionId);
        try {

            Collection collection = collectionService.export(uniqueInteractionId, workspaceId, collectionId);
            if (Objects.nonNull(collection)) {

                ObjectMapper objectMapper = new ObjectMapper();
                String fileName = Objects.nonNull(collection.getInfo()) && Objects.nonNull(collection.getInfo().getName()) ? collection.getInfo().getName() : "collection" + ".json";
                ByteArrayResource resource = new ByteArrayResource(objectMapper.writeValueAsString(collection).getBytes());
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                        .contentLength(resource.contentLength())
                        .contentType(MediaType.APPLICATION_JSON).body(resource);
            } else {
                throw new RuntimeException("NO RECORD AVAILABLE...");
            }
        } catch (Exception ex) {
            log.error("interactionId : [{}] :: errorMsg : {}" , uniqueInteractionId , ex.getMessage());
            throw ex;
        }
    }

}

