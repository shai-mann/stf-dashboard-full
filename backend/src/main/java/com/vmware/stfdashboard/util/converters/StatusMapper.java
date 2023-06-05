package com.vmware.stfdashboard.util.converters;

import com.vmware.stfdashboard.util.Status;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

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
