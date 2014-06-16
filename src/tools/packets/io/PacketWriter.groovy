package tools.packets.io

import tools.HexTool

import java.nio.charset.Charset
import java.util.concurrent.locks.ReentrantLock

public class PacketWriter {

	private int offset;
    private byte[] data;
    private ReentrantLock l;
    private static final Charset ASCII = Charset.forName("US-ASCII");

    public PacketWriter() {
        offset = -1;
        data = null;
        l = new ReentrantLock();
    }

    public final void next(int size) {
        l.lock();
        offset = 0;
        data = new byte[size];
    }

    private void expand(int size) {
        byte[] nd = new byte[size];
        System.arraycopy(data, 0, nd, 0, offset);
        data = nd;
    }

    private void trim() {
        expand(offset);
    }

    public final PacketWriter write(int b) {
        if (offset + 1 >= data.length) {
            expand(data.length * 2);
        }
        data[offset++] = (byte) b;
        return this;
    }

    public PacketWriter write(long lb) {
        return write((int) lb);
    }

    public final PacketWriter write(byte[] input) {
        return write(input, 0, input.length);
    }

    public final PacketWriter write(byte[] input, int off, int len) {
        for (int i = off; i < len; i++) {
            write(input[i]);
        }
        return this;
    }

    public final PacketWriter write(int... b) {
        for (int i = 0; i < b.length; i++) {
            write(b[i]);
        }
        return this;
    }

    public final PacketWriter writeByte(byte b) {
        return write(b);
    }
	
    public final PacketWriter writeShort(int s) {
        return write(s & 0xFF).write(s >>> 8);
    }

    public final PacketWriter writeShort(short s) {
        return write(s & 0xFF).write(s >>> 8);
    }

    public final PacketWriter writeChar(char c) {
        return writeShort(c);
    }

    public final PacketWriter writeInteger(int i) {
        return write(i & 0xFF).write(i >>> 8).write(i >>> 16).
                write(i >>> 24);
    }

    public final PacketWriter writeFloat(float f) {
        return writeInteger(Float.floatToIntBits(f));
    }

    public final PacketWriter writeLong(long l) {
        return write(l & 0xFF).write(l >>> 8).write(l >>> 16).
                write(l >>> 24).write(l >>> 32).write(l >>> 40).
                write(l >>> 48).write(l >>> 56);
    }

    public final PacketWriter writeDouble(double d) {
        return writeLong(Double.doubleToLongBits(d));
    }

    public final PacketWriter writeString(String s) {
        return write(s.getBytes(ASCII));
    }

    public final PacketWriter writeMapleString(String s) {
        return writeShort(s.length()).writeString(s);
    }

    public final PacketWriter writeNullTerminatedString(String s) {
        return writeString(s).write(0);
    }

    public final PacketWriter writeHex(String s) {
        return write(HexTool.toBytes(s));
    }

    public final PacketWriter writeBool(boolean b) {
        return write(b ? 1 : 0);
    }

    public final PacketWriter fill(int val, int num) {
        for (int i = 0; i < num; i++) {
            write(val);
        }
        return this;
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
    
    public final void acquire() {
        l.lock();
    }
    
    public final void release() {
        l.unlock();
    }

    @Override
    public final String toString() {
        return HexTool.toHex(data);
    }

    public final byte[] data() {
        try {
            if (data != null) {
                if (data.length > offset) {
                    trim();
                }
                return data;
            }
            return null;
        } finally {
            l.unlock();
        }
    }

    public final Packet createPacket() {
        return createPacket(null);
    }

    public final Packet createPacket(Runnable r) {
        try {
            if (data != null) {
                if (data.length > offset) {
                    trim();
                }
                return new Packet(data, r);
            }
            return null;
        } finally {
            l.unlock();
        }
    }
		
}
