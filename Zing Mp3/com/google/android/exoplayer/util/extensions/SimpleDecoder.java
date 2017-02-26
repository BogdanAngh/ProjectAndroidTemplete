package com.google.android.exoplayer.util.extensions;

import com.google.android.exoplayer.util.Assertions;
import java.util.LinkedList;

public abstract class SimpleDecoder<I extends InputBuffer, O extends OutputBuffer, E extends Exception> extends Thread implements Decoder<I, O, E> {
    private int availableInputBufferCount;
    private final I[] availableInputBuffers;
    private int availableOutputBufferCount;
    private final O[] availableOutputBuffers;
    private I dequeuedInputBuffer;
    private E exception;
    private boolean flushed;
    private final Object lock;
    private final LinkedList<I> queuedInputBuffers;
    private final LinkedList<O> queuedOutputBuffers;
    private boolean released;

    public interface EventListener<E> {
        void onDecoderError(E e);
    }

    protected abstract I createInputBuffer();

    protected abstract O createOutputBuffer();

    protected abstract E decode(I i, O o, boolean z);

    protected SimpleDecoder(I[] inputBuffers, O[] outputBuffers) {
        int i;
        this.lock = new Object();
        this.queuedInputBuffers = new LinkedList();
        this.queuedOutputBuffers = new LinkedList();
        this.availableInputBuffers = inputBuffers;
        this.availableInputBufferCount = inputBuffers.length;
        for (i = 0; i < this.availableInputBufferCount; i++) {
            this.availableInputBuffers[i] = createInputBuffer();
        }
        this.availableOutputBuffers = outputBuffers;
        this.availableOutputBufferCount = outputBuffers.length;
        for (i = 0; i < this.availableOutputBufferCount; i++) {
            this.availableOutputBuffers[i] = createOutputBuffer();
        }
    }

    protected final void setInitialInputBufferSize(int size) {
        Assertions.checkState(this.availableInputBufferCount == this.availableInputBuffers.length);
        for (InputBuffer inputBuffer : this.availableInputBuffers) {
            inputBuffer.sampleHolder.ensureSpaceForWrite(size);
        }
    }

    public final I dequeueInputBuffer() throws Exception {
        I i;
        synchronized (this.lock) {
            maybeThrowException();
            Assertions.checkState(this.dequeuedInputBuffer == null);
            if (this.availableInputBufferCount == 0) {
                i = null;
            } else {
                InputBuffer[] inputBufferArr = this.availableInputBuffers;
                int i2 = this.availableInputBufferCount - 1;
                this.availableInputBufferCount = i2;
                i = inputBufferArr[i2];
                i.reset();
                this.dequeuedInputBuffer = i;
            }
        }
        return i;
    }

    public final void queueInputBuffer(I inputBuffer) throws Exception {
        synchronized (this.lock) {
            maybeThrowException();
            Assertions.checkArgument(inputBuffer == this.dequeuedInputBuffer);
            this.queuedInputBuffers.addLast(inputBuffer);
            maybeNotifyDecodeLoop();
            this.dequeuedInputBuffer = null;
        }
    }

    public final O dequeueOutputBuffer() throws Exception {
        O o;
        synchronized (this.lock) {
            maybeThrowException();
            if (this.queuedOutputBuffers.isEmpty()) {
                o = null;
            } else {
                OutputBuffer outputBuffer = (OutputBuffer) this.queuedOutputBuffers.removeFirst();
            }
        }
        return o;
    }

