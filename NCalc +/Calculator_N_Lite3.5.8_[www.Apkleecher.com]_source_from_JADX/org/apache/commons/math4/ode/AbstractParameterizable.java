package org.apache.commons.math4.ode;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractParameterizable implements Parameterizable {
    private final Collection<String> parametersNames;

    protected AbstractParameterizable(String... names) {
        this.parametersNames = new ArrayList();
        for (String name : names) {
            this.parametersNames.add(name);
        }
    }

    protected AbstractParameterizable(Collection<String> names) {
        this.parametersNames = new ArrayList();
        this.parametersNames.addAll(names);
    }

    public Collection<String> getParametersNames() {
        return this.parametersNames;
    }

    public boolean isSupported(String name) {
        for (String supportedName : this.parametersNames) {
            if (supportedName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void complainIfNotSupported(String name) throws UnknownParameterException {
        if (!isSupported(name)) {
            throw new UnknownParameterException(name);
        }
    }
}
