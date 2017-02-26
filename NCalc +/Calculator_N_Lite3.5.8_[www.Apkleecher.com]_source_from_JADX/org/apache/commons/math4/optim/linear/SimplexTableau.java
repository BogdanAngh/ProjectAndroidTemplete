package org.apache.commons.math4.optim.linear;

import edu.jas.ps.UnivPowerSeriesRing;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.linear.MatrixUtils;
import org.apache.commons.math4.linear.RealVector;
import org.apache.commons.math4.optim.PointValuePair;
import org.apache.commons.math4.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math4.util.Precision;

class SimplexTableau implements Serializable {
    private static final String NEGATIVE_VAR_COLUMN_LABEL = "x-";
    private static final long serialVersionUID = -1369660067587938365L;
    private int[] basicRows;
    private int[] basicVariables;
    private final List<String> columnLabels;
    private final List<LinearConstraint> constraints;
    private final double epsilon;
    private final LinearObjectiveFunction f;
    private final int maxUlps;
    private int numArtificialVariables;
    private final int numDecisionVariables;
    private final int numSlackVariables;
    private final boolean restrictToNonNegative;
    private transient Array2DRowRealMatrix tableau;

    SimplexTableau(LinearObjectiveFunction f, Collection<LinearConstraint> constraints, GoalType goalType, boolean restrictToNonNegative, double epsilon) {
        this(f, constraints, goalType, restrictToNonNegative, epsilon, 10);
    }

    SimplexTableau(LinearObjectiveFunction f, Collection<LinearConstraint> constraints, GoalType goalType, boolean restrictToNonNegative, double epsilon, int maxUlps) {
        boolean z = true;
        this.columnLabels = new ArrayList();
        this.f = f;
        this.constraints = normalizeConstraints(constraints);
        this.restrictToNonNegative = restrictToNonNegative;
        this.epsilon = epsilon;
        this.maxUlps = maxUlps;
        this.numDecisionVariables = (restrictToNonNegative ? 0 : 1) + f.getCoefficients().getDimension();
        this.numSlackVariables = getConstraintTypeCounts(Relationship.LEQ) + getConstraintTypeCounts(Relationship.GEQ);
        this.numArtificialVariables = getConstraintTypeCounts(Relationship.EQ) + getConstraintTypeCounts(Relationship.GEQ);
        if (goalType != GoalType.MAXIMIZE) {
            z = false;
        }
        this.tableau = createTableau(z);
        initializeBasicVariables(getSlackVariableOffset());
        initializeColumnLabels();
    }

    protected void initializeColumnLabels() {
        int i;
        if (getNumObjectiveFunctions() == 2) {
            this.columnLabels.add("W");
        }
        this.columnLabels.add("Z");
        for (i = 0; i < getOriginalNumDecisionVariables(); i++) {
            this.columnLabels.add(new StringBuilder(UnivPowerSeriesRing.DEFAULT_NAME).append(i).toString());
        }
        if (!this.restrictToNonNegative) {
            this.columnLabels.add(NEGATIVE_VAR_COLUMN_LABEL);
        }
        for (i = 0; i < getNumSlackVariables(); i++) {
            this.columnLabels.add("s" + i);
        }
        for (i = 0; i < getNumArtificialVariables(); i++) {
            this.columnLabels.add("a" + i);
        }
        this.columnLabels.add("RHS");
    }

