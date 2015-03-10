package org.tmf.dsmapi.catalog.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.tmf.dsmapi.catalog.service.ServiceConstants;

/**
 *
 * @author bahman.barzideh
 *
 */
public class CatalogClient {

    private CatalogClient() {
    }

    public static Object getObject(String href, Class theClass, int depth) {
        Client client = createClient();
        WebTarget webResource = client.target(href);
        if (depth > ServiceConstants.MINIMUM_DEPTH_VALUE) {
            webResource = webResource.queryParam("depth", String.valueOf(depth));
        }
        
        return webResource.request(MediaType.APPLICATION_JSON).get(theClass);
    }
        
    private static Client createClient() {
        ObjectMapper mapper = new ObjectMapper();
        mapper = mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper = mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        mapper = mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, true);
        
        JacksonJaxbJsonProvider jacksonProvider = new JacksonJaxbJsonProvider();
        jacksonProvider.setMapper(mapper);
        ClientConfig config = new ClientConfig();
        config.register(jacksonProvider);
        Client client = ClientBuilder.newClient(config);
        client.property(ClientProperties.CONNECT_TIMEOUT, 3000);
        client.property(ClientProperties.READ_TIMEOUT, 3000);
        
        return client;
    }

}
