package org.tmf.dsmapi.catalog.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author bahman.barzideh
 *
 */
public class CatalogClient {

    private CatalogClient() {
    }

    public static Object getObject(String href, Class theClass, int depth) {
        ClientConfig config = new DefaultClientConfig();
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client client = Client.create(config);
        WebResource webResource = client.resource(href);

        if (depth > 0)
            webResource = webResource.queryParam("depth", String.valueOf(depth));

        return webResource.accept(MediaType.APPLICATION_JSON_TYPE).get(theClass);
    }

}
