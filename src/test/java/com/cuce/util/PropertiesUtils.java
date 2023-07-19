package com.cuce.util;

import com.google.common.base.Strings;

public class PropertiesUtils {

    public static String getTargetTestHost() {

        String hostProperty = System.getProperty("host");
        if (Strings.isNullOrEmpty(hostProperty)) {
            System.out.println("Testing host is not provided. Please set variable 'host'.");
        }
        return hostProperty;
    }
    public static String getTargetTestProduct() {
        String productProperty = System.getProperty("product");
        if (Strings.isNullOrEmpty(productProperty)) {
            System.out.println("Testing host is not provided. Please set variable 'product'.");
        }
        return productProperty;
    }



    public static boolean isProdEnvironment() {
        return getTargetTestHost().contains("");
    }

    public static boolean isDevEnvironment() {
        return getTargetTestHost().contains("");
    }

    public static String getSelenoidUrl() {
        return System.getProperty("selenoid");
    }

    public static String getBrowserVersion() {
        String version = System.getProperty("version");
        return version.matches("^\\d\\d\\.0$") ? version : "";
    }
}
