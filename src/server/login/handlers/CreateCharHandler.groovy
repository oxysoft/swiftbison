package server.login.handlers

import client.GameClient
import client.login.LoginStatus
import server.packets.AbstractPacketHandler
import tools.packets.io.PacketReader

/**
 * Author: Oxysoft
 */
class CreateCharHandler extends AbstractPacketHandler {
	@Override
	boolean validateState(GameClient c) {
		c.loginStatus == LoginStatus.CHARACTER_SELECTION
	}

	@Override
	void handlePacket(GameClient c, PacketReader reader) throws Exception {
		println "i hang myself tomorrow morning"
	}
}
