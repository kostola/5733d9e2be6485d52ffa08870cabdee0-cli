package com.redhat.service.bridge.cli.output;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

                String itemFieldText = Optional.ofNullable(item)
                        .filter(it -> it.has(fieldName))
                        .map(it -> it.get(fieldName))
                        .map(JsonNode::asText)
                        .orElse("");

                row.add(itemFieldText);
                rowSize.set(i, Math.max(rowSize.get(i), row.get(i).length()));
            }
            rows.add(row);
        }

        rowSize.set(rowSize.size() - 1, 1);

        String formatTemplate = rowSize.stream()
                .map(n -> "%-" + n + "s")
                .collect(Collectors.joining("  "));

        List<String> formattedRows = new ArrayList<>(rows.size() + 1);
        formattedRows.add(String.format(formatTemplate, fields.stream().map(String::toUpperCase).toArray()));
        for (List<String> row : rows) {
            formattedRows.add(String.format(formatTemplate, row.toArray()));
        }

        return String.join("\n", formattedRows);
    }

    private String generateObject(JsonNode input, List<String> fields) {
        List<String> realFields = new ArrayList<>();
        int maxFieldNameSize = 0;

        for (var fieldIt = input.fields(); fieldIt.hasNext(); ) {
            var fieldEntry = fieldIt.next();
            var fieldName = fieldEntry.getKey();
            if (fields == null || fields.isEmpty() || fields.contains(fieldName)) {
                realFields.add(fieldName);
                if (fieldName.length() > maxFieldNameSize) {
                    maxFieldNameSize = fieldName.length();
                }
            }
        }

        String formatTemplate = String.format("%%-%ds : %%s", maxFieldNameSize);

        return realFields.stream()
                .map(f -> String.format(formatTemplate, f, input.get(f).asText()))
                .collect(Collectors.joining("\n"));
    }
}
