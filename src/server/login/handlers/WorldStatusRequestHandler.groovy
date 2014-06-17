package server.login.handlers

import client.GameClient
import client.login.LoginStatus
import server.channel.ChannelServer
import server.core.Config
import server.core.Hivemind
import server.packets.AbstractPacketHandler
import tools.packets.LoginPackets
import tools.packets.io.PacketReader

/**
 * Author: Oxysoft
 */
class WorldStatusRequestHandler extends AbstractPacketHandler {

	@Override
	boolean validateState(GameClient c) {
		return c.loginStatus == LoginStatus.WORLD_SELECTION
	}

	@Override
	void handlePacket(GameClient c, PacketReader reader) throws Exception {
		byte world = (byte) reader.readShort()
		int sum = 0 //TODO: do this properly once we have players in the player storage

		short status = 0

		if (sum > Config.CHANNEL_LOAD)
			status = 2
		else if (sum > Config.CHANNEL_LOAD * 0.80)
			status = 1

		c.write(LoginPackets.getWorldStatus(c.writer, status))
	}
}
