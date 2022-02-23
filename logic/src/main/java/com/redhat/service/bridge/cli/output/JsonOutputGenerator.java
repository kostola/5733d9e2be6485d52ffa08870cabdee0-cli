package com.redhat.service.bridge.cli.output;

import java.util.List;

import javax.enterprise.context.Dependent;

import com.fasterxml.jackson.databind.JsonNode;

@Dependent
public class JsonOutputGenerator implements OutputGenerator {

    @Override
    public String generate(JsonNode input, List<String> fields) {
        return input.toPrettyString();
    }
}
