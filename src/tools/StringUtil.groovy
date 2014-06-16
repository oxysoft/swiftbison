package tools

import java.awt.Point

/**
 * Author: Oxysoft
 */
public static String padRight(String input, char fill, int len) {
	StringBuilder sb = new StringBuilder();
	sb.append(input);
	for (int i = 0; i < len - input.length(); i++) {
		sb.append(fill);
	}
	return sb.toString();
}

public static String padLeft(String input, char fill, int len) {
	StringBuilder sb = new StringBuilder();
	for (int i = 0; i < len - input.length(); i++) {
		sb.append(fill);
	}
	sb.append(input);
	return sb.toString();
}

public static int getIntegerFromLeftPadded(String s) {
	int index = 0;
	for (int i = 0; i < s.length(); i++) {
		if (s.charAt(i) != '0') {
			index = i;
			break;
		}
	}
	return Integer.parseInt(s.substring(index));
}

public static String combine(String[] input, int index, char sep) {
	StringBuilder sb = new StringBuilder();
	for (int i = index; i < input.length; i++) {
		sb.append(input[i]).append(i + 1 >= input.length ? "" : sep);
	}
	return sb.toString();
}
