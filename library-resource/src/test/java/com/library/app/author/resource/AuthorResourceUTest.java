package com.library.app.author.resource;

import com.library.app.author.model.Author;
import com.library.app.author.services.AuthorServices;
import com.library.app.common.exception.FieldNotValidException;
import com.library.app.common.model.HttpCode;
import com.library.app.commontests.utils.ResourceDefinitions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;

import static com.library.app.commontests.author.AuthorForTestRepository.authorWithId;
import static com.library.app.commontests.author.AuthorForTestRepository.robertMartin;
import static com.library.app.commontests.utils.FileTestNameUtils.getPathFileRequest;
import static com.library.app.commontests.utils.JsonTestUtils.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static com.library.app.common.json.JsonUtils.*;

public class AuthorResourceUTest {
    private AuthorResource authorResource;

    private static final String PATH_RESOURCE = ResourceDefinitions.AUTHOR.getResourceName();

    @Mock
    private AuthorServices authorServices;

    @Before
    public void initTestCase(){
        MockitoAnnotations.initMocks(this);
        authorResource=new AuthorResource();

        authorResource.authorServices = authorServices;
        authorResource.authorJsonConverter = new AuthorJsonConverter();


    }

    @Test
    public void addValidAuthor(){
        when(authorServices.add(robertMartin())).thenReturn(authorWithId(robertMartin(),1L));

        Response response = authorResource.add(readJsonFile(getPathFileRequest(PATH_RESOURCE,"robertMartin.json")));
        assertThat(response.getStatus(), is(equalTo(HttpCode.CREATED.getCode())));
        assertJsonMatchesExpectedJson(response.getEntity().toString(),"{\"id\": 1}");
    }

    @Test
    public void addAuthorWithNullName() throws Exception{
        when(authorServices.add((Author)anyObject()) ).thenThrow(new FieldNotValidException("name", "may not be null"));


        Response response = authorResource.add(readJsonFile(getPathFileRequest(PATH_RESOURCE,"authorWithNullName.json")));
        assertThat(response.getStatus(),is(equalTo(HttpCode.VALIDATION_ERROR.getCode())));
        assertJsonResponseWithFile(response, "authorErrorNullName.json");






    }
    private void assertJsonResponseWithFile(Response response, String fileName){
        assertJsonMatchesFileContent(response.getEntity().toString(), getPathFileRequest(PATH_RESOURCE, fileName));
    }

}
