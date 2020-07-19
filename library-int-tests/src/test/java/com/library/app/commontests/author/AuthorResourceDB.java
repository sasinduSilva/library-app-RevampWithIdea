package com.library.app.commontests.author;


import com.library.app.author.services.AuthorServices;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;

import static com.library.app.commontests.author.AuthorForTestRepository.allAuthors;

@Path("/DB/authors")
@Produces(MediaType.APPLICATION_JSON)
public class AuthorResourceDB {

    @Inject
    private AuthorServices authorServices;

    @POST //Indicates that the annotated method responds to HTTP POST requests.
    public void addAll(){
        allAuthors().forEach(authorServices::add);

    }

}
