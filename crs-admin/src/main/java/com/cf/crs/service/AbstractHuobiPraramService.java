package com.cf.crs.service;

public interface AbstractHuobiPraramService {
    String apiKey = "e86f40c1-c7d024f1e-ghxertfvbf-02f16",
            secretKey = "5e8d4b45-1fcd80618-f6a09700-b56b2";
    default String getApiKey()
    {
        return apiKey;
    }

    default String getSecretKey()
    {
        return apiKey;
    }
}
