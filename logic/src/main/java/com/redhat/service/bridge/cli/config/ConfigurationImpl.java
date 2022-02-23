package com.redhat.service.bridge.cli.config;

import java.util.Optional;
import java.util.function.Predicate;

import javax.inject.Singleton;

import com.redhat.service.bridge.cli.output.OutputType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Singleton
public class ConfigurationImpl implements Configuration {

    private static final Predicate<String> NOT_BLANK = s -> !s.isBlank();

    @ConfigProperty(name = "ob.output-type")
    Optional<OutputType> optOutputType;
    private OutputType outputType;
    @Override
    public OutputType getOutputType() {
        return getOrError(outputType, optOutputType, OutputType.HUMAN, null, "Missing output type");
    }
    @Override
    public void setOutputType(OutputType outputType) {
        this.outputType = outputType;
    }

    @ConfigProperty(name = "ob.token")
    Optional<String> optToken;
    private String token;
    @Override
    public String getToken() {
        return getOrError(token, optToken, null, NOT_BLANK, "Missing access token");
    }
    @Override
    public void setToken(String token) {
        this.token = token;
    }

    private static <T> T getOrError(T optionValue, Optional<T> propValue, T defaultValue, Predicate<T> validator, String errorMessage) {
        return Optional.ofNullable(optionValue != null ? optionValue : propValue.orElse(defaultValue))
                .filter(item -> validator == null || validator.test(item))
                .orElseThrow(() -> new ConfigurationException(errorMessage));
    }
}
