package com.redhat.service.bridge.cli.output;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        return generateObject(input, fields, 0);
    }

    private String generateObject(JsonNode input, List<String> fields, int padding) {
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

        final String formatTemplate = String.format("%%-%ds : %%s", maxFieldNameSize);
        final int subFieldPadding = padding + maxFieldNameSize + 3;

        return realFields.stream()
                .map(f -> String.format(formatTemplate, f, jsonNodeToHumanString(input.get(f), subFieldPadding)))
                .collect(Collectors.joining("\n" + spaces(padding)));
    }

    private String jsonNodeToHumanString(JsonNode input, int padding) {
        if (input == null) {
            return "";
        }
        if (input.isArray()) {
            String strPadding = spaces(padding);
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(input.iterator(), 0), false)
                    .map(i -> generateObject(i, null, padding))
                    .collect(Collectors.joining("\n" + strPadding + "---\n" + strPadding));
        }
        if (input.isObject()) {
            return generateObject(input, null, padding);
        }
        return input.asText();
    }

    private String spaces(int spaces) {
        if (spaces <= 0) {
            return "";
        }
        return CharBuffer.allocate( spaces ).toString().replace('\0', ' ');
    }
}
