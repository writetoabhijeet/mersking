package com.example.mesker;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ConfigLoader {

    private static final Map<String, SensitiveFieldConfig> sensitiveFieldConfigs = new HashMap<>();

    static {
        loadYamlConfig();
    }

    private static void loadYamlConfig() {
        Yaml yaml = new Yaml();
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("sensitive-fields.yml")) {
            if (input == null) {
                throw new RuntimeException("Unable to find sensitive-fields.yml");
            }
            Map<String, Object> yamlData = yaml.load(input);
            Map<String, Map<String, Object>> fields = (Map<String, Map<String, Object>>) yamlData.get("sensitiveFields");
            for (String field : fields.keySet()) {
                Map<String, Object> fieldConfig = fields.get(field);
                String regex = (String) fieldConfig.get("regex");
                MaskingStrategyType strategy = MaskingStrategyType.valueOf(((String) fieldConfig.get("strategy")).toUpperCase());
                Object params = fieldConfig.get("params");
                sensitiveFieldConfigs.put(field.toLowerCase(), new SensitiveFieldConfig(Pattern.compile(regex, Pattern.CASE_INSENSITIVE), strategy, params));
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error loading configuration", ex);
        }
    }

    public static Map<String, SensitiveFieldConfig> getSensitiveFieldConfigs() {
        return sensitiveFieldConfigs;
    }
}
