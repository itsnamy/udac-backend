package com.namy.udac.backend.util;

import org.json.JSONObject;
import org.json.XML;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class XmlToJsonUtil {

    // Convert XML string or base64 XML string to JSON string
    public static String convert(String input) throws Exception {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("XML input is null or empty");
        }

        String xmlString;

        // Check and decode base64 if needed
        if (input.startsWith("data:image/svg+xml;base64,")) {
            String base64 = input.replaceFirst("data:image/svg\\+xml;base64,", "");
            byte[] decodedBytes = Base64.getDecoder().decode(base64);
            xmlString = new String(decodedBytes, StandardCharsets.UTF_8);
        } else {
            xmlString = input;
        }

        // Now try converting XML to JSON
        JSONObject jsonObj = XML.toJSONObject(xmlString);
        return jsonObj.toString(2);
    }
}
