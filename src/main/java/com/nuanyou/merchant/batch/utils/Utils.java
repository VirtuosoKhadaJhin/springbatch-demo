package com.nuanyou.merchant.batch.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


/**
 * Created by xiejing on 2016/1/27.
 */
public class Utils {

	public static String md5Encode(String inStr) {
		StringBuffer hexValue = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] byteArray = inStr.getBytes("UTF-8");
			byte[] md5Bytes = md5.digest(byteArray);
			hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		return hexValue.toString();
	}

	public static String genNonceStr() {
		Random random = new Random();
		return MD5Util.md5Encode(String.valueOf(random.nextInt(10000)));
	}

	public static String sign(byte[] data, String privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException,
			InvalidKeySpecException {
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateK = keyFactory.generatePrivate(spec);
		Signature signature = Signature.getInstance("SHA1WithRSA");
		signature.initSign(privateK);
		signature.update(data);
		return Base64.encodeBase64String(signature.sign());
	}

	public static void sign(List<NameValuePair> pairs, String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException,
			InvalidKeyException, SignatureException {
		pairs.add(new BasicNameValuePair("timestamp", String.valueOf(System.currentTimeMillis())));
		pairs.add(new BasicNameValuePair("noncestr", String.valueOf(genNonceStr())));
		Collections.sort(pairs, new Comparator<NameValuePair>() {
			@Override
			public int compare(NameValuePair o1, NameValuePair o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		String toSign = URLEncodedUtils.format(pairs, StandardCharsets.UTF_8);
		String sign = sign(toSign.getBytes(StandardCharsets.UTF_8), privateKey);
		pairs.add(new BasicNameValuePair("sign", sign));
	}
}
