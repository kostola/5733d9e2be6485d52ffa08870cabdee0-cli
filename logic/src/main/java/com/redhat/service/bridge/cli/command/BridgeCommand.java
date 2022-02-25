package com.redhat.service.bridge.cli.command;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.JsonNode;
import com.redhat.service.bridge.cli.client.OpenBridgeClient;
import com.redhat.service.bridge.cli.client.dto.BridgeRequest;
import com.redhat.service.bridge.cli.output.OutputGeneratorFactoryImpl;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "bridge", description = "Manage bridges")
public class BridgeCommand extends BaseCommand {

    @Inject
    @RestClient
    OpenBridgeClient client;

    @Inject
    OutputGeneratorFactoryImpl outputGeneratorFactory;

    @Command(name = "list", description = "List bridges")
    void list() {
        Response response = client.bridgeList();
        JsonNode result = response.readEntity(JsonNode.class).get("items");
        String output = result.isEmpty()
                ? "No bridges found"
                : outputGeneratorFactory.get().generate(result, List.of("id", "name", "status", "endpoint"));
        System.out.println(output);
    }

    @Command(name = "create", description = "Create bridge")
    void create(@Parameters(index = "0", description = "New bridge name") String name) {
        BridgeRequest request = new BridgeRequest();
        request.setName(name);
        Response response = client.bridgeCreate(request);
        JsonNode result = response.readEntity(JsonNode.class);
        System.out.println(outputGeneratorFactory.get().generate(result, Collections.emptyList()));
    }

    @Command(name = "get", description = "Get bridge information")
    void get(@Parameters(index = "0", description = "Bridge ID") String id) {
        Response response = client.bridgeGet(id);
        JsonNode result = response.readEntity(JsonNode.class);
        System.out.println(outputGeneratorFactory.get().generate(result, Collections.emptyList()));
    }
}
