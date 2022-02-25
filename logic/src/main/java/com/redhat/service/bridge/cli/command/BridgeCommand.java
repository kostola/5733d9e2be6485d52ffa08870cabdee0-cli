package com.redhat.service.bridge.cli.command;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.JsonNode;
import com.redhat.service.bridge.cli.client.dto.BridgeRequest;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "bridge", description = "Manage bridges")
public class BridgeCommand extends BaseClientCommand {

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
    void create(@Option(names = "--name", required = true, description = "New bridge name") String name) {
        BridgeRequest request = new BridgeRequest();
        request.setName(name);
        Response response = client.bridgeCreate(request);
        JsonNode result = response.readEntity(JsonNode.class);
        System.out.println(outputGeneratorFactory.get().generate(result, Collections.emptyList()));
    }

    @Command(name = "get", description = "Get bridge information")
    void get(@Option(names = "--id", required = true, description = "Bridge ID") String id) {
        Response response = client.bridgeGet(id);
        JsonNode result = response.readEntity(JsonNode.class);
        System.out.println(outputGeneratorFactory.get().generate(result, Collections.emptyList()));
    }

    @Command(name = "delete", description = "Delete bridge")
    void delete(@Option(names = "--id", required = true, description = "Bridge ID") String id) {
        Response response = client.bridgeDelete(id);
        String output = response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL
                ? "OK"
                : "ERROR: unknown response status " + response.getStatus();
        System.out.println(output);
    }
}
