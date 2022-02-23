package com.redhat.service.bridge.cli.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/v1")
@RegisterRestClient(configKey = "openbridge")
@RegisterClientHeaders(AuthorizationHeadersFactory.class)
public interface OpenBridgeClient {

    @GET
    @Path("/bridges")
    Response bridgeList();
}
