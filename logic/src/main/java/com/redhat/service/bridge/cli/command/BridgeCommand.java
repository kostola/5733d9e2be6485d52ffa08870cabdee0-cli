package com.redhat.service.bridge.cli.command;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.JsonNode;
import com.redhat.service.bridge.cli.client.OpenBridgeClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import picocli.CommandLine.Command;

@Command(name = "bridge", description = "Manage bridges")
public class BridgeCommand extends BaseCommand {

    @Inject
    @RestClient
    OpenBridgeClient client;

    @Command(name = "list", description = "List bridges")
    void list() {
        Response response = client.bridgeList();
        System.out.println(response.readEntity(JsonNode.class));
    }
}