    protected void releaseOutputBuffer(O outputBuffer) {
        synchronized (this.lock) {
            OutputBuffer[] outputBufferArr = this.availableOutputBuffers;
            int i = this.availableOutputBufferCount;
            this.availableOutputBufferCount = i + 1;
            outputBufferArr[i] = outputBuffer;
            maybeNotifyDecodeLoop();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void flush() {
        /*
        r4 = this;
        r1 = r4.lock;
        monitor-enter(r1);
        r0 = 1;
        r4.flushed = r0;	 Catch:{ all -> 0x0034 }
        r0 = r4.dequeuedInputBuffer;	 Catch:{ all -> 0x0034 }
        if (r0 == 0) goto L_0x0019;
    L_0x000a:
        r0 = r4.availableInputBuffers;	 Catch:{ all -> 0x0034 }
        r2 = r4.availableInputBufferCount;	 Catch:{ all -> 0x0034 }
        r3 = r2 + 1;
        r4.availableInputBufferCount = r3;	 Catch:{ all -> 0x0034 }
        r3 = r4.dequeuedInputBuffer;	 Catch:{ all -> 0x0034 }
        r0[r2] = r3;	 Catch:{ all -> 0x0034 }
        r0 = 0;
        r4.dequeuedInputBuffer = r0;	 Catch:{ all -> 0x0034 }
    L_0x0019:
        r0 = r4.queuedInputBuffers;	 Catch:{ all -> 0x0034 }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x0034 }
        if (r0 != 0) goto L_0x0037;
    L_0x0021:
        r2 = r4.availableInputBuffers;	 Catch:{ all -> 0x0034 }
        r3 = r4.availableInputBufferCount;	 Catch:{ all -> 0x0034 }
        r0 = r3 + 1;
        r4.availableInputBufferCount = r0;	 Catch:{ all -> 0x0034 }
        r0 = r4.queuedInputBuffers;	 Catch:{ all -> 0x0034 }
        r0 = r0.removeFirst();	 Catch:{ all -> 0x0034 }
        r0 = (com.google.android.exoplayer.util.extensions.InputBuffer) r0;	 Catch:{ all -> 0x0034 }
        r2[r3] = r0;	 Catch:{ all -> 0x0034 }
        goto L_0x0019;
    L_0x0034:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0034 }
        throw r0;
    L_0x0037:
        r0 = r4.queuedOutputBuffers;	 Catch:{ all -> 0x0034 }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x0034 }
        if (r0 != 0) goto L_0x0052;
    L_0x003f:
        r2 = r4.availableOutputBuffers;	 Catch:{ all -> 0x0034 }
        r3 = r4.availableOutputBufferCount;	 Catch:{ all -> 0x0034 }
        r0 = r3 + 1;
        r4.availableOutputBufferCount = r0;	 Catch:{ all -> 0x0034 }
        r0 = r4.queuedOutputBuffers;	 Catch:{ all -> 0x0034 }
        r0 = r0.removeFirst();	 Catch:{ all -> 0x0034 }
        r0 = (com.google.android.exoplayer.util.extensions.OutputBuffer) r0;	 Catch:{ all -> 0x0034 }
        r2[r3] = r0;	 Catch:{ all -> 0x0034 }
        goto L_0x0037;
    L_0x0052:
        monitor-exit(r1);	 Catch:{ all -> 0x0034 }
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer.util.extensions.SimpleDecoder.flush():void");
    }

    public void release() {
        synchronized (this.lock) {
            this.released = true;
            this.lock.notify();
        }
        try {
            join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void maybeThrowException() throws Exception {
        if (this.exception != null) {
            throw this.exception;
        }
    }

    private void maybeNotifyDecodeLoop() {
        if (canDecodeBuffer()) {
            this.lock.notify();
        }
    }

    public final void run() {
        do {
            try {
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        } while (decode());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean decode() throws java.lang.InterruptedException {
        /*
        r9 = this;
        r8 = 134217728; // 0x8000000 float:3.85186E-34 double:6.63123685E-316;
        r4 = 1;
        r3 = 0;
        r5 = r9.lock;
        monitor-enter(r5);
    L_0x0007:
        r6 = r9.released;	 Catch:{ all -> 0x0017 }
        if (r6 != 0) goto L_0x001a;
    L_0x000b:
        r6 = r9.canDecodeBuffer();	 Catch:{ all -> 0x0017 }
        if (r6 != 0) goto L_0x001a;
    L_0x0011:
        r6 = r9.lock;	 Catch:{ all -> 0x0017 }
        r6.wait();	 Catch:{ all -> 0x0017 }
        goto L_0x0007;
    L_0x0017:
        r3 = move-exception;
        monitor-exit(r5);	 Catch:{ all -> 0x0017 }
        throw r3;
    L_0x001a:
        r6 = r9.released;	 Catch:{ all -> 0x0017 }
        if (r6 == 0) goto L_0x0020;
    L_0x001e:
        monitor-exit(r5);	 Catch:{ all -> 0x0017 }
    L_0x001f:
        return r3;
    L_0x0020:
        r6 = r9.queuedInputBuffers;	 Catch:{ all -> 0x0017 }
        r0 = r6.removeFirst();	 Catch:{ all -> 0x0017 }
        r0 = (com.google.android.exoplayer.util.extensions.InputBuffer) r0;	 Catch:{ all -> 0x0017 }
        r6 = r9.availableOutputBuffers;	 Catch:{ all -> 0x0017 }
        r7 = r9.availableOutputBufferCount;	 Catch:{ all -> 0x0017 }
        r7 = r7 + -1;
        r9.availableOutputBufferCount = r7;	 Catch:{ all -> 0x0017 }
        r1 = r6[r7];	 Catch:{ all -> 0x0017 }
        r2 = r9.flushed;	 Catch:{ all -> 0x0017 }
        r6 = 0;
        r9.flushed = r6;	 Catch:{ all -> 0x0017 }
        monitor-exit(r5);	 Catch:{ all -> 0x0017 }
        r1.reset();
        r5 = r0.getFlag(r4);
        if (r5 == 0) goto L_0x0069;
    L_0x0041:
        r1.setFlag(r4);
    L_0x0044:
        r5 = r9.lock;
        monitor-enter(r5);
        r3 = r9.flushed;	 Catch:{ all -> 0x008a }
        if (r3 != 0) goto L_0x0052;
    L_0x004b:
        r3 = 2;
        r3 = r1.getFlag(r3);	 Catch:{ all -> 0x008a }
        if (r3 == 0) goto L_0x0084;
    L_0x0052:
        r3 = r9.availableOutputBuffers;	 Catch:{ all -> 0x008a }
        r6 = r9.availableOutputBufferCount;	 Catch:{ all -> 0x008a }
        r7 = r6 + 1;
        r9.availableOutputBufferCount = r7;	 Catch:{ all -> 0x008a }
        r3[r6] = r1;	 Catch:{ all -> 0x008a }
    L_0x005c:
        r3 = r9.availableInputBuffers;	 Catch:{ all -> 0x008a }
        r6 = r9.availableInputBufferCount;	 Catch:{ all -> 0x008a }
        r7 = r6 + 1;
        r9.availableInputBufferCount = r7;	 Catch:{ all -> 0x008a }
        r3[r6] = r0;	 Catch:{ all -> 0x008a }
        monitor-exit(r5);	 Catch:{ all -> 0x008a }
        r3 = r4;
        goto L_0x001f;
    L_0x0069:
        r5 = r0.getFlag(r8);
        if (r5 == 0) goto L_0x0072;
    L_0x006f:
        r1.setFlag(r8);
    L_0x0072:
        r5 = r9.decode(r0, r1, r2);
        r9.exception = r5;
        r5 = r9.exception;
        if (r5 == 0) goto L_0x0044;
    L_0x007c:
        r4 = r9.lock;
        monitor-enter(r4);
        monitor-exit(r4);	 Catch:{ all -> 0x0081 }
        goto L_0x001f;
    L_0x0081:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0081 }
        throw r3;
    L_0x0084:
        r3 = r9.queuedOutputBuffers;	 Catch:{ all -> 0x008a }
        r3.addLast(r1);	 Catch:{ all -> 0x008a }
        goto L_0x005c;
    L_0x008a:
        r3 = move-exception;
        monitor-exit(r5);	 Catch:{ all -> 0x008a }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer.util.extensions.SimpleDecoder.decode():boolean");
    }

    private boolean canDecodeBuffer() {
        return !this.queuedInputBuffers.isEmpty() && this.availableOutputBufferCount > 0;
    }
}
