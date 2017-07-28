package com.szyciov.util;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

/**
 * @ClassName RSAUtil 
 * @author Efy Shu
 * @Description RSA加解密工具类
 * @date 2017年7月12日 上午10:31:51 
 */
public class RSAUtil {
    public static final String KEY_ALGORITHM = "RSA";
    /** 貌似默认是RSA/NONE/PKCS1Padding，未验证 */
    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String PUBLIC_KEY = "publicKey.pem";
    public static final String PRIVATE_KEY = "privateKey.pem";
    public static final String FILE_PATH = "/home/keys/";
    public static final String PUBLIC_KEY_STR = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvDwTVOWYmMirRWB1t9/VdjQl5Wf+rznG1p89/PSYOib0uIV4A4ZH1wYmNjQEIkdk53vgHCXVofYEVCthikMepk9UYw4d66Fdo2AcgYgElK7V9XXqm8k0b0HFbNH3pyqbf2Vp4p7G8TnjpsUoOr/xZCAXR0beXFhtTDrU7F1vLIZrzvMQSVJ85X+NnSYCRbadmnU4dWt7VPcwkU9n9Y6YjMsqLk7zAL9waT/Ah88yafzqqUe+4jVrNbEzPuOxQL5gPpatorJXqKTGI3yNblFE92BR/MYWKs9Y3miVmgJM+nimoTw1BT/4kZE7I49tHylMUBajF4mthlE1Zulk0YsnbwIDAQAB";
    public static final String PRIVATE_KEY_STR = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC8PBNU5ZiYyKtFYHW339V2NCXlZ/6vOcbWnz389Jg6JvS4hXgDhkfXBiY2NAQiR2Tne+AcJdWh9gRUK2GKQx6mT1RjDh3roV2jYByBiASUrtX1deqbyTRvQcVs0fenKpt/ZWninsbxOeOmxSg6v/FkIBdHRt5cWG1MOtTsXW8shmvO8xBJUnzlf42dJgJFtp2adTh1a3tU9zCRT2f1jpiMyyouTvMAv3BpP8CHzzJp/OqpR77iNWs1sTM+47FAvmA+lq2isleopMYjfI1uUUT3YFH8xhYqz1jeaJWaAkz6eKahPDUFP/iRkTsjj20fKUxQFqMXia2GUTVm6WTRiydvAgMBAAECggEAHVegC9PefbKKQD5RG2Cg/dQTNDeJ0/bFsB8SoqPeNvJro3fqxWHhQPuMaVaiblWCvTLqNHkRXDXlZtl8UvkzhelH1kkVEO4OkrEgwHBbhnHamHDRKYerzMwL4nK4kC5sHufIIbNuf5vuiMijBMV284ytFEdP0kVWhFOHbXEDK4VkE2eel7im5twKO8Pol16+gVQiSAjT1xG4R2PfLbMJF+Kmo1CZh4btk4YJxxjLw/zgsIoDJjN27Zz0Sk6onXnmcCgi/hPx0hsPEWXOyG52DRigdvlHLbG/WUUgU1h/g/pcUYuXBpK4L0lShoJlI/RHm8u3nCbhY+FYKc3C/of1wQKBgQDtTGb1ZvGsYFzG9muf0KO12DwALrDi4Hr7zA6W50BWolyLQQdpBggo3blWRLWJcfNu0NA2r9zn4eQ6xIt5Qpja9lwP8n9RAwu3OyNvqAa/95MUKv0XeeX4OXAh/plArSFwLTKuu1pJom+pd4iKtaOSa47TYk3o6sBDzmsQbdkKmwKBgQDLEcpmraxtGWjpvQba24fVHPhgqq94+90MphOL2rq97vL3yTbHUeiq6qhcs7Ml8TR3s0/ycLoDZowr0RL7v4l0/hdBHa636TwCxIrUC7kENVw8078Eh8rHnDz46F7AnOFUObPo3ZmJMgzgD1Yt1ImlcWCiZ7CAx5ndEaEXVsqpvQKBgFYadvFsnRyTCpcXKw4eQojlIUBfsMdh9L4Q2OpglrPYzgOpYOr2yVcqvOlUnRp2QoJfOm3deiaZjJrWXlVtmNE6u0T+FLxsgn/F8aG+MlTNnIyg3Jz8GjnrfYiYPTt+lreBbvZ//M7jJekz+lN6fRPP+08wSFzPcVwpNigL88kpAoGBAKDH0Dolzfx/ftp56KCF6nEow9s9qTsgJ2+pWpaDsxFIzKS1cqKzzoTs0Cq9uN0UshcIKQdqi0y/98GFgwNCLqIrv9u1la1VhEcouEaVlYKogQREHtyGa15J7NiimsrkK2AVY8W/Fp5Su3D3k6MrwWP1da6AbHY6Ey++ko45ELL9AoGAJWZD20pon4XUj3EgHzELIWA/tmiwgKNfSlvmJN+y/1l8kAVzYp8KVW2xxkOgbqqZT4+rXpC7aURDxN0WdzA8MFP+yrNLq1Ks4wyDWRSfjfx4mvXLmwg3hjbAI9Jk3wunnlzFJnnlFkDpdLdWH+GGgomoJYeeBSsfU/ZwX+bqEag=";
    
    
    /** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
    public static final int KEY_SIZE = 2048;

    public static final String PLAIN_TEXT = "123456";

    public static void main(String[] args) {
    	long start = System.currentTimeMillis();
    	// 生成密钥
//    	Map<String, byte[]> keyMap = generateKeyBytes();
//    	PUBLIC_KEY_STR = Base64.encodeBase64String(keyMap.get(PUBLIC_KEY));
//    	PRIVATE_KEY_STR = Base64.encodeBase64String(keyMap.get(PRIVATE_KEY));
//    	generatePublicKeyFile();
    	System.out.println("公钥:"+PUBLIC_KEY_STR);
    	System.out.println("私钥:"+PRIVATE_KEY_STR);
        
        // 加密
        String encodedText = RSAEncode(PLAIN_TEXT);
        System.out.println("RSA加密: " + encodedText);

        // 解密
        String decodedText = RSADecode(encodedText);
        System.out.println("RSA解密: " + decodedText);
        long end = System.currentTimeMillis();
        System.out.println("耗时:"+(end - start)/1000.0D);
    }

    /**
     * 生成密钥对。注意这里是生成密钥对KeyPair，再由密钥对获取公私钥
     * 
     * @return
     */
    public static Map<String, byte[]> generateKeyBytes() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            Map<String, byte[]> keyMap = new HashMap<String, byte[]>();
            keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
            keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原公钥，X509EncodedKeySpec 用于构建公钥的规范
     * 
     * @param keyBytes
     * @return
     */
    public static PublicKey restorePublicKey(byte[] bytes){
        try {
        	X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 还原公钥，X509EncodedKeySpec 用于构建公钥的规范
     * 使用默认公钥
     * @param keyBytes
     * @return
     */
    public static PublicKey restorePublicKey() {
    	byte[] bytes = Base64.decodeBase64(PUBLIC_KEY_STR);
        return restorePublicKey(bytes);
    }

    /**
     * 还原私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
     * 
     * @param keyBytes
     * @return
     */
    public static PrivateKey restorePrivateKey(byte[] bytes) {
        try {
        	PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = factory.generatePrivate(pkcs8EncodedKeySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
     * 使用默认私钥
     * @param keyBytes
     * @return
     */
    public static PrivateKey restorePrivateKey() {
    	byte[] bytes = Base64.decodeBase64(PRIVATE_KEY_STR);
    	return restorePrivateKey(bytes);
    }
    
    /**
     * 加密，三步走。
     * 
     * @param key
     * @param plainText
     * @return
     */
    public static String RSAEncode(PublicKey key, byte[] plainText) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.encodeBase64String(cipher.doFinal(plainText));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密 , 使用默认公钥
     * @param rawText 明文
     * @return
     */
    public static String RSAEncode(String rawText){
    	PublicKey key = restorePublicKey();
    	return RSAEncode(key,rawText.trim().getBytes());
    }
    
    
    /**
     * 解密，三步走。
     * 
     * @param key
     * @param encodedText
     * @return
     */
    public static String RSADecode(PrivateKey key, String encodedText) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.decodeBase64(encodedText))).trim();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 解密 , 使用默认私钥
     * @param encodedText 密文
     * @return
     */
    public static String RSADecode(String encodedText){
    	PrivateKey key = restorePrivateKey();
    	return RSADecode(key,encodedText);
    }
    
    /**
     * 生成公钥文件
     */
    public static void generatePublicKeyFile(){
    	String title = "-----BEGIN PUBLIC KEY-----\n";
    	String end = "-----END PUBLIC KEY-----\n";
    	int index;
    	StringBuffer sb = new StringBuffer();
    	sb.append(PUBLIC_KEY_STR);
    	for(index=76;index<sb.length();index+=77){
    		sb.insert(index, "\n");
    	}
    	sb.append("\n");
    	String content = title + sb.toString() + end;
    	FileUtil.saveFile(FILE_PATH + PUBLIC_KEY, content.getBytes());
    }
}