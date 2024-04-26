package com.eterio.postman.alt.service.implementation;

import com.eterio.postman.alt.model.collection.Collection;
import com.eterio.postman.alt.model.collection.response.CollectionResponse;
import com.eterio.postman.alt.model.common.CommonResponse;
import com.eterio.postman.alt.model.entity.CollectionEntity;
import com.eterio.postman.alt.repository.CollectionRepository;
import com.eterio.postman.alt.service.CollectionService;
import com.eterio.postman.alt.utils.HelperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

import static com.eterio.postman.alt.constant.AppConstant.FAILED;
import static com.eterio.postman.alt.constant.AppConstant.SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository repository;
    private final HelperUtils helperUtils;


    @Override
    public CollectionResponse importCollection(String uniqueInteractionId, String workspaceId, MultipartFile file) {

        CollectionEntity entity = new CollectionEntity();

        if (file.isEmpty())
            return CollectionResponse.builder()
                    .response(FAILED)
                    .description("file is empty")
                    .build();


        try {

            if (!Objects.equals(file.getContentType(), "application/json"))
                return CollectionResponse.builder()
                        .response(FAILED)
                        .description("file is not a JSON format")
                        .build();


            InputStream inputStream = file.getInputStream();
            byte[] bytes = inputStream.readAllBytes();

            String fileContent = new String(bytes, StandardCharsets.UTF_8);

            Collection collection = helperUtils.jsonStringToCollectionModel(fileContent);

            if (Objects.isNull(collection))
                return CollectionResponse.builder()
                        .response(FAILED)
                        .description("something went wrong in import... :)")
                        .build();

            BeanUtils.copyProperties(collection,entity);
            entity.setCollectionId(helperUtils.generateId("CI"));
            entity.setWorkspaceId(workspaceId);
            String name = Objects.nonNull(collection.getInfo()) && Objects.nonNull(collection.getInfo().getName()) ? collection.getInfo().getName() : "No Name";
            entity.setName(name);
            entity.setAudit(helperUtils.createAudit(uniqueInteractionId));  // TODO :: unique interaction is a specific format with user name and mobile
            entity.setStatusLifeCycle(helperUtils.upsertLifeCycles("import the new collection" , new ArrayList<>()));

            repository.save(entity);

            return CollectionResponse.builder()
                    .collection(collection)
                    .response(SUCCESS)
                    .description("import the JSON file is successfully")
                    .build();

        } catch (IOException e) {
            log.error("ERROR :: interactionId : [{}] errorMsg : {}", uniqueInteractionId, e.getMessage());
            return CollectionResponse.builder()
                    .response(FAILED)
                    .description("failed because of " + e.getMessage())
                    .build();
        }
    }
}
