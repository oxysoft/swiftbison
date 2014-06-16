package server.world

import server.channel.ChannelServer
import server.core.Config

/**
 * Author: Oxysoft
 */
class World {

	List<ChannelServer> channels;

	public World() {
		channels = new ArrayList<>()

		Config.CHANNELS.times {
			channels.add(new ChannelServer(it))
		}
	}

}
