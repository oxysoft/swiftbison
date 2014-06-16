package server.packets

import client.GameClient
import tools.packets.io.PacketReader

/**
 * Author: Oxysoft
 */
abstract class AbstractPacketHandler {

	public abstract boolean validateState(GameClient c)

	public abstract void handlePacket(GameClient c, PacketReader reader) throws Exception

}
