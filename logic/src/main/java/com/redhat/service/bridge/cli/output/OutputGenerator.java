package com.redhat.service.bridge.cli.output;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public interface OutputGenerator {

    String generate(JsonNode input, List<String> fields);

}
