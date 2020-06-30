package com.library.app.author.resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.LinkedHashMap;
import java.util.Map;

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




}

