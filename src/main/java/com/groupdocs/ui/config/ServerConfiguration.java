package com.groupdocs.ui.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ServerConfiguration.class);

    private int httpPort;
    @Value("${server.hostAddress}")
    private String hostAddress;

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    @Override
    public String toString() {
        return "ServerConfiguration{" +
                "httpPort=" + httpPort +
                ", hostAddress='" + hostAddress + '\'' +
                '}';
    }
}
