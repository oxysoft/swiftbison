package tools.socket

import tools.synch.SLinkedList

public class TCPSessionGroup extends Thread {
	
	private boolean closing = false
    private SLinkedList<TCPSession> s

    public TCPSessionGroup() {
        s = new SLinkedList<>()
    }

    void close() {
        closing = true
    }

    @Override
    public void run() {
        while (!closing) {
            try {
                s.acquireFullAccess()
                for (TCPSession ses : s) {
                    ses.fireSessionRead()
                }
            } catch (Exception e) {
                e.printStackTrace()
            } finally {
                s.releaseFullAccess()
            }
            try {
                Thread.sleep(100)
            } catch (Exception e) {
            }
        }
    }

    void register(TCPSession tcp) {
        s.add(tcp)
    }

    void deregister(TCPSession tcp) {
        s.remove(tcp)
    }
	
}
