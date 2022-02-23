package com.redhat.service.bridge.cli.config;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ConfigurationImpl implements Configuration {

    @ConfigProperty(name = "ob.token")
    Optional<String> optToken;

    private String token;

    @Override
    public String getToken() {
        return getOrError(token, optToken, "Missing access token");
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    private static String getOrError(String optionValue, Optional<String> propValue, String errorMessage) {
        return Optional.ofNullable(optionValue != null ? optionValue : propValue.orElse(null))
                .filter(s -> !s.isBlank())
                .orElseThrow(() -> new ConfigurationException(errorMessage));
    }
}
