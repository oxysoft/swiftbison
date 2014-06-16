package server.login.handlers

import client.GameClient
import client.login.LoginStatus
import server.packets.AbstractPacketHandler
import tools.packets.LoginPackets
import tools.packets.io.PacketReader

/**
 * Author: Oxysoft
 */
class RequestCharlistHandler extends AbstractPacketHandler {
	@Override
	boolean validateState(GameClient c) {
		return c.loginStatus == LoginStatus.WORLD_SELECTION
	}

	@Override
	void handlePacket(GameClient c, PacketReader reader) throws Exception {
		reader.read()
		c.world = reader.readByte()
		c.channel = reader.readByte() + 1
		c.write(LoginPackets.getCharlist(c.writer, c, c.getCharacters()))
	}
}