    protected Array2DRowRealMatrix createTableau(boolean maximize) {
        double constantTerm;
        int width = (((this.numDecisionVariables + this.numSlackVariables) + this.numArtificialVariables) + getNumObjectiveFunctions()) + 1;
        Array2DRowRealMatrix matrix = new Array2DRowRealMatrix(this.constraints.size() + getNumObjectiveFunctions(), width);
        if (getNumObjectiveFunctions() == 2) {
            matrix.setEntry(0, 0, -1.0d);
        }
        int zIndex = getNumObjectiveFunctions() == 1 ? 0 : 1;
        matrix.setEntry(zIndex, zIndex, (double) (maximize ? 1 : -1));
        RealVector objectiveCoefficients = maximize ? this.f.getCoefficients().mapMultiply(-1.0d) : this.f.getCoefficients();
        copyArray(objectiveCoefficients.toArray(), matrix.getDataRef()[zIndex]);
        int i = width - 1;
        if (maximize) {
            constantTerm = this.f.getConstantTerm();
        } else {
            constantTerm = -1.0d * this.f.getConstantTerm();
        }
        matrix.setEntry(zIndex, i, constantTerm);
        if (!this.restrictToNonNegative) {
            matrix.setEntry(zIndex, getSlackVariableOffset() - 1, getInvertedCoefficientSum(objectiveCoefficients));
        }
        int slackVar = 0;
        int artificialVar = 0;
        for (int i2 = 0; i2 < this.constraints.size(); i2++) {
            LinearConstraint constraint = (LinearConstraint) this.constraints.get(i2);
            int row = getNumObjectiveFunctions() + i2;
            copyArray(constraint.getCoefficients().toArray(), matrix.getDataRef()[row]);
            if (!this.restrictToNonNegative) {
                matrix.setEntry(row, getSlackVariableOffset() - 1, getInvertedCoefficientSum(constraint.getCoefficients()));
            }
            matrix.setEntry(row, width - 1, constraint.getValue());
            int slackVar2;
            if (constraint.getRelationship() == Relationship.LEQ) {
                slackVar2 = slackVar + 1;
                matrix.setEntry(row, getSlackVariableOffset() + slackVar, 1.0d);
                slackVar = slackVar2;
            } else if (constraint.getRelationship() == Relationship.GEQ) {
                slackVar2 = slackVar + 1;
                matrix.setEntry(row, getSlackVariableOffset() + slackVar, -1.0d);
                slackVar = slackVar2;
            }
            if (constraint.getRelationship() == Relationship.EQ || constraint.getRelationship() == Relationship.GEQ) {
                matrix.setEntry(0, getArtificialVariableOffset() + artificialVar, 1.0d);
                int artificialVar2 = artificialVar + 1;
                matrix.setEntry(row, getArtificialVariableOffset() + artificialVar, 1.0d);
                matrix.setRowVector(0, matrix.getRowVector(0).subtract(matrix.getRowVector(row)));
                artificialVar = artificialVar2;
            }
        }
        return matrix;
    }

    public List<LinearConstraint> normalizeConstraints(Collection<LinearConstraint> originalConstraints) {
        List<LinearConstraint> normalized = new ArrayList(originalConstraints.size());
        for (LinearConstraint constraint : originalConstraints) {
            normalized.add(normalize(constraint));
        }
        return normalized;
    }

    private LinearConstraint normalize(LinearConstraint constraint) {
        if (constraint.getValue() < 0.0d) {
            return new LinearConstraint(constraint.getCoefficients().mapMultiply(-1.0d), constraint.getRelationship().oppositeRelationship(), constraint.getValue() * -1.0d);
        }
        return new LinearConstraint(constraint.getCoefficients(), constraint.getRelationship(), constraint.getValue());
    }

    protected final int getNumObjectiveFunctions() {
        return this.numArtificialVariables > 0 ? 2 : 1;
    }

    private int getConstraintTypeCounts(Relationship relationship) {
        int count = 0;
        for (LinearConstraint constraint : this.constraints) {
            if (constraint.getRelationship() == relationship) {
                count++;
            }
        }
        return count;
    }

    protected static double getInvertedCoefficientSum(RealVector coefficients) {
        double sum = 0.0d;
        for (double coefficient : coefficients.toArray()) {
            sum -= coefficient;
        }
        return sum;
    }

    protected Integer getBasicRow(int col) {
        int row = this.basicVariables[col];
        return row == -1 ? null : Integer.valueOf(row);
    }

    protected int getBasicVariable(int row) {
        return this.basicRows[row];
    }

