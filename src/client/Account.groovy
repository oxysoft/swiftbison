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

		ret.with {
			salt = rs.getString("salt")
			id = rs.getInt("id")
			gender = rs.getInt("gender")
			banned = rs.getInt("banned")
			banreason = rs.getString("banreason")
			lastip = rs.getString("lastip")
			lastmac = rs.getString("lastmac")
			lastlogin = rs.getTimestamp("lastlogin")?.toLocalDateTime()
			birthdate = rs.getDate("birthdate")?.toLocalDate()
			creationdate = rs.getDate("birthdate")?.toLocalDate()

		}

		ret
	}
}