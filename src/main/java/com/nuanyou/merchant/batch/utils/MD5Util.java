package com.nuanyou.merchant.batch.utils;

import java.security.MessageDigest;

/**
 * MD5加密后得到32位字符串
 * @author Administrator
 *
 */
public class MD5Util {
	public static String md5Encode(String inStr){
		StringBuffer hexValue=null;
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
}
