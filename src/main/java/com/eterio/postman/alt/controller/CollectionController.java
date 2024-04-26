package com.eterio.postman.alt.controller;


import com.eterio.postman.alt.model.collection.response.CollectionResponse;
import com.eterio.postman.alt.model.common.CommonResponse;
import com.eterio.postman.alt.service.CollectionService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Api(tags = "Collection Application", value = "Collection Application")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/collection")
public class CollectionController {

    private final CollectionService collectionService;

    @PostMapping("/import")
    public CollectionResponse collectionImport(@RequestHeader String uniqueInteractionId,
                                               @RequestHeader(name = "clientToken") String clientToken ,
                                               @RequestParam(name = "workspace-id") String workspaceId ,
                                               @RequestPart("file") MultipartFile file) {

        log.info("interactionId :: [{}] import ", uniqueInteractionId);

        return collectionService.importCollection(uniqueInteractionId ,workspaceId , file);
    }

    @PostMapping("/export")
    public CommonResponse collectionExport(@RequestHeader String uniqueInteractionId,
                                           @RequestHeader(name = "clientToken") String clientToken ,
                                           @RequestParam(name = "workspace-id") String workspaceId ,
                                           @RequestParam(name = "collection-id") String collectionId
                                            ) {

        log.info("interactionId :: [{}] import  ", uniqueInteractionId);

        return null;
    }

}

