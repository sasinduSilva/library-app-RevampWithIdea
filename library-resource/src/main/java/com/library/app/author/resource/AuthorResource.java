package com.library.app.author.resource;

import com.library.app.author.model.Author;
import com.library.app.author.services.AuthorServices;
import com.library.app.common.exception.FieldNotValidException;
import com.library.app.common.json.JsonUtils;
import com.library.app.common.json.OperationResultJsonWriter;
import com.library.app.common.model.HttpCode;
import com.library.app.common.model.OperationResult;
import com.library.app.common.model.ResourceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.library.app.author.resource.AuthorJsonConverter.*;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.library.app.common.model.StandardsOperationResults.getOperationResultInvalidField;

//@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final ResourceMessage RESOURCE_MESSAGE = new ResourceMessage("author");

    @Inject
    AuthorServices authorServices;

    @Inject
    AuthorJsonConverter authorJsonConverter;

    @POST
    public Response add(final String body){
        logger.debug("Adding a new author with body {}", body);

        Author author = AuthorJsonConverter.convertFrom(body);


        HttpCode httpCode = HttpCode.CREATED;

        OperationResult result;
        try {
            authorServices.add(author);
            result = OperationResult.success(JsonUtils.getJsonElementWithId(author.getId()));

        }catch (final FieldNotValidException e){
            httpCode = HttpCode.VALIDATION_ERROR;
            logger.error("One of the fields of the author is not valid ", e);
            result = getOperationResultInvalidField(RESOURCE_MESSAGE, e);
        }

        logger.debug("Returning the operation result after adding author: {}", result);
        return Response.status(httpCode.getCode()).entity(OperationResultJsonWriter.toJson(result)).build();

    }


}
