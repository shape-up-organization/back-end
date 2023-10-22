package br.com.shapeup.common.utils;

import io.vavr.control.Try;
import java.beans.PropertyDescriptor;
import org.apache.commons.beanutils.PropertyUtils;

public class ObjectUtils {
    public static <T> void copyNonNullProperties(T dest, T orig) {
        PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(orig);
        for (PropertyDescriptor descriptor : descriptors) {
            String name = descriptor.getName();
            Try.of(() -> PropertyUtils.getSimpleProperty(orig, name))
                    .filter(value -> value != null)
                    .onSuccess(value -> Try.run(() -> PropertyUtils.setSimpleProperty(dest, name, value))
                            .onFailure(e -> e.getMessage()));
        }
    }
}


