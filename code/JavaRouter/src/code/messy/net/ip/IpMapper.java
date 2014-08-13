/*
 * Created on Aug 12, 2008
 */
package code.messy.net.ip;

import java.net.InetAddress;
import java.util.HashMap;

import code.messy.Receiver;
import code.messy.Registrable;
import code.messy.net.ip.IpInputPacket.Protocol;
import code.messy.util.IpAddressHelper;

/**
 * Matching destination
 * 
 * @author alei
 */
public class IpMapper implements Registrable<IpInputPacket.Protocol, Receiver<IpInputPacket>>, Receiver<IpInputPacket> {
    Receiver<IpInputPacket> defaultReceiver = null;
    
    HashMap<Byte, NetworkMap<Receiver<IpInputPacket>>> protocolMap = new HashMap<>();
    
    NetworkMap<Receiver<IpInputPacket>> networkMap = new NetworkMap<>();

    @Override
    public void receive(IpInputPacket ip) {
        Byte protocol = ip.getProtocol();
        NetworkMap<Receiver<IpInputPacket>> networkMap = protocolMap.get(protocol);
		if (networkMap != null) {
	        Receiver<IpInputPacket> ph = networkMap.getByMasking(ip.getDestinationAddress());
	        if (ph != null) {
	        	ph.receive(ip);
	        	return;
	        }
		}
        if (defaultReceiver != null) {
        	defaultReceiver.receive(ip);
        }
    }

	public void register(InetAddress dst, Protocol type, Receiver<IpInputPacket> handler) {
		NetworkMap<Receiver<IpInputPacket>> networkMap = protocolMap.get(type);
		if (networkMap == null) {
			networkMap = new NetworkMap<>();
			protocolMap.put(type.getValue(), networkMap);
		}
		networkMap.put(dst, handler);
	}
	
	public void register(NetworkNumber network, Protocol type, Receiver<IpInputPacket> handler) {
		NetworkMap<Receiver<IpInputPacket>> networkMap = protocolMap.get(type);
		if (networkMap == null) {
			networkMap = new NetworkMap<>();
			protocolMap.put(type.getValue(), networkMap);
		}
		networkMap.put(network, handler);
	}
	
	@Override
	public void register(Protocol type, Receiver<IpInputPacket> handler) {
		register(IpAddressHelper.ANY_NETWORK, type, handler);
	}

	@Override
	public void register(Receiver<IpInputPacket> handler) {
		defaultReceiver = handler;
	}
}
