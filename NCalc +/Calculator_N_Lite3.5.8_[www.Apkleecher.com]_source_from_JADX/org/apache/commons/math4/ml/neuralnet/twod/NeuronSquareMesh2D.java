package org.apache.commons.math4.ml.neuralnet.twod;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.exception.MathInternalError;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.ml.neuralnet.FeatureInitializer;
import org.apache.commons.math4.ml.neuralnet.Network;
import org.apache.commons.math4.ml.neuralnet.Neuron;
import org.apache.commons.math4.ml.neuralnet.SquareNeighbourhood;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public class NeuronSquareMesh2D implements Serializable {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$ml$neuralnet$SquareNeighbourhood = null;
    private static final long serialVersionUID = 1;
    private final long[][] identifiers;
    private final SquareNeighbourhood neighbourhood;
    private final Network network;
    private final int numberOfColumns;
    private final int numberOfRows;
    private final boolean wrapColumns;
    private final boolean wrapRows;

    private static class SerializationProxy implements Serializable {
        private static final long serialVersionUID = 20130226;
        private final double[][][] featuresList;
        private final SquareNeighbourhood neighbourhood;
        private final boolean wrapColumns;
        private final boolean wrapRows;

        SerializationProxy(boolean wrapRows, boolean wrapColumns, SquareNeighbourhood neighbourhood, double[][][] featuresList) {
            this.wrapRows = wrapRows;
            this.wrapColumns = wrapColumns;
            this.neighbourhood = neighbourhood;
            this.featuresList = featuresList;
        }

        private Object readResolve() {
            return new NeuronSquareMesh2D(this.wrapRows, this.wrapColumns, this.neighbourhood, this.featuresList);
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$ml$neuralnet$SquareNeighbourhood() {
        int[] iArr = $SWITCH_TABLE$org$apache$commons$math4$ml$neuralnet$SquareNeighbourhood;
        if (iArr == null) {
            iArr = new int[SquareNeighbourhood.values().length];
            try {
                iArr[SquareNeighbourhood.MOORE.ordinal()] = 2;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[SquareNeighbourhood.VON_NEUMANN.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            $SWITCH_TABLE$org$apache$commons$math4$ml$neuralnet$SquareNeighbourhood = iArr;
        }
        return iArr;
    }

    NeuronSquareMesh2D(boolean wrapRowDim, boolean wrapColDim, SquareNeighbourhood neighbourhoodType, double[][][] featuresList) {
        this.numberOfRows = featuresList.length;
        this.numberOfColumns = featuresList[0].length;
        if (this.numberOfRows < 2) {
            throw new NumberIsTooSmallException(Integer.valueOf(this.numberOfRows), Integer.valueOf(2), true);
        } else if (this.numberOfColumns < 2) {
            throw new NumberIsTooSmallException(Integer.valueOf(this.numberOfColumns), Integer.valueOf(2), true);
        } else {
            this.wrapRows = wrapRowDim;
            this.wrapColumns = wrapColDim;
            this.neighbourhood = neighbourhoodType;
            this.network = new Network(0, featuresList[0][0].length);
            this.identifiers = (long[][]) Array.newInstance(Long.TYPE, new int[]{this.numberOfRows, this.numberOfColumns});
            for (int i = 0; i < this.numberOfRows; i++) {
                for (int j = 0; j < this.numberOfColumns; j++) {
                    this.identifiers[i][j] = this.network.createNeuron(featuresList[i][j]);
                }
            }
            createLinks();
        }
    }

    public NeuronSquareMesh2D(int numRows, boolean wrapRowDim, int numCols, boolean wrapColDim, SquareNeighbourhood neighbourhoodType, FeatureInitializer[] featureInit) {
        if (numRows < 2) {
            throw new NumberIsTooSmallException(Integer.valueOf(numRows), Integer.valueOf(2), true);
        } else if (numCols < 2) {
            throw new NumberIsTooSmallException(Integer.valueOf(numCols), Integer.valueOf(2), true);
        } else {
            this.numberOfRows = numRows;
            this.wrapRows = wrapRowDim;
            this.numberOfColumns = numCols;
            this.wrapColumns = wrapColDim;
            this.neighbourhood = neighbourhoodType;
            this.identifiers = (long[][]) Array.newInstance(Long.TYPE, new int[]{this.numberOfRows, this.numberOfColumns});
            int fLen = featureInit.length;
            this.network = new Network(0, fLen);
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    double[] features = new double[fLen];
                    for (int fIndex = 0; fIndex < fLen; fIndex++) {
                        features[fIndex] = featureInit[fIndex].value();
                    }
                    this.identifiers[i][j] = this.network.createNeuron(features);
                }
            }
            createLinks();
        }
    }

    public Network getNetwork() {
        return this.network;
    }

    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    public Neuron getNeuron(int i, int j) {
        if (i < 0 || i >= this.numberOfRows) {
            throw new OutOfRangeException(Integer.valueOf(i), Integer.valueOf(0), Integer.valueOf(this.numberOfRows - 1));
        } else if (j >= 0 && j < this.numberOfColumns) {
            return this.network.getNeuron(this.identifiers[i][j]);
        } else {
            throw new OutOfRangeException(Integer.valueOf(j), Integer.valueOf(0), Integer.valueOf(this.numberOfColumns - 1));
        }
    }

    private void createLinks() {
        List<Long> linkEnd = new ArrayList();
        int iLast = this.numberOfRows - 1;
        int jLast = this.numberOfColumns - 1;
        for (int i = 0; i < this.numberOfRows; i++) {
            int j = 0;
            while (j < this.numberOfColumns) {
                linkEnd.clear();
                switch ($SWITCH_TABLE$org$apache$commons$math4$ml$neuralnet$SquareNeighbourhood()[this.neighbourhood.ordinal()]) {
                    case ValueServer.REPLAY_MODE /*1*/:
                        break;
                    case IExpr.DOUBLEID /*2*/:
                        if (i > 0) {
                            if (j > 0) {
                                linkEnd.add(Long.valueOf(this.identifiers[i - 1][j - 1]));
                            }
                            if (j < jLast) {
                                linkEnd.add(Long.valueOf(this.identifiers[i - 1][j + 1]));
                            }
                        }
                        if (i < iLast) {
                            if (j > 0) {
                                linkEnd.add(Long.valueOf(this.identifiers[i + 1][j - 1]));
                            }
                            if (j < jLast) {
                                linkEnd.add(Long.valueOf(this.identifiers[i + 1][j + 1]));
                            }
                        }
                        if (this.wrapRows) {
                            if (i == 0) {
                                if (j > 0) {
                                    linkEnd.add(Long.valueOf(this.identifiers[iLast][j - 1]));
                                }
                                if (j < jLast) {
                                    linkEnd.add(Long.valueOf(this.identifiers[iLast][j + 1]));
                                }
                            } else if (i == iLast) {
                                if (j > 0) {
                                    linkEnd.add(Long.valueOf(this.identifiers[0][j - 1]));
                                }
                                if (j < jLast) {
                                    linkEnd.add(Long.valueOf(this.identifiers[0][j + 1]));
                                }
                            }
                        }
                        if (this.wrapColumns) {
                            if (j == 0) {
                                if (i > 0) {
                                    linkEnd.add(Long.valueOf(this.identifiers[i - 1][jLast]));
                                }
                                if (i < iLast) {
                                    linkEnd.add(Long.valueOf(this.identifiers[i + 1][jLast]));
                                }
                            } else if (j == jLast) {
                                if (i > 0) {
                                    linkEnd.add(Long.valueOf(this.identifiers[i - 1][0]));
                                }
                                if (i < iLast) {
                                    linkEnd.add(Long.valueOf(this.identifiers[i + 1][0]));
                                }
                            }
                        }
                        if (this.wrapRows && this.wrapColumns) {
                            if (i != 0 || j != 0) {
                                if (i != 0 || j != jLast) {
                                    if (i != iLast || j != 0) {
                                        if (i == iLast && j == jLast) {
                                            linkEnd.add(Long.valueOf(this.identifiers[0][0]));
                                            break;
                                        }
                                    }
                                    linkEnd.add(Long.valueOf(this.identifiers[0][jLast]));
                                    break;
                                }
                                linkEnd.add(Long.valueOf(this.identifiers[iLast][0]));
                                break;
                            }
                            linkEnd.add(Long.valueOf(this.identifiers[iLast][jLast]));
                            break;
                        }
                        break;
                    default:
                        throw new MathInternalError();
                }
                if (i > 0) {
                    linkEnd.add(Long.valueOf(this.identifiers[i - 1][j]));
                }
                if (i < iLast) {
                    linkEnd.add(Long.valueOf(this.identifiers[i + 1][j]));
                }
                if (this.wrapRows) {
                    if (i == 0) {
                        linkEnd.add(Long.valueOf(this.identifiers[iLast][j]));
                    } else if (i == iLast) {
                        linkEnd.add(Long.valueOf(this.identifiers[0][j]));
                    }
                }
                if (j > 0) {
                    linkEnd.add(Long.valueOf(this.identifiers[i][j - 1]));
                }
                if (j < jLast) {
                    linkEnd.add(Long.valueOf(this.identifiers[i][j + 1]));
                }
                if (this.wrapColumns) {
                    if (j == 0) {
                        linkEnd.add(Long.valueOf(this.identifiers[i][jLast]));
                    } else if (j == jLast) {
                        linkEnd.add(Long.valueOf(this.identifiers[i][0]));
                    }
                }
                Neuron aNeuron = this.network.getNeuron(this.identifiers[i][j]);
                for (Long longValue : linkEnd) {
                    this.network.addLink(aNeuron, this.network.getNeuron(longValue.longValue()));
                }
                j++;
            }
        }
    }

    private void readObject(ObjectInputStream in) {
        throw new IllegalStateException();
    }

    private Object writeReplace() {
        double[][][] featuresList = (double[][][]) Array.newInstance(double[].class, new int[]{this.numberOfRows, this.numberOfColumns});
        for (int i = 0; i < this.numberOfRows; i++) {
            for (int j = 0; j < this.numberOfColumns; j++) {
                featuresList[i][j] = getNeuron(i, j).getFeatures();
            }
        }
        return new SerializationProxy(this.wrapRows, this.wrapColumns, this.neighbourhood, featuresList);
    }
}
