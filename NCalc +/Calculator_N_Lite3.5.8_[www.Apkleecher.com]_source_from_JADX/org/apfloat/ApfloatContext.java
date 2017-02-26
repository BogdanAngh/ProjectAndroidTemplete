package org.apfloat;

import android.support.v4.media.session.PlaybackStateCompat;
import com.example.duy.calculator.math_eval.Constants;
import io.github.kexanie.library.BuildConfig;
import java.util.Enumeration;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apfloat.spi.BuilderFactory;
import org.apfloat.spi.FilenameGenerator;
import org.apfloat.spi.Util;
import org.matheclipse.core.interfaces.IExpr;

public class ApfloatContext implements Cloneable {
    public static final String BLOCK_SIZE = "blockSize";
    public static final String BUILDER_FACTORY = "builderFactory";
    public static final String CACHE_BURST = "cacheBurst";
    public static final String CACHE_L1_SIZE = "cacheL1Size";
    public static final String CACHE_L2_SIZE = "cacheL2Size";
    public static final String CLEANUP_AT_EXIT = "cleanupAtExit";
    public static final String DEFAULT_RADIX = "defaultRadix";
    public static final String FILE_INITIAL_VALUE = "fileInitialValue";
    public static final String FILE_PATH = "filePath";
    public static final String FILE_SUFFIX = "fileSuffix";
    public static final String MAX_MEMORY_BLOCK_SIZE = "maxMemoryBlockSize";
    public static final String MEMORY_THRESHOLD = "memoryThreshold";
    @Deprecated
    public static final String MEMORY_TRESHOLD = "memoryTreshold";
    public static final String NUMBER_OF_PROCESSORS = "numberOfProcessors";
    public static final String SHARED_MEMORY_TRESHOLD = "sharedMemoryTreshold";
    private static ExecutorService defaultExecutorService;
    private static Properties defaultProperties;
    private static ApfloatContext globalContext;
    private static Map<Thread, ApfloatContext> threadContexts;
    private volatile ConcurrentHashMap<String, Object> attributes;
    private volatile int blockSize;
    private volatile BuilderFactory builderFactory;
    private volatile int cacheBurst;
    private volatile int cacheL1Size;
    private volatile int cacheL2Size;
    private volatile CleanupThread cleanupThread;
    private volatile int defaultRadix;
    private volatile ExecutorService executorService;
    private volatile FilenameGenerator filenameGenerator;
    private volatile long maxMemoryBlockSize;
    private volatile long memoryThreshold;
    private volatile int numberOfProcessors;
    private volatile Properties properties;
    private volatile Object sharedMemoryLock;
    private volatile long sharedMemoryTreshold;

    public ApfloatContext(Properties properties) throws ApfloatConfigurationException {
        this.sharedMemoryLock = new Object();
        this.executorService = defaultExecutorService;
        this.attributes = new ConcurrentHashMap();
        this.properties = (Properties) defaultProperties.clone();
        this.properties.putAll(properties);
        setProperties(this.properties);
    }

    public static ApfloatContext getContext() {
        ApfloatContext ctx = getThreadContext();
        if (ctx == null) {
            return getGlobalContext();
        }
        return ctx;
    }

    public static ApfloatContext getGlobalContext() {
        return globalContext;
    }

    public static ApfloatContext getThreadContext() {
        if (threadContexts.isEmpty()) {
            return null;
        }
        return getThreadContext(Thread.currentThread());
    }

    public static ApfloatContext getThreadContext(Thread thread) {
        return (ApfloatContext) threadContexts.get(thread);
    }

    public static void setThreadContext(ApfloatContext threadContext) {
        setThreadContext(threadContext, Thread.currentThread());
    }

    public static void setThreadContext(ApfloatContext threadContext, Thread thread) {
        threadContexts.put(thread, threadContext);
    }

    public static void removeThreadContext() {
        removeThreadContext(Thread.currentThread());
    }

    public static void removeThreadContext(Thread thread) {
        threadContexts.remove(thread);
    }

    public static void clearThreadContexts() {
        threadContexts.clear();
    }

