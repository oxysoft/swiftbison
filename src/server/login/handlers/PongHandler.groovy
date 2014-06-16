package server.login.handlers

import client.GameClient
import server.packets.AbstractPacketHandler
import tools.packets.io.PacketReader

/**
 * Author: Oxysoft
 */
public class PongHandler extends AbstractPacketHandler {

	@Override
	boolean validateState(GameClient c) {
		return true
	}

	@Override
	void handlePacket(GameClient c, PacketReader reader) throws Exception {
		c.pong = true
	}
}
