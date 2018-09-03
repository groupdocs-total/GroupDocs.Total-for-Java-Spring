package com.groupdocs.ui.comparison;

import com.groupdocs.ui.comparison.model.request.CompareRequest;
import com.groupdocs.ui.comparison.model.response.CompareResultResponse;
import com.groupdocs.ui.config.GlobalConfiguration;
import com.groupdocs.ui.exception.TotalGroupDocsException;
import com.groupdocs.ui.model.request.FileTreeRequest;
import com.groupdocs.ui.model.response.FileDescriptionEntity;
import com.groupdocs.ui.model.response.UploadedDocumentEntity;
import com.groupdocs.ui.util.Utils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Controller
@RequestMapping("/comparison")
public class ComparisonController {
    private static final Logger logger = LoggerFactory.getLogger(ComparisonController.class);

    @Autowired
    private GlobalConfiguration globalConfiguration;

    @Autowired
    private ComparisonService comparisonService;

    /**
     * Get comparison page
     * @param model model data for template
     * @return template name
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getView(Map<String, Object> model) {
        model.put("globalConfiguration", globalConfiguration);
        logger.debug("comparison config: {}", comparisonService.getComparisonConfiguration());
        model.put("comparisonConfiguration", comparisonService.getComparisonConfiguration());
        return "comparison";
    }

    /**
     * Get files and directories
     * @return files and directories list
     */
    @RequestMapping(method = RequestMethod.POST, value = "/loadFileTree", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<FileDescriptionEntity> loadFileTree(@RequestBody FileTreeRequest fileTreeRequest){
        return comparisonService.loadFiles(fileTreeRequest);
    }

    /**
     * Download results
     */
    @RequestMapping(method = RequestMethod.GET, value = "/downloadDocument")
    public void downloadDocument(@RequestParam(name = "path") String documentGuid, HttpServletResponse response) {
        File file = new File(documentGuid);
        // set response content info
        Utils.addFileDownloadHeaders(response, file.getName(), file.length());
        // download the document
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(documentGuid));
             ServletOutputStream outputStream = response.getOutputStream()) {

            IOUtils.copy(inputStream, outputStream);
        } catch (Exception ex){
            logger.error("Exception in downloading document", ex);
            throw new TotalGroupDocsException(ex.getMessage(), ex);
        }
    }

    /**
     * Upload document
     * @return uploaded document object (the object contains uploaded document guid)
     */
    @RequestMapping(method = RequestMethod.POST, value = "/uploadDocument",
            consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public UploadedDocumentEntity uploadDocument(@RequestParam("file") MultipartFile content,
                                                 @RequestParam("url") String url,
                                                 @RequestParam("rewrite") Boolean rewrite) {
        InputStream uploadedInputStream = null;
        try {
            String fileName;
            if (StringUtils.isEmpty(url)) {
                // get the InputStream to store the file
                uploadedInputStream = content.getInputStream();
                fileName = content.getOriginalFilename();
            } else {
                // get the InputStream from the URL
                URL fileUrl = URI.create(url).toURL();
                uploadedInputStream = fileUrl.openStream();
                fileName = FilenameUtils.getName(fileUrl.getPath());
            }
            // get documents storage path
            String documentStoragePath = comparisonService.getComparisonConfiguration().getFilesDirectory();
            // save the file
            File file = new File(documentStoragePath + File.separator + fileName);
            // check rewrite mode
            if(rewrite) {
                // save file with rewrite if exists
                Files.copy(uploadedInputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } else {
                if (file.exists()){
                    // get file with new name
                    file = Utils.getFreeFileName(documentStoragePath, fileName);
                }
                // save file with out rewriting
                Files.copy(uploadedInputStream, file.toPath());
            }
            UploadedDocumentEntity uploadedDocument = new UploadedDocumentEntity();
            uploadedDocument.setGuid(documentStoragePath + File.separator + fileName);
            return uploadedDocument;
        }catch(Exception ex){
            logger.error("Exception in uploading document", ex);
            throw new TotalGroupDocsException(ex.getMessage(), ex);
        } finally {
            try {
                uploadedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/compareWithPaths", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public CompareResultResponse compareWithPaths(@RequestBody CompareRequest compareRequest) {
        return comparisonService.compare(compareRequest);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/compareFiles",
            consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public CompareResultResponse compareFiles(@RequestParam("firstFile") MultipartFile firstContent,
                                               @RequestParam("secondFile") MultipartFile secondContent) {
        try {
            return comparisonService.compareFiles(firstContent.getInputStream(), firstContent.getOriginalFilename(), secondContent.getInputStream(), secondContent.getOriginalFilename());
        } catch (IOException e) {
            logger.error("Exception occurred while compare files by input streams.");
            throw new TotalGroupDocsException("Exception occurred while compare files by input streams.", e);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/compareWithUrls", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public CompareResultResponse compareWithUrls(@RequestBody CompareRequest compareRequest) {
        try {
            String firstPath = compareRequest.getFirstPath();
            String secondPath = compareRequest.getSecondPath();
            URL fUrl = URI.create(firstPath).toURL();
            URL sUrl = URI.create(secondPath).toURL();
            return comparisonService.compareFiles(fUrl.openStream(), firstPath, sUrl.openStream(), secondPath);
        } catch (IOException e) {
            logger.error("Exception occurred while compare files by urls.");
            throw new TotalGroupDocsException("Exception occurred while compare files by urls.", e);
        }
    }
}
