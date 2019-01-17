package com.example.demo.cipher;

public class XorCipher {
	
	public static String encrypt(String msg, String key) {
		String result = "";
		for(int i = 0; i< msg.length(); i++) {
			char c = (char) (key.charAt(i % key.length()) ^ msg.charAt(i));
			result += c;
		}
		return result;
	}

	
	public static void main(String[] args) {
		String msg = "Qualquer Mensagem!";
		String key = "chave";
		System.out.println("Mensagem original: "+msg);
		String enMsg = encrypt(msg, key);
		System.out.println("Mensagem encriptada: "+enMsg);
		String orMsg = encrypt(enMsg, key);
		System.out.println("Mensagem descriptografada: "+orMsg);
	}
}
