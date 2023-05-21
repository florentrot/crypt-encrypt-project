package com.app.cryptography.service.common;

import java.security.SecureRandom;
import java.util.HashMap;

public class SecureRandomService {

    private HashMap<String, byte[]> cryptoComponents = new HashMap<>();
    private SecureRandom sr = new SecureRandom();

    public SecureRandom generateSecureRandom() {
        //generate sr, key and iv
        byte[] key = new byte[16];
        sr.nextBytes(key); //128_bit key
        byte[] initVector = new byte[16];
        sr.nextBytes(initVector); // 16_bytes IV
        cryptoComponents.put("key", key);
        cryptoComponents.put("iv", initVector);
        return sr;
    }

    public HashMap<String, byte[]> getCryptoComponents() {
        return cryptoComponents;
    }

    public void setCryptoComponents(HashMap<String, byte[]> cryptoComponents) {
        this.cryptoComponents = cryptoComponents;
    }

    public SecureRandom getSr() {
        return sr;
    }

    public void setSr(SecureRandom sr) {
        this.sr = sr;
    }

}
