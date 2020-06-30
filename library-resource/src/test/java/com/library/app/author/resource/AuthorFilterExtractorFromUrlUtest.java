package com.library.app.author.resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.UriInfo;

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
        
    }




}