    private void initializeBasicVariables(int startColumn) {
        this.basicVariables = new int[(getWidth() - 1)];
        this.basicRows = new int[getHeight()];
        Arrays.fill(this.basicVariables, -1);
        for (int i = startColumn; i < getWidth() - 1; i++) {
            Integer row = findBasicRow(i);
            if (row != null) {
                this.basicVariables[i] = row.intValue();
                this.basicRows[row.intValue()] = i;
            }
        }
    }

    private Integer findBasicRow(int col) {
        Integer row = null;
        for (int i = 0; i < getHeight(); i++) {
            double entry = getEntry(i, col);
            if (Precision.equals(entry, 1.0d, this.maxUlps) && row == null) {
                row = Integer.valueOf(i);
            } else if (!Precision.equals(entry, 0.0d, this.maxUlps)) {
                return null;
            }
        }
        return row;
    }

    protected void dropPhase1Objective() {
        if (getNumObjectiveFunctions() != 1) {
            int i;
            int col;
            Set<Integer> columnsToDrop = new TreeSet();
            columnsToDrop.add(Integer.valueOf(0));
            for (i = getNumObjectiveFunctions(); i < getArtificialVariableOffset(); i++) {
                if (Precision.compareTo(getEntry(0, i), 0.0d, this.epsilon) > 0) {
                    columnsToDrop.add(Integer.valueOf(i));
                }
            }
            for (i = 0; i < getNumArtificialVariables(); i++) {
                col = i + getArtificialVariableOffset();
                if (getBasicRow(col) == null) {
                    columnsToDrop.add(Integer.valueOf(col));
                }
            }
            double[][] matrix = (double[][]) Array.newInstance(Double.TYPE, new int[]{getHeight() - 1, getWidth() - columnsToDrop.size()});
            for (i = 1; i < getHeight(); i++) {
                col = 0;
                for (int j = 0; j < getWidth(); j++) {
                    if (!columnsToDrop.contains(Integer.valueOf(j))) {
                        int col2 = col + 1;
                        matrix[i - 1][col] = getEntry(i, j);
                        col = col2;
                    }
                }
            }
            Integer[] drop = (Integer[]) columnsToDrop.toArray(new Integer[columnsToDrop.size()]);
            for (i = drop.length - 1; i >= 0; i--) {
                this.columnLabels.remove(drop[i].intValue());
            }
            this.tableau = new Array2DRowRealMatrix(matrix);
            this.numArtificialVariables = 0;
            initializeBasicVariables(getNumObjectiveFunctions());
        }
    }

    private void copyArray(double[] src, double[] dest) {
        System.arraycopy(src, 0, dest, getNumObjectiveFunctions(), src.length);
    }

    boolean isOptimal() {
        double[] objectiveFunctionRow = getRow(0);
        int end = getRhsOffset();
        for (int i = getNumObjectiveFunctions(); i < end; i++) {
            if (Precision.compareTo(objectiveFunctionRow[i], 0.0d, this.epsilon) < 0) {
                return false;
            }
        }
        return true;
    }

    protected PointValuePair getSolution() {
        int negativeVarColumn = this.columnLabels.indexOf(NEGATIVE_VAR_COLUMN_LABEL);
        Integer negativeVarBasicRow = negativeVarColumn > 0 ? getBasicRow(negativeVarColumn) : null;
        double mostNegative = negativeVarBasicRow == null ? 0.0d : getEntry(negativeVarBasicRow.intValue(), getRhsOffset());
        Set<Integer> usedBasicRows = new HashSet();
        double[] coefficients = new double[getOriginalNumDecisionVariables()];
        for (int i = 0; i < coefficients.length; i++) {
            int colIndex = this.columnLabels.indexOf(new StringBuilder(UnivPowerSeriesRing.DEFAULT_NAME).append(i).toString());
            if (colIndex < 0) {
                coefficients[i] = 0.0d;
            } else {
                Integer basicRow = getBasicRow(colIndex);
                if (basicRow != null && basicRow.intValue() == 0) {
                    coefficients[i] = 0.0d;
                } else if (usedBasicRows.contains(basicRow)) {
                    coefficients[i] = 0.0d - (this.restrictToNonNegative ? 0.0d : mostNegative);
                } else {
                    double d;
                    usedBasicRows.add(basicRow);
                    double entry = basicRow == null ? 0.0d : getEntry(basicRow.intValue(), getRhsOffset());
                    if (this.restrictToNonNegative) {
                        d = 0.0d;
                    } else {
                        d = mostNegative;
                    }
                    coefficients[i] = entry - d;
                }
            }
        }
        return new PointValuePair(coefficients, this.f.value(coefficients));
    }