    public BuilderFactory getBuilderFactory() {
        return this.builderFactory;
    }

    public void setBuilderFactory(BuilderFactory builderFactory) {
        this.properties.setProperty(BUILDER_FACTORY, builderFactory.getClass().getName());
        this.builderFactory = builderFactory;
        if (this.cleanupThread != null) {
            this.cleanupThread.setBuilderFactory(builderFactory);
        }
    }

    public FilenameGenerator getFilenameGenerator() {
        return this.filenameGenerator;
    }

    public void setFilenameGenerator(FilenameGenerator filenameGenerator) {
        this.properties.setProperty(FILE_PATH, filenameGenerator.getPath());
        this.properties.setProperty(FILE_INITIAL_VALUE, String.valueOf(filenameGenerator.getInitialValue()));
        this.properties.setProperty(FILE_SUFFIX, filenameGenerator.getSuffix());
        this.filenameGenerator = filenameGenerator;
    }

    public int getDefaultRadix() {
        return this.defaultRadix;
    }

    public void setDefaultRadix(int radix) {
        radix = Math.min(Math.max(radix, 2), 36);
        this.properties.setProperty(DEFAULT_RADIX, String.valueOf(radix));
        this.defaultRadix = radix;
    }

    public long getMaxMemoryBlockSize() {
        return this.maxMemoryBlockSize;
    }

    public void setMaxMemoryBlockSize(long maxMemoryBlockSize) {
        maxMemoryBlockSize = Util.round23down(Math.max(maxMemoryBlockSize, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH));
        this.properties.setProperty(MAX_MEMORY_BLOCK_SIZE, String.valueOf(maxMemoryBlockSize));
        this.maxMemoryBlockSize = maxMemoryBlockSize;
    }

    public int getCacheL1Size() {
        return this.cacheL1Size;
    }

    public void setCacheL1Size(int cacheL1Size) {
        cacheL1Size = Util.round2down(Math.max(cacheL1Size, IExpr.PATTERNID));
        this.properties.setProperty(CACHE_L1_SIZE, String.valueOf(cacheL1Size));
        this.cacheL1Size = cacheL1Size;
    }

    public int getCacheL2Size() {
        return this.cacheL2Size;
    }

    public void setCacheL2Size(int cacheL2Size) {
        cacheL2Size = Util.round2down(Math.max(cacheL2Size, IExpr.METHODSYMBOLID));
        this.properties.setProperty(CACHE_L2_SIZE, String.valueOf(cacheL2Size));
        this.cacheL2Size = cacheL2Size;
    }

    public int getCacheBurst() {
        return this.cacheBurst;
    }

    public void setCacheBurst(int cacheBurst) {
        cacheBurst = Util.round2down(Math.max(cacheBurst, 8));
        this.properties.setProperty(CACHE_BURST, String.valueOf(cacheBurst));
        this.cacheBurst = cacheBurst;
    }

    @Deprecated
    public int getMemoryTreshold() {
        return (int) Math.min(2147483647L, getMemoryThreshold());
    }

    @Deprecated
    public void setMemoryTreshold(int memoryThreshold) {
        setMemoryThreshold((long) memoryThreshold);
    }

    public long getMemoryThreshold() {
        return this.memoryThreshold;
    }

    public void setMemoryThreshold(long memoryThreshold) {
        memoryThreshold = Math.max(memoryThreshold, 128);
        this.properties.setProperty(MEMORY_TRESHOLD, String.valueOf(memoryThreshold));
        this.properties.setProperty(MEMORY_THRESHOLD, String.valueOf(memoryThreshold));
        this.memoryThreshold = memoryThreshold;
    }

    public long getSharedMemoryTreshold() {
        return this.sharedMemoryTreshold;
    }

    public void setSharedMemoryTreshold(long sharedMemoryTreshold) {
        sharedMemoryTreshold = Math.max(sharedMemoryTreshold, 128);
        this.properties.setProperty(SHARED_MEMORY_TRESHOLD, String.valueOf(sharedMemoryTreshold));
        this.sharedMemoryTreshold = sharedMemoryTreshold;
    }

