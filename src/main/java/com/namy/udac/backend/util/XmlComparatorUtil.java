package com.namy.udac.backend.util;

import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

public class XmlComparatorUtil {

    public static boolean isEquivalent(String actualXml, String expectedXml) {
        if (actualXml == null || expectedXml == null) return false;

        Diff diff = DiffBuilder.compare(expectedXml)
                .withTest(actualXml)
                .ignoreWhitespace()
                .checkForSimilar()
                .build();

        return !diff.hasDifferences();
    }
}