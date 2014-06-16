package tools.socket

import java.nio.ByteBuffer
import java.nio.channels.SocketChannel

public class TCPSession extends Thread {
	
	private ByteBuffer data
	private ByteBuffer lenBuf
	private SessionInterface i
	private final SocketChannel s
	private TCPSessionGroup group
	private boolean closing = false

	public TCPSession(String ip, int port, SessionInterface si) throws Exception {
		this(SocketChannel.open(new InetSocketAddress(ip, port)), null, si)
	}

	private TCPSession(SocketChannel sock, TCPSessionGroup g, SessionInterface si) throws Exception {
		i = si
		s = sock
		lenBuf = ByteBuffer.allocate(4)
		lenBuf.clear()
		group = g
		fireSessionOpened()
	}

	public final void write(byte[] data) {
		if (data.length == 0) {
			return
		}
		try {
			ByteBuffer buf = ByteBuffer.allocate(4 + data.length)
			buf.putInt(data.length)
			buf.put(data)
			buf.flip()
			s.write(buf)
		} catch (Exception e) {
			e.printStackTrace()
			fireSessionTerminated(true)
		}
	}

	void fireSessionRead() {
		if (!closing) {
			if (lenBuf.remaining() == 0) { // we have size
				if (data == null) {
					data = ByteBuffer.allocate(lenBuf.getInt(0))
				}
				try {
					if (s.read(data) < 0) {
						close()
						return
					}
					if (data.remaining() == 0) {
						firePacketReceived(data.array())
						data = null
						lenBuf.flip()
					}
				} catch (Exception e) {
					close()
				}
			} else {
				try {
					s.read(lenBuf)
				} catch (Exception e) {
					close()
				}
			}
		}
	}

	@Override
	public void run() { // shouldn't use this aside from single-session cases
		while (!closing) {
			try {
				fireSessionRead()
				sleep(100)
			} catch (Exception e) {
				e.printStackTrace()
				close()
			}
		}
	}

	public final void close() {
		closing = true
		try {
			s.close()
		} catch (Exception e) {
		}
		if (group != null) {
			group.deregister(this)
		}
		fireSessionTerminated(false)
	}

	public final void setHandler(SessionInterface s) {
		i = s
	}

	private void fireSessionOpened() {
		i.sessionOpened(this)
	}

	private void fireSessionTerminated(boolean remote) {
		closing = true
		i.sessionClosed(remote)
	}

	private void firePacketReceived(byte[] data) {
		i.messageReceieved(data)
	}
}
