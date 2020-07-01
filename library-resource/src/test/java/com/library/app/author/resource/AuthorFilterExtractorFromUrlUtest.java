package com.library.app.author.resource;

import com.library.app.author.model.filter.AuthorFilter;
import com.library.app.common.model.filter.PaginationData;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Equality;
import sun.nio.cs.Surrogate;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthorFilterExtractorFromUrlUtest {

    @Mock
    private UriInfo uriInfo;

    @Before
    public void initTestCase(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void OnlyDefaultValues(){
        setUpUriInfo(null,null,null,null);

        AuthorFilterExtractorFromUrl extractor = new AuthorFilterExtractorFromUrl(uriInfo);
        AuthorFilter authorFilter = extractor.getFilter();

        assertActualPaginationDataWithExpected(authorFilter.getPaginationData(), new PaginationData(0,10,"name", PaginationData.OrderMode.ASCENDING));
        assertThat(authorFilter.getName(), is(nullValue()));


    }
    @Test
    public void withPaginationAndNameAndSortAscending(){
        setUpUriInfo("2","5","Robert","id");

        AuthorFilterExtractorFromUrl extractor = new AuthorFilterExtractorFromUrl(uriInfo);
        AuthorFilter authorFilter = extractor.getFilter();

        assertActualPaginationDataWithExpected(authorFilter.getPaginationData(),new PaginationData(10,5,"id", PaginationData.OrderMode.ASCENDING));
        assertThat(authorFilter.getName(), is(equalTo("Robert")));
    }

    @Test
    public void withPaginationAndNameAndSortAscendingWithPrefix(){
        setUpUriInfo("2","5","Robert","+id");

        AuthorFilterExtractorFromUrl extractor = new AuthorFilterExtractorFromUrl(uriInfo);
        AuthorFilter authorFilter = extractor.getFilter();

        assertActualPaginationDataWithExpected(authorFilter.getPaginationData(),new PaginationData(10,5,"id", PaginationData.OrderMode.ASCENDING));
        assertThat(authorFilter.getName(), is(equalTo("Robert")));


    }

    @Test
    public void withPaginationAndNameAndSortDescending(){
        setUpUriInfo("2","5","Robert","-id");

        AuthorFilterExtractorFromUrl extractor = new AuthorFilterExtractorFromUrl(uriInfo);
        AuthorFilter authorFilter = extractor.getFilter();

        assertActualPaginationDataWithExpected(authorFilter.getPaginationData(),new PaginationData(10,5,"id", PaginationData.OrderMode.DESCENDIG));
        assertThat(authorFilter.getName(), is(equalTo("Robert")));


    }





    @SuppressWarnings("unchecked")
    private void setUpUriInfo(String page, String perPage, String name, String sort){
        Map<String,String> parameters = new LinkedHashMap<>();
        parameters.put("page",page);
        parameters.put("per page",perPage);
        parameters.put("name",name);
        parameters.put("sort",sort);

        MultivaluedMap<String, String> multiMap = mock(MultivaluedMap.class);

        for (Map.Entry<String, String> keyValue: parameters.entrySet()) {
            when(multiMap.getFirst(keyValue.getKey())).thenReturn(keyValue.getValue());

        }

        when(uriInfo.getQueryParameters()).thenReturn(multiMap);
    }

    private void assertActualPaginationDataWithExpected(PaginationData actual, PaginationData expected) {
        assertThat(actual.getFirstResult(),is(equalTo(expected.getFirstResult())));
        assertThat(actual.getMaxResults(),is(equalTo(expected.getMaxResults())));
        assertThat(actual.getOrderFields(),is(equalTo(expected.getOrderFields())));
        assertThat(actual.getOrderMode(),is(equalTo(expected.getOrderMode())));

    }




}

