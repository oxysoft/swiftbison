package tools.mina

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

class CodecFactory implements ProtocolCodecFactory {

	private final PacketEncoder encoder
	private final PacketDecoder decoder

	public CodecFactory() {
		this.encoder = new PacketEncoder()
		this.decoder = new PacketDecoder()
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return encoder
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return decoder
	}

}
