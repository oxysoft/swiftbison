package tools.mina

import java.util.concurrent.locks.ReentrantLock

import org.apache.mina.core.session.IoSession

import tools.packets.io.PacketReader
import tools.packets.io.PacketWriter

class MinaClient {

	private byte[] siv
	private byte[] riv
	private final IoSession s
	private int storedLength = -1
	private final PacketReader reader
	private final PacketWriter writer
	private final ReentrantLock lock
	public long lastReceived;
	public static final String AES = "AES"
	public static final String KEY = "CLIENT"

	public MinaClient(IoSession io, byte[] alpha, byte[] delta) {
		s = io
		siv = alpha
		riv = delta
		reader = new PacketReader()
		writer = new PacketWriter()
		lock = new ReentrantLock(true)
	}

	public final void write(Object msg) {
		writer.acquire()
		writer.close()
		writer.release()
		s.write(msg)
	}

	public final void flushSocket() {
		byte[] d = writer.getData()
		if (d != null && d.length > 0) {
			write(d)
			writer.close()
		}
	}

	public final void close() {
		s.close(true) // force close
		dispose()
	}

	public final ReentrantLock getEncoderLock() {
		return lock
	}

	public final IoSession getSession() {
		return s
	}

	public final byte[] getSendIV() {
		return siv
	}

	public final byte[] getRecvIV() {
		return riv
	}

	public final PacketReader getReader() {
		return reader
	}

	public final PacketWriter getWriter() {
		return writer
	}

	public final void updateSendIV(byte[] alpha) {
		siv = alpha
	}

	public final void updateRecvIV(byte[] alpha) {
		riv = alpha
	}

	public void setStoredLength(int slen) {
		storedLength = slen
	}

	public int getStoredLength() {
		return storedLength
	}

	public String getIp() {
		return getSession().getRemoteAddress().toString().replace("/", "").split(":")[0];
	}

	public void setLastReceived() {
		lastReceived = System.currentTimeMillis()
	}

	public void dispose() {
		siv = null
		riv = null
		reader.close()
		writer.close()
	}
}
