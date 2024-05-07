package com.eterio.postman.alt.utils;

import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.RepositoryFile;
import org.gitlab4j.api.models.TreeItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GitLabIntegrationService {

    private static final String projectId = "57263373";
    private static final String personalAccessToken = "glpat-CB3hsFaGx8iwC3jEBRpK";
    private static final String gitLabApiUrl = "https://gitlab.com/";

    public RepositoryFile getFile(String projectId, String personalAccessToken, String filePath) throws GitLabApiException {

        GitLabApi gitLabApi = new GitLabApi(gitLabApiUrl, personalAccessToken);

        try {
            return gitLabApi.getRepositoryFileApi().getFile(projectId, filePath, "main");

        } catch (GitLabApiException e) {
            throw e;
        }
    }

    public RepositoryFile getFile(String filePath) throws GitLabApiException {

        GitLabApi gitLabApi = new GitLabApi(gitLabApiUrl, personalAccessToken);

        try {
            return gitLabApi.getRepositoryFileApi().getFile(projectId, filePath, "main");

        } catch (GitLabApiException e) {
            throw e;
        }
    }

    public List<TreeItem> getFolderStructure(String filePath) throws GitLabApiException {
        GitLabApi gitLabApi = new GitLabApi(gitLabApiUrl, personalAccessToken);

        try {
            return gitLabApi.getRepositoryApi().getTree(projectId, filePath, "main");

        } catch (GitLabApiException e) {
            throw e;
        }
    }
}
