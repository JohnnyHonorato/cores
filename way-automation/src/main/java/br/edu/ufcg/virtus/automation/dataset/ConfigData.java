package br.edu.ufcg.virtus.automation.dataset;

public abstract class ConfigData {

    public static final Integer WEB_DRIVER_WAIT_TIMEOUT = 10;

    public static String PORTAL_APPLICATION_URL;
    public static String CORE_APPLICATION_URL;
    public static String BUSINESS_APPLICATION_URL;

    public static final String DOWNLOADS_DIRECTORY = "/Downloads/";
    public static final String FILES_DIRECTORY = "/way/file/";

    public static Boolean HEADLESS = false;
    public static String ENVIRONMENT;

}
