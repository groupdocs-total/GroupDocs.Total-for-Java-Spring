package com.groupdocs.ui.signature;

import com.groupdocs.ui.config.CommonConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SignatureConfiguration extends CommonConfiguration {

    @Value("${signature.filesDirectory}")
    private String filesDirectory;

    @Value("${signature.outputDirectory}")
    private String outputDirectory;

    @Value("${signature.dataDirectory}")
    private String dataDirectory;

    @Value("#{new Boolean('${signature.textSignature}')}")
    private Boolean textSignature;

    @Value("#{new Boolean('${signature.imageSignature}')}")
    private Boolean imageSignature;

    @Value("#{new Boolean('${signature.digitalSignature}')}")
    private Boolean digitalSignature;

    @Value("#{new Boolean('${signature.qrCodeSignature}')}")
    private Boolean qrCodeSignature;

    @Value("#{new Boolean('${signature.barCodeSignature}')}")
    private Boolean barCodeSignature;

    @Value("#{new Boolean('${signature.stampSignature}')}")
    private Boolean stampSignature;

    @Value("#{new Boolean('${signature.downloadOriginal}')}")
    private Boolean  downloadOriginal;

    @Value("#{new Boolean('${signature.downloadSigned}')}")
    private Boolean downloadSigned;

    @Value("#{new Integer('${signature.preloadPageCount}')}")
    private Integer preloadPageCount;

    @Value("${signature.defaultDocument}")
    private String defaultDocument;

    public String getFilesDirectory() {
        return filesDirectory;
    }

    public void setFilesDirectory(String filesDirectory) {
        this.filesDirectory = filesDirectory;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public String getDataDirectory() {
        return dataDirectory;
    }

    public void setDataDirectory(String dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    public Boolean getTextSignature() {
        return textSignature;
    }

    public void setTextSignature(Boolean textSignature) {
        this.textSignature = textSignature;
    }

    public Boolean getImageSignature() {
        return imageSignature;
    }

    public void setImageSignature(Boolean imageSignature) {
        this.imageSignature = imageSignature;
    }

    public Boolean getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(Boolean digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    public Boolean getQrCodeSignature() {
        return qrCodeSignature;
    }

    public void setQrCodeSignature(Boolean qrCodeSignature) {
        this.qrCodeSignature = qrCodeSignature;
    }

    public Boolean getBarCodeSignature() {
        return barCodeSignature;
    }

    public void setBarCodeSignature(Boolean barCodeSignature) {
        this.barCodeSignature = barCodeSignature;
    }

    public Boolean getStampSignature() {
        return stampSignature;
    }

    public void setStampSignature(Boolean stampSignature) {
        this.stampSignature = stampSignature;
    }

    public Boolean getDownloadOriginal() {
        return downloadOriginal;
    }

    public void setDownloadOriginal(Boolean downloadOriginal) {
        this.downloadOriginal = downloadOriginal;
    }

    public Boolean getDownloadSigned() {
        return downloadSigned;
    }

    public void setDownloadSigned(Boolean downloadSigned) {
        this.downloadSigned = downloadSigned;
    }

    public Integer getPreloadPageCount() {
        return preloadPageCount;
    }

    public void setPreloadPageCount(Integer preloadPageCount) {
        this.preloadPageCount = preloadPageCount;
    }

    public String getDefaultDocument() {
        return defaultDocument;
    }

    public void setDefaultDocument(String defaultDocument) {
        this.defaultDocument = defaultDocument;
    }

    @Override
    public String toString() {
        return super.toString() +
                "SignatureConfiguration{" +
                "filesDirectory='" + filesDirectory + '\'' +
                ", outputDirectory='" + outputDirectory + '\'' +
                ", dataDirectory='" + dataDirectory + '\'' +
                ", textSignature=" + textSignature +
                ", imageSignature=" + imageSignature +
                ", digitalSignature=" + digitalSignature +
                ", qrCodeSignature=" + qrCodeSignature +
                ", barCodeSignature=" + barCodeSignature +
                ", stampSignature=" + stampSignature +
                ", downloadOriginal=" + downloadOriginal +
                ", downloadSigned=" + downloadSigned +
                ", preloadPageCount=" + preloadPageCount +
                ", defaultDocument='" + defaultDocument + '\'' +
                '}';
    }
}
