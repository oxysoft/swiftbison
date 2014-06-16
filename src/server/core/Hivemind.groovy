package server.core

import server.login.LoginServer
import server.world.World


public class Hivemind {

	//singleton why not
	public static Hivemind instance

	public LoginServer loginServer
	public World world

	public void init() {
		Config.init()
		
		loginServer = new LoginServer(8484)
		world = new World()
	}
			
	public static void main(String[] args) {
		instance = new Hivemind()
		instance.init()
	}
}