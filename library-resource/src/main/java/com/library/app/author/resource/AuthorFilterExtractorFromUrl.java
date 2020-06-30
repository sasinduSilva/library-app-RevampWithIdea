package com.library.app.author.resource;

import com.library.app.author.model.filter.AuthorFilter;

import javax.ws.rs.core.UriInfo;

public class AuthorFilterExtractorFromUrl {
    private UriInfo uriInfo;

    public AuthorFilterExtractorFromUrl(UriInfo uriInfo) {
        this.uriInfo= uriInfo;
    }

    public AuthorFilter getFilter(){
        AuthorFilter authorFilter = new AuthorFilter();

        return authorFilter;
    }

}
