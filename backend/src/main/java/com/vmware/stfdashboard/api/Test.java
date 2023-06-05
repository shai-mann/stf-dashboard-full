package com.vmware.stfdashboard.api;

import com.vmware.stfdashboard.models.processed.TestEntity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Contains relevant information about a specific {@link TestEntity}
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record Test(int id, String name, String parameters, String suite) {}
