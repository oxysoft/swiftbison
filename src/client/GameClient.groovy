package client

import client.login.LoginResponse
import client.login.LoginStatus
import client.models.PlayerModel
import org.apache.mina.core.session.IoSession
import tools.Database
import tools.mina.MinaClient

import java.security.MessageDigest
import java.sql.Connection

class GameClient extends MinaClient {

	public boolean pong
	public LoginStatus loginStatus
	public Account account
	public int world, channel
	public int characterSlots = 7

	public GameClient(IoSession io, byte[] siv, byte[] riv) {
		super(io, siv, riv)
	}

	public boolean keepAlive(long delay) {
		if (pong) {
			pong = false
			return true
		}

		return System.currentTimeMillis() - lastReceived < delay
	}

	public void disconnect(boolean save) {
		if (save) {
			// TODO: add player saving code here
		}

		close()
	}

	public LoginResponse tryLogin(String user, String pass) {
		LoginResponse result = LoginResponse.ERROR

		Connection con = Database.database.getConnection()
		def ps = con.prepareStatement("SELECT * FROM accounts WHERE name = ?")
		ps.setString(1, user)
		def rs = ps.executeQuery()

		try {
			if (rs.next()) {
				MessageDigest digest = MessageDigest.getInstance("sha-1")
				String hash = new String(digest.digest(pass.bytes))

				if (hash.contentEquals(rs.getString("password")) || pass.contentEquals(rs.getString("password"))) {
					result = LoginResponse.WERE_COOL

					if (rs.getBoolean("loggedin")) {
						result = LoginResponse.ALREADY_LOGGEDIN
					}

					if (rs.getInt("banned") > 0) {
						result = LoginResponse.BANNED
					}

					if (rs.getInt("gender") == -1) {
						result = LoginResponse.GENDER_NOT_SET
					}

					if (result.code < 0) {
						this.account = Account.fromResultSet(rs)
					}
				} else {
					result = LoginResponse.WRONG_PASSWORD
				}
			} else {
				return LoginResponse.NOT_REGISTERED
			}

			if (rs.next()) {
				println "Warning: two accounts have the same username ($user)"
				this.account = null
				return LoginResponse.ERROR
			}

		} catch (e) {
			e.printStackTrace()
		} finally {
			ps.close()
			rs.close()
		}

		return result
	}

	void updateLoginStatus(LoginStatus loginStatus) {
		this.loginStatus = loginStatus

		//TODO: make it save to database; sometimes
	}

	List<PlayerModel> getCharacters() {
		return []
	}
}
