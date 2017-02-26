package com.badlogic.gdx.net;

import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.nearby.connection.Connections;

public class ServerSocketHints {
    public int acceptTimeout;
    public int backlog;
    public int performancePrefBandwidth;
    public int performancePrefConnectionTime;
    public int performancePrefLatency;
    public int receiveBufferSize;
    public boolean reuseAddress;

    public ServerSocketHints() {
        this.backlog = 16;
        this.performancePrefConnectionTime = 0;
        this.performancePrefLatency = 1;
        this.performancePrefBandwidth = 0;
        this.reuseAddress = true;
        this.acceptTimeout = FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS;
        this.receiveBufferSize = Connections.MAX_RELIABLE_MESSAGE_LEN;
    }
}
