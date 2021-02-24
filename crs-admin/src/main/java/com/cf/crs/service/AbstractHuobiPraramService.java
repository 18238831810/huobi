package com.cf.crs.service;

public interface AbstractHuobiPraramService {
    String apiKey = "e86f40c1-c7d024fe-ghxertfvbf-02f16321",
            secretKey = "5e8d4b45-1fcd8068-f6a09700-b56b2123";
    default String getApiKey()
    {
        return apiKey;
    }

    default String getSecretKey()
    {
        return secretKey;
    }
}
