/*
 * Created on Sep 2, 2008
 */
package code.messy.net.ip.udp;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import code.messy.net.OutputPayload;
import code.messy.util.Flow;
import code.messy.util.PayloadHelper;

public class UdpOutputPayload implements OutputPayload {
	private ByteBuffer header;
	private OutputPayload payload;
	public UdpOutputPayload(int src, int dst, OutputPayload payload) {
        Flow.trace("UdpOutputPayload: src=" + src + " dst=" + dst);

		this.payload = payload;
		
        header = ByteBuffer.allocateDirect(8);

        int length = PayloadHelper.getLength(payload);
        header.putShort((short)src);
        header.putShort((short)dst);
        header.putShort((short)(length + 8));
        
        // TODO zero checksum for now
        header.putShort((short)0);
        
        header.flip();
	}

	@Override
	public void getByteBuffers(ArrayList<ByteBuffer> bbs) {
        bbs.add(header);
        payload.getByteBuffers(bbs);
	}
}