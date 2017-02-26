package com.example.duy.calculator;

import android.util.Log;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.AbstractEvalStepListener;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;

public class StepListenerExample {
    private final String TAG;

    private static class StepListener extends AbstractEvalStepListener {
        private String TAG;

        private StepListener() {
            this.TAG = StepListener.class.getName();
        }

        public void add(IExpr inputExpr, IExpr resultExpr, int recursionDepth, long iterationCounter, String hint) {
            Log.d(this.TAG, "Depth " + recursionDepth + " Iteration " + iterationCounter + ": " + inputExpr.toString() + " ==> " + resultExpr.toString());
        }
    }

    public StepListenerExample() {
        this.TAG = StepListenerExample.class.getName();
        try {
            ExprEvaluator util = new ExprEvaluator();
            EvalEngine engine = util.getEvalEngine();
            engine.setStepListener(new StepListener());
            Log.d(this.TAG, "Result: " + util.evaluate("solve({2x^2 + x + 1}, {x})").toString());
            engine.setTraceMode(false);
        } catch (SyntaxError e) {
            Log.d(this.TAG, e.getMessage());
        } catch (MathException me) {
            Log.d(this.TAG, me.getMessage());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
