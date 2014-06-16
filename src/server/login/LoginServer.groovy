package server.login

import client.GameClient
import org.apache.mina.core.session.IoSession
import server.ServerPacketHandler
import server.core.AbstractServer
import server.core.AbstractServerPacketHandler
import tools.packets.LoginPackets

public class LoginServer extends AbstractServer {

	public LoginServer(int port) {
		super(port)

		new Thread({
			for (IoSession s : acceptor.getManagedSessions().values()) {
				GameClient c = (GameClient) s.getAttribute(GameClient.KEY)

				if (c != null && c.keepAlive(10_000)) {
					c.write(LoginPackets.ping(c.writer))
				}
			}

			try {
				Thread.sleep(10_000)
			} catch (e) {
			}

		}).run()
	}

	@Override
	public void onStarted() {
		println "Login server listening on port $port"
	}

	@Override
	AbstractServerPacketHandler getPacketHandler() {
		return ServerPacketHandler.instance
	}
}