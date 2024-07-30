package com.example.mesker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.Map;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;

public class XmlProcessor implements DataProcessor {

    private static final XmlMapper xmlMapper = new XmlMapper();

    @Override
    public String process(String input) {
        try {
            JsonNode rootNode = xmlMapper.readTree(input.getBytes());
            maskSensitiveFields(rootNode);
            return xmlMapper.writeValueAsString(rootNode);
        } catch (Exception e) {
            throw new RuntimeException("Error processing XML input", e);
        }
    }

    private void maskSensitiveFields(JsonNode node) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                if (field.getValue().isTextual()) {
                    String fieldName = field.getKey().toLowerCase();
                    String fieldValue = field.getValue().asText();
                    String maskedValue = maskField(fieldName, fieldValue);
                    ((ObjectNode) node).put(field.getKey(), maskedValue);
                } else {
                    maskSensitiveFields(field.getValue());
                }
            }
        } else if (node.isArray()) {
            for (JsonNode arrayItem : node) {
                maskSensitiveFields(arrayItem);
            }
        }
    }

    private String maskField(String fieldName, String fieldValue) {
        for (Map.Entry<String, SensitiveFieldConfig> entry : ConfigLoader.getSensitiveFieldConfigs().entrySet()) {
            if (fieldName.contains(entry.getKey())) {
                Matcher matcher = entry.getValue().getPattern().matcher(fieldValue);
                if (matcher.matches()) {
                    MaskingStrategy strategy = MaskingStrategyFactory.getStrategy(entry.getValue().getStrategy(), entry.getValue().getParams());
                    return strategy.mask(fieldValue);
                }
            }
        }
        return fieldValue;
    }
}