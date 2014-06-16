package client.models

import java.util.concurrent.atomic.AtomicInteger

/**
 * Author: Oxysoft
 */
class PlayerModel {

	int id
	String name
	int maxhp, maxmp
	int level, str, dex, luk, int_
	AtomicInteger exp, mesos
	//BuddyList buddylist
	int fames, married, gmLevel
	int gender, hair, face, skincolor
	int remainingAp, remainingSp
	int guildid, guildrank, allianceRank
	int omokwins, omokties, omoklosses, matchcardwins, matchcardties, matchcardlosses
	int mapid

	PlayerModel(int id) {
		this.id = id
	}

	boolean isGM() {
		return gmLevel > 2
	}
}
