package com.vmware.stfdashboard.util.converters;

import com.vmware.stfdashboard.util.SddcType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * A {@link Converter} that is hooked into Spring Boot to allow for conversion between
 * the {@link String} values in the H2 database and the {@link SddcType} enum.
 */
@Converter(autoApply = true)
public class SddcMapper implements AttributeConverter<SddcType, String> {

    @Override
    public String convertToDatabaseColumn(SddcType sddcType) {
        return sddcType.value();
    }

    @Override
    public SddcType convertToEntityAttribute(String s) {
        return SddcType.findByValue(s);
    }
}
