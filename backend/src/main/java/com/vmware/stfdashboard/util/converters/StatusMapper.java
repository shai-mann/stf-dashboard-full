package com.vmware.stfdashboard.util.converters;

import com.vmware.stfdashboard.util.SddcType;
import com.vmware.stfdashboard.util.Status;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * A {@link Converter} that is hooked into Spring Boot to allow for conversion between
 * the {@link String} values in the H2 database and the {@link Status} enum.
 */
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
