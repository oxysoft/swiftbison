package tools.packets.io

import java.nio.charset.Charset

class PacketReader {

	private int offset;
    private byte[] data;
    private static final Charset ASCII = Charset.forName("US-ASCII");

    public PacketReader() {
        offset = -1;
        data = null;
    }

    public final PacketReader next(byte[] d) {
        offset = 0;
        data = d;
        return this;
    }

    public final PacketReader next(Packet p) {
        return next(p.getData());
    }

    public final int read() {
        try {
            return 0xFF & data[offset++];
        } catch (Exception e) {
            return -1;
        }
    }

    public final void read(byte[] input) {
        read(input, 0, input.length);
    }

    public final void read(byte[] input, int off, int len) {
        for (int i = off; i < len; i++) {
            input[i] = readByte();
        }
    }

    public final byte[] read(int num) {
        byte[] ret = new byte[num];
        for (int i = 0; i < num; i++) {
            ret[i] = readByte();
        }
        return ret;
    }

    public final boolean readBool() {
        return read() > 0;
    }

    public final byte readByte() {
        return (byte) read();
    }

    public final short readShort() {
        return (short) (read() + (read() << 8));
    }

    public final char readChar() {
        return (char) (read() + (read() << 8));
    }

    public final int readInteger() {
        return read() + (read() << 8) + (read() << 16)
                + (read() << 24);
    }

    public final float readFloat() {
        return Float.intBitsToFloat(readInteger());
    }

    public final long readLong() {
        return read() + (read() << 8) + (read() << 16)
                + (read() << 24) + (read() << 32)
                + (read() << 40) + (read() << 48)
                + (read() << 56);
    }

    public final double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    public final String readString(int len) {
        byte[] sd = new byte[len];
        for (int i = 0; i < len; i++) {
            sd[i] = readByte();
        }
        return new String(sd, ASCII);
    }

    public final String readMapleString() {
        return readString(readShort());
    }

    public final String readNullTerminatedString() {
        int c = 0;
        while (read() != 0) {
            c++;
        }
        offset -= (c + 1);
        return readString(c);
    }

    public PacketReader skip(int num) {
        offset += num;
        return this;
    }

    public final int available() {
        return data.length - offset;
    }

    public final int getOffset() {
        return offset;
    }

    public final byte[] getData() {
        return data;
    }

    public final void close() {
        offset = -1;
        data = null;
    }

}
