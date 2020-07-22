package com.library.app.author.resource;


import com.google.gson.JsonObject;
import com.library.app.author.model.Author;
import com.library.app.common.json.JsonReader;
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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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

    private void findAuthorAndAssertResponseWithAuthor(Long authorIdToBeFound, Author expectedAuthor) {
            String json = IntTestUtils.findById(resourceClient,PATH_RESOURCE,authorIdToBeFound);

            JsonObject cateGoryAsJson = JsonReader.readAsJsonObject(json);
            assertThat(JsonReader.getStringOrNull(cateGoryAsJson,"name"), is(equalTo(expectedAuthor.getName())));
    }

    private Long addAuthorAndGetId(String fileName) {
            return IntTestUtils.addElementWithFileAndGetId(resourceClient,PATH_RESOURCE,PATH_RESOURCE,fileName);



    }
}
