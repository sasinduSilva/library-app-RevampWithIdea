package com.library.app.author.resource;

import com.library.app.author.model.filter.AuthorFilter;
import com.library.app.common.model.PaginatedData;
import com.library.app.common.model.filter.PaginationData;

import javax.ws.rs.core.UriInfo;

public class AuthorFilterExtractorFromUrl {
    private UriInfo uriInfo;

    public AuthorFilterExtractorFromUrl(UriInfo uriInfo) {

        this.uriInfo= uriInfo;
    }

    public AuthorFilter getFilter(){
        AuthorFilter authorFilter = new AuthorFilter();
        authorFilter.setPaginationData(extractPaginationDate());
        authorFilter.setName(uriInfo.getQueryParameters().getFirst("name"));
        return authorFilter;
    }

    private PaginationData extractPaginationDate(){
        int perPage = getPerPage();
        int firstResult = getPage() * perPage;

        String orderField;
        PaginationData.OrderMode orderMode;
        String sortField = getSortField();

        if (sortField.startsWith("+")){
            orderField = sortField.substring(1);
            orderMode = PaginationData.OrderMode.ASCENDING;

        }else if (sortField.startsWith("-")){
            orderField = sortField.substring(1);
            orderMode = PaginationData.OrderMode.DESCENDIG;
        }else {
            orderField = sortField;
            orderMode = PaginationData.OrderMode.ASCENDING;
        }

        return new PaginationData(firstResult,perPage, orderField, orderMode);
    }

    private String getSortField() {
        String sortField = uriInfo.getQueryParameters().getFirst("sort");
        if (sortField == null){
            return "name";
        }
        return sortField;
    }

    private Integer getPage() {
        String page = uriInfo.getQueryParameters().getFirst("page");
        if (page == null){
            return 0;
        }
        return Integer.parseInt(page);
    }

    private Integer getPerPage() {
        String perPage = uriInfo.getQueryParameters().getFirst("per_page");
        if (perPage == null){
            return 10;
        }
        return Integer.parseInt(perPage);
    }

}
