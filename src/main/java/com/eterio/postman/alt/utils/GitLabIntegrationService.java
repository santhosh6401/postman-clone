package com.eterio.postman.alt.utils;

import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.RepositoryFile;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GitLabIntegrationService {

    private static final String gitLabApiUrl = "https://gitlab.com/";
    public RepositoryFile getFile(String projectId , String personalAccessToken , String filePath) throws GitLabApiException {

        GitLabApi gitLabApi = new GitLabApi(gitLabApiUrl, personalAccessToken);

        try {
            return gitLabApi.getRepositoryFileApi().getFile(projectId, filePath, "main");

        } catch (GitLabApiException e) {
            throw e;
        }
    }
}