    public int getBlockSize() {
        return this.blockSize;
    }

    public void setBlockSize(int blockSize) {
        blockSize = Util.round2down(Math.max(blockSize, IExpr.SYMBOLID));
        this.properties.setProperty(BLOCK_SIZE, String.valueOf(blockSize));
        this.blockSize = blockSize;
    }

    public int getNumberOfProcessors() {
        return this.numberOfProcessors;
    }

    public void setNumberOfProcessors(int numberOfProcessors) {
        numberOfProcessors = Math.max(numberOfProcessors, 1);
        this.properties.setProperty(NUMBER_OF_PROCESSORS, String.valueOf(numberOfProcessors));
        this.numberOfProcessors = numberOfProcessors;
    }

    public boolean getCleanupAtExit() {
        return this.cleanupThread != null;
    }

    public void setCleanupAtExit(boolean cleanupAtExit) {
        this.properties.setProperty(CLEANUP_AT_EXIT, String.valueOf(cleanupAtExit));
        if (cleanupAtExit && this.cleanupThread == null) {
            this.cleanupThread = new CleanupThread();
            this.cleanupThread.setBuilderFactory(this.builderFactory);
            Runtime.getRuntime().addShutdownHook(this.cleanupThread);
        } else if (!cleanupAtExit && this.cleanupThread != null) {
            Runtime.getRuntime().removeShutdownHook(this.cleanupThread);
            this.cleanupThread = null;
        }
    }

    public String getProperty(String propertyName) {
        return this.properties.getProperty(propertyName);
    }

    public String getProperty(String propertyName, String defaultValue) {
        return this.properties.getProperty(propertyName, defaultValue);
    }

    public void setProperty(String propertyName, String propertyValue) throws ApfloatConfigurationException {
        try {
            if (propertyName.equals(BUILDER_FACTORY)) {
                setBuilderFactory((BuilderFactory) Class.forName(propertyValue).newInstance());
            } else if (propertyName.equals(DEFAULT_RADIX)) {
                setDefaultRadix(Integer.parseInt(propertyValue));
            } else if (propertyName.equals(MAX_MEMORY_BLOCK_SIZE)) {
                setMaxMemoryBlockSize(Long.parseLong(propertyValue));
            } else if (propertyName.equals(CACHE_L1_SIZE)) {
                setCacheL1Size(Integer.parseInt(propertyValue));
            } else if (propertyName.equals(CACHE_L2_SIZE)) {
                setCacheL2Size(Integer.parseInt(propertyValue));
            } else if (propertyName.equals(CACHE_BURST)) {
                setCacheBurst(Integer.parseInt(propertyValue));
            } else if (propertyName.equals(MEMORY_TRESHOLD) || propertyName.equals(MEMORY_THRESHOLD)) {
                setMemoryThreshold(Long.parseLong(propertyValue));
            } else if (propertyName.equals(SHARED_MEMORY_TRESHOLD)) {
                setSharedMemoryTreshold(Long.parseLong(propertyValue));
            } else if (propertyName.equals(BLOCK_SIZE)) {
                setBlockSize(Integer.parseInt(propertyValue));
            } else if (propertyName.equals(NUMBER_OF_PROCESSORS)) {
                setNumberOfProcessors(Integer.parseInt(propertyValue));
            } else if (propertyName.equals(FILE_PATH)) {
                setFilenameGenerator(new FilenameGenerator(propertyValue, getProperty(FILE_INITIAL_VALUE), getProperty(FILE_SUFFIX)));
            } else if (propertyName.equals(FILE_INITIAL_VALUE)) {
                setFilenameGenerator(new FilenameGenerator(getProperty(FILE_PATH), propertyValue, getProperty(FILE_SUFFIX)));
            } else if (propertyName.equals(FILE_SUFFIX)) {
                setFilenameGenerator(new FilenameGenerator(getProperty(FILE_PATH), getProperty(FILE_INITIAL_VALUE), propertyValue));
            } else if (!propertyName.equals(CLEANUP_AT_EXIT)) {
                this.properties.setProperty(propertyName, propertyValue);
            }
        } catch (Exception e) {
            throw new ApfloatConfigurationException("Error setting property \"" + propertyName + "\" to value \"" + propertyValue + Letters.QUOTE, e);
        }
    }

