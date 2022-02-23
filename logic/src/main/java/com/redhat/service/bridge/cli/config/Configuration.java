package com.redhat.service.bridge.cli.config;

import com.redhat.service.bridge.cli.output.OutputType;

public interface Configuration {

    OutputType getOutputType();
    void setOutputType(OutputType outputType);

    String getToken();
    void setToken(String token);

}
