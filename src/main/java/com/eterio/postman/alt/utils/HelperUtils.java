package com.eterio.postman.alt.utils;

import com.eterio.postman.alt.model.common.Audit;
import com.eterio.postman.alt.model.common.StatusLifeCycle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

}
