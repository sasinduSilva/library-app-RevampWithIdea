package com.library.app.common.model.filter;

public class AuthorFilter extends GenericFilter {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AuthorFilter{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}
