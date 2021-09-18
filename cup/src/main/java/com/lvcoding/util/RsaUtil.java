/*
 *
 *        Copyright (c) 2021-2015, wuyanshen All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the lvcoding.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: wuyanshen
 *
 */

package com.lvcoding.util;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.experimental.UtilityClass;

/**
 * Rsa非对称加密，解密工具类
 */
@UtilityClass
public class RsaUtil {

    private RSA rsa = null;

    static {
        // 公钥(java需要pkcs8格式的公钥)
        String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiOn6nGu8Y2+hjro9eK/7JKoUh8JUTPwgT9nALay1wBM7fvOKCZy6wrcgFsOvmyC176e+jLJYTDODErgO3V9V2M+n0QAeo5kDb4oWJhhcWLiAVWdfdisnXJ51bKoKWp/bb4yDX6IHXVZe6rCDxopflwS8G1FKsukEfBBbP8PjfDQIDAQAB";
        // 私钥(java需要pkcs8格式的私钥)
        String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALX7aGP0AyXxiEZfrS2LgFPwHYSK2SCJsnyRNDICLDEdP1t6osMp5E/DiJNs7V6s8bpOoGW6U6B1CCe0ygohMWTUD5wSf+/omnMY056sbwV6H+p2j74DgxIYzwlNoP174FnOL0/dAjm4mgavxTzpqbJPjEerY/JTkeRtAZQSUb/XAgMBAAECgYBTB1KYkANDs5B2cz6jEgvJwDrndyYbd1etB97lliItgmeeyDQskRdu4QqWINOoJ4Xed/MslreC9QJ0g0LPHlfXIPf2zSTl2QSthwLHF/xQ+f0y6F4ASRs3zQq3TeJQYf65P8qBtcJdzVAuIJYsr9LxLEaiWS4gL9eJd4+YxBhECQJBAN2DSaAEwzaSzOSFavBJCA7BU+JcMEdHH6KKInMjoaahW2XoJqZGfBdImo8fr0q8LTOYjw458o6xzoLwpUkSyWsCQQDSUJBscHJuJizYs9PFKQVjPAalLgadi420/tbfVrww5Z4GcDFAOAzYPaQupp7+Bke0IJh731+H2cgY44/mk+JFAkBEyEWOWKw9P+w7cWo5XpQP8NwZR8L9/wnFsNrtobzKPwRgamvF6dESccr8cjw+Gpx2jwKsyjWVNYUIh6zrOdgtAkBcuujy5yGNL4fWhHN7GvslJfJIImMIU9/HThWvo66WYKesbwtIJW6EaalaaFzx5BL5eOXCuFqGq59uWee44ruBAkEAthh9IvHPRZiv0R3MVqoaBMu3MaNmVN/ec3J8CF6zPCRO3snx40Y6wDE8Iri2wBR6yiEcp4kpsjQMk1OwItYXAQ==";
        rsa = new RSA(priKey, pubKey);
    }

    /**
     * 解密
     */
    public String decode(String rsaStr) {
        return rsa.decryptStr(rsaStr, KeyType.PrivateKey);
    }

    /**
     * 加密
     */
    public String encode(String str) {
        return rsa.encryptBcd(str, KeyType.PublicKey);
    }

}
