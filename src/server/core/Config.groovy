package server.core

import groovy.json.JsonParserType
import groovy.json.JsonSlurper

/**
 * Author: Oxysoft
 */
class Config {
	//will need to make this smarter sometime in the future
	private static final String CONFIG_PATH = "C:\\Users\\Oxysoft\\EclipseWorkspace\\Swiftbison\\configs\\config.json"

	class Database {
		public static String HOST
		public static String PORT
		public static String DATABASE
		public static String USERNAME
		public static String PASSWORD
	}

	public static short MAPLE_VERSION
	public static String BUILD_VERSION

	public static String SERVER_NAME
	public static boolean ENABLE_PIC

	public static String EVENT_MESSAGE
	public static int WORLD_FLAG
	public static int CHANNELS
	public static int CHANNEL_LOAD

	public static int EXPRATE
	public static int MESORATE
	public static int DROPRATE

	public static void init() {
		JsonSlurper slurper = new JsonSlurper()
		slurper.setType(JsonParserType.LAX)
		def json = slurper.parse(new File(CONFIG_PATH))

		//database config
		Config.Database.HOST = json.database.host
		Config.Database.PORT = json.database.port
		Config.Database.DATABASE = json.database.database
		Config.Database.USERNAME = json.database.username
		Config.Database.PASSWORD = json.database.password

		//server config
		MAPLE_VERSION = json.server.maple_version
		BUILD_VERSION = json.server.build_version

		SERVER_NAME = json.server.server_name
		ENABLE_PIC = json.server.enable_pic

		EVENT_MESSAGE = json.server.event_message
		CHANNELS = json.server.channels
		CHANNEL_LOAD = json.server.channel_load
		WORLD_FLAG = json.server.world_flag

		EXPRATE = json.server.exprate
		MESORATE = json.server.mesorate
		DROPRATE = json.server.droprate

	}

}
