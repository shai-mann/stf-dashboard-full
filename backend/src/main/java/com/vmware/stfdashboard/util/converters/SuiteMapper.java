package com.vmware.stfdashboard.util.converters;

import com.vmware.stfdashboard.util.SddcType;
import com.vmware.stfdashboard.util.SuiteType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * A {@link Converter} that is hooked into Spring Boot to allow for conversion between
 * the {@link String} values in the H2 database and the {@link SuiteType} enum.
 */
@Converter(autoApply = true)
public class SuiteMapper implements AttributeConverter<SuiteType, String> {

    @Override
    public String convertToDatabaseColumn(SuiteType suite) {
        return suite.value();
    }

    @Override
    public SuiteType convertToEntityAttribute(String s) {
        return SuiteType.findByValue(s);
    }

}
