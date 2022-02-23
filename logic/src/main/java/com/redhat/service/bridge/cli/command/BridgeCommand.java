package com.redhat.service.bridge.cli.command;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.JsonNode;
import com.redhat.service.bridge.cli.client.OpenBridgeClient;
import com.redhat.service.bridge.cli.output.OutputGeneratorFactoryImpl;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import picocli.CommandLine.Command;

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
        JsonNode result = response.readEntity(JsonNode.class);
        // result.get("items").forEach(System.out::println);
        System.out.println(outputGeneratorFactory.get().generate(result.get("items"), List.of("id", "name", "status", "endpoint")));
    }
}
