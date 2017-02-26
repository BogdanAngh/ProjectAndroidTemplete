package edu.jas.gbmod;

import edu.jas.poly.ModuleList;
import edu.jas.poly.PolynomialList;
import edu.jas.structure.GcdRingElem;
import org.apache.log4j.Logger;

public abstract class ModGroebnerBaseAbstract<C extends GcdRingElem<C>> implements ModGroebnerBase<C> {
    private static final Logger logger;

    static {
        logger = Logger.getLogger(ModGroebnerBaseAbstract.class);
    }

    public boolean isGB(ModuleList<C> M) {
        if (M == null || M.list == null || M.rows == 0 || M.cols == 0) {
            return true;
        }
        return isGB(M.cols, M.getPolynomialList().list);
    }

    public ModuleList<C> GB(ModuleList<C> M) {
        ModuleList<C> N = M;
        if (M == null || M.list == null) {
            return N;
        }
        if (M.rows == 0 || M.cols == 0) {
            return N;
        }
        PolynomialList<C> F = M.getPolynomialList();
        int modv = M.cols;
        return new PolynomialList(F.ring, GB(modv, F.list)).getModuleList(modv);
    }

    public void terminate() {
        logger.info("terminate not implemented");
    }

    public int cancel() {
        logger.info("cancel not implemented");
        return 0;
    }
}
