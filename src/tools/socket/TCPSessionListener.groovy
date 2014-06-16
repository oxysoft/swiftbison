package tools.socket

import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel

public class TCPSessionListener extends Thread {

	private final TCPSessionGroup group
    private final ServerSocketChannel ss
    private final Class<SessionInterface> i

    public TCPSessionListener(int port, Class<?> si) throws Exception {
        i = (Class<SessionInterface>) si
        ss = ServerSocketChannel.open()
        ss.socket().bind(new InetSocketAddress(port))
        group = new TCPSessionGroup()
        setName("TCPSessionListener")
    }

    public void shutdown() throws Exception {
        ss.close()
    }

    @Override
    public final void run() {
        group.start()
        while (true) {
            try {
                SocketChannel s = ss.accept()
                s.configureBlocking(false)
                TCPSession tcp = new TCPSession(s, group, i.newInstance())
                group.register(tcp)
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
    }

}
