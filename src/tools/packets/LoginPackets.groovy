package tools.packets

import client.GameClient
import client.models.PlayerModel
import server.channel.ChannelServer
import server.core.Config
import server.core.Hivemind
import server.packets.SendOpcode
import tools.packets.io.PacketWriter

/**
 * Author: Oxysoft
 */

static byte[] handshake(PacketWriter writer, byte[] sendIv, byte[] recvIv) {
	writer.with {
		next(16)
		writeShort(0x0E)
		writeShort(Config.MAPLE_VERSION)
		writeShort(1)
		write(49)
		write(recvIv)
		write(sendIv)
		write(8)

		data()
	}
}

static byte[] ping(PacketWriter writer) {
	writer.with {
		next(2)
		writeShort(SendOpcode.PING.value)

		data()
	}
}

/***
 * Gets a login failed packet.
 *
 * Possible values for <code>reason</code>:<br>
 * 3: ID deleted or blocked<br>
 * 4: Incorrect
 * password<br>
 * 5: Not a registered id<br>
 * 6: System error<br>
 * 7: Already
 * logged in<br>
 * 8: System error<br>
 * 9: System error<br>
 * 10: Cannot process
 * so many connections<br>
 * 11: Only users older than 20 can use this
 * channel<br>
 * 13: Unable to log on as master at this ip<br>
 * 14: Wrong
 * gateway or personal info and weird korean button<br>
 * 15: Processing
 * request with that korean button!<br>
 * 16: Please verify your account
 * through email...<br>
 * 17: Wrong gateway or personal info<br>
 * 21: Please
 * verify your account through email...<br>
 * 23: License agreement<br>
 * 25:
 * Maple Europe notice =[ FUCK YOU NEXON<br>
 * 27: Some weird full client
 * notice, probably for trial versions<br>
 *
 * @param The status code
 * @return The packet
 */
static byte[] loginStatus(PacketWriter writer, int code) {
	writer.with {
		next(8)
		writeShort(SendOpcode.LOGIN_STATUS.value)
		write(code)
		write(0)
		writeInteger(0)

		data()
	}
}


static byte[] authSuccess(PacketWriter writer, GameClient c) {
	writer.with {
		next(32)
		writeShort(SendOpcode.LOGIN_STATUS.value)
		writeInteger(0)
		writeShort(0)
		writeInteger(c.account.id)
		write(c.account.gender)
		writeBool(false) //admin byte?
		write(0) //admin/gm shit
		writeBool(false) //gm byte?
		writeMapleString(c.account.username)
		write(0)
		write(0) //has quiet ban
		writeLong(0)
		writeLong(0) //creation time?
		writeInteger(0)
		writeShort(2) //pin shit, fuck off

		data()
	}
}

/***
 * Get a packet representing the only world we support (Single world only, no more)
 *
 * @return The packet
 */
static byte[] getWorld(PacketWriter writer) {
	writer.with {
		next(64)
		writeShort(SendOpcode.SERVERLIST.value)
		write(0) //world id
		writeMapleString(Config.SERVER_NAME)
		write(Config.WORLD_FLAG)
		writeMapleString(Config.EVENT_MESSAGE)
		write(100) // rate modifier? wat
		write(0) // event xp * 2.6? wat
		write(100) // rate modifier? wat
		write(0) // drop rate * 2.6? wat
		write(0)
		write(Hivemind.instance.world.channels.size())
		Hivemind.instance.world.channels.each {
			writeMapleString("$Config.SERVER_NAME - $it.id ")
			writeInteger((int) 2 / 10)
			write(1)
			writeShort(it.id - 1)
		}
		writeShort(0)

		data()
	}
}

static byte[] getEndOfWorldList(PacketWriter writer) {
	writer.with {
		next(4)
		writeShort(SendOpcode.SERVERLIST.value)
		writeShort(0xff)

		data()
	}
}

static byte[] selectWorld(PacketWriter writer, int world) {
	writer.with {
		next(6)
		writeShort(SendOpcode.LAST_CONNECTED_WORLD.value)
		writeInteger(world) //World with the most players, according to GMS

		data()
	}
}

static byte[] getRecommendedWorlds(PacketWriter writer) {
	writer.with {
		next(8)
		writeShort(SendOpcode.RECOMMENDED_WORLD_MESSAGE.value)
		write(1) //n
		writeInteger(0).writeMapleString("I am the recommended world; Pick me!")

		data()
	}
}

static byte[] getWorldStatus(PacketWriter writer, short status) {
	writer.with {
		next(4)
		writeShort(SendOpcode.WORLDSTATUS.value)
		writeShort(status)

		data()
	}
}

static byte[] getCharlist(PacketWriter writer, GameClient c, List<PlayerModel> characters) {
	writer.with {
		next(128)
		writeShort(SendOpcode.CHARLIST.value)
		write(0)
		write(characters.size())

		characters.each {
			UtilPackets.addPlayerEntry(writer, it, false)
		}

		writeBool(Config.ENABLE_PIC)
		writeInteger(c.characterSlots)

		data()
	}
}