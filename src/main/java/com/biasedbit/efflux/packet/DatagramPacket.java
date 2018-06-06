package com.biasedbit.efflux.packet;

import java.net.SocketAddress;

public class DatagramPacket {
    private SocketAddress remoteAddress;

    public SocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(SocketAddress address) {
        this.remoteAddress = address;
    }
}