    protected void performRowOperations(int pivotCol, int pivotRow) {
        divideRow(pivotRow, getEntry(pivotRow, pivotCol));
        for (int i = 0; i < getHeight(); i++) {
            if (i != pivotRow) {
                double multiplier = getEntry(i, pivotCol);
                if (multiplier != 0.0d) {
                    subtractRow(i, pivotRow, multiplier);
                }
            }
        }
        this.basicVariables[getBasicVariable(pivotRow)] = -1;
        this.basicVariables[pivotCol] = pivotRow;
        this.basicRows[pivotRow] = pivotCol;
    }

    protected void divideRow(int dividendRowIndex, double divisor) {
        double[] dividendRow = getRow(dividendRowIndex);
        for (int j = 0; j < getWidth(); j++) {
            dividendRow[j] = dividendRow[j] / divisor;
        }
    }

    protected void subtractRow(int minuendRowIndex, int subtrahendRowIndex, double multiplier) {
        double[] minuendRow = getRow(minuendRowIndex);
        double[] subtrahendRow = getRow(subtrahendRowIndex);
        for (int i = 0; i < getWidth(); i++) {
            minuendRow[i] = minuendRow[i] - (subtrahendRow[i] * multiplier);
        }
    }

    protected final int getWidth() {
        return this.tableau.getColumnDimension();
    }

    protected final int getHeight() {
        return this.tableau.getRowDimension();
    }

    protected final double getEntry(int row, int column) {
        return this.tableau.getEntry(row, column);
    }

    protected final void setEntry(int row, int column, double value) {
        this.tableau.setEntry(row, column, value);
    }

    protected final int getSlackVariableOffset() {
        return getNumObjectiveFunctions() + this.numDecisionVariables;
    }

    protected final int getArtificialVariableOffset() {
        return (getNumObjectiveFunctions() + this.numDecisionVariables) + this.numSlackVariables;
    }

    protected final int getRhsOffset() {
        return getWidth() - 1;
    }

    protected final int getNumDecisionVariables() {
        return this.numDecisionVariables;
    }

    protected final int getOriginalNumDecisionVariables() {
        return this.f.getCoefficients().getDimension();
    }

    protected final int getNumSlackVariables() {
        return this.numSlackVariables;
    }

    protected final int getNumArtificialVariables() {
        return this.numArtificialVariables;
    }

    protected final double[] getRow(int row) {
        return this.tableau.getDataRef()[row];
    }

    protected final double[][] getData() {
        return this.tableau.getData();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SimplexTableau)) {
            return false;
        }
        SimplexTableau rhs = (SimplexTableau) other;
        if (this.restrictToNonNegative == rhs.restrictToNonNegative && this.numDecisionVariables == rhs.numDecisionVariables && this.numSlackVariables == rhs.numSlackVariables && this.numArtificialVariables == rhs.numArtificialVariables && this.epsilon == rhs.epsilon && this.maxUlps == rhs.maxUlps && this.f.equals(rhs.f) && this.constraints.equals(rhs.constraints) && this.tableau.equals(rhs.tableau)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((((Boolean.valueOf(this.restrictToNonNegative).hashCode() ^ this.numDecisionVariables) ^ this.numSlackVariables) ^ this.numArtificialVariables) ^ Double.valueOf(this.epsilon).hashCode()) ^ this.maxUlps) ^ this.f.hashCode()) ^ this.constraints.hashCode()) ^ this.tableau.hashCode();
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        MatrixUtils.serializeRealMatrix(this.tableau, oos);
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        MatrixUtils.deserializeRealMatrix(this, "tableau", ois);
    }
}
