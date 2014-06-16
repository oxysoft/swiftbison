package client.login

/**
 * Author: Oxysoft
 */
enum LoginStatus {

	LOGGED_OUT(0),
	WORLD_SELECTION(1),
	CHARACTER_SELECTION(2),
	IN_GAME(3)

	public final int code

	private LoginStatus(int code) {
		this.code = code
	}


}
