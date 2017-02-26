package org.apache.commons.math4.geometry.enclosing;

import java.util.List;
import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Space;

public interface SupportBallGenerator<S extends Space, P extends Point<S>> {
    EnclosingBall<S, P> ballOnSupport(List<P> list);
}
