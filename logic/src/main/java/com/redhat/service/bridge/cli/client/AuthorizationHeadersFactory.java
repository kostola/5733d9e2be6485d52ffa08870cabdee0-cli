package com.redhat.service.bridge.cli.client;

import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import com.redhat.service.bridge.cli.config.Configuration;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

@RegisterForReflection
public class AuthorizationHeadersFactory implements ClientHeadersFactory {

    @Inject
    Configuration config;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> multivaluedMap, MultivaluedMap<String, String> multivaluedMap1) {
        MultivaluedMap<String, String> result = new MultivaluedHashMap<>();
            result.add("Authorization", String.format("Bearer %s", config.getToken()));
        return result;
    }
}
