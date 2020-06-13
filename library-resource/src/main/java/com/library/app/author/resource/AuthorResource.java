package com.library.app.author.resource;

import com.library.app.author.services.AuthorServices;
import com.library.app.common.model.ResourceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response add(String body){

    }


}
