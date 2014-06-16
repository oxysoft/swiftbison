package tools.packets

import client.models.PlayerModel
import tools.StringUtil
import tools.packets.io.PacketWriter

/**
 * Author: Oxysoft
 */

static byte[] addPlayerEntry(PacketWriter writer, PlayerModel model, boolean viewall) {
	addPlayerStats(writer, model)
	addPlayerLook(writer, model, false)

	if (!viewall)
		writer.write(0)

	if (model.isGM()) {
		writer.write(0)
		return
	}

	final boolean showRanks = false

	writer.write(showRanks)

	if (showRanks) {
		//TODO: add ranks
		//mplew.writeInt(chr.getRank()); // world rank
		//mplew.writeInt(chr.getRankMove()); // move (negative is downwards)
		//mplew.writeInt(chr.getJobRank()); // job rank
		//mplew.writeInt(chr.getJobRankMove()); // move (negative is downwards)
	}
}

static byte[] addPlayerStats(PacketWriter writer, PlayerModel model) {
	writer.writeInteger(model.id)
	writer.writeString(StringUtil.padRight(model.name, '\0' as char, 13))
	writer.write(model.gender)
	writer.write(model.skincolor)

	for (def i = 0; i < 3; i++) {
		//TODO: add pet here
		writer.writeLong(0)
	}

	writer.write(model.level)
	writer.writeShort(0)
	writer.writeShort(model.str)
	writer.writeShort(model.dex)
	writer.writeShort(model.int_)
	writer.writeShort(model.luk)
	writer.writeShort(model.maxhp)
	writer.writeShort(model.maxhp)
	writer.writeShort(model.maxmp)
	writer.writeShort(model.maxmp)
	writer.writeShort(model.remainingAp)
	writer.writeShort(model.remainingSp)
	writer.writeInteger(model.exp.get())
	writer.writeShort(model.fames)
	writer.writeInteger(0) //gacha exp, gay shit
	writer.writeInteger(model.mapid)
	writer.write(0) //initial spawn point
	writer.writeInteger(0)
}

static byte[] addPlayerLook(PacketWriter writer, PlayerModel model, boolean mega) {
	writer.write(model.gender)
	writer.write(model.skincolor)
	writer.writeInteger(model.face)
	writer.writeBool(mega)
	writer.writeInteger(model.hair)
	addPlayerEquips(writer, model)
}

static byte[] addPlayerEquips(PacketWriter writer, PlayerModel mode) {

}