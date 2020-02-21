package com.groupdocs.ui.search.controller;

import com.groupdocs.ui.model.request.FileTreeRequest;
import com.groupdocs.ui.model.request.LoadDocumentRequest;
import com.groupdocs.ui.model.response.FileDescriptionEntity;
import com.groupdocs.ui.model.response.UploadedDocumentEntity;
import com.groupdocs.ui.search.config.SearchConfiguration;
import com.groupdocs.ui.search.model.IndexDocumentResult;
import com.groupdocs.ui.search.model.SearchDocumentResult;
import com.groupdocs.ui.search.model.SearchRequest;
import com.groupdocs.ui.search.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.util.List;

import static com.groupdocs.ui.util.Utils.uploadFile;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * SearchController
 *
 * @author Aspose Pty Ltd
 */
@Controller
@RequestMapping(value = "/search")
public class SearchController {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    private SearchService searchService;

    public SearchController(@Autowired SearchService searchService) {
        this.searchService = searchService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/loadConfig", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public SearchConfiguration loadConfig() {
        return searchService.getSearchConfiguration();
    }

    /**
     * Get files and directories
     *
     * @param fileTreeRequest request's object with specified path
     * @return files and directories list
     */
    @RequestMapping(value = "/loadFileTree", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<FileDescriptionEntity> loadFileTree(@RequestBody FileTreeRequest fileTreeRequest) {
        return searchService.getFileList(fileTreeRequest);
    }

    /**
     * Get document description
     *
     * @return document description
     */
    @RequestMapping(value = "/loadDocumentDescription", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public IndexDocumentResult loadDocumentDescription(@RequestBody LoadDocumentRequest loadDocumentRequest) {
        return searchService.getDocumentDescription(loadDocumentRequest);
    }

    /**
     * Get document description
     *
     * @return document description
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<SearchDocumentResult> loadDocumentDescription(@RequestBody SearchRequest searchRequest) {
        return searchService.search(searchRequest);
    }

    /**
     * Upload document
     *
     * @param content file data
     * @param url     url for document
     * @param rewrite flag for rewriting file
     * @return uploaded document object (the object contains uploaded document guid)
     */
    @RequestMapping(value = "/uploadDocument", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE, consumes = MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public UploadedDocumentEntity uploadDocument(@Nullable @RequestParam("file") MultipartFile content,
                                                 @RequestParam(value = "url", required = false) String url,
                                                 @RequestParam("rewrite") Boolean rewrite) {
        // get documents storage path
        String documentStoragePath = searchService.getSearchConfiguration().getFilesDirectory();
        // save the file
        String pathname = uploadFile(documentStoragePath, content, url, rewrite);
        // create response data
        UploadedDocumentEntity uploadedDocument = new UploadedDocumentEntity();
        uploadedDocument.setGuid(pathname);
        return uploadedDocument;
    }
}
