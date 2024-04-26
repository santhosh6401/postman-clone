package com.eterio.postman.alt.service.implementation;

import com.eterio.postman.alt.model.response.CommonResponse;
import com.eterio.postman.alt.service.CollectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {
    @Override
    public CommonResponse importCollection(String uniqueInteractionId, String workspaceId, MultipartFile file) {
        return null;
    }
}
