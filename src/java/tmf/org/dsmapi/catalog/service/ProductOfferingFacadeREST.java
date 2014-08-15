package tmf.org.dsmapi.catalog.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jackson.node.ObjectNode;
import tmf.org.dsmapi.catalog.ProductOffering;
import tmf.org.dsmapi.commons.exceptions.BadUsageException;

/**
 *
 * @author pierregauthier
 *
 */
@Stateless
@Path("tmf.org.dsmapi.catalog.productOffering")
public class ProductOfferingFacadeREST {

    @EJB
    ProductOfferingFacade manager;

    public ProductOfferingFacadeREST() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@Context UriInfo uriInfo) throws BadUsageException {
        MultivaluedMap<String, String> criteria = uriInfo.getQueryParameters();
        Set<ProductOffering> results = manager.find(criteria, ProductOffering.class);

        Set<String> outputFields = FacadeRestUtil.getFieldSet(criteria);
        if (outputFields.isEmpty() || outputFields.contains(FacadeRestUtil.ALL_FIELDS)) {
            return Response.ok(results).build();
        }

        outputFields.add(FacadeRestUtil.ID_FIELD);
        List<ObjectNode> nodeList = new ArrayList<ObjectNode>();
        for (ProductOffering productOffering : results) {
            ObjectNode node = FacadeRestUtil.createNodeViewWithFields(productOffering, outputFields);
            nodeList.add(node);
        }

        return Response.ok(nodeList).build ();
    }

    @GET
    @Path("{productOfferingId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("productOfferingId") String productOfferingId, @Context UriInfo uriInfo) {
        ProductOffering productOffering = manager.find(productOfferingId);
        if (productOffering == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Set<String> outputFields = FacadeRestUtil.getFieldSet(uriInfo.getQueryParameters());
        if (outputFields.isEmpty() || outputFields.contains(FacadeRestUtil.ALL_FIELDS)) {
            return Response.ok(productOffering).build();
        }

        outputFields.add(FacadeRestUtil.ID_FIELD);
        ObjectNode node = FacadeRestUtil.createNodeViewWithFields(productOffering, outputFields);
        return Response.ok(node).build();
    }

    @GET
    @Path("catalog/{catalogId}/category/{productOfferingId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("catalogId") String catalogId, @PathParam("productOfferingId") String productOfferingId, @Context UriInfo uriInfo) {
        return Response.status(Response.Status.FORBIDDEN).build ();
    }

    @GET
    @Path("proto")
    @Produces({MediaType.APPLICATION_JSON})
    public ProductOffering proto() {
        return ProductOffering.createProto();
    }

    @GET
    @Path("count")
    @Produces({MediaType.TEXT_PLAIN})
    public String count() {
        return String.valueOf(manager.count());
    }

/*
    @GET
    @Path("admin/count")
    @Produces({MediaType.APPLICATION_JSON})
    public Report count() {
        return new Report(manager.count());
    }
    */

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(ProductOffering input) {
        if (input == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        input.setId(null);
        manager.create(input);
        return Response.ok(input).build();
    }

    @PUT
    @Path("{productOfferingId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("productOfferingId") String productOfferingId, ProductOffering input) {
        if (input == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        ProductOffering productOffering = manager.find(productOfferingId);
        if (productOffering == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        input.setId(productOfferingId);
        manager.edit(input);
        return Response.ok(input).build();
    }

    @DELETE
    @Path("{productOfferingId}")
    public Response remove(@PathParam("productOfferingId") String productOfferingId) {
        ProductOffering productOffering = manager.find(productOfferingId);
        if (productOffering == null) {
            return Response.status(Response.Status.NOT_FOUND).build ();
        }

        manager.remove(productOffering);
        return Response.ok().build();
    }

/*
    @DELETE
    @Path("admin")
    public Report deleteAll() {

        int previousRows = manager.count();
        manager.removeAll();
        int currentRows = manager.count();
        int affectedRows = previousRows - currentRows;

        Report stat = new Report(currentRows);
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        return stat;
    }
  */
/*
    @DELETE
    @Path("admin/{id}")
    public void remove(@PathParam("id") String id) {
        manager.remove(manager.find(id));
    }
*/
/*
    @POST
    @Path("admin")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createList(List<ProductOffering> entities) {

        if (entities == null) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
        }

        int previousRows = manager.count();
        int affectedRows;

        affectedRows = manager.create(entities);

        Report stat = new Report(manager.count());
        stat.setAffectedRows(affectedRows);
        stat.setPreviousRows(previousRows);

        // 201 OK
        return Response.created(null).
                entity(stat).
                build();
    }
*/
}
