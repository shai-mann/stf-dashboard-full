package com.vmware.stfdashboard.util.converters;

import com.vmware.stfdashboard.util.SuiteType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

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
