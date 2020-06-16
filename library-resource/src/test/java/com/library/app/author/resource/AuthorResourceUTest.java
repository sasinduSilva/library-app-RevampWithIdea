package com.library.app.author.resource;

import com.library.app.author.exception.AuthorNotFoundException;
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
import static com.library.app.common.json.JsonUtils.*;
import static org.mockito.Mockito.*;

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



//    @Test
//    public void updateValidAuthor() throws Exception{
//        Response response = authorResource.update(1L,readJsonFile(getPathFileRequest(PATH_RESOURCE,"robertMartin.json")));
//        assertThat(response.getStatus(),is(equalTo(HttpCode.OK.getCode())));
//        assertThat(response.getEntity().toString(), is(equalTo("")));
//
//        verify(authorServices).update(authorWithId(robertMartin(), 1L));
//    }
//
//    @Test
//    public void updateAuthorWithNullName() throws Exception{
//        doThrow(new FieldNotValidException("name", "may not be null")).when(authorServices).update((Author) anyObject());
//        Response response = authorResource.update(1L,readJsonFile(getPathFileRequest(PATH_RESOURCE, "authorWithNullName.json")));
//        assertThat(response.getStatus(), is(equalTo(HttpCode.VALIDATION_ERROR.getCode())));
//        assertJsonResponseWithFile(response,"authorErrorNullName.json");
//
//    }
//    @Test
//    public void updateAuthorNotFound()throws Exception{
//        doThrow(new AuthorNotFoundException()).when(authorServices).update(authorWithId(robertMartin(),2L));
//        Response response = authorResource.update(2L,readJsonFile(getPathFileRequest(PATH_RESOURCE,"robertMartin.json")));
//        assertThat(response.getStatus(), is(equalTo(HttpCode.NOT_FOUND.getCode())));
//    }
//
//    @Test
//    public void findAuthor()throws AuthorNotFoundException{
//        when(authorServices.findById(1L)).thenReturn(authorWithId(robertMartin(), 1L));
//
//        Response response = authorResource.findById(1L);
//        assertThat(response.getStatus(),is(equalTo(HttpCode.OK.getCode())));
//        assertJsonResponseWithFile(response,"robertMartinFound.json");
//    }
//    @Test
//    public void findAuthorNotFound()throws AuthorNotFoundException{
//        when(authorServices.findById(1L)).thenThrow(new AuthorNotFoundException());
//
//        Response response = authorResource.findById(1L);
//        assertThat(response.getStatus(), is(equalTo(HttpCode.NOT_FOUND.getCode())));
//    }

    private void assertJsonResponseWithFile(Response response, String fileName){
        assertJsonMatchesFileContent(response.getEntity().toString(), getPathFileRequest(PATH_RESOURCE, fileName));
    }

}
