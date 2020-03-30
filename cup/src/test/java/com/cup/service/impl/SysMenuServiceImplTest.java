package com.cup.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class SysMenuServiceImplTest {



    @org.junit.jupiter.api.Test
    void queryById() {
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }
}