    public Properties getProperties() {
        return (Properties) this.properties.clone();
    }

    public Object getSharedMemoryLock() {
        return this.sharedMemoryLock;
    }

    public void setSharedMemoryLock(Object lock) {
        this.sharedMemoryLock = lock;
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    public Object setAttribute(String name, Object value) {
        return this.attributes.put(name, value);
    }

    public Object removeAttribute(String name) {
        return this.attributes.remove(name);
    }

    public Enumeration<String> getAttributeNames() {
        return this.attributes.keys();
    }

    public static Properties loadProperties() throws ApfloatRuntimeException {
        Properties properties = new Properties();
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("apfloat");
            Enumeration<String> keys = resourceBundle.getKeys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                properties.setProperty(key, resourceBundle.getString(key));
            }
        } catch (MissingResourceException e) {
        }
        return properties;
    }

    public static ExecutorService getDefaultExecutorService() {
        ThreadFactory threadFactory = new 1();
        int numberOfThreads = Math.max(1, getContext().getNumberOfProcessors() - 1);
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(numberOfThreads, numberOfThreads, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), threadFactory);
        executorService.allowCoreThreadTimeOut(true);
        return executorService;
    }

    public void setProperties(Properties properties) throws ApfloatConfigurationException {
        Enumeration<?> keys = properties.propertyNames();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            setProperty(key, properties.getProperty(key));
        }
    }

    public Object clone() {
        try {
            ApfloatContext ctx = (ApfloatContext) super.clone();
            ctx.properties = (Properties) ctx.properties.clone();
            ctx.attributes = new ConcurrentHashMap(ctx.attributes);
            return ctx;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    static {
        threadContexts = new ConcurrentWeakHashMap();
        defaultProperties = new Properties();
        long totalMemory = Runtime.getRuntime().maxMemory();
        long maxMemoryBlockSize = Util.round23down((totalMemory / 5) * 4);
        int numberOfProcessors = Runtime.getRuntime().availableProcessors();
        long memoryThreshold = Math.max(maxMemoryBlockSize >> 10, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH);
        int blockSize = Util.round2down((int) Math.min(memoryThreshold, 2147483647L));
        defaultProperties.setProperty(BUILDER_FACTORY, "org.apfloat.internal." + (totalMemory >= 4294967296L ? "Long" : "Int") + "BuilderFactory");
        defaultProperties.setProperty(DEFAULT_RADIX, "10");
        defaultProperties.setProperty(MAX_MEMORY_BLOCK_SIZE, String.valueOf(maxMemoryBlockSize));
        defaultProperties.setProperty(CACHE_L1_SIZE, "8192");
        defaultProperties.setProperty(CACHE_L2_SIZE, "262144");
        defaultProperties.setProperty(CACHE_BURST, "32");
        defaultProperties.setProperty(MEMORY_THRESHOLD, String.valueOf(memoryThreshold));
        defaultProperties.setProperty(SHARED_MEMORY_TRESHOLD, String.valueOf((maxMemoryBlockSize / ((long) numberOfProcessors)) / 32));
        defaultProperties.setProperty(BLOCK_SIZE, String.valueOf(blockSize));
        defaultProperties.setProperty(NUMBER_OF_PROCESSORS, String.valueOf(numberOfProcessors));
        defaultProperties.setProperty(FILE_PATH, BuildConfig.FLAVOR);
        defaultProperties.setProperty(FILE_INITIAL_VALUE, Constants.ZERO);
        defaultProperties.setProperty(FILE_SUFFIX, ".ap");
        defaultProperties.setProperty(CLEANUP_AT_EXIT, Constants.TRUE);
        globalContext = new ApfloatContext(loadProperties());
        defaultExecutorService = getDefaultExecutorService();
        globalContext.setExecutorService(defaultExecutorService);
    }
}
