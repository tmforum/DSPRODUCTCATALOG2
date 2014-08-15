package tmf.org.dsmapi.catalog.service;

import java.util.ArrayList;
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
import tmf.org.dsmapi.catalog.ProductSpecification;
import tmf.org.dsmapi.commons.exceptions.BadUsageException;

/**
 *
 * @author pierregauthier
 *
 */
@Stateless
@Path("tmf.org.dsmapi.catalog.productSpecification")
public class ProductSpecificationFacadeREST {

    @EJB
    private ProductSpecificationFacade manager;

    public ProductSpecificationFacadeREST() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findByCriteriaWithFields(@Context UriInfo uriInfo) throws BadUsageException {

        // search criteria
        MultivaluedMap<String, String> criteria = uriInfo.getQueryParameters();
        // fields to filter view
        Set<String> fieldSet = FacadeRestUtil.getFieldSet(criteria);

        Set<ProductSpecification> resultList = manager.find(criteria, ProductSpecification.class);

        Response response;
        if (fieldSet.isEmpty() || fieldSet.contains(FacadeRestUtil.ALL_FIELDS)) {
            response = Response.ok(resultList).build();
        } else {
            fieldSet.add(FacadeRestUtil.ID_FIELD);
            List<ObjectNode> nodeList = new ArrayList<ObjectNode>();
            for (ProductSpecification productOffering : resultList) {
                ObjectNode node = FacadeRestUtil.createNodeViewWithFields(productOffering, fieldSet);
                nodeList.add(node);
            }
            response = Response.ok(nodeList).build();
        }
        return response;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(ProductSpecification input) {
         if (input == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

     //   input.setId(null);
        manager.create(input);
        return Response.ok(input).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response edit(ProductSpecification entity) {
        Response response = null;
        ProductSpecification productSpecification = manager.find(entity.getId());
        if (productSpecification != null) {
            // 200
            manager.edit(entity);
            response = Response.ok(entity).build();
        } else {
            // 404 not found
            response = Response.status(404).build();
        }
        return response;
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        manager.remove(manager.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findWithFields(@PathParam("id") String id, @Context UriInfo uriInfo) {
        // fields to filter view
        Set<String> fieldSet = FacadeRestUtil.getFieldSet(uriInfo.getQueryParameters());

        ProductSpecification p = manager.find(id);
        Response response;
        // if troubleTicket exists
        if (p != null) {
            // 200
            if (fieldSet.isEmpty() || fieldSet.contains(FacadeRestUtil.ALL_FIELDS)) {
                response = Response.ok(p).build();
            } else {
                fieldSet.add(FacadeRestUtil.ID_FIELD);
                ObjectNode node = FacadeRestUtil.createNodeViewWithFields(p, fieldSet);
                response = Response.ok(node).build();
            }
        } else {
            // 404 not found
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(manager.count());
    }

    @GET
    @Path("proto")
    @Produces({MediaType.APPLICATION_JSON})
    public Response proto() {
        return Response.ok(ProductSpecification.createProto()).build();
    }

}
