package server.core

import org.apache.mina.core.service.IoHandlerAdapter
import server.packets.AbstractPacketHandler
import server.packets.RecvOpcode
import tools.synch.SHashMap

/**
 * Author: Oxysoft
 */
public abstract class AbstractServerPacketHandler extends IoHandlerAdapter {
	protected final SHashMap<Integer, AbstractPacketHandler> handlers

	public AbstractServerPacketHandler() {
		this.handlers = new SHashMap<>()
	}

	protected void registerHandler(RecvOpcode opcode, AbstractPacketHandler handler) {
		handlers.put(opcode.value, handler)
	}

	protected AbstractPacketHandler getHandler(int header) {
		return handlers.get(header)
	}

	protected abstract void initialize()

}