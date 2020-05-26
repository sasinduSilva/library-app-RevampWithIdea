package com.library.app.commontests.utils;

import org.junit.Ignore;

@Ignore
public enum ResourceDefinition {
    CATEGORY("categories");

    private String resourceName;

    private ResourceDefinition(final String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }
}