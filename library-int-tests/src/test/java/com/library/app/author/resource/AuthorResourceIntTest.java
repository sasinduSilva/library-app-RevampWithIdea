package com.library.app.author.resource;


import com.library.app.author.model.Author;
import com.library.app.commontests.utils.ArquillianTestUtils;
import com.library.app.commontests.utils.IntTestUtils;
import com.library.app.commontests.utils.ResourceClient;
import com.library.app.commontests.utils.ResourceDefinitions;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

import static com.library.app.commontests.author.AuthorForTestRepository.robertMartin;

@RunWith(Arquillian.class)
public class AuthorResourceIntTest {

        @ArquillianResource
        private URL url;

        private ResourceClient resourceClient;

        private static final String PATH_RESOURCE = ResourceDefinitions.AUTHOR.getResourceName();

        @Deployment
    public static WebArchive createDeployment(){
            return ArquillianTestUtils.createDeploymentArchive();

        }
        @Before
        public void initTestCase(){
            this.resourceClient = new ResourceClient(url);

            resourceClient.resourcePath("/DB").delete();
        }


        @Test
        @RunAsClient
        public void addValidAuthorAndFindIt(){
            final Long authorId = addAuthorAndGetId("robertMartin.json");
            findAuthorAndAssertResponseWithAuthor(authorId, robertMartin());
        }

    private void findAuthorAndAssertResponseWithAuthor(Long authorId, Author robertMartin) {
    }

    private Long addAuthorAndGetId(String fileName) {
            return IntTestUtils.addElementWithFileAndGetId(resourceClient,PATH_RESOURCE,PATH_RESOURCE,fileName);



    }
}
