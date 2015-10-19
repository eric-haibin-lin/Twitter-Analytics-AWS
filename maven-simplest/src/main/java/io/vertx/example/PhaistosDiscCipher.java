package io.vertx.example;

import java.math.BigInteger;

public class PhaistosDiscCipher {
    private BigInteger secretKey;
    public PhaistosDiscCipher (String privateKeyStr) {
        secretKey = new BigInteger(privateKeyStr);
    }
    
    private BigInteger getMessageKey(BigInteger keyProduct) {
        return keyProduct.divide(secretKey);
    }

    private int getIntermediateKey(BigInteger messageKey) {
        return 1 + messageKey.mod(new BigInteger("25")).intValue();
    }

    private boolean isValidText(String text) {
        int len = text.length();
        int n = (int) Math.sqrt(len);
        if (n * n != len) {
            return false;
        }
        return text == text.toUpperCase();
    }

    private boolean isValidKey(String key) {
        return key.matches("\\d+");
    }

    public String encrypt(String plainText, String messageKey) {
        if (!isValidText(plainText) || !isValidKey(messageKey)) {
            return null;
        }
        int intermediateKey = getIntermediateKey(new BigInteger(messageKey));
        int len = plainText.length();
        char[] cipherText = new char[len];
        int n = (int) Math.sqrt(len);
        int i = 0, j = 0, k = 0, index = 0;

        for (k = 1; k <= n; k++) {
            for (i = 0, j = k - 1; i <= k - 1 && j >= 0; i++, j--, index++) {
                char m = (char) ((plainText.charAt(i * n + j) - 'A' + intermediateKey) % 26 + 'A');
                cipherText[index] = m;
            }
        }
        for (k = n - 1; k >= 1; k--) {
            for (i = n - k, j = n - 1; i <= n - 1 && j >= n - k; i++, j--, index++) {
                char m = (char) ((plainText.charAt(i * n + j) - 'A' + intermediateKey) % 26 + 'A');
                cipherText[index] = m;
            }
        }
        return new String(cipherText);
    }
    
    public String decrypt(String cipherText, String keyProduct) {
        if (!isValidText(cipherText) || !isValidKey(keyProduct)) {
            return null;
        }
        BigInteger messageKey = getMessageKey(new BigInteger(keyProduct));
        int intermediateKey = getIntermediateKey(messageKey);
        int len = cipherText.length();
        char[] plainText = new char[len];
        int n = (int) Math.sqrt(len);
        int i = 0, j = 0, k = 0, index = 0;

        for (k = 1; k <= n; k++) {
            for (i = 0, j = k - 1; i <= k - 1 && j >= 0; i++, j--, index++) {
                char m = (char) ((cipherText.charAt(index) - 'A' - intermediateKey + 26) % 26 + 'A');
                plainText[i * n + j] = m;
            }
        }
        for (k = n - 1; k >= 1; k--) {
            for (i = n - k, j = n - 1; i <= n - 1 && j >= n - k; i++, j--, index++) {
                char m = (char) ((cipherText.charAt(index) - 'A' - intermediateKey + 26) % 26 + 'A');
                plainText[i * n + j] = m;
            }
        }
        return new String(plainText);
    }
}
