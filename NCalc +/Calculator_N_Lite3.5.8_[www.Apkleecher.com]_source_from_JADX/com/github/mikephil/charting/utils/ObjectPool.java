package com.github.mikephil.charting.utils;

public class ObjectPool<T extends Poolable> {
    private static int ids;
    private int desiredCapacity;
    private T modelObject;
    private Object[] objects;
    private int objectsPointer;
    private int poolId;
    private float replenishPercentage;

    public static abstract class Poolable {
        public static int NO_OWNER;
        int currentOwnerId;

        protected abstract Poolable instantiate();

        public Poolable() {
            this.currentOwnerId = NO_OWNER;
        }

        static {
            NO_OWNER = -1;
        }
    }

    static {
        ids = 0;
    }

    public int getPoolId() {
        return this.poolId;
    }

    public static synchronized ObjectPool create(int withCapacity, Poolable object) {
        ObjectPool result;
        synchronized (ObjectPool.class) {
            result = new ObjectPool(withCapacity, object);
            result.poolId = ids;
            ids++;
        }
        return result;
    }

    private ObjectPool(int withCapacity, T object) {
        if (withCapacity <= 0) {
            throw new IllegalArgumentException("Object Pool must be instantiated with a capacity greater than 0!");
        }
        this.desiredCapacity = withCapacity;
        this.objects = new Object[this.desiredCapacity];
        this.objectsPointer = 0;
        this.modelObject = object;
        this.replenishPercentage = 1.0f;
        refillPool();
    }

    public void setReplenishPercentage(float percentage) {
        float p = percentage;
        if (p > 1.0f) {
            p = 1.0f;
        } else if (p < 0.0f) {
            p = 0.0f;
        }
        this.replenishPercentage = p;
    }

    public float getReplenishPercentage() {
        return this.replenishPercentage;
    }

    private void refillPool() {
        refillPool(this.replenishPercentage);
    }

    private void refillPool(float percentage) {
        int portionOfCapacity = (int) (((float) this.desiredCapacity) * percentage);
        if (portionOfCapacity < 1) {
            portionOfCapacity = 1;
        } else if (portionOfCapacity > this.desiredCapacity) {
            portionOfCapacity = this.desiredCapacity;
        }
        for (int i = 0; i < portionOfCapacity; i++) {
            this.objects[i] = this.modelObject.instantiate();
        }
        this.objectsPointer = portionOfCapacity - 1;
    }

    public synchronized T get() {
        Poolable result;
        if (this.objectsPointer == -1 && this.replenishPercentage > 0.0f) {
            refillPool();
        }
        result = (Poolable) this.objects[this.objectsPointer];
        result.currentOwnerId = Poolable.NO_OWNER;
        this.objectsPointer--;
        return result;
    }

    public synchronized void recycle(T object) {
        if (object.currentOwnerId == Poolable.NO_OWNER) {
            this.objectsPointer++;
            if (this.objectsPointer >= this.objects.length) {
                resizePool();
            }
            object.currentOwnerId = this.poolId;
            this.objects[this.objectsPointer] = object;
        } else if (object.currentOwnerId == this.poolId) {
            throw new IllegalArgumentException("The object passed is already stored in this pool!");
        } else {
            throw new IllegalArgumentException("The object to recycle already belongs to poolId " + object.currentOwnerId + ".  Object cannot belong to two different pool instances simultaneously!");
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void recycle(java.util.List<T> r7) {
        /*
        r6 = this;
        monitor-enter(r6);
    L_0x0001:
        r3 = r7.size();	 Catch:{ all -> 0x0012 }
        r4 = r6.objectsPointer;	 Catch:{ all -> 0x0012 }
        r3 = r3 + r4;
        r3 = r3 + 1;
        r4 = r6.desiredCapacity;	 Catch:{ all -> 0x0012 }
        if (r3 <= r4) goto L_0x0015;
    L_0x000e:
        r6.resizePool();	 Catch:{ all -> 0x0012 }
        goto L_0x0001;
    L_0x0012:
        r3 = move-exception;
        monitor-exit(r6);
        throw r3;
    L_0x0015:
        r2 = r7.size();	 Catch:{ all -> 0x0012 }
        r0 = 0;
    L_0x001a:
        if (r0 >= r2) goto L_0x0067;
    L_0x001c:
        r1 = r7.get(r0);	 Catch:{ all -> 0x0012 }
        r1 = (com.github.mikephil.charting.utils.ObjectPool.Poolable) r1;	 Catch:{ all -> 0x0012 }
        r3 = r1.currentOwnerId;	 Catch:{ all -> 0x0012 }
        r4 = com.github.mikephil.charting.utils.ObjectPool.Poolable.NO_OWNER;	 Catch:{ all -> 0x0012 }
        if (r3 == r4) goto L_0x0057;
    L_0x0028:
        r3 = r1.currentOwnerId;	 Catch:{ all -> 0x0012 }
        r4 = r6.poolId;	 Catch:{ all -> 0x0012 }
        if (r3 != r4) goto L_0x0036;
    L_0x002e:
        r3 = new java.lang.IllegalArgumentException;	 Catch:{ all -> 0x0012 }
        r4 = "The object passed is already stored in this pool!";
        r3.<init>(r4);	 Catch:{ all -> 0x0012 }
        throw r3;	 Catch:{ all -> 0x0012 }
    L_0x0036:
        r3 = new java.lang.IllegalArgumentException;	 Catch:{ all -> 0x0012 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0012 }
        r4.<init>();	 Catch:{ all -> 0x0012 }
        r5 = "The object to recycle already belongs to poolId ";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0012 }
        r5 = r1.currentOwnerId;	 Catch:{ all -> 0x0012 }
        r4 = r4.append(r5);	 Catch:{ all -> 0x0012 }
        r5 = ".  Object cannot belong to two different pool instances simultaneously!";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0012 }
        r4 = r4.toString();	 Catch:{ all -> 0x0012 }
        r3.<init>(r4);	 Catch:{ all -> 0x0012 }
        throw r3;	 Catch:{ all -> 0x0012 }
    L_0x0057:
        r3 = r6.poolId;	 Catch:{ all -> 0x0012 }
        r1.currentOwnerId = r3;	 Catch:{ all -> 0x0012 }
        r3 = r6.objects;	 Catch:{ all -> 0x0012 }
        r4 = r6.objectsPointer;	 Catch:{ all -> 0x0012 }
        r4 = r4 + 1;
        r4 = r4 + r0;
        r3[r4] = r1;	 Catch:{ all -> 0x0012 }
        r0 = r0 + 1;
        goto L_0x001a;
    L_0x0067:
        r3 = r6.objectsPointer;	 Catch:{ all -> 0x0012 }
        r3 = r3 + r2;
        r6.objectsPointer = r3;	 Catch:{ all -> 0x0012 }
        monitor-exit(r6);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.utils.ObjectPool.recycle(java.util.List):void");
    }

    private void resizePool() {
        int oldCapacity = this.desiredCapacity;
        this.desiredCapacity *= 2;
        Object[] temp = new Object[this.desiredCapacity];
        for (int i = 0; i < oldCapacity; i++) {
            temp[i] = this.objects[i];
        }
        this.objects = temp;
    }

    public int getPoolCapacity() {
        return this.objects.length;
    }

    public int getPoolCount() {
        return this.objectsPointer + 1;
    }
}
