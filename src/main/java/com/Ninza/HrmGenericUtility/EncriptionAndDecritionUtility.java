package com.Ninza.HrmGenericUtility;

import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.*;
import javax.crypto.Cipher;


//private key: Ac03tEam@j!tu_#1

public class EncriptionAndDecritionUtility {
			public String encrypt(String input,String secretkey) throws Exception {
			SecretKeySpec secretKeySpec=new SecretKeySpec (secretkey.getBytes(),"AES");
			IvParameterSpec ivParameterSpec= new IvParameterSpec("4234567890123456".getBytes());
			Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec) ;
			byte[] encrypted=cipher.doFinal(input.getBytes());
			return Base64.getEncoder().encodeToString(encrypted);
			}

			public String decrypt(String input, String secretkey) throws Exception {
			SecretKeySpec secretkeySpec = new SecretKeySpec (secretkey.getBytes (),"AES") ;
			IvParameterSpec ivParameterSpec = new IvParameterSpec("4234567890123456".getBytes());
			Cipher cipher = Cipher.getInstance ("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretkeySpec, ivParameterSpec);
			byte[] decrypted=cipher.doFinal (Base64.getDecoder().decode(input));
			 return new String (decrypted) ;

	}

}
