package com.example.mesker;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ConfigLoader {

    private static final Map<String, Pattern> sensitiveFieldPatterns = new HashMap<>();

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
            Map<String, String> fields = (Map<String, String>) yamlData.get("sensitiveFields");
            for (String field : fields.keySet()) {
                String regex = fields.get(field);
                sensitiveFieldPatterns.put(field.toLowerCase(), Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error loading configuration", ex);
        }
    }

    public static Map<String, Pattern> getSensitiveFieldPatterns() {
        return sensitiveFieldPatterns;
    }
}
