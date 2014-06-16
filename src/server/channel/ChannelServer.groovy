package server.channel

import groovyjarjarasm.asm.TypeReference
import server.ServerPacketHandler
import server.core.AbstractServer
import server.core.AbstractServerPacketHandler

/**
 * Author: Oxysoft
 */
class ChannelServer extends AbstractServer {

	public byte id
	public PlayerStorage storage

	ChannelServer(int id) {
		super(7575 + id)

		this.id = id
	}

	@Override
	void onStarted() {
		println "Channel server listening on port $port"
	}

	@Override
	AbstractServerPacketHandler getPacketHandler() {
		return ServerPacketHandler.instance
	}
}
