package com.google.android.gms.analytics.internal;

import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.util.zzt;
import java.util.HashSet;
import java.util.Set;

public class zzr {
    private final zzf ao;
    private volatile Boolean eb;
    private String ec;
    private Set<Integer> ed;

    protected zzr(zzf com_google_android_gms_analytics_internal_zzf) {
        zzaa.zzy(com_google_android_gms_analytics_internal_zzf);
        this.ao = com_google_android_gms_analytics_internal_zzf;
    }

    public boolean zzaef() {
        if (this.eb == null) {
            synchronized (this) {
                if (this.eb == null) {
                    ApplicationInfo applicationInfo = this.ao.getContext().getApplicationInfo();
                    String zzayz = zzt.zzayz();
                    if (applicationInfo != null) {
                        String str = applicationInfo.processName;
                        boolean z = str != null && str.equals(zzayz);
                        this.eb = Boolean.valueOf(z);
                    }
                    if ((this.eb == null || !this.eb.booleanValue()) && "com.google.android.gms.analytics".equals(zzayz)) {
                        this.eb = Boolean.TRUE;
                    }
                    if (this.eb == null) {
                        this.eb = Boolean.TRUE;
                        this.ao.zzaca().zzew("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.eb.booleanValue();
    }

    public boolean zzaeg() {
        return ((Boolean) zzy.em.get()).booleanValue();
    }

    public int zzaeh() {
        return ((Integer) zzy.eF.get()).intValue();
    }

    public int zzaei() {
        return ((Integer) zzy.eJ.get()).intValue();
    }

    public int zzaej() {
        return ((Integer) zzy.eK.get()).intValue();
    }

    public int zzaek() {
        return ((Integer) zzy.eL.get()).intValue();
    }

    public long zzael() {
        return ((Long) zzy.eu.get()).longValue();
    }

    public long zzaem() {
        return ((Long) zzy.et.get()).longValue();
    }

    public long zzaen() {
        return ((Long) zzy.ex.get()).longValue();
    }

    public long zzaeo() {
        return ((Long) zzy.ey.get()).longValue();
    }

    public int zzaep() {
        return ((Integer) zzy.ez.get()).intValue();
    }

    public int zzaeq() {
        return ((Integer) zzy.eA.get()).intValue();
    }

    public long zzaer() {
        return (long) ((Integer) zzy.eN.get()).intValue();
    }

    public String zzaes() {
        return (String) zzy.eC.get();
    }

    public String zzaet() {
        return (String) zzy.eB.get();
    }

    public String zzaeu() {
        return (String) zzy.eD.get();
    }

    public String zzaev() {
        return (String) zzy.eE.get();
    }

    public zzm zzaew() {
        return zzm.zzfb((String) zzy.eG.get());
    }

    public zzo zzaex() {
        return zzo.zzfc((String) zzy.eH.get());
    }

    public Set<Integer> zzaey() {
        String str = (String) zzy.eM.get();
        if (this.ed == null || this.ec == null || !this.ec.equals(str)) {
            String[] split = TextUtils.split(str, ",");
            Set hashSet = new HashSet();
            for (String parseInt : split) {
                try {
                    hashSet.add(Integer.valueOf(Integer.parseInt(parseInt)));
                } catch (NumberFormatException e) {
                }
            }
            this.ec = str;
            this.ed = hashSet;
        }
        return this.ed;
    }

    public long zzaez() {
        return ((Long) zzy.eV.get()).longValue();
    }

    public long zzafa() {
        return ((Long) zzy.eW.get()).longValue();
    }

    public long zzafb() {
        return ((Long) zzy.eZ.get()).longValue();
    }

    public int zzafc() {
        return ((Integer) zzy.eq.get()).intValue();
    }

    public int zzafd() {
        return ((Integer) zzy.es.get()).intValue();
    }

    public String zzafe() {
        return "google_analytics_v4.db";
    }

    public String zzaff() {
        return "google_analytics2_v4.db";
    }

    public int zzafg() {
        return ((Integer) zzy.eP.get()).intValue();
    }

    public int zzafh() {
        return ((Integer) zzy.eQ.get()).intValue();
    }

    public long zzafi() {
        return ((Long) zzy.eR.get()).longValue();
    }

    public long zzafj() {
        return ((Long) zzy.fa.get()).longValue();
    }
}
