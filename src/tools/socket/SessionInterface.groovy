package tools.socket

public interface SessionInterface {

	public void sessionOpened(TCPSession session)
	
	public void sessionClosed(boolean remote)
	
	public void messsageReceived(byte[] data)
	
}
