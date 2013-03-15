/*
 * Created on Aug 15, 2008
 */
package code.messy.net.ip;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public interface IpLinkSupport {
	// void loopback(ByteBuffer[] payload) throws IOException;
//    void forward(InetAddress src, InetAddress gw, IpPacket packet) throws IOException;
    void send(InetAddress src, InetAddress gw, ByteBuffer[] payload) throws IOException;
}