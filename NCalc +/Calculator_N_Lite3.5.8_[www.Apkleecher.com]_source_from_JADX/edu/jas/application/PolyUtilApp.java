package edu.jas.application;

import edu.jas.arith.BigDecimal;
import edu.jas.arith.Product;
import edu.jas.arith.ProductRing;
import edu.jas.arith.Rational;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.Complex;
import edu.jas.poly.ComplexRing;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.poly.PolynomialList;
import edu.jas.poly.TermOrder;
import edu.jas.root.ComplexRootsAbstract;
import edu.jas.root.ComplexRootsSturm;
import edu.jas.root.RealAlgebraicNumber;
import edu.jas.root.RealRootsAbstract;
import edu.jas.root.RealRootsSturm;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.util.ListUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import org.apache.log4j.Logger;

public class PolyUtilApp<C extends RingElem<C>> {
    private static boolean debug;
    private static final Logger logger;

    static {
        logger = Logger.getLogger(PolyUtilApp.class);
        debug = logger.isDebugEnabled();
    }

    public static <C extends GcdRingElem<C>> List<GenPolynomial<Product<Residue<C>>>> toProductRes(GenPolynomialRing<Product<Residue<C>>> pfac, List<GenPolynomial<GenPolynomial<C>>> L) {
        List<GenPolynomial<Product<Residue<C>>>> list = new ArrayList();
        if (!(L == null || L.size() == 0)) {
            for (GenPolynomial a : L) {
                list.add(toProductRes((GenPolynomialRing) pfac, a));
            }
        }
        return list;
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<Product<Residue<C>>> toProductRes(GenPolynomialRing<Product<Residue<C>>> pfac, GenPolynomial<GenPolynomial<C>> A) {
        GenPolynomial<Product<Residue<C>>> P = pfac.getZERO().copy();
        if (!(A == null || A.isZERO())) {
            ProductRing fac = (ProductRing) pfac.coFac;
            for (Entry<ExpVector, GenPolynomial<C>> y : A.getMap().entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                Product<Residue<C>> p = toProductRes(fac, (GenPolynomial) y.getValue());
                if (!p.isZERO()) {
                    P.doPutToMap(e, p);
                }
            }
        }
        return P;
    }

    public static <C extends GcdRingElem<C>> Product<Residue<C>> toProductRes(ProductRing<Residue<C>> pfac, GenPolynomial<C> c) {
        SortedMap<Integer, Residue<C>> elem = new TreeMap();
        for (int i = 0; i < pfac.length(); i++) {
            Residue<C> u = new Residue((ResidueRing) pfac.getFactory(i), c);
            if (!u.isZERO()) {
                elem.put(Integer.valueOf(i), u);
            }
        }
        return new Product(pfac, elem);
    }

    public static <C extends GcdRingElem<C>> List<GenPolynomial<Product<Residue<C>>>> toProductRes(List<ColoredSystem<C>> CS) {
        List<GenPolynomial<Product<Residue<C>>>> list = new ArrayList();
        if (CS == null || CS.isEmpty()) {
            return list;
        }
        GenPolynomialRing<GenPolynomial<C>> pr = null;
        List<RingFactory<Residue<C>>> rrl = new ArrayList(CS.size());
        for (ColoredSystem<C> cs : CS) {
            ResidueRing<C> r = new ResidueRing(cs.condition.zero);
            if (!rrl.contains(r)) {
                rrl.add(r);
            }
            if (pr == null && cs.list.size() > 0) {
                pr = ((ColorPolynomial) cs.list.get(0)).green.ring;
            }
        }
        if (pr != null) {
            return toProductRes(new GenPolynomialRing(new ProductRing(rrl), pr.nvar, pr.tord, pr.getVars()), new GroebnerSystem(CS).getCGB());
        }
        throw new IllegalArgumentException("no polynomial ring found");
    }

    public static <C extends GcdRingElem<C>> List<GenPolynomial<Residue<C>>> toResidue(GenPolynomialRing<Residue<C>> pfac, List<GenPolynomial<GenPolynomial<C>>> L) {
        List<GenPolynomial<Residue<C>>> list = new ArrayList();
        if (!(L == null || L.size() == 0)) {
            for (GenPolynomial a : L) {
                GenPolynomial<Residue<C>> b = toResidue((GenPolynomialRing) pfac, a);
                if (!b.isZERO()) {
                    list.add(b);
                }
            }
        }
        return list;
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<Residue<C>> toResidue(GenPolynomialRing<Residue<C>> pfac, GenPolynomial<GenPolynomial<C>> A) {
        GenPolynomial<Residue<C>> P = pfac.getZERO().copy();
        if (!(A == null || A.isZERO())) {
            ResidueRing<C> fac = (ResidueRing) pfac.coFac;
            for (Entry<ExpVector, GenPolynomial<C>> y : A.getMap().entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                Residue<C> p = new Residue(fac, (GenPolynomial) y.getValue());
                if (!p.isZERO()) {
                    P.doPutToMap(e, p);
                }
            }
        }
        return P;
    }

    public static <C extends GcdRingElem<C>> Map<Ideal<C>, PolynomialList<GenPolynomial<C>>> productSlice(PolynomialList<Product<Residue<C>>> L) {
        ProductRing<Residue<C>> pr = (ProductRing) L.ring.coFac;
        int s = pr.length();
        Map<Ideal<C>, PolynomialList<GenPolynomial<C>>> map = new TreeMap();
        List plist = L.list;
        for (int i = 0; i < s; i++) {
            ResidueRing<C> rr = (ResidueRing) pr.getFactory(i);
            Ideal<C> id = rr.ideal;
            GenPolynomialRing pfc = new GenPolynomialRing(rr.ring, L.ring);
            PolynomialList<GenPolynomial<C>> spl = new PolynomialList(pfc, fromProduct(pfc, plist, i));
            if (((PolynomialList) map.get(id)) != null) {
                throw new RuntimeException("ideal exists twice " + id);
            }
            map.put(id, spl);
        }
        return map;
    }

    public static <C extends GcdRingElem<C>> PolynomialList<GenPolynomial<C>> productSlice(PolynomialList<Product<Residue<C>>> L, int i) {
        ProductRing<Residue<C>> pr = (ProductRing) L.ring.coFac;
        List plist = L.list;
        GenPolynomialRing pfc = new GenPolynomialRing(((ResidueRing) pr.getFactory(i)).ring, L.ring);
        return new PolynomialList(pfc, fromProduct(pfc, plist, i));
    }

    public static <C extends GcdRingElem<C>> List<GenPolynomial<GenPolynomial<C>>> fromProduct(GenPolynomialRing<GenPolynomial<C>> pfac, List<GenPolynomial<Product<Residue<C>>>> L, int i) {
        List<GenPolynomial<GenPolynomial<C>>> list = new ArrayList();
        if (!(L == null || L.size() == 0)) {
            for (GenPolynomial a : L) {
                GenPolynomial<GenPolynomial<C>> b = fromProduct((GenPolynomialRing) pfac, a, i);
                if (!b.isZERO()) {
                    b = b.abs();
                    if (!list.contains(b)) {
                        list.add(b);
                    }
                }
            }
        }
        return list;
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<GenPolynomial<C>> fromProduct(GenPolynomialRing<GenPolynomial<C>> pfac, GenPolynomial<Product<Residue<C>>> P, int i) {
        GenPolynomial<GenPolynomial<C>> b = pfac.getZERO().copy();
        if (!(P == null || P.isZERO())) {
            for (Entry<ExpVector, Product<Residue<C>>> y : P.getMap().entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                Residue<C> r = (Residue) ((Product) y.getValue()).get(i);
                if (!(r == null || r.isZERO())) {
                    GenPolynomial<C> p = r.val;
                    if (!p.isZERO()) {
                        b.doPutToMap(e, p);
                    }
                }
            }
        }
        return b;
    }

    public static <C extends GcdRingElem<C>> String productSliceToString(Map<Ideal<C>, PolynomialList<GenPolynomial<C>>> L) {
        Set<GenPolynomial<GenPolynomial<C>>> sl = new TreeSet();
        StringBuffer sb = new StringBuffer();
        for (Entry<Ideal<C>, PolynomialList<GenPolynomial<C>>> en : L.entrySet()) {
            sb.append("\n\ncondition == 0:\n");
            sb.append(((Ideal) en.getKey()).list.toScript());
            PolynomialList<GenPolynomial<C>> pl = (PolynomialList) en.getValue();
            sl.addAll(pl.list);
            sb.append("\ncorresponding ideal:\n");
            sb.append(pl.toScript());
        }
        return sb.toString();
    }

    public static <C extends GcdRingElem<C>> String productToString(PolynomialList<Product<Residue<C>>> L) {
        return productSliceToString(productSlice(L));
    }

    public static <D extends GcdRingElem<D> & Rational> List<List<Complex<BigDecimal>>> complexRootTuples(Ideal<D> I, D eps) {
        List<GenPolynomial<D>> univs = I.constructUnivariate();
        if (logger.isInfoEnabled()) {
            logger.info("univs = " + univs);
        }
        return complexRoots(I, univs, eps);
    }

    public static <D extends GcdRingElem<D> & Rational> List<List<Complex<BigDecimal>>> complexRoots(Ideal<D> I, List<GenPolynomial<D>> univs, D eps) {
        List<List<Complex<BigDecimal>>> croots = new ArrayList();
        RingFactory cr = new ComplexRing(I.list.ring.coFac);
        ComplexRootsAbstract<D> cra = new ComplexRootsSturm(cr);
        List<GenPolynomial<Complex<D>>> cunivs = new ArrayList();
        for (GenPolynomial<D> p : univs) {
            cunivs.add(PolyUtil.toComplex(new GenPolynomialRing(cr, p.ring), p));
        }
        for (int i = 0; i < I.list.ring.nvar; i++) {
            croots.add(cra.approximateRoots((GenPolynomial) cunivs.get(i), eps));
        }
        return ListUtil.tupleFromList(croots);
    }

    public static <D extends GcdRingElem<D> & Rational> List<List<Complex<BigDecimal>>> complexRootTuples(List<IdealWithUniv<D>> Il, D eps) {
        List<List<Complex<BigDecimal>>> croots = new ArrayList();
        for (IdealWithUniv<D> I : Il) {
            croots.addAll(complexRoots(I.ideal, I.upolys, eps));
        }
        return croots;
    }

    public static <D extends GcdRingElem<D> & Rational> List<IdealWithComplexRoots<D>> complexRoots(List<IdealWithUniv<D>> Il, D eps) {
        List<IdealWithComplexRoots<D>> Ic = new ArrayList(Il.size());
        for (IdealWithUniv<D> I : Il) {
            Ic.add(new IdealWithComplexRoots(I, complexRoots(I.ideal, I.upolys, eps)));
        }
        return Ic;
    }

    public static <D extends GcdRingElem<D> & Rational> List<IdealWithComplexRoots<D>> complexRoots(Ideal<D> G, D eps) {
        return complexRoots(G.zeroDimDecomposition(), (GcdRingElem) eps);
    }

    public static <D extends GcdRingElem<D> & Rational> List<List<BigDecimal>> realRootTuples(Ideal<D> I, D eps) {
        List<GenPolynomial<D>> univs = I.constructUnivariate();
        if (logger.isInfoEnabled()) {
            logger.info("univs = " + univs);
        }
        return realRoots(I, univs, eps);
    }

    public static <D extends GcdRingElem<D> & Rational> List<List<BigDecimal>> realRoots(Ideal<D> I, List<GenPolynomial<D>> univs, D eps) {
        List<List<BigDecimal>> roots = new ArrayList();
        RealRootsAbstract<D> rra = new RealRootsSturm();
        for (int i = 0; i < I.list.ring.nvar; i++) {
            roots.add(rra.approximateRoots((GenPolynomial) univs.get(i), eps));
        }
        return ListUtil.tupleFromList(roots);
    }

    public static <D extends GcdRingElem<D> & Rational> List<List<BigDecimal>> realRootTuples(List<IdealWithUniv<D>> Il, D eps) {
        List<List<BigDecimal>> rroots = new ArrayList();
        for (IdealWithUniv<D> I : Il) {
            rroots.addAll(realRoots(I.ideal, I.upolys, eps));
        }
        return rroots;
    }

    public static <D extends GcdRingElem<D> & Rational> List<IdealWithRealRoots<D>> realRoots(List<IdealWithUniv<D>> Il, D eps) {
        List<IdealWithRealRoots<D>> Ir = new ArrayList(Il.size());
        for (IdealWithUniv<D> I : Il) {
            Ir.add(new IdealWithRealRoots(I, realRoots(I.ideal, I.upolys, eps)));
        }
        return Ir;
    }

    public static <D extends GcdRingElem<D> & Rational> List<IdealWithRealRoots<D>> realRoots(Ideal<D> G, D eps) {
        return realRoots(G.zeroDimDecomposition(), (GcdRingElem) eps);
    }

    public static boolean isRealRoots(List<GenPolynomial<BigDecimal>> L, List<List<BigDecimal>> roots, BigDecimal eps) {
        if (L == null || L.size() == 0) {
            return true;
        }
        BigDecimal dc = BigDecimal.ONE;
        for (GenPolynomial<BigDecimal> dp : L) {
            for (List<BigDecimal> r : roots) {
                BigDecimal ev = (BigDecimal) PolyUtil.evaluateAll(dc, dp, r);
                if (ev.abs().compareTo(eps) > 0) {
                    System.out.println("ev = " + ev);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isComplexRoots(List<GenPolynomial<Complex<BigDecimal>>> L, List<List<Complex<BigDecimal>>> roots, BigDecimal eps) {
        if (L == null || L.size() == 0) {
            return true;
        }
        ComplexRing<BigDecimal> dcc = new ComplexRing(BigDecimal.ONE);
        for (GenPolynomial<Complex<BigDecimal>> dp : L) {
            for (List<Complex<BigDecimal>> r : roots) {
                Complex<BigDecimal> ev = (Complex) PolyUtil.evaluateAll(dcc, dp, r);
                if (((BigDecimal) ev.norm().getRe()).compareTo(eps) > 0) {
                    System.out.println("ev = " + ev);
                    return false;
                }
            }
        }
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <D extends edu.jas.structure.GcdRingElem<D> & edu.jas.arith.Rational> edu.jas.application.IdealWithRealAlgebraicRoots<D> realAlgebraicRoots(edu.jas.application.IdealWithUniv<D> r44) {
        /*
        r23 = new java.util.ArrayList;
        r23.<init>();
        if (r44 != 0) goto L_0x000f;
    L_0x0007:
        r41 = new java.lang.IllegalArgumentException;
        r42 = "null ideal not permitted";
        r41.<init>(r42);
        throw r41;
    L_0x000f:
        r0 = r44;
        r0 = r0.ideal;
        r41 = r0;
        if (r41 == 0) goto L_0x001f;
    L_0x0017:
        r0 = r44;
        r0 = r0.upolys;
        r41 = r0;
        if (r41 != 0) goto L_0x003c;
    L_0x001f:
        r41 = new java.lang.IllegalArgumentException;
        r42 = new java.lang.StringBuilder;
        r42.<init>();
        r43 = "null ideal components not permitted ";
        r42 = r42.append(r43);
        r0 = r42;
        r1 = r44;
        r42 = r0.append(r1);
        r42 = r42.toString();
        r41.<init>(r42);
        throw r41;
    L_0x003c:
        r0 = r44;
        r0 = r0.ideal;
        r41 = r0;
        r41 = r41.isZERO();
        if (r41 != 0) goto L_0x0054;
    L_0x0048:
        r0 = r44;
        r0 = r0.upolys;
        r41 = r0;
        r41 = r41.size();
        if (r41 != 0) goto L_0x005e;
    L_0x0054:
        r4 = new edu.jas.application.IdealWithRealAlgebraicRoots;
        r0 = r44;
        r1 = r23;
        r4.<init>(r0, r1);
    L_0x005d:
        return r4;
    L_0x005e:
        r0 = r44;
        r0 = r0.ideal;
        r41 = r0;
        r0 = r41;
        r0 = r0.list;
        r41 = r0;
        r0 = r41;
        r7 = r0.ring;
        r0 = r44;
        r0 = r0.upolys;
        r41 = r0;
        r42 = 0;
        r13 = r41.get(r42);
        r13 = (edu.jas.poly.GenPolynomial) r13;
        r0 = r44;
        r0 = r0.ideal;
        r41 = r0;
        r0 = r41;
        r0 = r0.list;
        r41 = r0;
        r0 = r41;
        r0 = r0.list;
        r41 = r0;
        r0 = r7.nvar;
        r42 = r0;
        r42 = r42 + -1;
        r14 = edu.jas.poly.PolyUtil.selectWithVariable(r41, r42);
        if (r14 != 0) goto L_0x00c9;
    L_0x009a:
        r41 = new java.lang.RuntimeException;
        r42 = new java.lang.StringBuilder;
        r42.<init>();
        r43 = "no polynomial found in ";
        r42 = r42.append(r43);
        r0 = r7.nvar;
        r43 = r0;
        r43 = r43 + -1;
        r42 = r42.append(r43);
        r43 = " of  ";
        r42 = r42.append(r43);
        r0 = r44;
        r0 = r0.ideal;
        r43 = r0;
        r42 = r42.append(r43);
        r42 = r42.toString();
        r41.<init>(r42);
        throw r41;
    L_0x00c9:
        r41 = logger;
        r41 = r41.isInfoEnabled();
        if (r41 == 0) goto L_0x00eb;
    L_0x00d1:
        r41 = logger;
        r42 = new java.lang.StringBuilder;
        r42.<init>();
        r43 = "p0p = ";
        r42 = r42.append(r43);
        r0 = r42;
        r42 = r0.append(r14);
        r42 = r42.toString();
        r41.info(r42);
    L_0x00eb:
        r41 = r14.degreeVector();
        r5 = r41.dependencyOnVariables();
        r0 = r5.length;
        r41 = r0;
        r42 = 1;
        r0 = r41;
        r1 = r42;
        if (r0 == r1) goto L_0x011b;
    L_0x00fe:
        r41 = new java.lang.RuntimeException;
        r42 = new java.lang.StringBuilder;
        r42.<init>();
        r43 = "wrong number of variables ";
        r42 = r42.append(r43);
        r43 = java.util.Arrays.toString(r5);
        r42 = r42.append(r43);
        r42 = r42.toString();
        r41.<init>(r42);
        throw r41;
    L_0x011b:
        r31 = edu.jas.root.RootFactory.realAlgebraicNumbersIrred(r13);
        r41 = logger;
        r41 = r41.isInfoEnabled();
        if (r41 == 0) goto L_0x0166;
    L_0x0127:
        r11 = new java.util.ArrayList;
        r11.<init>();
        r9 = r31.iterator();
    L_0x0130:
        r41 = r9.hasNext();
        if (r41 == 0) goto L_0x014c;
    L_0x0136:
        r30 = r9.next();
        r30 = (edu.jas.root.RealAlgebraicNumber) r30;
        r0 = r30;
        r0 = r0.ring;
        r41 = r0;
        r41 = r41.getRoot();
        r0 = r41;
        r11.add(r0);
        goto L_0x0130;
    L_0x014c:
        r41 = logger;
        r42 = new java.lang.StringBuilder;
        r42.<init>();
        r43 = "roots(p0) = ";
        r42 = r42.append(r43);
        r0 = r42;
        r42 = r0.append(r11);
        r42 = r42.toString();
        r41.info(r42);
    L_0x0166:
        r9 = r31.iterator();
    L_0x016a:
        r41 = r9.hasNext();
        if (r41 == 0) goto L_0x018a;
    L_0x0170:
        r30 = r9.next();
        r30 = (edu.jas.root.RealAlgebraicNumber) r30;
        r28 = new java.util.ArrayList;
        r28.<init>();
        r0 = r28;
        r1 = r30;
        r0.add(r1);
        r0 = r23;
        r1 = r28;
        r0.add(r1);
        goto L_0x016a;
    L_0x018a:
        r8 = 1;
    L_0x018b:
        r0 = r44;
        r0 = r0.upolys;
        r41 = r0;
        r41 = r41.size();
        r0 = r41;
        if (r8 >= r0) goto L_0x0407;
    L_0x0199:
        r29 = new java.util.ArrayList;
        r29.<init>();
        r0 = r44;
        r0 = r0.upolys;
        r41 = r0;
        r0 = r41;
        r15 = r0.get(r8);
        r15 = (edu.jas.poly.GenPolynomial) r15;
        r0 = r44;
        r0 = r0.ideal;
        r41 = r0;
        r0 = r41;
        r0 = r0.list;
        r41 = r0;
        r0 = r41;
        r0 = r0.list;
        r41 = r0;
        r0 = r7.nvar;
        r42 = r0;
        r42 = r42 + -1;
        r42 = r42 - r8;
        r16 = edu.jas.poly.PolyUtil.selectWithVariable(r41, r42);
        if (r16 != 0) goto L_0x01fd;
    L_0x01cc:
        r41 = new java.lang.RuntimeException;
        r42 = new java.lang.StringBuilder;
        r42.<init>();
        r43 = "no polynomial found in ";
        r42 = r42.append(r43);
        r0 = r7.nvar;
        r43 = r0;
        r43 = r43 + -1;
        r43 = r43 - r8;
        r42 = r42.append(r43);
        r43 = " of  ";
        r42 = r42.append(r43);
        r0 = r44;
        r0 = r0.ideal;
        r43 = r0;
        r42 = r42.append(r43);
        r42 = r42.toString();
        r41.<init>(r42);
        throw r41;
    L_0x01fd:
        r41 = logger;
        r41 = r41.isInfoEnabled();
        if (r41 == 0) goto L_0x023b;
    L_0x0205:
        r41 = logger;
        r42 = new java.lang.StringBuilder;
        r42.<init>();
        r43 = "pi  = ";
        r42 = r42.append(r43);
        r0 = r42;
        r42 = r0.append(r15);
        r42 = r42.toString();
        r41.info(r42);
        r41 = logger;
        r42 = new java.lang.StringBuilder;
        r42.<init>();
        r43 = "pip = ";
        r42 = r42.append(r43);
        r0 = r42;
        r1 = r16;
        r42 = r0.append(r1);
        r42 = r42.toString();
        r41.info(r42);
    L_0x023b:
        r41 = r16.degreeVector();
        r6 = r41.dependencyOnVariables();
        r0 = r6.length;
        r41 = r0;
        r42 = 1;
        r0 = r41;
        r1 = r42;
        if (r0 < r1) goto L_0x0259;
    L_0x024e:
        r0 = r6.length;
        r41 = r0;
        r42 = 2;
        r0 = r41;
        r1 = r42;
        if (r0 <= r1) goto L_0x0276;
    L_0x0259:
        r41 = new java.lang.RuntimeException;
        r42 = new java.lang.StringBuilder;
        r42.<init>();
        r43 = "wrong number of variables ";
        r42 = r42.append(r43);
        r43 = java.util.Arrays.toString(r6);
        r42 = r42.append(r43);
        r42 = r42.toString();
        r41.<init>(r42);
        throw r41;
    L_0x0276:
        r31 = edu.jas.root.RootFactory.realAlgebraicNumbersIrred(r15);
        r41 = logger;
        r41 = r41.isInfoEnabled();
        if (r41 == 0) goto L_0x02c1;
    L_0x0282:
        r11 = new java.util.ArrayList;
        r11.<init>();
        r9 = r31.iterator();
    L_0x028b:
        r41 = r9.hasNext();
        if (r41 == 0) goto L_0x02a7;
    L_0x0291:
        r30 = r9.next();
        r30 = (edu.jas.root.RealAlgebraicNumber) r30;
        r0 = r30;
        r0 = r0.ring;
        r41 = r0;
        r41 = r41.getRoot();
        r0 = r41;
        r11.add(r0);
        goto L_0x028b;
    L_0x02a7:
        r41 = logger;
        r42 = new java.lang.StringBuilder;
        r42.<init>();
        r43 = "roots(pi) = ";
        r42 = r42.append(r43);
        r0 = r42;
        r42 = r0.append(r11);
        r42 = r42.toString();
        r41.info(r42);
    L_0x02c1:
        r0 = r6.length;
        r41 = r0;
        r42 = 1;
        r0 = r41;
        r1 = r42;
        if (r0 != r1) goto L_0x0307;
    L_0x02cc:
        r9 = r31.iterator();
    L_0x02d0:
        r41 = r9.hasNext();
        if (r41 == 0) goto L_0x0401;
    L_0x02d6:
        r30 = r9.next();
        r30 = (edu.jas.root.RealAlgebraicNumber) r30;
        r10 = r23.iterator();
    L_0x02e0:
        r41 = r10.hasNext();
        if (r41 == 0) goto L_0x02d0;
    L_0x02e6:
        r33 = r10.next();
        r33 = (java.util.List) r33;
        r34 = new java.util.ArrayList;
        r34.<init>();
        r0 = r34;
        r1 = r33;
        r0.addAll(r1);
        r0 = r34;
        r1 = r30;
        r0.add(r1);
        r0 = r29;
        r1 = r34;
        r0.add(r1);
        goto L_0x02e0;
    L_0x0307:
        r17 = edu.jas.poly.PolyUtil.removeUnusedUpperVariables(r16);
        r0 = r17;
        r0 = r0.ring;
        r41 = r0;
        r42 = 1;
        r39 = r41.contract(r42);
        r38 = new edu.jas.poly.TermOrder;
        r41 = 2;
        r0 = r38;
        r1 = r41;
        r0.<init>(r1);
        r27 = new edu.jas.poly.GenPolynomialRing;
        r41 = 1;
        r0 = r27;
        r1 = r39;
        r2 = r41;
        r3 = r38;
        r0.<init>(r1, r2, r3);
        r0 = r27;
        r1 = r17;
        r22 = edu.jas.poly.PolyUtil.recursive(r0, r1);
        r0 = r7.nvar;
        r41 = r0;
        r41 = r41 + -1;
        r0 = r6.length;
        r42 = r0;
        r42 = r42 + -1;
        r42 = r6[r42];
        r12 = r41 - r42;
        r9 = r31.iterator();
    L_0x034c:
        r41 = r9.hasNext();
        if (r41 == 0) goto L_0x0401;
    L_0x0352:
        r30 = r9.next();
        r30 = (edu.jas.root.RealAlgebraicNumber) r30;
        r0 = r30;
        r0 = r0.ring;
        r41 = r0;
        r32 = r41.getRoot();
        r0 = r32;
        r0 = r0.left;
        r41 = r0;
        r0 = r39;
        r1 = r22;
        r2 = r41;
        r18 = edu.jas.poly.PolyUtil.evaluateMainRecursive(r0, r1, r2);
        r0 = r32;
        r0 = r0.right;
        r41 = r0;
        r0 = r39;
        r1 = r22;
        r2 = r41;
        r20 = edu.jas.poly.PolyUtil.evaluateMainRecursive(r0, r1, r2);
        r0 = r44;
        r0 = r0.upolys;
        r41 = r0;
        r0 = r41;
        r41 = r0.get(r12);
        r41 = (edu.jas.poly.GenPolynomial) r41;
        r0 = r41;
        r0 = r0.ring;
        r40 = r0;
        r0 = r40;
        r1 = r18;
        r19 = convert(r0, r1);
        r0 = r40;
        r1 = r20;
        r21 = convert(r0, r1);
        r10 = r23.iterator();
    L_0x03aa:
        r41 = r10.hasNext();
        if (r41 == 0) goto L_0x034c;
    L_0x03b0:
        r33 = r10.next();
        r33 = (java.util.List) r33;
        r0 = r33;
        r41 = r0.get(r12);
        r41 = (edu.jas.root.RealAlgebraicNumber) r41;
        r0 = r41;
        r0 = r0.ring;
        r24 = r0;
        r25 = new edu.jas.root.RealAlgebraicNumber;
        r0 = r25;
        r1 = r24;
        r2 = r19;
        r0.<init>(r1, r2);
        r26 = new edu.jas.root.RealAlgebraicNumber;
        r0 = r26;
        r1 = r24;
        r2 = r21;
        r0.<init>(r1, r2);
        r36 = r25.signum();
        r37 = r26.signum();
        r41 = r36 * r37;
        if (r41 > 0) goto L_0x03aa;
    L_0x03e6:
        r34 = new java.util.ArrayList;
        r34.<init>();
        r0 = r34;
        r1 = r33;
        r0.addAll(r1);
        r0 = r34;
        r1 = r30;
        r0.add(r1);
        r0 = r29;
        r1 = r34;
        r0.add(r1);
        goto L_0x03aa;
    L_0x0401:
        r23 = r29;
        r8 = r8 + 1;
        goto L_0x018b;
    L_0x0407:
        r41 = logger;
        r41 = r41.isInfoEnabled();
        if (r41 == 0) goto L_0x045f;
    L_0x040f:
        r9 = r23.iterator();
    L_0x0413:
        r41 = r9.hasNext();
        if (r41 == 0) goto L_0x045f;
    L_0x0419:
        r35 = r9.next();
        r35 = (java.util.List) r35;
        r11 = new java.util.ArrayList;
        r11.<init>();
        r10 = r35.iterator();
    L_0x0428:
        r41 = r10.hasNext();
        if (r41 == 0) goto L_0x0444;
    L_0x042e:
        r30 = r10.next();
        r30 = (edu.jas.root.RealAlgebraicNumber) r30;
        r0 = r30;
        r0 = r0.ring;
        r41 = r0;
        r41 = r41.getRoot();
        r0 = r41;
        r11.add(r0);
        goto L_0x0428;
    L_0x0444:
        r41 = logger;
        r42 = new java.lang.StringBuilder;
        r42.<init>();
        r43 = "root-tuple = ";
        r42 = r42.append(r43);
        r0 = r42;
        r42 = r0.append(r11);
        r42 = r42.toString();
        r41.info(r42);
        goto L_0x0413;
    L_0x045f:
        r4 = new edu.jas.application.IdealWithRealAlgebraicRoots;
        r0 = r44;
        r1 = r23;
        r4.<init>(r0, r1);
        goto L_0x005d;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.application.PolyUtilApp.realAlgebraicRoots(edu.jas.application.IdealWithUniv):edu.jas.application.IdealWithRealAlgebraicRoots<D>");
    }

    public static <D extends GcdRingElem<D> & Rational> List<IdealWithRealAlgebraicRoots<D>> realAlgebraicRoots(List<IdealWithUniv<D>> I) {
        List<IdealWithRealAlgebraicRoots<D>> lir = new ArrayList(I.size());
        for (IdealWithUniv iu : I) {
            lir.add(realAlgebraicRoots(iu));
        }
        return lir;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <D extends edu.jas.structure.GcdRingElem<D> & edu.jas.arith.Rational> edu.jas.application.IdealWithComplexAlgebraicRoots<D> complexAlgebraicRootsWrong(edu.jas.application.IdealWithUniv<D> r78) {
        /*
        r6 = new java.util.ArrayList;
        r6.<init>();
        if (r78 != 0) goto L_0x000f;
    L_0x0007:
        r73 = new java.lang.IllegalArgumentException;
        r74 = "null ideal not permitted";
        r73.<init>(r74);
        throw r73;
    L_0x000f:
        r0 = r78;
        r0 = r0.ideal;
        r73 = r0;
        if (r73 == 0) goto L_0x001f;
    L_0x0017:
        r0 = r78;
        r0 = r0.upolys;
        r73 = r0;
        if (r73 != 0) goto L_0x003c;
    L_0x001f:
        r73 = new java.lang.IllegalArgumentException;
        r74 = new java.lang.StringBuilder;
        r74.<init>();
        r75 = "null ideal components not permitted ";
        r74 = r74.append(r75);
        r0 = r74;
        r1 = r78;
        r74 = r0.append(r1);
        r74 = r74.toString();
        r73.<init>(r74);
        throw r73;
    L_0x003c:
        r0 = r78;
        r0 = r0.ideal;
        r73 = r0;
        r73 = r73.isZERO();
        if (r73 != 0) goto L_0x0054;
    L_0x0048:
        r0 = r78;
        r0 = r0.upolys;
        r73 = r0;
        r73 = r73.size();
        if (r73 != 0) goto L_0x005c;
    L_0x0054:
        r4 = new edu.jas.application.IdealWithComplexAlgebraicRoots;
        r0 = r78;
        r4.<init>(r0, r6);
    L_0x005b:
        return r4;
    L_0x005c:
        r0 = r78;
        r0 = r0.ideal;
        r73 = r0;
        r0 = r73;
        r0 = r0.list;
        r73 = r0;
        r0 = r73;
        r0 = r0.ring;
        r21 = r0;
        r0 = r21;
        r0 = r0.nvar;
        r73 = r0;
        if (r73 != 0) goto L_0x007e;
    L_0x0076:
        r4 = new edu.jas.application.IdealWithComplexAlgebraicRoots;
        r0 = r78;
        r4.<init>(r0, r6);
        goto L_0x005b;
    L_0x007e:
        r0 = r21;
        r0 = r0.nvar;
        r73 = r0;
        r0 = r78;
        r0 = r0.upolys;
        r74 = r0;
        r74 = r74.size();
        r0 = r73;
        r1 = r74;
        if (r0 == r1) goto L_0x00b1;
    L_0x0094:
        r73 = new java.lang.IllegalArgumentException;
        r74 = new java.lang.StringBuilder;
        r74.<init>();
        r75 = "ideal not zero dimnsional: ";
        r74 = r74.append(r75);
        r0 = r74;
        r1 = r78;
        r74 = r0.append(r1);
        r74 = r74.toString();
        r73.<init>(r74);
        throw r73;
    L_0x00b1:
        r0 = r78;
        r0 = r0.upolys;
        r73 = r0;
        r74 = 0;
        r32 = r73.get(r74);
        r32 = (edu.jas.poly.GenPolynomial) r32;
        r0 = r78;
        r0 = r0.ideal;
        r73 = r0;
        r0 = r73;
        r0 = r0.list;
        r73 = r0;
        r0 = r73;
        r0 = r0.list;
        r73 = r0;
        r0 = r21;
        r0 = r0.nvar;
        r74 = r0;
        r74 = r74 + -1;
        r34 = edu.jas.poly.PolyUtil.selectWithVariable(r73, r74);
        if (r34 != 0) goto L_0x0110;
    L_0x00df:
        r73 = new java.lang.RuntimeException;
        r74 = new java.lang.StringBuilder;
        r74.<init>();
        r75 = "no polynomial found in ";
        r74 = r74.append(r75);
        r0 = r21;
        r0 = r0.nvar;
        r75 = r0;
        r75 = r75 + -1;
        r74 = r74.append(r75);
        r75 = " of  ";
        r74 = r74.append(r75);
        r0 = r78;
        r0 = r0.ideal;
        r75 = r0;
        r74 = r74.append(r75);
        r74 = r74.toString();
        r73.<init>(r74);
        throw r73;
    L_0x0110:
        r73 = logger;
        r73 = r73.isInfoEnabled();
        if (r73 == 0) goto L_0x0150;
    L_0x0118:
        r73 = logger;
        r74 = new java.lang.StringBuilder;
        r74.<init>();
        r75 = "p0  = ";
        r74 = r74.append(r75);
        r0 = r74;
        r1 = r32;
        r74 = r0.append(r1);
        r74 = r74.toString();
        r73.info(r74);
        r73 = logger;
        r74 = new java.lang.StringBuilder;
        r74.<init>();
        r75 = "p0p = ";
        r74 = r74.append(r75);
        r0 = r74;
        r1 = r34;
        r74 = r0.append(r1);
        r74 = r74.toString();
        r73.info(r74);
    L_0x0150:
        r73 = r34.degreeVector();
        r18 = r73.dependencyOnVariables();
        r0 = r18;
        r0 = r0.length;
        r73 = r0;
        r74 = 1;
        r0 = r73;
        r1 = r74;
        if (r0 == r1) goto L_0x0182;
    L_0x0165:
        r73 = new java.lang.RuntimeException;
        r74 = new java.lang.StringBuilder;
        r74.<init>();
        r75 = "wrong number of variables ";
        r74 = r74.append(r75);
        r75 = java.util.Arrays.toString(r18);
        r74 = r74.append(r75);
        r74 = r74.toString();
        r73.<init>(r74);
        throw r73;
    L_0x0182:
        r0 = r32;
        r0 = r0.ring;
        r73 = r0;
        r0 = r73;
        r10 = r0.coFac;
        r9 = new edu.jas.poly.ComplexRing;
        r9.<init>(r10);
        r22 = new edu.jas.poly.GenPolynomialRing;
        r0 = r32;
        r0 = r0.ring;
        r73 = r0;
        r0 = r22;
        r1 = r73;
        r0.<init>(r9, r1);
        r0 = r22;
        r1 = r32;
        r33 = edu.jas.poly.PolyUtil.complexFromAny(r0, r1);
        r14 = edu.jas.application.RootFactory.complexAlgebraicNumbersSquarefree(r33);
        r73 = logger;
        r74 = new java.lang.StringBuilder;
        r74.<init>();
        r75 = "#roots(p0c) = ";
        r74 = r74.append(r75);
        r75 = r14.size();
        r74 = r74.append(r75);
        r74 = r74.toString();
        r73.info(r74);
        r73 = debug;
        if (r73 == 0) goto L_0x01f1;
    L_0x01cc:
        r0 = r33;
        r60 = edu.jas.application.RootFactory.isRoot(r0, r14);
        if (r60 != 0) goto L_0x01f1;
    L_0x01d4:
        r73 = new java.lang.RuntimeException;
        r74 = new java.lang.StringBuilder;
        r74.<init>();
        r75 = "no roots of ";
        r74 = r74.append(r75);
        r0 = r74;
        r1 = r33;
        r74 = r0.append(r1);
        r74 = r74.toString();
        r73.<init>(r74);
        throw r73;
    L_0x01f1:
        r24 = r14.iterator();
    L_0x01f5:
        r73 = r24.hasNext();
        if (r73 == 0) goto L_0x020d;
    L_0x01fb:
        r13 = r24.next();
        r13 = (edu.jas.poly.Complex) r13;
        r11 = new java.util.ArrayList;
        r11.<init>();
        r11.add(r13);
        r6.add(r11);
        goto L_0x01f5;
    L_0x020d:
        r0 = r21;
        r0 = r0.nvar;
        r73 = r0;
        r74 = 1;
        r0 = r73;
        r1 = r74;
        if (r0 != r1) goto L_0x0224;
    L_0x021b:
        r4 = new edu.jas.application.IdealWithComplexAlgebraicRoots;
        r0 = r78;
        r4.<init>(r0, r6);
        goto L_0x005b;
    L_0x0224:
        r23 = 1;
    L_0x0226:
        r0 = r78;
        r0 = r0.upolys;
        r73 = r0;
        r73 = r73.size();
        r0 = r23;
        r1 = r73;
        if (r0 >= r1) goto L_0x07f8;
    L_0x0236:
        r12 = new java.util.ArrayList;
        r12.<init>();
        r0 = r78;
        r0 = r0.upolys;
        r73 = r0;
        r0 = r73;
        r1 = r23;
        r35 = r0.get(r1);
        r35 = (edu.jas.poly.GenPolynomial) r35;
        r0 = r78;
        r0 = r0.ideal;
        r73 = r0;
        r0 = r73;
        r0 = r0.list;
        r73 = r0;
        r0 = r73;
        r0 = r0.list;
        r73 = r0;
        r0 = r21;
        r0 = r0.nvar;
        r74 = r0;
        r74 = r74 + -1;
        r74 = r74 - r23;
        r37 = edu.jas.poly.PolyUtil.selectWithVariable(r73, r74);
        if (r37 != 0) goto L_0x02a0;
    L_0x026d:
        r73 = new java.lang.RuntimeException;
        r74 = new java.lang.StringBuilder;
        r74.<init>();
        r75 = "no polynomial found in ";
        r74 = r74.append(r75);
        r0 = r21;
        r0 = r0.nvar;
        r75 = r0;
        r75 = r75 + -1;
        r75 = r75 - r23;
        r74 = r74.append(r75);
        r75 = " of  ";
        r74 = r74.append(r75);
        r0 = r78;
        r0 = r0.ideal;
        r75 = r0;
        r74 = r74.append(r75);
        r74 = r74.toString();
        r73.<init>(r74);
        throw r73;
    L_0x02a0:
        r73 = logger;
        r73 = r73.isInfoEnabled();
        if (r73 == 0) goto L_0x02ee;
    L_0x02a8:
        r73 = logger;
        r74 = new java.lang.StringBuilder;
        r74.<init>();
        r75 = "pi(";
        r74 = r74.append(r75);
        r0 = r74;
        r1 = r23;
        r74 = r0.append(r1);
        r75 = ") = ";
        r74 = r74.append(r75);
        r0 = r74;
        r1 = r35;
        r74 = r0.append(r1);
        r74 = r74.toString();
        r73.info(r74);
        r73 = logger;
        r74 = new java.lang.StringBuilder;
        r74.<init>();
        r75 = "pip  = ";
        r74 = r74.append(r75);
        r0 = r74;
        r1 = r37;
        r74 = r0.append(r1);
        r74 = r74.toString();
        r73.info(r74);
    L_0x02ee:
        r22 = new edu.jas.poly.GenPolynomialRing;
        r0 = r35;
        r0 = r0.ring;
        r73 = r0;
        r0 = r22;
        r1 = r73;
        r0.<init>(r9, r1);
        r0 = r22;
        r1 = r35;
        r36 = edu.jas.poly.PolyUtil.complexFromAny(r0, r1);
        r73 = r37.degreeVector();
        r19 = r73.dependencyOnVariables();
        r0 = r19;
        r0 = r0.length;
        r73 = r0;
        r74 = 1;
        r0 = r73;
        r1 = r74;
        if (r0 < r1) goto L_0x0327;
    L_0x031a:
        r0 = r19;
        r0 = r0.length;
        r73 = r0;
        r74 = 2;
        r0 = r73;
        r1 = r74;
        if (r0 <= r1) goto L_0x0352;
    L_0x0327:
        r73 = new java.lang.RuntimeException;
        r74 = new java.lang.StringBuilder;
        r74.<init>();
        r75 = "wrong number of variables ";
        r74 = r74.append(r75);
        r75 = java.util.Arrays.toString(r19);
        r74 = r74.append(r75);
        r75 = " for ";
        r74 = r74.append(r75);
        r0 = r74;
        r1 = r37;
        r74 = r0.append(r1);
        r74 = r74.toString();
        r73.<init>(r74);
        throw r73;
    L_0x0352:
        r14 = edu.jas.application.RootFactory.complexAlgebraicNumbersSquarefree(r36);
        r73 = logger;
        r74 = new java.lang.StringBuilder;
        r74.<init>();
        r75 = "#roots(pic) = ";
        r74 = r74.append(r75);
        r75 = r14.size();
        r74 = r74.append(r75);
        r74 = r74.toString();
        r73.info(r74);
        r73 = debug;
        if (r73 == 0) goto L_0x039b;
    L_0x0376:
        r0 = r36;
        r60 = edu.jas.application.RootFactory.isRoot(r0, r14);
        if (r60 != 0) goto L_0x039b;
    L_0x037e:
        r73 = new java.lang.RuntimeException;
        r74 = new java.lang.StringBuilder;
        r74.<init>();
        r75 = "no roots of ";
        r74 = r74.append(r75);
        r0 = r74;
        r1 = r36;
        r74 = r0.append(r1);
        r74 = r74.toString();
        r73.<init>(r74);
        throw r73;
    L_0x039b:
        r0 = r19;
        r0 = r0.length;
        r73 = r0;
        r74 = 1;
        r0 = r73;
        r1 = r74;
        if (r0 != r1) goto L_0x03df;
    L_0x03a8:
        r24 = r14.iterator();
    L_0x03ac:
        r73 = r24.hasNext();
        if (r73 == 0) goto L_0x07f3;
    L_0x03b2:
        r13 = r24.next();
        r13 = (edu.jas.poly.Complex) r13;
        r25 = r6.iterator();
    L_0x03bc:
        r73 = r25.hasNext();
        if (r73 == 0) goto L_0x03ac;
    L_0x03c2:
        r16 = r25.next();
        r16 = (java.util.List) r16;
        r17 = new java.util.ArrayList;
        r17.<init>();
        r0 = r17;
        r1 = r16;
        r0.addAll(r1);
        r0 = r17;
        r0.add(r13);
        r0 = r17;
        r12.add(r0);
        goto L_0x03bc;
    L_0x03df:
        r38 = edu.jas.poly.PolyUtil.removeUnusedUpperVariables(r37);
        r0 = r38;
        r0 = r0.ring;
        r73 = r0;
        r74 = 1;
        r50 = r73.recursive(r74);
        r0 = r38;
        r0 = r0.ring;
        r73 = r0;
        r74 = 1;
        r65 = r73.contract(r74);
        r64 = new edu.jas.poly.GenPolynomialRing;
        r0 = r64;
        r1 = r65;
        r0.<init>(r9, r1);
        r5 = new edu.jas.poly.GenPolynomialRing;
        r0 = r38;
        r0 = r0.ring;
        r73 = r0;
        r0 = r73;
        r5.<init>(r9, r0);
        r0 = r38;
        r39 = edu.jas.poly.PolyUtil.complexFromAny(r5, r0);
        r46 = new edu.jas.poly.GenPolynomialRing;
        r0 = r46;
        r1 = r64;
        r2 = r50;
        r0.<init>(r1, r2);
        r0 = r46;
        r1 = r39;
        r42 = edu.jas.poly.PolyUtil.recursive(r0, r1);
        r0 = r21;
        r0 = r0.nvar;
        r73 = r0;
        r73 = r73 + -1;
        r0 = r19;
        r0 = r0.length;
        r74 = r0;
        r74 = r74 + -1;
        r74 = r19[r74];
        r29 = r73 - r74;
        r24 = r14.iterator();
    L_0x0441:
        r73 = r24.hasNext();
        if (r73 == 0) goto L_0x07f3;
    L_0x0447:
        r13 = r24.next();
        r13 = (edu.jas.poly.Complex) r13;
        r73 = java.lang.System.out;
        r74 = new java.lang.StringBuilder;
        r74.<init>();
        r75 = "cr = ";
        r74 = r74.append(r75);
        r75 = toString(r13);
        r74 = r74.append(r75);
        r74 = r74.toString();
        r73.println(r74);
        r0 = r13.ring;
        r73 = r0;
        r0 = r73;
        r15 = r0.ring;
        r15 = (edu.jas.application.RealAlgebraicRing) r15;
        r52 = r15.getRoot();
        r0 = r52;
        r0 = r0.tuple;
        r51 = r0;
        r73 = 0;
        r0 = r51;
        r1 = r73;
        r73 = r0.get(r1);
        r73 = (edu.jas.root.RealAlgebraicNumber) r73;
        r0 = r73;
        r0 = r0.ring;
        r73 = r0;
        r70 = r73.getRoot();
        r73 = 1;
        r0 = r51;
        r1 = r73;
        r73 = r0.get(r1);
        r73 = (edu.jas.root.RealAlgebraicNumber) r73;
        r0 = r73;
        r0 = r0.ring;
        r73 = r0;
        r69 = r73.getRoot();
        r73 = logger;
        r74 = new java.lang.StringBuilder;
        r74.<init>();
        r75 = "vr = ";
        r74 = r74.append(r75);
        r0 = r74;
        r1 = r70;
        r74 = r0.append(r1);
        r75 = ", vi = ";
        r74 = r74.append(r75);
        r0 = r74;
        r1 = r69;
        r74 = r0.append(r1);
        r74 = r74.toString();
        r73.info(r74);
        r73 = r70.length();
        r73 = (edu.jas.structure.GcdRingElem) r73;
        r73 = r73.isZERO();
        if (r73 == 0) goto L_0x0536;
    L_0x04df:
        r0 = r70;
        r0 = r0.left;
        r73 = r0;
        r73 = (edu.jas.structure.GcdRingElem) r73;
        r73 = r73.factory();
        r74 = "1/2";
        r20 = r73.parse(r74);
        r20 = (edu.jas.structure.GcdRingElem) r20;
        r0 = r70;
        r0 = r0.left;
        r30 = r0;
        r30 = (edu.jas.structure.GcdRingElem) r30;
        r70 = new edu.jas.root.Interval;
        r0 = r30;
        r1 = r20;
        r73 = r0.subtract(r1);
        r73 = (edu.jas.structure.RingElem) r73;
        r0 = r30;
        r1 = r20;
        r74 = r0.sum(r1);
        r74 = (edu.jas.structure.RingElem) r74;
        r0 = r70;
        r1 = r73;
        r2 = r74;
        r0.<init>(r1, r2);
        r73 = logger;
        r74 = new java.lang.StringBuilder;
        r74.<init>();
        r75 = "|vr| == 0: ";
        r74 = r74.append(r75);
        r0 = r74;
        r1 = r70;
        r74 = r0.append(r1);
        r74 = r74.toString();
        r73.info(r74);
    L_0x0536:
        r73 = r69.length();
        r73 = (edu.jas.structure.GcdRingElem) r73;
        r73 = r73.isZERO();
        if (r73 == 0) goto L_0x0599;
    L_0x0542:
        r0 = r69;
        r0 = r0.left;
        r73 = r0;
        r73 = (edu.jas.structure.GcdRingElem) r73;
        r73 = r73.factory();
        r74 = "1/2";
        r20 = r73.parse(r74);
        r20 = (edu.jas.structure.GcdRingElem) r20;
        r0 = r69;
        r0 = r0.left;
        r30 = r0;
        r30 = (edu.jas.structure.GcdRingElem) r30;
        r69 = new edu.jas.root.Interval;
        r0 = r30;
        r1 = r20;
        r73 = r0.subtract(r1);
        r73 = (edu.jas.structure.RingElem) r73;
        r0 = r30;
        r1 = r20;
        r74 = r0.sum(r1);
        r74 = (edu.jas.structure.RingElem) r74;
        r0 = r69;
        r1 = r73;
        r2 = r74;
        r0.<init>(r1, r2);
        r73 = logger;
        r74 = new java.lang.StringBuilder;
        r74.<init>();
        r75 = "|vi| == 0: ";
        r74 = r74.append(r75);
        r0 = r74;
        r1 = r69;
        r74 = r0.append(r1);
        r74 = r74.toString();
        r73.info(r74);
    L_0x0599:
        r59 = new edu.jas.poly.Complex;
        r0 = r70;
        r0 = r0.left;
        r73 = r0;
        r0 = r69;
        r0 = r0.left;
        r74 = r0;
        r0 = r59;
        r1 = r73;
        r2 = r74;
        r0.<init>(r9, r1, r2);
        r31 = new edu.jas.poly.Complex;
        r0 = r70;
        r0 = r0.right;
        r73 = r0;
        r0 = r69;
        r0 = r0.right;
        r74 = r0;
        r0 = r31;
        r1 = r73;
        r2 = r74;
        r0.<init>(r9, r1, r2);
        r73 = logger;
        r74 = new java.lang.StringBuilder;
        r74.<init>();
        r75 = "sw   = ";
        r74 = r74.append(r75);
        r75 = toString1(r59);
        r74 = r74.append(r75);
        r75 = ", ne   = ";
        r74 = r74.append(r75);
        r75 = toString1(r31);
        r74 = r74.append(r75);
        r74 = r74.toString();
        r73.info(r74);
        r0 = r64;
        r1 = r42;
        r2 = r59;
        r41 = edu.jas.poly.PolyUtil.evaluateMainRecursive(r0, r1, r2);
        r0 = r64;
        r1 = r42;
        r2 = r31;
        r40 = edu.jas.poly.PolyUtil.evaluateMainRecursive(r0, r1, r2);
        r0 = r78;
        r0 = r0.upolys;
        r73 = r0;
        r0 = r73;
        r1 = r29;
        r73 = r0.get(r1);
        r73 = (edu.jas.poly.GenPolynomial) r73;
        r0 = r73;
        r0 = r0.ring;
        r67 = r0;
        r66 = new edu.jas.poly.GenPolynomialRing;
        r0 = r66;
        r1 = r67;
        r0.<init>(r9, r1);
        r0 = r66;
        r1 = r41;
        r44 = convertComplexComplex(r0, r1);
        r0 = r66;
        r1 = r40;
        r43 = convertComplexComplex(r0, r1);
        r25 = r6.iterator();
    L_0x0638:
        r73 = r25.hasNext();
        if (r73 == 0) goto L_0x0441;
    L_0x063e:
        r16 = r25.next();
        r16 = (java.util.List) r16;
        r0 = r16;
        r1 = r29;
        r8 = r0.get(r1);
        r8 = (edu.jas.poly.Complex) r8;
        r7 = r8.ring;
        r0 = r7.ring;
        r45 = r0;
        r45 = (edu.jas.application.RealAlgebraicRing) r45;
        r63 = new edu.jas.poly.TermOrder;
        r73 = 2;
        r0 = r63;
        r1 = r73;
        r0.<init>(r1);
        r0 = r45;
        r0 = r0.algebraic;
        r73 = r0;
        r0 = r73;
        r0 = r0.ring;
        r73 = r0;
        r73 = r73.getVars();
        r74 = 0;
        r72 = r73[r74];
        r0 = r45;
        r0 = r0.algebraic;
        r73 = r0;
        r0 = r73;
        r0 = r0.ring;
        r73 = r0;
        r73 = r73.getVars();
        r74 = 1;
        r71 = r73[r74];
        r73 = 2;
        r0 = r73;
        r0 = new java.lang.String[r0];
        r68 = r0;
        r73 = 0;
        r68[r73] = r72;
        r73 = 1;
        r68[r73] = r71;
        r62 = new edu.jas.poly.GenPolynomialRing;
        r0 = r62;
        r1 = r63;
        r2 = r68;
        r0.<init>(r9, r1, r2);
        r73 = 1;
        r74 = 1;
        r0 = r62;
        r1 = r73;
        r2 = r74;
        r73 = r0.univariate(r1, r2);
        r74 = 0;
        r76 = 1;
        r0 = r62;
        r1 = r74;
        r2 = r76;
        r74 = r0.univariate(r1, r2);
        r75 = r9.getIMAG();
        r74 = r74.multiply(r75);
        r61 = r73.sum(r74);
        r53 = new edu.jas.poly.GenPolynomialRing;
        r0 = r53;
        r1 = r62;
        r0.<init>(r10, r1);
        r0 = r44;
        r1 = r61;
        r58 = edu.jas.poly.PolyUtil.substituteUnivariate(r0, r1);
        r0 = r53;
        r1 = r58;
        r47 = edu.jas.poly.PolyUtil.realPartFromComplex(r0, r1);
        r0 = r53;
        r1 = r58;
        r26 = edu.jas.poly.PolyUtil.imaginaryPartFromComplex(r0, r1);
        r49 = new edu.jas.application.RealAlgebraicNumber;
        r0 = r49;
        r1 = r45;
        r2 = r47;
        r0.<init>(r1, r2);
        r57 = r49.signum();
        r28 = new edu.jas.application.RealAlgebraicNumber;
        r0 = r28;
        r1 = r45;
        r2 = r26;
        r0.<init>(r1, r2);
        r56 = r28.signum();
        r0 = r43;
        r1 = r61;
        r58 = edu.jas.poly.PolyUtil.substituteUnivariate(r0, r1);
        r0 = r53;
        r1 = r58;
        r47 = edu.jas.poly.PolyUtil.realPartFromComplex(r0, r1);
        r0 = r53;
        r1 = r58;
        r26 = edu.jas.poly.PolyUtil.imaginaryPartFromComplex(r0, r1);
        r48 = new edu.jas.application.RealAlgebraicNumber;
        r0 = r48;
        r1 = r45;
        r2 = r47;
        r0.<init>(r1, r2);
        r55 = r48.signum();
        r27 = new edu.jas.application.RealAlgebraicNumber;
        r0 = r27;
        r1 = r45;
        r2 = r26;
        r0.<init>(r1, r2);
        r54 = r27.signum();
        r73 = r57 * r55;
        if (r73 > 0) goto L_0x07a9;
    L_0x0745:
        r73 = r56 * r54;
        if (r73 > 0) goto L_0x07a9;
    L_0x0749:
        r74 = logger;
        r73 = new java.lang.StringBuilder;
        r73.<init>();
        r75 = "   hit, cxi = ";
        r0 = r73;
        r1 = r75;
        r75 = r0.append(r1);
        r0 = r16;
        r1 = r29;
        r73 = r0.get(r1);
        r73 = (edu.jas.poly.Complex) r73;
        r73 = toString(r73);
        r0 = r75;
        r1 = r73;
        r73 = r0.append(r1);
        r75 = ", cr = ";
        r0 = r73;
        r1 = r75;
        r73 = r0.append(r1);
        r75 = toString(r13);
        r0 = r73;
        r1 = r75;
        r73 = r0.append(r1);
        r73 = r73.toString();
        r0 = r74;
        r1 = r73;
        r0.info(r1);
        r17 = new java.util.ArrayList;
        r17.<init>();
        r0 = r17;
        r1 = r16;
        r0.addAll(r1);
        r0 = r17;
        r0.add(r13);
        r0 = r17;
        r12.add(r0);
        goto L_0x0638;
    L_0x07a9:
        r74 = logger;
        r73 = new java.lang.StringBuilder;
        r73.<init>();
        r75 = "no hit, cxi = ";
        r0 = r73;
        r1 = r75;
        r75 = r0.append(r1);
        r0 = r16;
        r1 = r29;
        r73 = r0.get(r1);
        r73 = (edu.jas.poly.Complex) r73;
        r73 = toString(r73);
        r0 = r75;
        r1 = r73;
        r73 = r0.append(r1);
        r75 = ", cr = ";
        r0 = r73;
        r1 = r75;
        r73 = r0.append(r1);
        r75 = toString(r13);
        r0 = r73;
        r1 = r75;
        r73 = r0.append(r1);
        r73 = r73.toString();
        r0 = r74;
        r1 = r73;
        r0.info(r1);
        goto L_0x0638;
    L_0x07f3:
        r6 = r12;
        r23 = r23 + 1;
        goto L_0x0226;
    L_0x07f8:
        r4 = new edu.jas.application.IdealWithComplexAlgebraicRoots;
        r0 = r78;
        r4.<init>(r0, r6);
        goto L_0x005b;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.application.PolyUtilApp.complexAlgebraicRootsWrong(edu.jas.application.IdealWithUniv):edu.jas.application.IdealWithComplexAlgebraicRoots<D>");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <D extends edu.jas.structure.GcdRingElem<D> & edu.jas.arith.Rational> edu.jas.application.IdealWithComplexAlgebraicRoots<D> complexAlgebraicRoots(edu.jas.application.IdealWithUniv<D> r60) {
        /*
        r6 = new java.util.ArrayList;
        r6.<init>();
        if (r60 != 0) goto L_0x000f;
    L_0x0007:
        r57 = new java.lang.IllegalArgumentException;
        r58 = "null ideal not permitted";
        r57.<init>(r58);
        throw r57;
    L_0x000f:
        r0 = r60;
        r0 = r0.ideal;
        r57 = r0;
        if (r57 == 0) goto L_0x001f;
    L_0x0017:
        r0 = r60;
        r0 = r0.upolys;
        r57 = r0;
        if (r57 != 0) goto L_0x003c;
    L_0x001f:
        r57 = new java.lang.IllegalArgumentException;
        r58 = new java.lang.StringBuilder;
        r58.<init>();
        r59 = "null ideal components not permitted ";
        r58 = r58.append(r59);
        r0 = r58;
        r1 = r60;
        r58 = r0.append(r1);
        r58 = r58.toString();
        r57.<init>(r58);
        throw r57;
    L_0x003c:
        r0 = r60;
        r0 = r0.ideal;
        r57 = r0;
        r57 = r57.isZERO();
        if (r57 != 0) goto L_0x0054;
    L_0x0048:
        r0 = r60;
        r0 = r0.upolys;
        r57 = r0;
        r57 = r57.size();
        if (r57 != 0) goto L_0x005c;
    L_0x0054:
        r4 = new edu.jas.application.IdealWithComplexAlgebraicRoots;
        r0 = r60;
        r4.<init>(r0, r6);
    L_0x005b:
        return r4;
    L_0x005c:
        r0 = r60;
        r0 = r0.ideal;
        r57 = r0;
        r0 = r57;
        r0 = r0.list;
        r57 = r0;
        r0 = r57;
        r0 = r0.ring;
        r24 = r0;
        r0 = r24;
        r0 = r0.nvar;
        r57 = r0;
        if (r57 != 0) goto L_0x007e;
    L_0x0076:
        r4 = new edu.jas.application.IdealWithComplexAlgebraicRoots;
        r0 = r60;
        r4.<init>(r0, r6);
        goto L_0x005b;
    L_0x007e:
        r0 = r24;
        r0 = r0.nvar;
        r57 = r0;
        r0 = r60;
        r0 = r0.upolys;
        r58 = r0;
        r58 = r58.size();
        r0 = r57;
        r1 = r58;
        if (r0 == r1) goto L_0x00b1;
    L_0x0094:
        r57 = new java.lang.IllegalArgumentException;
        r58 = new java.lang.StringBuilder;
        r58.<init>();
        r59 = "ideal not zero dimnsional: ";
        r58 = r58.append(r59);
        r0 = r58;
        r1 = r60;
        r58 = r0.append(r1);
        r58 = r58.toString();
        r57.<init>(r58);
        throw r57;
    L_0x00b1:
        r0 = r60;
        r0 = r0.upolys;
        r57 = r0;
        r58 = 0;
        r32 = r57.get(r58);
        r32 = (edu.jas.poly.GenPolynomial) r32;
        r0 = r60;
        r0 = r0.ideal;
        r57 = r0;
        r0 = r57;
        r0 = r0.list;
        r57 = r0;
        r0 = r57;
        r0 = r0.list;
        r57 = r0;
        r0 = r24;
        r0 = r0.nvar;
        r58 = r0;
        r58 = r58 + -1;
        r34 = edu.jas.poly.PolyUtil.selectWithVariable(r57, r58);
        if (r34 != 0) goto L_0x0110;
    L_0x00df:
        r57 = new java.lang.RuntimeException;
        r58 = new java.lang.StringBuilder;
        r58.<init>();
        r59 = "no polynomial found in ";
        r58 = r58.append(r59);
        r0 = r24;
        r0 = r0.nvar;
        r59 = r0;
        r59 = r59 + -1;
        r58 = r58.append(r59);
        r59 = " of  ";
        r58 = r58.append(r59);
        r0 = r60;
        r0 = r0.ideal;
        r59 = r0;
        r58 = r58.append(r59);
        r58 = r58.toString();
        r57.<init>(r58);
        throw r57;
    L_0x0110:
        r57 = logger;
        r57 = r57.isInfoEnabled();
        if (r57 == 0) goto L_0x0150;
    L_0x0118:
        r57 = logger;
        r58 = new java.lang.StringBuilder;
        r58.<init>();
        r59 = "p0  = ";
        r58 = r58.append(r59);
        r0 = r58;
        r1 = r32;
        r58 = r0.append(r1);
        r58 = r58.toString();
        r57.info(r58);
        r57 = logger;
        r58 = new java.lang.StringBuilder;
        r58.<init>();
        r59 = "p0p = ";
        r58 = r58.append(r59);
        r0 = r58;
        r1 = r34;
        r58 = r0.append(r1);
        r58 = r58.toString();
        r57.info(r58);
    L_0x0150:
        r57 = r34.degreeVector();
        r21 = r57.dependencyOnVariables();
        r0 = r21;
        r0 = r0.length;
        r57 = r0;
        r58 = 1;
        r0 = r57;
        r1 = r58;
        if (r0 == r1) goto L_0x0182;
    L_0x0165:
        r57 = new java.lang.RuntimeException;
        r58 = new java.lang.StringBuilder;
        r58.<init>();
        r59 = "wrong number of variables ";
        r58 = r58.append(r59);
        r59 = java.util.Arrays.toString(r21);
        r58 = r58.append(r59);
        r58 = r58.toString();
        r57.<init>(r58);
        throw r57;
    L_0x0182:
        r0 = r32;
        r0 = r0.ring;
        r57 = r0;
        r0 = r57;
        r10 = r0.coFac;
        r9 = new edu.jas.poly.ComplexRing;
        r9.<init>(r10);
        r25 = new edu.jas.poly.GenPolynomialRing;
        r0 = r32;
        r0 = r0.ring;
        r57 = r0;
        r0 = r25;
        r1 = r57;
        r0.<init>(r9, r1);
        r0 = r25;
        r1 = r32;
        r33 = edu.jas.poly.PolyUtil.complexFromAny(r0, r1);
        r15 = edu.jas.application.RootFactory.complexAlgebraicNumbersSquarefree(r33);
        r57 = logger;
        r58 = new java.lang.StringBuilder;
        r58.<init>();
        r59 = "#roots(p0c) = ";
        r58 = r58.append(r59);
        r59 = r15.size();
        r58 = r58.append(r59);
        r58 = r58.toString();
        r57.info(r58);
        r27 = r15.iterator();
    L_0x01cc:
        r57 = r27.hasNext();
        if (r57 == 0) goto L_0x01e4;
    L_0x01d2:
        r14 = r27.next();
        r14 = (edu.jas.poly.Complex) r14;
        r11 = new java.util.ArrayList;
        r11.<init>();
        r11.add(r14);
        r6.add(r11);
        goto L_0x01cc;
    L_0x01e4:
        r0 = r24;
        r0 = r0.nvar;
        r57 = r0;
        r58 = 1;
        r0 = r57;
        r1 = r58;
        if (r0 != r1) goto L_0x01fb;
    L_0x01f2:
        r4 = new edu.jas.application.IdealWithComplexAlgebraicRoots;
        r0 = r60;
        r4.<init>(r0, r6);
        goto L_0x005b;
    L_0x01fb:
        r26 = 1;
    L_0x01fd:
        r0 = r60;
        r0 = r0.upolys;
        r57 = r0;
        r57 = r57.size();
        r0 = r26;
        r1 = r57;
        if (r0 >= r1) goto L_0x0605;
    L_0x020d:
        r12 = new java.util.ArrayList;
        r12.<init>();
        r0 = r60;
        r0 = r0.upolys;
        r57 = r0;
        r0 = r57;
        r1 = r26;
        r37 = r0.get(r1);
        r37 = (edu.jas.poly.GenPolynomial) r37;
        r0 = r60;
        r0 = r0.ideal;
        r57 = r0;
        r0 = r57;
        r0 = r0.list;
        r57 = r0;
        r0 = r57;
        r0 = r0.list;
        r57 = r0;
        r0 = r24;
        r0 = r0.nvar;
        r58 = r0;
        r58 = r58 + -1;
        r58 = r58 - r26;
        r39 = edu.jas.poly.PolyUtil.selectWithVariable(r57, r58);
        if (r39 != 0) goto L_0x0277;
    L_0x0244:
        r57 = new java.lang.RuntimeException;
        r58 = new java.lang.StringBuilder;
        r58.<init>();
        r59 = "no polynomial found in ";
        r58 = r58.append(r59);
        r0 = r24;
        r0 = r0.nvar;
        r59 = r0;
        r59 = r59 + -1;
        r59 = r59 - r26;
        r58 = r58.append(r59);
        r59 = " of  ";
        r58 = r58.append(r59);
        r0 = r60;
        r0 = r0.ideal;
        r59 = r0;
        r58 = r58.append(r59);
        r58 = r58.toString();
        r57.<init>(r58);
        throw r57;
    L_0x0277:
        r57 = logger;
        r57 = r57.isInfoEnabled();
        if (r57 == 0) goto L_0x02c5;
    L_0x027f:
        r57 = logger;
        r58 = new java.lang.StringBuilder;
        r58.<init>();
        r59 = "pi(";
        r58 = r58.append(r59);
        r0 = r58;
        r1 = r26;
        r58 = r0.append(r1);
        r59 = ") = ";
        r58 = r58.append(r59);
        r0 = r58;
        r1 = r37;
        r58 = r0.append(r1);
        r58 = r58.toString();
        r57.info(r58);
        r57 = logger;
        r58 = new java.lang.StringBuilder;
        r58.<init>();
        r59 = "pip  = ";
        r58 = r58.append(r59);
        r0 = r58;
        r1 = r39;
        r58 = r0.append(r1);
        r58 = r58.toString();
        r57.info(r58);
    L_0x02c5:
        r25 = new edu.jas.poly.GenPolynomialRing;
        r0 = r37;
        r0 = r0.ring;
        r57 = r0;
        r0 = r25;
        r1 = r57;
        r0.<init>(r9, r1);
        r0 = r25;
        r1 = r37;
        r38 = edu.jas.poly.PolyUtil.complexFromAny(r0, r1);
        r57 = r39.degreeVector();
        r22 = r57.dependencyOnVariables();
        r0 = r22;
        r0 = r0.length;
        r57 = r0;
        r58 = 1;
        r0 = r57;
        r1 = r58;
        if (r0 < r1) goto L_0x02fe;
    L_0x02f1:
        r0 = r22;
        r0 = r0.length;
        r57 = r0;
        r58 = 2;
        r0 = r57;
        r1 = r58;
        if (r0 <= r1) goto L_0x0329;
    L_0x02fe:
        r57 = new java.lang.RuntimeException;
        r58 = new java.lang.StringBuilder;
        r58.<init>();
        r59 = "wrong number of variables ";
        r58 = r58.append(r59);
        r59 = java.util.Arrays.toString(r22);
        r58 = r58.append(r59);
        r59 = " for ";
        r58 = r58.append(r59);
        r0 = r58;
        r1 = r39;
        r58 = r0.append(r1);
        r58 = r58.toString();
        r57.<init>(r58);
        throw r57;
    L_0x0329:
        r15 = edu.jas.application.RootFactory.complexAlgebraicNumbersSquarefree(r38);
        r57 = logger;
        r58 = new java.lang.StringBuilder;
        r58.<init>();
        r59 = "#roots(pic) = ";
        r58 = r58.append(r59);
        r59 = r15.size();
        r58 = r58.append(r59);
        r58 = r58.toString();
        r57.info(r58);
        r0 = r22;
        r0 = r0.length;
        r57 = r0;
        r58 = 1;
        r0 = r57;
        r1 = r58;
        if (r0 != r1) goto L_0x038d;
    L_0x0356:
        r27 = r15.iterator();
    L_0x035a:
        r57 = r27.hasNext();
        if (r57 == 0) goto L_0x0600;
    L_0x0360:
        r14 = r27.next();
        r14 = (edu.jas.poly.Complex) r14;
        r28 = r6.iterator();
    L_0x036a:
        r57 = r28.hasNext();
        if (r57 == 0) goto L_0x035a;
    L_0x0370:
        r19 = r28.next();
        r19 = (java.util.List) r19;
        r20 = new java.util.ArrayList;
        r20.<init>();
        r0 = r20;
        r1 = r19;
        r0.addAll(r1);
        r0 = r20;
        r0.add(r14);
        r0 = r20;
        r12.add(r0);
        goto L_0x036a;
    L_0x038d:
        r40 = edu.jas.poly.PolyUtil.removeUnusedUpperVariables(r39);
        r40 = edu.jas.poly.PolyUtil.removeUnusedLowerVariables(r40);
        r40 = edu.jas.poly.PolyUtil.removeUnusedMiddleVariables(r40);
        r0 = r40;
        r0 = r0.ring;
        r57 = r0;
        r58 = 1;
        r46 = r57.recursive(r58);
        r0 = r40;
        r0 = r0.ring;
        r57 = r0;
        r58 = 1;
        r50 = r57.contract(r58);
        r49 = new edu.jas.poly.GenPolynomialRing;
        r0 = r49;
        r1 = r50;
        r0.<init>(r9, r1);
        r5 = new edu.jas.poly.GenPolynomialRing;
        r0 = r40;
        r0 = r0.ring;
        r57 = r0;
        r0 = r57;
        r5.<init>(r9, r0);
        r0 = r40;
        r41 = edu.jas.poly.PolyUtil.complexFromAny(r5, r0);
        r43 = new edu.jas.poly.GenPolynomialRing;
        r0 = r43;
        r1 = r49;
        r2 = r46;
        r0.<init>(r1, r2);
        r0 = r43;
        r1 = r41;
        r42 = edu.jas.poly.PolyUtil.recursive(r0, r1);
        r0 = r24;
        r0 = r0.nvar;
        r57 = r0;
        r57 = r57 + -1;
        r0 = r22;
        r0 = r0.length;
        r58 = r0;
        r58 = r58 + -1;
        r58 = r22[r58];
        r29 = r57 - r58;
        r27 = r15.iterator();
    L_0x03f7:
        r57 = r27.hasNext();
        if (r57 == 0) goto L_0x0600;
    L_0x03fd:
        r14 = r27.next();
        r14 = (edu.jas.poly.Complex) r14;
        r0 = r14.ring;
        r57 = r0;
        r0 = r57;
        r0 = r0.ring;
        r16 = r0;
        r16 = (edu.jas.application.RealAlgebraicRing) r16;
        r48 = r16.getRoot();
        r0 = r48;
        r0 = r0.tuple;
        r47 = r0;
        r57 = 0;
        r0 = r47;
        r1 = r57;
        r57 = r0.get(r1);
        r57 = (edu.jas.root.RealAlgebraicNumber) r57;
        r0 = r57;
        r0 = r0.ring;
        r57 = r0;
        r54 = r57.getRoot();
        r57 = 1;
        r0 = r47;
        r1 = r57;
        r57 = r0.get(r1);
        r57 = (edu.jas.root.RealAlgebraicNumber) r57;
        r0 = r57;
        r0 = r0.ring;
        r57 = r0;
        r51 = r57.getRoot();
        r55 = new edu.jas.application.RealAlgebraicNumber;
        r0 = r54;
        r0 = r0.left;
        r57 = r0;
        r57 = (edu.jas.structure.GcdRingElem) r57;
        r0 = r55;
        r1 = r16;
        r2 = r57;
        r0.<init>(r1, r2);
        r52 = new edu.jas.application.RealAlgebraicNumber;
        r0 = r51;
        r0 = r0.left;
        r57 = r0;
        r57 = (edu.jas.structure.GcdRingElem) r57;
        r0 = r52;
        r1 = r16;
        r2 = r57;
        r0.<init>(r1, r2);
        r56 = new edu.jas.application.RealAlgebraicNumber;
        r0 = r54;
        r0 = r0.right;
        r57 = r0;
        r57 = (edu.jas.structure.GcdRingElem) r57;
        r0 = r56;
        r1 = r16;
        r2 = r57;
        r0.<init>(r1, r2);
        r53 = new edu.jas.application.RealAlgebraicNumber;
        r0 = r51;
        r0 = r0.right;
        r57 = r0;
        r57 = (edu.jas.structure.GcdRingElem) r57;
        r0 = r53;
        r1 = r16;
        r2 = r57;
        r0.<init>(r1, r2);
        r17 = new edu.jas.poly.ComplexRing;
        r0 = r17;
        r1 = r16;
        r0.<init>(r1);
        r18 = new edu.jas.poly.Complex;
        r0 = r18;
        r1 = r17;
        r2 = r55;
        r3 = r52;
        r0.<init>(r1, r2, r3);
        r13 = new edu.jas.poly.Complex;
        r0 = r17;
        r1 = r56;
        r2 = r53;
        r13.<init>(r0, r1, r2);
        r44 = new edu.jas.root.Rectangle;
        r0 = r44;
        r1 = r18;
        r0.<init>(r1, r13);
        r28 = r6.iterator();
    L_0x04bf:
        r57 = r28.hasNext();
        if (r57 == 0) goto L_0x03f7;
    L_0x04c5:
        r19 = r28.next();
        r19 = (java.util.List) r19;
        r0 = r19;
        r1 = r29;
        r8 = r0.get(r1);
        r8 = (edu.jas.poly.Complex) r8;
        r7 = r8.ring;
        r36 = new edu.jas.poly.GenPolynomialRing;
        r0 = r36;
        r1 = r43;
        r0.<init>(r7, r1);
        r0 = r36;
        r1 = r42;
        r35 = evaluateToComplexRealCoefficients(r0, r1, r8);
        r45 = new edu.jas.root.ComplexRootsSturm;
        r0 = r45;
        r0.<init>(r7);
        r30 = 0;
        r0 = r45;
        r1 = r44;
        r2 = r35;
        r30 = r0.complexRootCount(r1, r2);	 Catch:{ InvalidBoundaryException -> 0x0561 }
    L_0x04fb:
        r58 = 1;
        r57 = (r30 > r58 ? 1 : (r30 == r58 ? 0 : -1));
        if (r57 != 0) goto L_0x0566;
    L_0x0501:
        r58 = logger;
        r57 = new java.lang.StringBuilder;
        r57.<init>();
        r59 = "   hit, cxi = ";
        r0 = r57;
        r1 = r59;
        r59 = r0.append(r1);
        r0 = r19;
        r1 = r29;
        r57 = r0.get(r1);
        r57 = (edu.jas.poly.Complex) r57;
        r57 = toString(r57);
        r0 = r59;
        r1 = r57;
        r57 = r0.append(r1);
        r59 = ", cr = ";
        r0 = r57;
        r1 = r59;
        r57 = r0.append(r1);
        r59 = toString(r14);
        r0 = r57;
        r1 = r59;
        r57 = r0.append(r1);
        r57 = r57.toString();
        r0 = r58;
        r1 = r57;
        r0.info(r1);
        r20 = new java.util.ArrayList;
        r20.<init>();
        r0 = r20;
        r1 = r19;
        r0.addAll(r1);
        r0 = r20;
        r0.add(r14);
        r0 = r20;
        r12.add(r0);
        goto L_0x04bf;
    L_0x0561:
        r23 = move-exception;
        r23.printStackTrace();
        goto L_0x04fb;
    L_0x0566:
        r58 = 1;
        r57 = (r30 > r58 ? 1 : (r30 == r58 ? 0 : -1));
        if (r57 <= 0) goto L_0x05b6;
    L_0x056c:
        r58 = logger;
        r57 = new java.lang.StringBuilder;
        r57.<init>();
        r59 = "to many roots, cxi = ";
        r0 = r57;
        r1 = r59;
        r59 = r0.append(r1);
        r0 = r19;
        r1 = r29;
        r57 = r0.get(r1);
        r57 = (edu.jas.poly.Complex) r57;
        r57 = toString(r57);
        r0 = r59;
        r1 = r57;
        r57 = r0.append(r1);
        r59 = ", cr = ";
        r0 = r57;
        r1 = r59;
        r57 = r0.append(r1);
        r59 = toString(r14);
        r0 = r57;
        r1 = r59;
        r57 = r0.append(r1);
        r57 = r57.toString();
        r0 = r58;
        r1 = r57;
        r0.error(r1);
        goto L_0x04bf;
    L_0x05b6:
        r58 = logger;
        r57 = new java.lang.StringBuilder;
        r57.<init>();
        r59 = "no hit, cxi = ";
        r0 = r57;
        r1 = r59;
        r59 = r0.append(r1);
        r0 = r19;
        r1 = r29;
        r57 = r0.get(r1);
        r57 = (edu.jas.poly.Complex) r57;
        r57 = toString(r57);
        r0 = r59;
        r1 = r57;
        r57 = r0.append(r1);
        r59 = ", cr = ";
        r0 = r57;
        r1 = r59;
        r57 = r0.append(r1);
        r59 = toString(r14);
        r0 = r57;
        r1 = r59;
        r57 = r0.append(r1);
        r57 = r57.toString();
        r0 = r58;
        r1 = r57;
        r0.info(r1);
        goto L_0x04bf;
    L_0x0600:
        r6 = r12;
        r26 = r26 + 1;
        goto L_0x01fd;
    L_0x0605:
        r4 = new edu.jas.application.IdealWithComplexAlgebraicRoots;
        r0 = r60;
        r4.<init>(r0, r6);
        goto L_0x005b;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.application.PolyUtilApp.complexAlgebraicRoots(edu.jas.application.IdealWithUniv):edu.jas.application.IdealWithComplexAlgebraicRoots<D>");
    }

    public static <D extends GcdRingElem<D> & Rational> String toString(Complex<RealAlgebraicNumber<D>> c) {
        RealAlgebraicNumber<D> im = (RealAlgebraicNumber) c.getIm();
        String s = ((RealAlgebraicNumber) c.getRe()).decimalMagnitude().toString();
        if (im.isZERO()) {
            return s;
        }
        return s + "i" + im.decimalMagnitude();
    }

    public static <D extends GcdRingElem<D> & Rational> String toString1(Complex<D> c) {
        GcdRingElem im = (GcdRingElem) c.getIm();
        String s = new BigDecimal(((Rational) ((GcdRingElem) c.getRe())).getRational()).toString();
        if (im.isZERO()) {
            return s;
        }
        return s + "i" + new BigDecimal(((Rational) im).getRational());
    }

    public static <D extends GcdRingElem<D> & Rational> List<IdealWithComplexAlgebraicRoots<D>> complexAlgebraicRoots(List<IdealWithUniv<D>> I) {
        List<IdealWithComplexAlgebraicRoots<D>> lic = new ArrayList();
        for (IdealWithUniv iu : I) {
            lic.add(complexAlgebraicRoots(iu));
        }
        return lic;
    }

    public static <D extends GcdRingElem<D> & Rational> List<IdealWithComplexAlgebraicRoots<D>> complexAlgebraicRoots(Ideal<D> I) {
        return complexAlgebraicRoots(I.zeroDimRootDecomposition());
    }

    static <C extends RingElem<C>> GenPolynomial<C> convert(GenPolynomialRing<C> fac, GenPolynomial<C> p) {
        if (fac.equals(p.factory())) {
            return p;
        }
        GenPolynomial<C> q = fac.parse(p.toString());
        if (q.toString().equals(p.toString())) {
            return q;
        }
        throw new RuntimeException("convert(" + p + ") = " + q);
    }

    static <C extends RingElem<C>> GenPolynomial<Complex<C>> convertComplex(GenPolynomialRing<Complex<C>> fac, GenPolynomial<C> p) {
        GenPolynomial<Complex<C>> q = fac.parse(p.toString());
        if (q.toString().equals(p.toString())) {
            return q;
        }
        throw new RuntimeException("convert(" + p + ") = " + q);
    }

    static <C extends RingElem<C>> GenPolynomial<Complex<C>> convertComplexComplex(GenPolynomialRing<Complex<C>> fac, GenPolynomial<Complex<C>> p) {
        if (fac.equals(p.factory())) {
            return p;
        }
        GenPolynomial<Complex<C>> q = fac.parse(p.toString());
        if (q.toString().equals(p.toString())) {
            return q;
        }
        throw new RuntimeException("convert(" + p + ") = " + q);
    }

    public static <D extends GcdRingElem<D> & Rational> List<IdealWithRealAlgebraicRoots<D>> realAlgebraicRoots(Ideal<D> I) {
        return realAlgebraicRoots(I.zeroDimRootDecomposition());
    }

    public static <C extends GcdRingElem<C>> PrimitiveElement<C> primitiveElement(AlgebraicNumberRing<C> a, AlgebraicNumberRing<C> b) {
        GenPolynomial<C> ap = a.modul;
        String[] cv = new String[]{ap.ring.getVars()[0], b.modul.ring.getVars()[0]};
        TermOrder termOrder = new TermOrder(2);
        GenPolynomialRing<C> genPolynomialRing = new GenPolynomialRing(ap.ring.coFac, 2, termOrder, cv);
        GenPolynomial<C> as = ap.extendUnivariate(genPolynomialRing, 0);
        GenPolynomial<C> bs = bp.extendUnivariate(genPolynomialRing, 1);
        List L = new ArrayList(2);
        L.add(as);
        L.add(bs);
        List<GenPolynomial<C>> Op = new ArrayList();
        List<GenPolynomial<C>> Np = new Ideal((GenPolynomialRing) genPolynomialRing, L).normalPositionFor(0, 1, Op).ideal.getList();
        as = PolyUtil.selectWithVariable(Np, 1);
        bs = PolyUtil.selectWithVariable(Np, 0);
        genPolynomialRing = new GenPolynomialRing(ap.ring.coFac, 1, termOrder, new String[]{PolyUtil.selectWithVariable(Np, 2).ring.getVars()[0]});
        GenPolynomial<C> cs = cs.contractCoeff(genPolynomialRing);
        as = as.reductum().contractCoeff(genPolynomialRing).negate();
        bs = bs.reductum().contractCoeff(genPolynomialRing).negate();
        AlgebraicNumberRing<C> c = new AlgebraicNumberRing(cs);
        PrimitiveElement<C> pe = new PrimitiveElement(c, new AlgebraicNumber(c, as), new AlgebraicNumber(c, bs), a, b);
        if (logger.isInfoEnabled()) {
            logger.info("primitive element = " + c);
        }
        return pe;
    }

    public static <C extends GcdRingElem<C>> AlgebraicNumber<C> convertToPrimitiveElem(AlgebraicNumberRing<C> cfac, AlgebraicNumber<C> A, AlgebraicNumber<C> a) {
        return (AlgebraicNumber) PolyUtil.evaluateMain((RingFactory) cfac, PolyUtil.convertToAlgebraicCoefficients(new GenPolynomialRing((RingFactory) cfac, a.ring.ring), a.val), (RingElem) A);
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<AlgebraicNumber<C>> convertToPrimitiveElem(AlgebraicNumberRing<C> cfac, AlgebraicNumber<C> A, GenPolynomial<AlgebraicNumber<C>> a) {
        return PolyUtil.map(new GenPolynomialRing((RingFactory) cfac, a.ring), a, new CoeffConvertAlg(cfac, A));
    }

    public static <C extends GcdRingElem<C>> AlgebraicNumber<C> convertToPrimitiveElem(AlgebraicNumberRing<C> cfac, AlgebraicNumber<C> A, AlgebraicNumber<C> B, AlgebraicNumber<AlgebraicNumber<C>> a) {
        return (AlgebraicNumber) PolyUtil.evaluateMain((RingFactory) cfac, convertToPrimitiveElem((AlgebraicNumberRing) cfac, (AlgebraicNumber) A, a.val), (RingElem) B);
    }

    public static <C extends GcdRingElem<C>> PrimitiveElement<C> primitiveElement(AlgebraicNumberRing<AlgebraicNumber<C>> b) {
        GenPolynomial<AlgebraicNumber<C>> bp = b.modul;
        AlgebraicNumberRing<C> a = b.ring.coFac;
        GenPolynomial<C> ap = a.modul;
        cv = new String[2];
        cv[0] = ap.ring.getVars()[0];
        cv[1] = bp.ring.getVars()[0];
        TermOrder to = new TermOrder(2);
        GenPolynomialRing<C> genPolynomialRing = new GenPolynomialRing(ap.ring.coFac, 2, to, cv);
        GenPolynomialRing<GenPolynomial<C>> genPolynomialRing2 = new GenPolynomialRing((RingFactory) a.ring, 1, bp.ring.getVars());
        GenPolynomial<C> as = ap.extendUnivariate(genPolynomialRing, 0);
        GenPolynomial<C> bs = PolyUtil.distribute((GenPolynomialRing) genPolynomialRing, PolyUtil.fromAlgebraicCoefficients(genPolynomialRing2, bp));
        List L = new ArrayList(2);
        L.add(as);
        L.add(bs);
        List<GenPolynomial<C>> Op = new ArrayList();
        List<GenPolynomial<C>> Np = new Ideal((GenPolynomialRing) genPolynomialRing, L).normalPositionFor(0, 1, Op).ideal.getList();
        as = PolyUtil.selectWithVariable(Np, 1);
        bs = PolyUtil.selectWithVariable(Np, 0);
        GenPolynomial<C> cs = PolyUtil.selectWithVariable(Np, 2);
        String[] ev = new String[1];
        ev[0] = cs.ring.getVars()[0];
        genPolynomialRing = new GenPolynomialRing(ap.ring.coFac, 1, to, ev);
        cs = cs.contractCoeff(genPolynomialRing);
        as = as.reductum().contractCoeff(genPolynomialRing).negate();
        bs = bs.reductum().contractCoeff(genPolynomialRing).negate();
        AlgebraicNumberRing<C> algebraicNumberRing = new AlgebraicNumberRing(cs);
        PrimitiveElement<C> primitiveElement = new PrimitiveElement(algebraicNumberRing, new AlgebraicNumber(algebraicNumberRing, as), new AlgebraicNumber(algebraicNumberRing, bs));
        if (logger.isInfoEnabled()) {
            logger.info("primitive element = " + primitiveElement);
        }
        return primitiveElement;
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<AlgebraicNumber<C>> convertToPrimitiveElem(AlgebraicNumberRing<C> cfac, AlgebraicNumber<C> A, AlgebraicNumber<C> B, GenPolynomial<AlgebraicNumber<AlgebraicNumber<C>>> a) {
        return PolyUtil.map(new GenPolynomialRing((RingFactory) cfac, a.ring), a, new CoeffRecConvertAlg(cfac, A, B));
    }

    public static <C extends GcdRingElem<C> & Rational> GenPolynomial<RealAlgebraicNumber<C>> realAlgFromRealCoefficients(GenPolynomialRing<RealAlgebraicNumber<C>> afac, GenPolynomial<RealAlgebraicNumber<C>> A) {
        return PolyUtil.map(afac, A, new ReAlgFromRealCoeff(afac.coFac));
    }

    public static <C extends GcdRingElem<C> & Rational> GenPolynomial<RealAlgebraicNumber<C>> realFromRealAlgCoefficients(GenPolynomialRing<RealAlgebraicNumber<C>> rfac, GenPolynomial<RealAlgebraicNumber<C>> A) {
        return PolyUtil.map(rfac, A, new RealFromReAlgCoeff(rfac.coFac));
    }

    public static <C extends GcdRingElem<C> & Rational> GenPolynomial<Complex<RealAlgebraicNumber<C>>> convertToComplexRealCoefficients(GenPolynomialRing<Complex<RealAlgebraicNumber<C>>> pfac, GenPolynomial<Complex<C>> A) {
        return PolyUtil.map(pfac, A, new CoeffToComplexReal(pfac.coFac));
    }

    public static <C extends GcdRingElem<C> & Rational> GenPolynomial<Complex<RealAlgebraicNumber<C>>> evaluateToComplexRealCoefficients(GenPolynomialRing<Complex<RealAlgebraicNumber<C>>> pfac, GenPolynomial<GenPolynomial<Complex<C>>> A, Complex<RealAlgebraicNumber<C>> r) {
        return PolyUtil.map(pfac, A, new EvaluateToComplexReal(pfac, r));
    }
}
