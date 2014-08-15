package tmf.org.dsmapi.catalog.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author bahman.barzideh
 *
 */
public class CatalogClient {

    private CatalogClient() {
    }

    public static <T extends Class> Object getObject(String href, T theClass, int depth) {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource webResource = client.resource(href);

        if (depth > 0)
            webResource = webResource.queryParam("depth", String.valueOf(depth));

        return webResource.type(MediaType.APPLICATION_JSON).get(theClass);
    }

}
