package com.metasoft.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;

import javax.crypto.Cipher;

import com.metasoft.service.SpringService;

public class RsaUtils {
	/**
	 * RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	// pkcs8 methods
	private static final String PRIVATE_KEY_FILE = "/pem/pkcs8_priv.pem";
	private static final String PUBLIC_KEY_FILE = "/pem/public.key";
	public static PrivateKey gPrivateKey;
	public static String gBase64PublicKey;

	static {
		try {
			Reader privReader = null;
			Reader pubReader = null;
	        if(SpringService.development){
	        	//not work with tomcat war
				File file = new File(RsaUtils.class.getResource(PRIVATE_KEY_FILE).toURI());
				privReader = new FileReader(file);
				file = new File(RsaUtils.class.getResource(PUBLIC_KEY_FILE).toURI());
				pubReader = new FileReader(file);
	        }else{
	        	InputStream is = RsaUtils.class.getClassLoader().getResourceAsStream(PRIVATE_KEY_FILE);
				privReader = new InputStreamReader(is);
				is = RsaUtils.class.getClassLoader().getResourceAsStream(PUBLIC_KEY_FILE);
				pubReader = new InputStreamReader(is);
	        }
			
			BufferedReader privateKey = new BufferedReader(privReader);
			String strPrivKey = "";
			String line = "";
			while ((line = privateKey.readLine()) != null) {
				strPrivKey += line;
			}
			privateKey.close();
			// 私钥需要使用pkcs8格式的，公钥使用x509格式的
			strPrivKey = strPrivKey.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
			Decoder Base64Decoder = Base64.getDecoder();
			byte[] privKeyByte = Base64Decoder.decode(strPrivKey);
			PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(privKeyByte);
			KeyFactory kf = KeyFactory.getInstance(RsaUtils.KEY_ALGORITHM);
			gPrivateKey = kf.generatePrivate(privKeySpec);
			
			BufferedReader publicKey = new BufferedReader(pubReader);
			gBase64PublicKey = "";
			while ((line = publicKey.readLine()) != null) {
				gBase64PublicKey += line;
			}
			gBase64PublicKey = gBase64PublicKey.replace("-----BEGIN PUBLIC KEY-----", "").replace(
					"-----END PUBLIC KEY-----", "");
			publicKey.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public static synchronized byte[] encryptWithX509(String text, PublicKey key) {
		byte[] cipherText = null;
		try {
			// get an RSA cipher object and print the provider
			final Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			// encrypt the plain text using the public key
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipherText = cipher.doFinal(text.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cipherText;
	}

	public static synchronized String decryptWithPkcs8(byte[] text, PrivateKey key) {
		byte[] dectyptedText = null;
		try {
			// get an RSA cipher object and print the provider
			final Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);

			// decrypt the text using the private key
			cipher.init(Cipher.DECRYPT_MODE, key);
			dectyptedText = cipher.doFinal(text);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new String(dectyptedText);
	}

	public static void main(String[] args) throws Exception {
		String source = "da39a3ee5e6b4b0d3255bfef95601890afd80709";// "01234567890abcdefghijklmnopqrstuvwxyz";
		System.out.println("source:\r\n" + source);

		// test openssl
		try {
			File privateKeyFile = new File(source.getClass().getResource(PRIVATE_KEY_FILE).toURI());
			File publicKeyFile = new File(source.getClass().getResource(PUBLIC_KEY_FILE).toURI());
			BufferedReader privateKey = new BufferedReader(new FileReader(privateKeyFile));
			BufferedReader publicKey = new BufferedReader(new FileReader(publicKeyFile));
			String strPrivateKey = "";
			String strPublicKey = "";
			String line = "";
			while ((line = privateKey.readLine()) != null) {
				strPrivateKey += line;
			}
			while ((line = publicKey.readLine()) != null) {
				strPublicKey += line;
			}
			privateKey.close();
			publicKey.close();

			// 私钥需要使用pkcs8格式的，公钥使用x509格式的
			String strPrivKey = strPrivateKey.replace("-----BEGIN PRIVATE KEY-----", "").replace(
					"-----END PRIVATE KEY-----", "");
			String strPubKey = strPublicKey.replace("-----BEGIN PUBLIC KEY-----", "").replace(
					"-----END PUBLIC KEY-----", "");
			// System.out.print(strPrivKey);
			// System.out.println(strPubKey);

			Decoder Base64Decoder = Base64.getDecoder();
			byte[] privKeyByte = Base64Decoder.decode(strPrivKey);
			byte[] pubKeyByte = Base64Decoder.decode(strPubKey);
			PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(privKeyByte);
			// PKCS8EncodedKeySpec pubKeySpec = new
			// PKCS8EncodedKeySpec(pubKeyByte);

			// X509EncodedKeySpec privKeySpec = new
			// X509EncodedKeySpec(privKeyByte);
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(pubKeyByte);

			KeyFactory kf = KeyFactory.getInstance(RsaUtils.KEY_ALGORITHM);

			PrivateKey privKey = kf.generatePrivate(privKeySpec);
			PublicKey pubKey = kf.generatePublic(pubKeySpec);

			byte[] encryptByte = encryptWithX509(source, pubKey);
			System.out.printf("encryptWithX509:%s\r\n", bytesToString(encryptByte));
			System.out.println(decryptWithPkcs8(encryptByte, privKey));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}

	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String bytesToString(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

}
