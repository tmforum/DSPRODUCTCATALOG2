package org.tmf.dsmapi.jaxrs.resource.hub;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.commons.exceptions.UnknownResourceException;
import org.tmf.dsmapi.commons.jaxrs.model.Report;
import org.tmf.dsmapi.hub.model.Hub;
import org.tmf.dsmapi.catalog.hub.service.catalog.CatalogEventFacade;
import org.tmf.dsmapi.hub.service.HubFacade;

@Stateless
@Path("hub")
public class CatalogHubResource {

    @EJB
    HubFacade hubFacade;
    @EJB
    CatalogEventFacade eventFacade;

    public CatalogHubResource() {
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(Hub entity) throws BadUsageException {
        entity.setId(null);
        hubFacade.create(entity);
        Response response = Response.ok(entity).build();
        return response;
    }

    @DELETE
    public Report deleteAllHub() {

        int previousRows = hubFacade.count();
        hubFacade.removeAll();
        int currentRows = hubFacade.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        return stat;
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) throws UnknownResourceException {
        hubFacade.remove(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Hub> findAll() {
        return hubFacade.findAll();
    }

}
