package tools

import server.core.AbstractServer;

//groovy <3

public static AbstractServer loadServerIntoThread(Class<? extends AbstractServer> ctype, short port) {
	AbstractServer server

	Thread thread = new Thread({
		server = ctype.newInstance(port)
	})
	thread.start()

	return server
}