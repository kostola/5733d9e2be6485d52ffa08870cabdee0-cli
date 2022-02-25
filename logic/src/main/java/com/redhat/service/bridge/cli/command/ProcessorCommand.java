package com.redhat.service.bridge.cli.command;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.JsonNode;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "processor", description = "Manage processors")
public class ProcessorCommand extends BaseClientCommand {

    @Command(name = "list", description = "List processors")
    void get(@Option(names = {"-b", "--bridge"}, required = true, description = "Bridge ID") String bridgeId) {
        Response response = client.processorList(bridgeId);
        JsonNode result = response.readEntity(JsonNode.class).get("items");
        String output = result.isEmpty()
                ? "No processors found in bridge " + bridgeId
                : outputGeneratorFactory.get().generate(result, List.of("id", "name", "status"));
        System.out.println(output);
    }

    @Command(name = "get", description = "Get processor information")
    void get(
            @Option(names = {"-b", "--bridge"}, required = true, description = "Bridge ID") String bridgeId,
            @Option(names = "--id", required = true, description = "Processor ID") String processorId
    ) {
        Response response = client.processorGet(bridgeId, processorId);
        JsonNode result = response.readEntity(JsonNode.class);
        System.out.println(outputGeneratorFactory.get().generate(result, Collections.emptyList()));
    }

    @Command(name = "delete", description = "Delete processor")
    void delete(
            @Option(names = {"-b", "--bridge"}, required = true, description = "Bridge ID") String bridgeId,
            @Option(names = "--id", required = true, description = "Processor ID") String processorId
    ) {
        Response response = client.processorDelete(bridgeId, processorId);
        String output = response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL
                ? "OK"
                : "ERROR: unknown response status " + response.getStatus();
        System.out.println(output);
    }
}
