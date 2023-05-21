package com.app.cryptography.service.common;

public class ConvertHexAndBytesService {
    private String hex;    //hexToByte
    private byte[] ans;
    private byte[] bytes;    //ByteToHex

    public ConvertHexAndBytesService(byte[] bytes) {
        this.bytes = bytes;
    }

    public ConvertHexAndBytesService(String hex) {
        this.hex = hex;
        this.ans = new byte[hex.length() / 2];
    }

    public String bytesToHex() {
        StringBuilder builder = new StringBuilder();
        for (byte b: bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }


    public byte[] hexToBytes() {
        for (int i = 0; i < ans.length; i++) {
            int index = i * 2;

            int val = Integer.parseInt(hex.substring(index, index + 2), 16);
            ans[i] = (byte) val;
        }
        return ans;
    }

}
