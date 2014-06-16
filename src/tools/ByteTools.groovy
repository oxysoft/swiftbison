package tools

public class ByteTools {
	public static byte[] multiplyBytes(byte[] iv, int i, int i0) {
		byte[] ret = new byte[i * i0]
		for (int x = 0; x < ret.length; x++) {
			ret[x] = iv[x % i] // XXX do we need to & 0xFF? I think we may
		}
		return ret
	}

	public static byte rollLeft(byte input, int count) {
		return (byte) (((input & 0xFF) << (count % 8) & 0xFF)
		| ((input & 0xFF) << (count % 8) >> 8))
	}

	public static byte rollRight(byte input, int count) {
		int tmp = (int) input & 0xFF
		tmp = (tmp << 8) >>> (count % 8)
		return (byte) ((tmp & 0xFF) | (tmp >>> 8))
	}
}