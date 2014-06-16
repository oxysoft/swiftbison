package tools.mina

import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.CumulativeProtocolDecoder
import org.apache.mina.filter.codec.ProtocolDecoderOutput
import tools.crypto.MapleAES
import tools.crypto.MapleCustomEncryption;

class PacketDecoder extends CumulativeProtocolDecoder {
	@Override
	protected boolean doDecode(IoSession s, IoBuffer input, ProtocolDecoderOutput out) throws Exception {
		MapleAES aes = (MapleAES) s.getAttribute(MinaClient.AES);
		MinaClient c = (MinaClient) s.getAttribute(MinaClient.KEY);
		if (c != null) {
			byte[] iv = c.getRecvIV();
			if (input.remaining() >= 4 && c.getStoredLength() == -1) {
				int h = input.getInt();
				if (!MapleAES.checkPacket(h, iv)) {
					s.close(true);
					return false;
				}
				c.setStoredLength(MapleAES.getLength(h));
			} else if (input.remaining() < 4 && c.getStoredLength() == -1) {
				return false;
			}
			if (input.remaining() >= c.getStoredLength()) {
				byte[] dec = new byte[c.getStoredLength()];
				input.get(dec, 0, c.getStoredLength());
				c.setStoredLength(-1);
				dec = aes.crypt(dec, iv);
				c.updateRecvIV(MapleAES.getNewIv(iv));
				dec = MapleCustomEncryption.decrypt(dec);
				out.write(dec);
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
