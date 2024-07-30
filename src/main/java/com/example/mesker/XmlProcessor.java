package com.example.mesker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.Map;
import java.util.regex.Pattern;

public class XmlProcessor implements DataProcessor {

    private static final XmlMapper xmlMapper = new XmlMapper();
    private static final Map<String, Pattern> sensitiveFieldPatterns = ConfigLoader.getSensitiveFieldPatterns();

    @Override
    public String process(String input) {
        try {
            JsonNode rootNode = xmlMapper.readTree(input.getBytes());
            maskSensitiveData(rootNode);
            return xmlMapper.writeValueAsString(rootNode);
        } catch (Exception e) {
            throw new RuntimeException("Error processing XML", e);
        }
    }

    private void maskSensitiveData(JsonNode node) {
        if (node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                if (isSensitiveField(entry.getKey()) || isSensitiveValue(entry.getValue().asText())) {
                    ((ObjectNode) node).put(entry.getKey(), mask(entry.getValue().asText()));
                } else {
                    maskSensitiveData(entry.getValue());
                }
            });
        } else if (node.isArray()) {
            node.forEach(this::maskSensitiveData);
        }
    }

    private boolean isSensitiveField(String fieldName) {
        return sensitiveFieldPatterns.containsKey(fieldName.toLowerCase());
    }

    private boolean isSensitiveValue(String value) {
        return sensitiveFieldPatterns.values().stream().anyMatch(pattern -> pattern.matcher(value).matches());
    }

    private String mask(String value) {
        return value.replaceAll(".", "*");
    }
}

