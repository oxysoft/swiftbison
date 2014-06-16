package client

import java.sql.ResultSet
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Author: Oxysoft
 */
class Account {

	int id, gender
	int banned
	String username, password, salt //password & salt stored as sha1 of course
	String banreason
	String lastip, lastmac
	LocalDateTime lastlogin
	LocalDate birthdate, creationdate

	public Account(String name, String pass) {
		this.username = name;
		this.password = pass
	}

	public static Account fromResultSet(ResultSet rs) {
		Account ret = new Account(rs.getString("name"), rs.getString("password"))
		ret.salt = rs.getString("salt")
		ret.id = rs.getInt("id")
		ret.gender = rs.getInt("gender")
		ret.banned = rs.getInt("banned")
		ret.banreason = rs.getString("banreason")
		ret.lastip = rs.getString("lastip")
		ret.lastmac = rs.getString("lastmac")
		ret.lastlogin = rs.getTimestamp("lastlogin")?.toLocalDateTime()
		ret.birthdate = rs.getDate("birthdate")?.toLocalDate()
		ret.creationdate = rs.getDate("birthdate")?.toLocalDate()
		return ret
	}
}