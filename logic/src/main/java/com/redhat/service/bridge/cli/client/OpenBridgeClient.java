package com.redhat.service.bridge.cli.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.redhat.service.bridge.cli.client.dto.BridgeRequest;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/api/v1")
@RegisterRestClient(configKey = "openbridge")
@RegisterClientHeaders(AuthorizationHeadersFactory.class)
public interface OpenBridgeClient {

    @GET
    @Path("/bridges")
    Response bridgeList();

    @POST
    @Path("/bridges")
    @Consumes(MediaType.APPLICATION_JSON)
    Response bridgeCreate(BridgeRequest user);

    @GET
    @Path("/bridges/{id}")
    Response bridgeGet(@PathParam String id);
}
