package com.groot.base.web.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Component
public class EncryptionUtil {

    private static String minix;

    @Value(value = "${groot.minix:blog}")
    public void setMinix(String minix) {
        EncryptionUtil.minix = minix;
    }

    public static String getMD5(String v) {
        return DigestUtils.md5DigestAsHex((minix + v).getBytes(StandardCharsets.UTF_8));
    }
}
