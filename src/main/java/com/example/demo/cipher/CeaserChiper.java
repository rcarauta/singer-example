package com.example.demo.cipher;

public class CeaserChiper {

	public String encrypt(String plainText) {
		String cipherText = "";
		plainText = plainText.toUpperCase();
		
		for (int i = 0; i < plainText.length(); i++) {
			char character = plainText.charAt(i);
			int charIndex = ConstantsChiper.ALPHABET.indexOf(character);
			int encryptedIndex = Math.floorMod(charIndex + ConstantsChiper.KEY,ConstantsChiper.ALPHABET.length());
			cipherText += ConstantsChiper.ALPHABET.charAt(encryptedIndex);
		}
		
		return cipherText;
	}
	
	public String decripty(String chiperText) {
		String plainText = "";
		
		for (int i = 0; i < chiperText.length(); ++i) {
			char character = chiperText.charAt(i);
			int charIndex = ConstantsChiper.ALPHABET.indexOf(character);
			int decryptedIndex = Math.floorMod(charIndex - ConstantsChiper.KEY, ConstantsChiper.ALPHABET.length());
			plainText += ConstantsChiper.ALPHABET.charAt(decryptedIndex);
		}
		
		return plainText;
	}
	
	public static void main(String[] args) {
		CeaserChiper ceaserChiper = new CeaserChiper();
		String plainText = "Text Plain to decript";
		String chiperText = ceaserChiper.encrypt(plainText);
		String textPlain = ceaserChiper.decripty(chiperText);
		
		System.out.println(chiperText);
		System.out.println(textPlain);
	}
	
	
}
