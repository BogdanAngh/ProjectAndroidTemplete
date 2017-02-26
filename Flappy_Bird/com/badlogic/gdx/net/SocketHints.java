package com.badlogic.gdx.net;

import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.nearby.connection.Connections;

public class SocketHints {
    public int connectTimeout;
    public boolean keepAlive;
    public boolean linger;
    public int lingerDuration;
    public int performancePrefBandwidth;
    public int performancePrefConnectionTime;
    public int performancePrefLatency;
    public int receiveBufferSize;
    public int sendBufferSize;
    public boolean tcpNoDelay;
    public int trafficClass;

    public SocketHints() {
        this.connectTimeout = FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS;
        this.performancePrefConnectionTime = 0;
        this.performancePrefLatency = 1;
        this.performancePrefBandwidth = 0;
        this.trafficClass = 20;
        this.keepAlive = true;
        this.tcpNoDelay = true;
        this.sendBufferSize = Connections.MAX_RELIABLE_MESSAGE_LEN;
        this.receiveBufferSize = Connections.MAX_RELIABLE_MESSAGE_LEN;
        this.linger = false;
        this.lingerDuration = 0;
    }
}
