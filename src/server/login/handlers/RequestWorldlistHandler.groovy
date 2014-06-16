package server.login.handlers

import client.GameClient
import client.login.LoginStatus
import server.packets.AbstractPacketHandler
import tools.packets.LoginPackets
import tools.packets.io.PacketReader

/**
 * Author: Oxysoft
 */
class RequestWorldlistHandler extends AbstractPacketHandler {
	@Override
	boolean validateState(GameClient c) {
		return c.loginStatus == LoginStatus.LOGGED_OUT
	}

	@Override
	void handlePacket(GameClient c, PacketReader reader) throws Exception {
		c.updateLoginStatus(LoginStatus.WORLD_SELECTION)

		c.write(LoginPackets.getWorld(c.writer, c))
		c.write(LoginPackets.getEndOfWorldList(c.writer, c))
		c.write(LoginPackets.selectWorld(c.writer, c, 0))
		c.write(LoginPackets.getRecommendedWorlds(c.writer, c))
	}
}
