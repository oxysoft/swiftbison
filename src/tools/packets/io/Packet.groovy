package tools.packets.io

import tools.HexTool

class Packet {

	private byte[] data
	private Runnable onSend

	public Packet() {
		data = null
		onSend = null
	}

	public Packet(byte[] d) {
		data = d
		onSend = null
	}

	public Packet(Runnable r) {
		data = null
		onSend = r
	}

	public Packet(byte[] d, Runnable r) {
		data = d
		onSend = r
	}

	public final byte[] getData() {
		return data
	}

	public final void setData(byte[] d) {
		data = d
	}

	public final Runnable onSend() {
		return onSend
	}

	public final void onSend(Runnable r) {
		onSend = r
	}

	@Override
	public final Packet clone() {
		return new Packet(data.clone(), onSend)
	}

	public final int getHeader() {
		if (data.length < 2) {
			return 0xFFFF
		}
		return (data[0] + (data[1] << 8))
	}

	@Override
	public final String toString() {
		return HexTool.toHex(data)
	}
}
