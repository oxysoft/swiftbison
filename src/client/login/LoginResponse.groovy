package client.login

/**
 * Author: Oxysoft
 */
enum LoginResponse {

	ERROR(8),
	NOT_REGISTERED(5),
	WRONG_PASSWORD(4),
	ALREADY_LOGGEDIN(7),
	BANNED(3),
	WERE_COOL(-1),
	GENDER_NOT_SET(-2)

	public final int code

	private LoginResponse(int code) {
		this.code = code
	}

}
