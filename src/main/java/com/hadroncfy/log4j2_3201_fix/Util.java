package com.hadroncfy.log4j2_3201_fix;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.lookup.Interpolator;
import org.apache.logging.log4j.core.lookup.StrLookup;

public class Util {
    private static final Field Interpolator_lookups;
    private static final Field Interpolator_defaultLookup;
    static {
        Field f = null, f2 = null;
        try {
            f = Interpolator.class.getDeclaredField("lookups");
            f2 = Interpolator.class.getDeclaredField("defaultLookup");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        f.setAccessible(true);
        Interpolator_lookups = f;
        Interpolator_defaultLookup = f2;
    }

    private static void removeLookups(Interpolator it) {
        try {
            ((Map<?, ?>) Interpolator_lookups.get(it)).clear();
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void disableLogLookups() {
        org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger)LogManager.getRootLogger();
        {
            StrLookup st = logger.getContext().getConfiguration().getStrSubstitutor().getVariableResolver();
            if (st instanceof Interpolator) {
                removeLookups((Interpolator) st);
            }
        }
        for (Map.Entry<String, Appender> entry: logger.getAppenders().entrySet()) {
            Layout<?> layout = entry.getValue().getLayout();
            if (layout instanceof PatternLayout) {
                PatternLayout pl = (PatternLayout) layout;
                StrLookup st = pl.getConfiguration().getStrSubstitutor().getVariableResolver();
                if (st instanceof Interpolator) {
                    removeLookups((Interpolator) st);
                }
            }
        }
    }
}
