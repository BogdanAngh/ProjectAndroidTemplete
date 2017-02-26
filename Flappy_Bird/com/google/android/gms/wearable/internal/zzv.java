package com.google.android.gms.wearable.internal;

import com.google.android.gms.fitness.FitnessActivities;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataItem;

public class zzv implements DataEvent {
    private int zzSq;
    private DataItem zzaUl;

    public zzv(DataEvent dataEvent) {
        this.zzSq = dataEvent.getType();
        this.zzaUl = (DataItem) dataEvent.getDataItem().freeze();
    }

    public /* synthetic */ Object freeze() {
        return zzBc();
    }

    public DataItem getDataItem() {
        return this.zzaUl;
    }

    public int getType() {
        return this.zzSq;
    }

    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        String str = getType() == 1 ? "changed" : getType() == 2 ? "deleted" : FitnessActivities.UNKNOWN;
        return "DataEventEntity{ type=" + str + ", dataitem=" + getDataItem() + " }";
    }

    public DataEvent zzBc() {
        return this;
    }
}
