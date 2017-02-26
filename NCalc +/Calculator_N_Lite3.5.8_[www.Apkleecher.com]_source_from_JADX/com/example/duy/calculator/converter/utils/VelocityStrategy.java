package com.example.duy.calculator.converter.utils;

import android.content.Context;
import com.example.duy.calculator.R;

public class VelocityStrategy implements Strategy {
    private Context context;

    public VelocityStrategy(Context context) {
        this.context = context;
    }

    public double Convert(String from, String to, double input) {
        if (from.equals(this.context.getResources().getString(R.string.velocityunitmilesperh)) && to.equals(this.context.getResources().getString(R.string.velocityunitkmph))) {
            return input * 1.60934d;
        }
        if (from.equals(this.context.getResources().getString(R.string.velocityunitkmph)) && to.equals(this.context.getResources().getString(R.string.velocityunitmilesperh))) {
            return input * 0.62137d;
        }
        if (from.equals(this.context.getResources().getString(R.string.velocityunitmilesperh)) && to.equals(this.context.getResources().getString(R.string.velocityunitmeterpers))) {
            return (1609.34d * input) / 3600.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.velocityunitmeterpers)) && to.equals(this.context.getResources().getString(R.string.velocityunitmilesperh))) {
            return (input * 3600.0d) / 1609.34d;
        }
        if (from.equals(this.context.getResources().getString(R.string.velocityunitmilesperh)) && to.equals(this.context.getResources().getString(R.string.velocityunitfeetpers))) {
            return (5280.0d * input) / 3600.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.velocityunitfeetpers)) && to.equals(this.context.getResources().getString(R.string.velocityunitmilesperh))) {
            return (input * 3600.0d) / 5280.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.velocityunitkmph)) && to.equals(this.context.getResources().getString(R.string.velocityunitmeterpers))) {
            return (1000.0d * input) / 3600.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.velocityunitmeterpers)) && to.equals(this.context.getResources().getString(R.string.velocityunitkmph))) {
            return (input * 3600.0d) / 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.velocityunitkmph)) && to.equals(this.context.getResources().getString(R.string.velocityunitfeetpers))) {
            return (3280.84d * input) / 3600.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.velocityunitfeetpers)) && to.equals(this.context.getResources().getString(R.string.velocityunitkmph))) {
            return (input * 3600.0d) / 3280.84d;
        }
        if (from.equals(this.context.getResources().getString(R.string.velocityunitmeterpers)) && to.equals(this.context.getResources().getString(R.string.velocityunitfeetpers))) {
            return input * 3.28084d;
        }
        if (from.equals(this.context.getResources().getString(R.string.velocityunitfeetpers)) && to.equals(this.context.getResources().getString(R.string.velocityunitmeterpers))) {
            return input / 3.28084d;
        }
        if (from.equals(to)) {
            return input;
        }
        return 0.0d;
    }

    public String getUnitDefault() {
        return this.context.getResources().getString(R.string.velocityunitmeterpers);
    }
}
