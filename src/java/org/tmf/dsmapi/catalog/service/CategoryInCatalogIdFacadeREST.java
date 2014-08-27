package org.tmf.dsmapi.catalog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.catalog.Category;
import org.tmf.dsmapi.commons.jaxrs.PATCH;

/**
 *
 * @author bahman.barzideh
 *
 */
@Stateless
@Path("catalog/{catalogId}/category")
public class CategoryInCatalogIdFacadeREST {
    private static final Logger logger = Logger.getLogger(Category.class.getName());

    @EJB
    private CategoryFacade manager;

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(@PathParam("catalogId") String catalogId, @PathParam("categoryId") String categoryId, Category input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "CategoryFacadeREST:create()");

        if (input == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        input.setHref(FacadeRestUtil.buildHref(uriInfo, "category", input.getId(), input.getVersion()));
        manager.create(input);

        return Response.ok(input).build();
    }

    @PUT
    @Path("{categoryId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("catalogId") String catalogId, @PathParam("categoryId") String categoryId, Category input) {
        logger.log(Level.FINE, "CategoryFacadeREST:update(categoryId: {0})", categoryId);

        if (input == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Category category = manager.find(categoryId);
        if (category == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

//        category.setId(categoryId);
        manager.edit(input);
        return Response.ok(input).build();
    }

    @PUT
    @Path("{categoryId}:({categoryVersion})")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("catalogId") String catalogId, @PathParam("categoryId") String categoryId, @PathParam("categoryVersion") Float categoryVersion, Category input) {
        logger.log(Level.FINE, "CategoryFacadeREST:update(categoryId: {0}, categoryVersion: {1})", new Object[]{categoryId, categoryVersion});

        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @PATCH
    @Path("{categoryId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("catalogId") String catalogId, @PathParam("categoryId") String categoryId, Category input) {
        logger.log(Level.FINE, "CategoryFacadeREST:edit(categoryId: {0})", categoryId);

        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @PATCH
    @Path("{categoryId}:({categoryVersion})")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("catalogId") String catalogId, @PathParam("categoryId") String categoryId, @PathParam("categoryVersion") Float categoryVersion, Category input) {
        logger.log(Level.FINE, "CategoryFacadeREST:edit(categoryId: {0}, categoryVersion: {1})", new Object[]{categoryId, categoryVersion});

        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @DELETE
    @Path("{categoryId}")
    public Response remove(@PathParam("catalogId") String catalogId, @PathParam("categoryId") String categoryId) {
        logger.log(Level.FINE, "CategoryFacadeREST:remove(categoryId: {0})", categoryId);

        Category category = manager.find(categoryId);
        if (category == null) {
            return Response.status(Response.Status.NOT_FOUND).build ();
        }

        manager.remove(category);
        return Response.ok().build();
    }

    @DELETE
    @Path("{categoryId}:({categoryVersion})")
    public Response remove(@PathParam("catalogId") String catalogId, @PathParam("categoryId") String categoryId, @PathParam("categoryVersion") Float categoryVersion) {
        logger.log(Level.FINE, "CategoryFacadeREST:remove(categoryId: {0}, categoryVersion: {1})", new Object[]{categoryId, categoryVersion});

        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("catalogId") String catalogId, @Context UriInfo uriInfo) throws BadUsageException {
        logger.log(Level.FINE, "CategoryFacadeREST:find()");

        MultivaluedMap<String, String> criteria = uriInfo.getQueryParameters();
        Set<Category> results = manager.find(criteria, Category.class);

        Set<String> outputFields = FacadeRestUtil.getFieldSet(criteria);
        if (outputFields.isEmpty() || outputFields.contains(FacadeRestUtil.ALL_FIELDS)) {
            return Response.ok(results).build();
        }

        outputFields.add(FacadeRestUtil.ID_FIELD);
        List<ObjectNode> nodeList = new ArrayList<ObjectNode>();
        for (Category category : results) {
            ObjectNode node = FacadeRestUtil.createNodeViewWithFields(category, outputFields);
            nodeList.add(node);
        }

        return Response.ok(nodeList).build ();
    }

    @GET
    @Path("{categoryId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findById(@PathParam("catalogId") String catalogId, @PathParam("categoryId") String categoryId, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "CategoryFacadeREST:find(categoryId: {0})", categoryId);

        List<Category> results = manager.findById(null, null,categoryId, null);
        if (results == null || results.size() <= 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Category category = results.get(0);

        Set<String> outputFields = FacadeRestUtil.getFieldSet(uriInfo.getQueryParameters());
        if (outputFields.isEmpty() || outputFields.contains(FacadeRestUtil.ALL_FIELDS)) {
            return Response.ok(category).build();
        }

        outputFields.add(FacadeRestUtil.ID_FIELD);
        ObjectNode node = FacadeRestUtil.createNodeViewWithFields(category, outputFields);
        return Response.ok(node).build();
    }

    @GET
    @Path("{categoryId}:({categoryVersion})")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("catalogId") String catalogId, @PathParam("categoryId") String categoryId, @PathParam("categoryVersion") Float categoryVersion, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "CategoryFacadeREST:find(categoryId: {0}, categoryVersion: {1})", new Object[]{categoryId, categoryVersion});

        List<Category> results = manager.findById(null, null,categoryId, categoryVersion);
        if (results == null || results.size() <= 0) {
        }

        Category category = results.get(0);

        Set<String> outputFields = FacadeRestUtil.getFieldSet(uriInfo.getQueryParameters());
        if (outputFields.isEmpty() || outputFields.contains(FacadeRestUtil.ALL_FIELDS)) {
            return Response.ok(category).build();
        }

        outputFields.add(FacadeRestUtil.ID_FIELD);
        ObjectNode node = FacadeRestUtil.createNodeViewWithFields(category, outputFields);
        return Response.ok(node).build();
    }

    @GET
    @Path("admin/proto")
    @Produces({MediaType.APPLICATION_JSON})
    public Response proto() {
        logger.log(Level.FINE, "CategoryFacadeREST:proto()");

        return Response.ok(Category.createProto()).build();
    }

    @GET
    @Path("admin/count")
    @Produces({MediaType.TEXT_PLAIN})
    public String count() {
        logger.log(Level.FINE, "CategoryFacadeREST:count()");

        return String.valueOf(manager.count());
    }

}

