package tools.mina

import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolEncoder
import org.apache.mina.filter.codec.ProtocolEncoderOutput
import tools.crypto.MapleAES
import tools.crypto.MapleCustomEncryption
import tools.packets.io.Packet

class PacketEncoder implements ProtocolEncoder {

	@Override
	public void encode(IoSession s, Object msg, ProtocolEncoderOutput out) throws Exception {
		byte[] data;
		if (msg instanceof Packet) {
			data = ((Packet) msg).getData();
		} else if (msg instanceof byte[]) {
			data = (byte[]) msg;
		} else {
			throw new RuntimeException("Unknown data type for encoding.");
		}
		MapleAES aes = (MapleAES) s.getAttribute(MinaClient.AES);
		MinaClient c = (MinaClient) s.getAttribute(MinaClient.KEY);
		if (c != null) {
			byte[] iv = c.getSendIV();
			byte[] head = MapleAES.getHeader(data.length, iv);
			MapleCustomEncryption.encrypt(data);
			c.getEncoderLock().lock();
			try {
				aes.crypt(data, iv);
				c.updateSendIV(MapleAES.getNewIv(iv));
			} finally {
				c.getEncoderLock().unlock();
			}
			byte[] ret = new byte[data.length + 4];
			System.arraycopy(head, 0, ret, 0, 4);
			System.arraycopy(data, 0, ret, 4, data.length);
			out.write(IoBuffer.wrap(ret));
			ret = null;
			head = null;
			data = null;
		} else {
			out.write(IoBuffer.wrap(data));
		}
	}

	@Override
	public void dispose(IoSession arg0) throws Exception {
	}
	
}
