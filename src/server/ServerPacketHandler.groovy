package server

import client.GameClient
import client.login.LoginStatus
import org.apache.mina.core.session.IoSession
import server.core.AbstractServerPacketHandler
import server.login.handlers.AfterLoginHandler
import server.login.handlers.CreateCharHandler
import server.login.handlers.LoginPasswordHandler
import server.login.handlers.RequestCharlistHandler
import server.login.handlers.RequestWorldlistHandler
import server.login.handlers.WorldStatusRequestHandler
import server.packets.AbstractPacketHandler
import server.packets.RecvOpcode
import tools.HexTool
import tools.crypto.MapleAES
import tools.packets.LoginPackets
import tools.packets.io.Packet
import tools.packets.io.PacketReader

/**
 * Author: Oxysoft
 */
class ServerPacketHandler extends AbstractServerPacketHandler {

	public static final ServerPacketHandler instance = new ServerPacketHandler()
	private final MapleAES AES

	public ServerPacketHandler() {
		super();

		this.AES = new MapleAES()

		initialize()
	}

	protected void initialize() {
		registerHandler(RecvOpcode.AFTER_LOGIN, new AfterLoginHandler())
		registerHandler(RecvOpcode.LOGIN_PASSWORD, new LoginPasswordHandler())
		registerHandler(RecvOpcode.WORLDLIST_REQUEST, new RequestWorldlistHandler())
		registerHandler(RecvOpcode.WORLDSTATUS_REQUEST, new WorldStatusRequestHandler())
		registerHandler(RecvOpcode.CHARLIST_REQUEST, new RequestCharlistHandler())
		registerHandler(RecvOpcode.CREATE_CHAR, new CreateCharHandler())
	}

	@Override
	public final void messageReceived(IoSession s, Object msg) {
		println ""
		println "Packet received: ${HexTool.toHex(msg as byte[])}"
		byte[] p = (byte[]) msg
		GameClient c = (GameClient) s.getAttribute(GameClient.KEY)
		PacketReader reader = c.reader.next(p)
		int header = reader.readShort()
		//println "Received packet: ${HexTool.toHex(p)}"
		AbstractPacketHandler handler = getHandler(header)
		if (handler != null) {
			if (handler.validateState(c)) {
				try {
					handler.handlePacket(c, reader)
				} catch (AssertionError e) {
					println "Error while handling packet: "
					e.printStackTrace()
					c.close() //TODO: change to disconnect
				} catch (Exception e) {
					println "Error while handling packet: "
					e.printStackTrace()
				}
			} else {
				System.out.printf("Bad Packet from %s : %s\n", s.getRemoteAddress().toString(), HexTool.toHex(p))
				s.close(true)
			}
		} else {
			//System.out.printf("%s: %s\n", RecvOpcode.getByOpcode(head), HexTool.toHex(p))
		}
	}

	@Override
	public final void messageSent(IoSession session, Object msg) throws Exception {
		println "Packet sent: ${HexTool.toHex(msg as byte[])}"

		byte[] pb = (byte[]) msg

		if (msg instanceof Packet) {
			Packet p = (Packet) msg
			if (p != null && p.onSend() != null) {
				p.onSend().run()
			}
		}
		super.messageSent(session, msg)
	}

	@Override
	public final void sessionOpened(IoSession s) throws Exception {
		byte[] siv = [82, 48, (byte) (Math.random() * 255), 115]
		byte[] riv = [70, 114, (byte) (Math.random() * 255), 82]
		GameClient c = new GameClient(s, siv, riv)
		c.write(LoginPackets.handshake(c.writer, siv, riv))
		s.setAttribute(GameClient.KEY, c)
		s.setAttribute(GameClient.AES, AES)
		c.updateLoginStatus(LoginStatus.LOGGED_OUT)
		println "${c.getIp()} has connected"
	}

	@Override
	public void sessionClosed(IoSession s) throws Exception {
		GameClient c = (GameClient) s.getAttribute(GameClient.KEY)
		if (c != null) {
			println "${c.getIp()} has disconnected"
			c.close() //TODO: change to disconnect
		}
		s.removeAttribute(GameClient.KEY)
		s.removeAttribute(GameClient.AES)
		super.sessionClosed(s)
	}

}
