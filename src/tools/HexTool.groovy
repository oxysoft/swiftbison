package tools

import java.io.ByteArrayOutputStream

public class HexTool {
	private static final char[] HEX = [
        '0', '1', '2', '3',
        '4', '5', '6', '7',
        '8', '9', 'A', 'B',
        'C', 'D', 'E', 'F'
    ];

    private HexTool() {
    }

    public static String toHex(byte b) {
        return new StringBuilder().append(HEX[((b << 8) >> 12) & 0x0F]).
                append(HEX[((b << 8) >> 8) & 0x0F]).
                toString();
    }

    public static String toHex(byte[] arr) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            ret.append(toHex(arr[i]));
            ret.append(' ');
        }
        return ret.substring(0, ret.length() - 1);
    }

    public static byte[] toBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
