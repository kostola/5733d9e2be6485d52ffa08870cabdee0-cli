package com.redhat.service.bridge.cli.output;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.Dependent;

import com.fasterxml.jackson.databind.JsonNode;

@Dependent
public class HumanOutputGenerator implements OutputGenerator {

    @Override
    public String generate(JsonNode input, List<String> fields) {
        return input.isArray() ? generateArray(input, fields) : generateObject(input, fields);
    }

    private String generateArray(JsonNode input, List<String> fields) {
        List<Integer> rowSize = new ArrayList<>(fields.size());
        for (int i = 0; i < fields.size(); i++) {
            rowSize.add(fields.get(i).length());
        }

        List<List<String>> rows = new ArrayList<>(input.size());
        for (JsonNode item : input) {
            List<String> row = new ArrayList<>(fields.size());
            for (int i = 0; i < fields.size(); i++) {
                String fieldName = fields.get(i);
                row.add(item.get(fieldName).asText());
                rowSize.set(i, Math.max(rowSize.get(i), row.get(i).length()));
            }
            rows.add(row);
        }

        String formatTemplate = rowSize.stream()
                .map(n -> "%-" + n + "s")
                .collect(Collectors.joining("  "));

        System.out.println(formatTemplate);

        List<String> formattedRows = new ArrayList<>(rows.size() + 1);
        formattedRows.add(String.format(formatTemplate, fields.stream().map(String::toUpperCase).toArray()));
        for (List<String> row : rows) {
            formattedRows.add(String.format(formatTemplate, row.toArray()));
        }

        return String.join("\n", formattedRows);
    }

    private String generateObject(JsonNode input, List<String> fields) {
        return input.toPrettyString();
    }
}
