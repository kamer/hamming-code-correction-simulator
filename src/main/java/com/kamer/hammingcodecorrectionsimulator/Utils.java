package com.kamer.hammingcodecorrectionsimulator;

import com.google.common.base.Splitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created on January, 2020
 *
 * @author kamer
 */
public class Utils {

	private static final int BITS_PER_CHAR = 8;

	public static String convertTextToBinary(String inputText) {

		final StringBuilder inputTextBinary = new StringBuilder();

		for (int i = 0; i < inputText.length(); i++) {

			inputTextBinary.append('0').append(Integer.toBinaryString(inputText.charAt(i)));
		}

		return inputTextBinary.toString();

	}

	public static String divideByNum(String text, int number) {

		final StringBuilder binaryTextByFour = new StringBuilder();

		for (int i = 0; i < text.length(); i++) {

			binaryTextByFour.append(text.charAt(i));

			if ((i + 1) % number == 0) {

				binaryTextByFour.append(" ");
			}

		}

		return binaryTextByFour.toString();
	}

	public static String corruptText(String text) {

		final int quadletCount = text.length() / 7;

		final StringBuilder stringBuilder = new StringBuilder(text);

		final Random random = new Random();

		for (int i = 0; i < quadletCount; i++) {

			final int corruptionIndex = (i * 7) + random.nextInt(7);

			stringBuilder.setCharAt(corruptionIndex, stringBuilder.charAt(corruptionIndex) == '1' ? '0' : '1');

		}

		return stringBuilder.toString();

	}

	public static String convertToString(String binary) {

		final Iterable<String> iterableBinaryWords = Splitter.fixedLength(BITS_PER_CHAR).split(binary);

		final ArrayList<String> listBinaryWords = new ArrayList<>();

		iterableBinaryWords.forEach(listBinaryWords::add);

		return listBinaryWords.stream()
				.map(binaryCharacter -> (char) Integer.parseInt(binaryCharacter, 2))
				.map(String::valueOf)
				.collect(Collectors.joining(""));
	}

	public static String encode(String inputText) {

		final StringBuilder encodedBinary = new StringBuilder();

		for (int i = 0; i < inputText.length() / 4; i++) {

			final String quadlet = inputText.substring(i * 4, ((i + 1) * 4));

			char p1 = xorChar(xorChar(quadlet.charAt(0), quadlet.charAt(1)), quadlet.charAt(3));
			char p2 = xorChar(xorChar(quadlet.charAt(0), quadlet.charAt(2)), quadlet.charAt(3));
			char p3 = xorChar(xorChar(quadlet.charAt(1), quadlet.charAt(2)), quadlet.charAt(3));

			char[] quadletArray = new char[] { p1, p2, quadlet.charAt(0), p3, quadlet.charAt(1), quadlet.charAt(2), quadlet.charAt(3) };

			encodedBinary.append(quadletArray);

		}

		return encodedBinary.toString();

	}

	public static String decode(String inputText) {

		final StringBuilder decodedText = new StringBuilder();

		for (int i = 0; i < inputText.length() / 7; i++) {

			final String substring = inputText.substring(i * 7, ((i + 1) * 7));

			decodedText.append(new String(new char[] { substring.charAt(2), substring.charAt(4), substring.charAt(5), substring.charAt(6) }));

		}

		return decodedText.toString();

	}

	public static String correct(String corruptedText, List<Integer> errorIndexes) {

		final StringBuilder correctedText = new StringBuilder(corruptedText);

		for (int i = 0; i < correctedText.length() / 7; i++) {

			int errorIndex = (i * 7) + errorIndexes.get(i);

			correctedText.setCharAt(errorIndex, correctedText.charAt(errorIndex) == '0' ? '1' : '0');

		}

		return correctedText.toString();
	}

	public static List<Integer> detect(String inputText) {

		final List<Integer> indexes = new ArrayList<>();

		for (int i = 0; i < inputText.length() / 7; i++) {

			final String sevenBits = inputText.substring(i * 7, ((i + 1) * 7));

			char c1 = xorChar(xorChar(xorChar(sevenBits.charAt(0), sevenBits.charAt(2)), sevenBits.charAt(4)), sevenBits.charAt(6));
			char c2 = xorChar(xorChar(xorChar(sevenBits.charAt(1), sevenBits.charAt(2)), sevenBits.charAt(5)), sevenBits.charAt(6));
			char c3 = xorChar(xorChar(xorChar(sevenBits.charAt(3), sevenBits.charAt(4)), sevenBits.charAt(5)), sevenBits.charAt(6));

			final String binaryStringError = new String(new char[] { c3, c2, c1 });

			final int errorIndex = Integer.parseInt(binaryStringError, 2);

			indexes.add(errorIndex - 1);

		}

		return indexes;

	}

	private static char xorChar(char o1, char o2) {

		if (o1 == o2) {
			return '0';
		}
		else {
			return '1';
		}

	}

}
