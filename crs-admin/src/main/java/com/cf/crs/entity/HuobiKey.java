package com.cf.crs.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public abstract class HuobiKey {
    /**
     * 火币的Key
     */
    @Getter @Setter

     String apiKey;

    /**
     * 火币的secretKey
     */
    @Getter @Setter
     String secretKey;
}
