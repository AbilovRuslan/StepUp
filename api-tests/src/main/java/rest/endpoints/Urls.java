package rest.endpoints;

import config.ConfigReader;

public class Urls {
    public static final String GOODS = ConfigReader.getApiGoodsPath();
    public static final String ADD = ConfigReader.getApiAddPath();
    public static final String LIST = ConfigReader.getApiListPath();
    public static final String ID = ConfigReader.getApiIdPath();

    private Urls() {}
}