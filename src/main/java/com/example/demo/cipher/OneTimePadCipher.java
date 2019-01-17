package com.example.demo.cipher;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OneTimePadCipher {

	private Map<Character, Integer> analyse(String text) {

		text = text.toUpperCase();
		Map<Character, Integer> letterFrequencies = new HashMap<>();

		for (int i = 0; i < ConstantsChiper.ALPHABET.length(); i++) {
			letterFrequencies.put(ConstantsChiper.ALPHABET.charAt(i), 0);
		}

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			if (ConstantsChiper.ALPHABET.indexOf(c) != -1) {
				letterFrequencies.put(c, letterFrequencies.get(c) + 1);
			}
		}

		return letterFrequencies;
	}

	public void showFrequencies(String text) {
		Map<Character, Integer> letterFrequencies = analyse(text);

		for (Map.Entry<Character, Integer> entry : letterFrequencies.entrySet()) {
			System.out.format("Character %s has frequency: %s%n", entry.getKey(), entry.getValue());
		}
	}

	public int[] generate(int n) {
		Random random = new Random();
		int[] randomSequence = new int[n];

		for (int i = 0; i < n; i++) {
			randomSequence[i] = random.nextInt(ConstantsChiper.ALPHABET.length());
		}

		return randomSequence;
	}

	public String encrypt(String plainText, int[] key) {

		plainText = plainText.toUpperCase();
		String cipherText = "";

		for (int i = 0; i < plainText.length(); i++) {
			int keyIndex = key[i];
			int characterIndex = ConstantsChiper.ALPHABET.indexOf(plainText.charAt(i));

			cipherText = cipherText + ConstantsChiper.ALPHABET
					.charAt(Math.floorMod(characterIndex + keyIndex, ConstantsChiper.ALPHABET.length()));
		}

		return cipherText;
	}

	public String decrypt(String cipherText, int[] key) {

		cipherText = cipherText.toUpperCase();
		String plainText = "";

		for (int i = 0; i < cipherText.length(); i++) {
			int keyIndex = key[i];
			int characterIndex = ConstantsChiper.ALPHABET.indexOf(cipherText.charAt(i));

			plainText = plainText + ConstantsChiper.ALPHABET
					.charAt(Math.floorMod(characterIndex - keyIndex, ConstantsChiper.ALPHABET.length()));
		}

		return plainText;
	}

	public static void main(String[] args) {
		
		String plainText = 	"O conceito de algoritmo é frequentemente ilustrado pelo exemplo de uma receita culinária, embora muitos algoritmos sejam mais complexos. Eles podem repetir passos (fazer iterações) ou necessitar de decisões (tais como comparações ou lógica) até que a tarefa seja completada. Um algoritmo corretamente executado não irá resolver um problema se estiver implementado incorretamente ou se não for apropriado ao problema";
		
		OneTimePadCipher oneTimePadCipher = new OneTimePadCipher();
		
		int[] key = oneTimePadCipher.generate(plainText.length());
		
		String cipherText = oneTimePadCipher.encrypt(plainText, key);
		
		oneTimePadCipher.showFrequencies(cipherText);
		System.out.println(cipherText);
		System.out.println(oneTimePadCipher.decrypt(cipherText, key));

	}

}
