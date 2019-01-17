package com.example.demo.cipher;

public class VigenereChiper {
	
	public String encrypt(String plainText, String key) {
		
		plainText = plainText.toUpperCase();
		
		key = key.toUpperCase();
		
		String cipherText = "";
		
		int keyIndex = 0;
		
		for (int i = 0; i < plainText.length(); i++) {
			char c = plainText.charAt(i);
			int index = Math.floorMod(ConstantsChiper.ALPHABET.indexOf(c) + ConstantsChiper.ALPHABET.indexOf(key.charAt(keyIndex)), 27);
			cipherText = cipherText + ConstantsChiper.ALPHABET.charAt(index);
			keyIndex++;
			
			if(keyIndex==key.length()) {
				keyIndex=0;
			}
			
		}
		return cipherText;
	}
	
	public String decrypt(String cipherText, String key) {
		
		cipherText = cipherText.toUpperCase();
		key = key.toUpperCase();
		
		String plainText = "";
		int keyIndex = 0;
		
		for (int i = 0; i < cipherText.length(); i++) {
			char c = cipherText.charAt(i);
			int index = Math.floorMod(ConstantsChiper.ALPHABET.indexOf(c) - ConstantsChiper.ALPHABET.indexOf(key.charAt(keyIndex)), 27);
			plainText = plainText + ConstantsChiper.ALPHABET.charAt(index);
			keyIndex++;
			
			if(keyIndex==key.length()) {
				keyIndex=0;
			}
			
		}
		return plainText;
	}
	
	public static void main(String[] args) {
		
		VigenereChiper vigenereChiper = new VigenereChiper();
		
		String cipherText = vigenereChiper.encrypt("O conceito de algoritmo é frequentemente ilustrado pelo exemplo de uma receita culinária, embora muitos algoritmos sejam mais complexos. Eles podem repetir passos (fazer iterações) ou necessitar de decisões (tais como comparações ou lógica) até que a tarefa seja completada. Um algoritmo corretamente executado não irá resolver um problema se estiver implementado incorretamente ou se não for apropriado ao problema", 
				"secret");
		
		System.out.println("Cipher text: "+cipherText);
		
		String plainText = vigenereChiper.decrypt(cipherText, "secret");
		System.out.println("Plain text is: "+plainText);
	}

}
