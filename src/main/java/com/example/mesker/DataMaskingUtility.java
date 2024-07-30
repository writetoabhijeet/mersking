package com.example.mesker;

public class DataMaskingUtility {

    private final DataProcessor jsonProcessor;
    private final DataProcessor xmlProcessor;

    public DataMaskingUtility() {
        this.jsonProcessor = new JsonProcessor();
        this.xmlProcessor = new XmlProcessor();
    }

    public String processInput(String input, String type) {
        switch (type.toLowerCase()) {
            case "json":
                return jsonProcessor.process(input);
            case "xml":
                return xmlProcessor.process(input);
            default:
                throw new IllegalArgumentException("Unsupported input type: " + type);
        }
    }

    public static void main(String[] args) {
        DataMaskingUtility utility = new DataMaskingUtility();
        String jsonInput = "{\"name\":\"John Doe\",\"ssn\":\"123-45-6789\",\"creditCardNumber\":\"4111111111111111\",\"pan\":\"ABCDE1234F\",\"ban\":\"1234567890\",\"tel\":\"+123-456-7890\",\"email\":\"john.doe@example.com\"}";
        String maskedJson = utility.processInput(jsonInput, "json");
        System.out.println(maskedJson);

        String xmlInput = "<person><name>John Doe</name><ssn>123-45-6789</ssn><creditCardNumber>4111111111111111</creditCardNumber><pan>ABCDE1234F</pan><ban>1234567890</ban><tel>+123-456-7890</tel><email>john.doe@example.com</email></person>";
        String maskedXml = utility.processInput(xmlInput, "xml");
        System.out.println(maskedXml);
    }
}

