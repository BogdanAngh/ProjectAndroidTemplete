package com.github.mikephil.charting.renderer.scatter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import com.github.mikephil.charting.buffer.ScatterBuffer;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class TriangleShapeRenderer implements ShapeRenderer {
    protected Path mTrianglePathBuffer;

    public TriangleShapeRenderer() {
        this.mTrianglePathBuffer = new Path();
    }

    public void renderShape(Canvas c, IScatterDataSet dataSet, ViewPortHandler viewPortHandler, ScatterBuffer buffer, Paint renderPaint, float shapeSize) {
        float shapeHalf = shapeSize / 2.0f;
        float shapeStrokeSize = (shapeSize - (Utils.convertDpToPixel(dataSet.getScatterShapeHoleRadius()) * 2.0f)) / 2.0f;
        int shapeHoleColor = dataSet.getScatterShapeHoleColor();
        renderPaint.setStyle(Style.FILL);
        Path tri = this.mTrianglePathBuffer;
        tri.reset();
        int i = 0;
        while (i < buffer.size()) {
            if (viewPortHandler.isInBoundsRight(buffer.buffer[i])) {
                if (viewPortHandler.isInBoundsLeft(buffer.buffer[i])) {
                    if (viewPortHandler.isInBoundsY(buffer.buffer[i + 1])) {
                        renderPaint.setColor(dataSet.getColor(i / 2));
                        tri.moveTo(buffer.buffer[i], buffer.buffer[i + 1] - shapeHalf);
                        tri.lineTo(buffer.buffer[i] + shapeHalf, buffer.buffer[i + 1] + shapeHalf);
                        tri.lineTo(buffer.buffer[i] - shapeHalf, buffer.buffer[i + 1] + shapeHalf);
                        if (((double) shapeSize) > 0.0d) {
                            tri.lineTo(buffer.buffer[i], buffer.buffer[i + 1] - shapeHalf);
                            tri.moveTo((buffer.buffer[i] - shapeHalf) + shapeStrokeSize, (buffer.buffer[i + 1] + shapeHalf) - shapeStrokeSize);
                            tri.lineTo((buffer.buffer[i] + shapeHalf) - shapeStrokeSize, (buffer.buffer[i + 1] + shapeHalf) - shapeStrokeSize);
                            tri.lineTo(buffer.buffer[i], (buffer.buffer[i + 1] - shapeHalf) + shapeStrokeSize);
                            tri.lineTo((buffer.buffer[i] - shapeHalf) + shapeStrokeSize, (buffer.buffer[i + 1] + shapeHalf) - shapeStrokeSize);
                        }
                        tri.close();
                        c.drawPath(tri, renderPaint);
                        tri.reset();
                        if (((double) shapeSize) > 0.0d && shapeHoleColor != ColorTemplate.COLOR_NONE) {
                            renderPaint.setColor(shapeHoleColor);
                            tri.moveTo(buffer.buffer[i], (buffer.buffer[i + 1] - shapeHalf) + shapeStrokeSize);
                            tri.lineTo((buffer.buffer[i] + shapeHalf) - shapeStrokeSize, (buffer.buffer[i + 1] + shapeHalf) - shapeStrokeSize);
                            tri.lineTo((buffer.buffer[i] - shapeHalf) + shapeStrokeSize, (buffer.buffer[i + 1] + shapeHalf) - shapeStrokeSize);
                            tri.close();
                            c.drawPath(tri, renderPaint);
                            tri.reset();
                        }
                    }
                }
                i += 2;
            } else {
                return;
            }
        }
    }
}
