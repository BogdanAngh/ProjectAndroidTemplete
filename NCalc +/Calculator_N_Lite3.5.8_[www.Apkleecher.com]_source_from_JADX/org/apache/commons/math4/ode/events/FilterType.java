package org.apache.commons.math4.ode.events;

import org.apache.commons.math4.exception.MathInternalError;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public enum FilterType {
    TRIGGER_ONLY_DECREASING_EVENTS {
        private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$ode$events$Transformer;

        static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$ode$events$Transformer() {
            int[] iArr = $SWITCH_TABLE$org$apache$commons$math4$ode$events$Transformer;
            if (iArr == null) {
                iArr = new int[Transformer.values().length];
                try {
                    iArr[Transformer.MAX.ordinal()] = 5;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[Transformer.MIN.ordinal()] = 4;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[Transformer.MINUS.ordinal()] = 3;
                } catch (NoSuchFieldError e3) {
                }
                try {
                    iArr[Transformer.PLUS.ordinal()] = 2;
                } catch (NoSuchFieldError e4) {
                }
                try {
                    iArr[Transformer.UNINITIALIZED.ordinal()] = 1;
                } catch (NoSuchFieldError e5) {
                }
                $SWITCH_TABLE$org$apache$commons$math4$ode$events$Transformer = iArr;
            }
            return iArr;
        }

        protected boolean getTriggeredIncreasing() {
            return false;
        }

        protected Transformer selectTransformer(Transformer previous, double g, boolean forward) {
            if (forward) {
                switch ($SWITCH_TABLE$org$apache$commons$math4$ode$events$Transformer()[previous.ordinal()]) {
                    case ValueServer.REPLAY_MODE /*1*/:
                        if (g > 0.0d) {
                            return Transformer.MAX;
                        }
                        if (g < 0.0d) {
                            return Transformer.PLUS;
                        }
                        return Transformer.UNINITIALIZED;
                    case IExpr.DOUBLEID /*2*/:
                        if (g >= 0.0d) {
                            return Transformer.MIN;
                        }
                        return previous;
                    case ValueServer.EXPONENTIAL_MODE /*3*/:
                        if (g >= 0.0d) {
                            return Transformer.MAX;
                        }
                        return previous;
                    case IExpr.DOUBLECOMPLEXID /*4*/:
                        if (g <= 0.0d) {
                            return Transformer.MINUS;
                        }
                        return previous;
                    case ValueServer.CONSTANT_MODE /*5*/:
                        if (g <= 0.0d) {
                            return Transformer.PLUS;
                        }
                        return previous;
                    default:
                        throw new MathInternalError();
                }
            }
            switch ($SWITCH_TABLE$org$apache$commons$math4$ode$events$Transformer()[previous.ordinal()]) {
                case ValueServer.REPLAY_MODE /*1*/:
                    if (g > 0.0d) {
                        return Transformer.MINUS;
                    }
                    if (g < 0.0d) {
                        return Transformer.MIN;
                    }
                    return Transformer.UNINITIALIZED;
                case IExpr.DOUBLEID /*2*/:
                    if (g <= 0.0d) {
                        return Transformer.MAX;
                    }
                    return previous;
                case ValueServer.EXPONENTIAL_MODE /*3*/:
                    if (g <= 0.0d) {
                        return Transformer.MIN;
                    }
                    return previous;
                case IExpr.DOUBLECOMPLEXID /*4*/:
                    if (g >= 0.0d) {
                        return Transformer.PLUS;
                    }
                    return previous;
                case ValueServer.CONSTANT_MODE /*5*/:
                    if (g >= 0.0d) {
                        return Transformer.MINUS;
                    }
                    return previous;
                default:
                    throw new MathInternalError();
            }
        }
    },
    TRIGGER_ONLY_INCREASING_EVENTS {
        private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$ode$events$Transformer;

        static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$ode$events$Transformer() {
            int[] iArr = $SWITCH_TABLE$org$apache$commons$math4$ode$events$Transformer;
            if (iArr == null) {
                iArr = new int[Transformer.values().length];
                try {
                    iArr[Transformer.MAX.ordinal()] = 5;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[Transformer.MIN.ordinal()] = 4;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[Transformer.MINUS.ordinal()] = 3;
                } catch (NoSuchFieldError e3) {
                }
                try {
                    iArr[Transformer.PLUS.ordinal()] = 2;
                } catch (NoSuchFieldError e4) {
                }
                try {
                    iArr[Transformer.UNINITIALIZED.ordinal()] = 1;
                } catch (NoSuchFieldError e5) {
                }
                $SWITCH_TABLE$org$apache$commons$math4$ode$events$Transformer = iArr;
            }
            return iArr;
        }

        protected boolean getTriggeredIncreasing() {
            return true;
        }

        protected Transformer selectTransformer(Transformer previous, double g, boolean forward) {
            if (forward) {
                switch ($SWITCH_TABLE$org$apache$commons$math4$ode$events$Transformer()[previous.ordinal()]) {
                    case ValueServer.REPLAY_MODE /*1*/:
                        if (g > 0.0d) {
                            return Transformer.PLUS;
                        }
                        if (g < 0.0d) {
                            return Transformer.MIN;
                        }
                        return Transformer.UNINITIALIZED;
                    case IExpr.DOUBLEID /*2*/:
                        if (g <= 0.0d) {
                            return Transformer.MAX;
                        }
                        return previous;
                    case ValueServer.EXPONENTIAL_MODE /*3*/:
                        if (g <= 0.0d) {
                            return Transformer.MIN;
                        }
                        return previous;
                    case IExpr.DOUBLECOMPLEXID /*4*/:
                        if (g >= 0.0d) {
                            return Transformer.PLUS;
                        }
                        return previous;
                    case ValueServer.CONSTANT_MODE /*5*/:
                        if (g >= 0.0d) {
                            return Transformer.MINUS;
                        }
                        return previous;
                    default:
                        throw new MathInternalError();
                }
            }
            switch ($SWITCH_TABLE$org$apache$commons$math4$ode$events$Transformer()[previous.ordinal()]) {
                case ValueServer.REPLAY_MODE /*1*/:
                    if (g > 0.0d) {
                        return Transformer.MAX;
                    }
                    if (g < 0.0d) {
                        return Transformer.MINUS;
                    }
                    return Transformer.UNINITIALIZED;
                case IExpr.DOUBLEID /*2*/:
                    if (g >= 0.0d) {
                        return Transformer.MIN;
                    }
                    return previous;
                case ValueServer.EXPONENTIAL_MODE /*3*/:
                    if (g >= 0.0d) {
                        return Transformer.MAX;
                    }
                    return previous;
                case IExpr.DOUBLECOMPLEXID /*4*/:
                    if (g <= 0.0d) {
                        return Transformer.MINUS;
                    }
                    return previous;
                case ValueServer.CONSTANT_MODE /*5*/:
                    if (g <= 0.0d) {
                        return Transformer.PLUS;
                    }
                    return previous;
                default:
                    throw new MathInternalError();
            }
        }
    };

    protected abstract boolean getTriggeredIncreasing();

    protected abstract Transformer selectTransformer(Transformer transformer, double d, boolean z);
}
