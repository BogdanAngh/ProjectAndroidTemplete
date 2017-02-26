package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public class CombinedChartRenderer extends DataRenderer {
    protected WeakReference<Chart> mChart;
    protected List<Highlight> mHighlightBuffer;
    protected List<DataRenderer> mRenderers;

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$charts$CombinedChart$DrawOrder;

        static {
            $SwitchMap$com$github$mikephil$charting$charts$CombinedChart$DrawOrder = new int[DrawOrder.values().length];
            try {
                $SwitchMap$com$github$mikephil$charting$charts$CombinedChart$DrawOrder[DrawOrder.BAR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$charts$CombinedChart$DrawOrder[DrawOrder.BUBBLE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$charts$CombinedChart$DrawOrder[DrawOrder.LINE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$charts$CombinedChart$DrawOrder[DrawOrder.CANDLE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$charts$CombinedChart$DrawOrder[DrawOrder.SCATTER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public CombinedChartRenderer(CombinedChart chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(animator, viewPortHandler);
        this.mRenderers = new ArrayList(5);
        this.mHighlightBuffer = new ArrayList();
        this.mChart = new WeakReference(chart);
        createRenderers(chart, animator, viewPortHandler);
    }

    protected void createRenderers(CombinedChart chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        this.mRenderers.clear();
        for (DrawOrder order : chart.getDrawOrder()) {
            switch (1.$SwitchMap$com$github$mikephil$charting$charts$CombinedChart$DrawOrder[order.ordinal()]) {
                case ValueServer.REPLAY_MODE /*1*/:
                    if (chart.getBarData() == null) {
                        break;
                    }
                    this.mRenderers.add(new BarChartRenderer(chart, animator, viewPortHandler));
                    break;
                case IExpr.DOUBLEID /*2*/:
                    if (chart.getBubbleData() == null) {
                        break;
                    }
                    this.mRenderers.add(new BubbleChartRenderer(chart, animator, viewPortHandler));
                    break;
                case ValueServer.EXPONENTIAL_MODE /*3*/:
                    if (chart.getLineData() == null) {
                        break;
                    }
                    this.mRenderers.add(new LineChartRenderer(chart, animator, viewPortHandler));
                    break;
                case IExpr.DOUBLECOMPLEXID /*4*/:
                    if (chart.getCandleData() == null) {
                        break;
                    }
                    this.mRenderers.add(new CandleStickChartRenderer(chart, animator, viewPortHandler));
                    break;
                case ValueServer.CONSTANT_MODE /*5*/:
                    if (chart.getScatterData() == null) {
                        break;
                    }
                    this.mRenderers.add(new ScatterChartRenderer(chart, animator, viewPortHandler));
                    break;
                default:
                    break;
            }
        }
    }

    public void initBuffers() {
        for (DataRenderer renderer : this.mRenderers) {
            renderer.initBuffers();
        }
    }

    public void drawData(Canvas c) {
        for (DataRenderer renderer : this.mRenderers) {
            renderer.drawData(c);
        }
    }

    public void drawValues(Canvas c) {
        for (DataRenderer renderer : this.mRenderers) {
            renderer.drawValues(c);
        }
    }

    public void drawExtras(Canvas c) {
        for (DataRenderer renderer : this.mRenderers) {
            renderer.drawExtras(c);
        }
    }

    public void drawHighlighted(Canvas c, Highlight[] indices) {
        Chart chart = (Chart) this.mChart.get();
        if (chart != null) {
            for (DataRenderer renderer : this.mRenderers) {
                int dataIndex;
                ChartData data = null;
                if (renderer instanceof BarChartRenderer) {
                    data = ((BarChartRenderer) renderer).mChart.getBarData();
                } else if (renderer instanceof LineChartRenderer) {
                    data = ((LineChartRenderer) renderer).mChart.getLineData();
                } else if (renderer instanceof CandleStickChartRenderer) {
                    data = ((CandleStickChartRenderer) renderer).mChart.getCandleData();
                } else if (renderer instanceof ScatterChartRenderer) {
                    data = ((ScatterChartRenderer) renderer).mChart.getScatterData();
                } else if (renderer instanceof BubbleChartRenderer) {
                    data = ((BubbleChartRenderer) renderer).mChart.getBubbleData();
                }
                if (data == null) {
                    dataIndex = -1;
                } else {
                    dataIndex = ((CombinedData) chart.getData()).getAllData().indexOf(data);
                }
                this.mHighlightBuffer.clear();
                for (Highlight h : indices) {
                    if (h.getDataIndex() == dataIndex || h.getDataIndex() == -1) {
                        this.mHighlightBuffer.add(h);
                    }
                }
                renderer.drawHighlighted(c, (Highlight[]) this.mHighlightBuffer.toArray(new Highlight[this.mHighlightBuffer.size()]));
            }
        }
    }

    public DataRenderer getSubRenderer(int index) {
        if (index >= this.mRenderers.size() || index < 0) {
            return null;
        }
        return (DataRenderer) this.mRenderers.get(index);
    }

    public List<DataRenderer> getSubRenderers() {
        return this.mRenderers;
    }

    public void setSubRenderers(List<DataRenderer> renderers) {
        this.mRenderers = renderers;
    }
}
