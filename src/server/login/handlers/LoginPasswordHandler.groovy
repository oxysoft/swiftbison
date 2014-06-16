package server.login.handlers

import client.GameClient
import client.login.LoginResponse
import server.packets.AbstractPacketHandler
import tools.packets.LoginPackets
import tools.packets.io.PacketReader

/**
 * Author: Oxysoft
 */
class LoginPasswordHandler extends AbstractPacketHandler {
	@Override
	boolean validateState(GameClient c) {
		return true
	}

	@Override
	void handlePacket(GameClient c, PacketReader reader) throws Exception {
		String username = reader.readMapleString()
		String password = reader.readMapleString()

		LoginResponse status = c.tryLogin(username, password)

		if (status.code >= 0) {
			c.write(LoginPackets.loginStatus(c.getWriter(), status.code))
		} else {
			c.write(LoginPackets.authSuccess(c.getWriter(), c))
		}
	}
}
