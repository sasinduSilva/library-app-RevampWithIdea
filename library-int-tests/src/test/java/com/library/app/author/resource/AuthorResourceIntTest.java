package com.library.app.author.resource;

import com.library.app.commontests.utils.ResourceClient;
import com.library.app.commontests.utils.ResourceDefinitions;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.runner.RunWith;

import java.io.File;
import java.net.URL;

@RunWith(Arquillian.class)
public class AuthorResourceIntTest {

        @ArquillianResource
        private URL url;

        private ResourceClient resourceClient;

        private static final String PATH_RESOURCE = ResourceDefinitions.AUTHOR.getResourceName();

        @Deployment
    public static WebArchive createDeployment(){
            return ShrinkWrap
                    .create(WebArchive.class)
                    .addPackages(true, "com.library.app")
                    .addAsResource("persistence-integration.xml", "META-INF/persistence.xml")
                    .addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml")
                    .setWebXML(new File("src/test/resource/web.xml"))
                    .addAsLibraries(Maven.resolver().resolve("com.google.code.gson:gson:2.3.1", "org.mockito:mockito-core:1.9.5").withTransitivity().asFile());

        }
}
