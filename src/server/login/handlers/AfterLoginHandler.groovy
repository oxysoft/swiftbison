package server.login.handlers

import client.GameClient
import server.packets.AbstractPacketHandler
import tools.packets.io.PacketReader

/**
 * Author: Oxysoft
 */
class AfterLoginHandler extends AbstractPacketHandler {

	@Override
	boolean validateState(GameClient c) {
		return false
	}

	@Override
	void handlePacket(GameClient c, PacketReader reader) throws Exception {
	}
}
