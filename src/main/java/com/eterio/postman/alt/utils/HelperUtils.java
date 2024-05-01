package com.eterio.postman.alt.utils;

import com.eterio.postman.alt.model.postmancollectionmodel.Collection;
import com.eterio.postman.alt.model.common.Audit;
import com.eterio.postman.alt.model.common.StatusLifeCycle;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HelperUtils {


    public String generateId(String prefix) {
        return prefix + Instant.now().toEpochMilli();
    }

    public Audit createAudit(String uniqueInteractionId) {
        Audit audit = new Audit();
        audit.setCreatedBy(uniqueInteractionId);
        audit.setCreatedDate(LocalDateTime.now());
        audit.setLastModifiedBy(uniqueInteractionId);
        audit.setLastModifiedDate(LocalDateTime.now());
        return audit;
    }

    public Audit updateAudit(String uniqueInteractionId, Audit audit) {
        audit.setLastModifiedDate(LocalDateTime.now());
        audit.setLastModifiedBy(uniqueInteractionId);
        return audit;
    }

    public List<StatusLifeCycle> upsertLifeCycles(String statusDescription, List<StatusLifeCycle> statusLifeCycles) {
        StatusLifeCycle statusLifeCycle = new StatusLifeCycle();
        statusLifeCycle.setStatus(statusDescription);
        statusLifeCycle.setCreatedOn(LocalDateTime.now());
        if (statusLifeCycles.isEmpty()) {
            List<StatusLifeCycle> list = new ArrayList<>();
            list.add(statusLifeCycle);
            return list;
        }
        statusLifeCycles.add(statusLifeCycle);
        return statusLifeCycles;
    }


    @SneakyThrows
    public Collection jsonStringToCollectionModel(String jsonString){
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString , Collection.class);
    }


    public String encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }


    public String decode(String value){
        return Base64.getDecoder().decode(value).toString();
    }

}
