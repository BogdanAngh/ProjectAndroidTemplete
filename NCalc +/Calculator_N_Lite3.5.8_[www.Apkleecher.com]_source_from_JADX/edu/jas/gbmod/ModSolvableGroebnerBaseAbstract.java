package edu.jas.gbmod;

import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.poly.GenSolvablePolynomialRing;
import edu.jas.poly.ModuleList;
import edu.jas.poly.PolynomialList;
import edu.jas.poly.TermOrder;
import edu.jas.structure.RingElem;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public abstract class ModSolvableGroebnerBaseAbstract<C extends RingElem<C>> implements ModSolvableGroebnerBase<C> {
    private static final Logger logger;
    private final boolean debug;

    public ModSolvableGroebnerBaseAbstract() {
        this.debug = logger.isDebugEnabled();
    }

    static {
        logger = Logger.getLogger(ModSolvableGroebnerBaseAbstract.class);
    }

    public boolean isLeftGB(ModuleList<C> M) {
        if (M == null || M.list == null || M.rows == 0 || M.cols == 0) {
            return true;
        }
        return isLeftGB(M.cols, M.getPolynomialList().castToSolvableList());
    }

    public ModuleList<C> leftGB(ModuleList<C> M) {
        ModuleList<C> N = M;
        if (M == null || M.list == null) {
            return N;
        }
        if (M.rows == 0 || M.cols == 0) {
            return N;
        }
        PolynomialList<C> F = M.getPolynomialList();
        if (this.debug) {
            logger.info("F left +++++++++++++++++++ \n" + F);
        }
        GenSolvablePolynomialRing sring = F.ring;
        int modv = M.cols;
        F = new PolynomialList(sring, leftGB(modv, F.castToSolvableList()));
        if (this.debug) {
            logger.info("G left +++++++++++++++++++ \n" + F);
        }
        return F.getModuleList(modv);
    }

    public boolean isTwosidedGB(ModuleList<C> M) {
        if (M == null || M.list == null || M.rows == 0 || M.cols == 0) {
            return true;
        }
        return isTwosidedGB(M.cols, M.getPolynomialList().castToSolvableList());
    }

    public ModuleList<C> twosidedGB(ModuleList<C> M) {
        ModuleList<C> N = M;
        if (M == null || M.list == null) {
            return N;
        }
        if (M.rows == 0 || M.cols == 0) {
            return N;
        }
        PolynomialList<C> F = M.getPolynomialList();
        GenSolvablePolynomialRing sring = F.ring;
        int modv = M.cols;
        return new PolynomialList(sring, twosidedGB(modv, F.castToSolvableList())).getModuleList(modv);
    }

    public boolean isRightGB(ModuleList<C> M) {
        if (M == null || M.list == null || M.rows == 0 || M.cols == 0) {
            return true;
        }
        return isRightGB(M.cols, M.getPolynomialList().castToSolvableList());
    }

    public ModuleList<C> rightGB(ModuleList<C> M) {
        ModuleList<C> N = M;
        if (M == null || M.list == null || M.rows == 0 || M.cols == 0) {
            return N;
        }
        if (this.debug) {
            logger.info("M ====================== \n" + M);
        }
        TermOrder to = M.ring.tord;
        if (to.getSplit() <= M.ring.nvar) {
            throw new IllegalArgumentException("extended TermOrders not supported for rightGBs: " + to);
        }
        List<List<GenSolvablePolynomial<C>>> mlist = M.castToSolvableList();
        GenSolvablePolynomialRing rring = M.ring.reverse(true);
        GenSolvablePolynomialRing sring = rring.reverse(true);
        List nlist = new ArrayList(M.rows);
        for (List<GenSolvablePolynomial<C>> row : mlist) {
            List<GenSolvablePolynomial<C>> nrow = new ArrayList(row.size());
            for (GenSolvablePolynomial<C> elem : row) {
                nrow.add((GenSolvablePolynomial) elem.reverse(rring));
            }
            nlist.add(nrow);
        }
        ModuleList<C> rM = new ModuleList(rring, nlist);
        if (this.debug) {
            logger.info("rM -------------------- \n" + rM);
        }
        ModuleList<C> rMg = leftGB(rM);
        if (this.debug) {
            logger.info("rMg -------------------- \n" + rMg);
            logger.info("isLeftGB(rMg) ---------- " + isLeftGB(rMg));
        }
        mlist = rMg.castToSolvableList();
        nlist = new ArrayList(rMg.rows);
        for (List<GenSolvablePolynomial<C>> row2 : mlist) {
            nrow = new ArrayList(row2.size());
            for (GenSolvablePolynomial<C> elem2 : row2) {
                nrow.add((GenSolvablePolynomial) elem2.reverse(sring));
            }
            nlist.add(nrow);
        }
        ModuleList<C> Mg = new ModuleList(sring, nlist);
        if (this.debug) {
            logger.info("Mg -------------------- \n" + Mg);
            logger.info("isRightGB(Mg) --------- " + isRightGB(Mg));
        }
        return Mg;
    }

    public void terminate() {
        logger.info("terminate not implemented");
    }

    public int cancel() {
        logger.info("cancel not implemented");
        return 0;
    }
}
