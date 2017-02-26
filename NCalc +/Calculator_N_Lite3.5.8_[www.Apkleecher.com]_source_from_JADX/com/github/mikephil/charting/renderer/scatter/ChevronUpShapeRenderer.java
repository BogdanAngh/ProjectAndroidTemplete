package com.github.mikephil.charting.renderer.scatter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import com.github.mikephil.charting.buffer.ScatterBuffer;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class ChevronUpShapeRenderer implements ShapeRenderer {
    public void renderShape(Canvas c, IScatterDataSet dataSet, ViewPortHandler viewPortHandler, ScatterBuffer buffer, Paint renderPaint, float shapeSize) {
        float shapeHalf = shapeSize / 2.0f;
        renderPaint.setStyle(Style.STROKE);
        renderPaint.setStrokeWidth(Utils.convertDpToPixel(1.0f));
        int i = 0;
        while (i < buffer.size() && viewPortHandler.isInBoundsRight(buffer.buffer[i])) {
            if (viewPortHandler.isInBoundsLeft(buffer.buffer[i]) && viewPortHandler.isInBoundsY(buffer.buffer[i + 1])) {
                renderPaint.setColor(dataSet.getColor(i / 2));
                c.drawLine(buffer.buffer[i], buffer.buffer[i + 1] - (2.0f * shapeHalf), (2.0f * shapeHalf) + buffer.buffer[i], buffer.buffer[i + 1], renderPaint);
                c.drawLine(buffer.buffer[i], buffer.buffer[i + 1] - (2.0f * shapeHalf), buffer.buffer[i] - (2.0f * shapeHalf), buffer.buffer[i + 1], renderPaint);
            }
            i += 2;
        }
    }
}
