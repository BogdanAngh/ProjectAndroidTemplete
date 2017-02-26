import android.support.v4.media.session.PlaybackStateCompat;
import com.example.duy.calculator.math_eval.Constants;
import io.github.kexanie.library.BuildConfig;
import java.util.ListResourceBundle;
import org.apfloat.ApfloatContext;
import org.apfloat.spi.Util;

public class apfloat extends ListResourceBundle {
    private static final Object[][] CONTENTS;

    public Object[][] getContents() {
        return CONTENTS;
    }

    static {
        long totalMemory = Runtime.getRuntime().maxMemory();
        long maxMemoryBlockSize = Util.round23down((totalMemory / 5) * 4);
        int numberOfProcessors = Runtime.getRuntime().availableProcessors();
        int blockSize = Util.round2down((int) Math.min(Math.max(maxMemoryBlockSize >> 10, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH), 2147483647L));
        String elementType = totalMemory >= 4294967296L ? "Long" : "Int";
        contents = new Object[14][];
        contents[0] = new Object[]{ApfloatContext.BUILDER_FACTORY, "org.apfloat.internal." + elementType + "BuilderFactory"};
        contents[1] = new Object[]{ApfloatContext.DEFAULT_RADIX, "10"};
        contents[2] = new Object[]{ApfloatContext.MAX_MEMORY_BLOCK_SIZE, String.valueOf(maxMemoryBlockSize)};
        contents[3] = new Object[]{ApfloatContext.CACHE_L1_SIZE, "8192"};
        contents[4] = new Object[]{ApfloatContext.CACHE_L2_SIZE, "262144"};
        contents[5] = new Object[]{ApfloatContext.CACHE_BURST, "32"};
        contents[6] = new Object[]{ApfloatContext.MEMORY_THRESHOLD, String.valueOf(memoryThreshold)};
        contents[7] = new Object[]{ApfloatContext.SHARED_MEMORY_TRESHOLD, String.valueOf((maxMemoryBlockSize / ((long) numberOfProcessors)) / 32)};
        contents[8] = new Object[]{ApfloatContext.BLOCK_SIZE, String.valueOf(blockSize)};
        contents[9] = new Object[]{ApfloatContext.NUMBER_OF_PROCESSORS, String.valueOf(numberOfProcessors)};
        contents[10] = new Object[]{ApfloatContext.FILE_PATH, BuildConfig.FLAVOR};
        contents[11] = new Object[]{ApfloatContext.FILE_INITIAL_VALUE, Constants.ZERO};
        contents[12] = new Object[]{ApfloatContext.FILE_SUFFIX, ".ap"};
        contents[13] = new Object[]{ApfloatContext.CLEANUP_AT_EXIT, Constants.TRUE};
        CONTENTS = contents;
    }
}
