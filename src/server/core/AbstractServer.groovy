package server.core

import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.buffer.SimpleBufferAllocator
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.transport.socket.nio.NioSocketAcceptor

import tools.crypto.MapleAES
import tools.mina.CodecFactory
import tools.socket.TCPSessionListener

public abstract class AbstractServer {

	int port
	TCPSessionListener socket
	NioSocketAcceptor acceptor

	public AbstractServer(int port) {
		this.port = port
		this.acceptor = new NioSocketAcceptor()

		IoBuffer.setUseDirectBuffer(true)
		IoBuffer.setAllocator(new SimpleBufferAllocator())
		MapleAES.initialize(Config.MAPLE_VERSION)
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CodecFactory()))
		acceptor.setHandler(getPacketHandler())
		try {
			acceptor.bind(new InetSocketAddress(port))
			onStarted()
			//socket = new TCPSessionListener(port, WorldInterface.class);
		} catch (Exception e) {
			println "Could not bind for port `$port`"
			System.exit(0)
		}
	}

	public abstract void onStarted()

	public abstract AbstractServerPacketHandler getPacketHandler()
}