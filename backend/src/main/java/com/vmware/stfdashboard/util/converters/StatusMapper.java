package com.vmware.stfdashboard.util.converters;

import com.vmware.stfdashboard.util.Status;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatusMapper implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        return status.value();
    }

    @Override
    public Status convertToEntityAttribute(String s) {
        return Status.findByValue(s);
    }

}
