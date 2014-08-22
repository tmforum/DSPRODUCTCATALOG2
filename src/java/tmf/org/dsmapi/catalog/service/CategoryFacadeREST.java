package tmf.org.dsmapi.catalog.service;

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
import tmf.org.dsmapi.commons.exceptions.BadUsageException;
import tmf.org.dsmapi.catalog.Category;
import tmf.org.dsmapi.commons.jaxrs.PATCH;

/**
 *
 * @author bahman.barzideh
 *
 */
@Stateless
@Path("category")
public class CategoryFacadeREST {
    private static final Logger logger = Logger.getLogger(Category.class.getName());
    private static final String RELATIVE_CONTEXT = "category";

    @EJB
    private CategoryFacade manager;

    /*
     *
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(Category input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "CategoryFacadeREST:create()");

        if (input == null) {
            logger.log(Level.FINE, "input is required");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (input.isValid() == false) {
            logger.log(Level.FINE, "input is not valid");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        input.setCatalogId(Category.getDefaultCatalogId());
        input.setCatalogVersion(Category.getDefaultCatalogVersion());
        manager.create(input);

        input.setHref(FacadeRestUtil.buildHref(uriInfo, RELATIVE_CONTEXT, input.getId(), input.getVersion()));
        manager.edit(input);

        return Response.status(Response.Status.CREATED).entity(input).build();
    }

    /*
     *
     */
    @PUT
    @Path("{categoryId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("categoryId") String categoryId, Category input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "CategoryFacadeREST:update(categoryId: {0})", categoryId);

        return update_(categoryId, null, input, uriInfo);
    }

    /*
     *
     */
    @PUT
    @Path("{categoryId}:({categoryVersion})")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("categoryId") String categoryId, @PathParam("categoryVersion") Float categoryVersion, Category input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "CategoryFacadeREST:update(categoryId: {0}, categoryVersion: {1})", new Object[]{categoryId, categoryVersion});

        return update_(categoryId, categoryVersion, input, uriInfo);
    }

    /*
     *
     */
    @PATCH
    @Path("{categoryId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("categoryId") String categoryId, Category input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "CategoryFacadeREST:edit(categoryId: {0})", categoryId);

        return edit_(categoryId, null, input, uriInfo);
    }

    /*
     *
     */
    @PATCH
    @Path("{categoryId}:({categoryVersion})")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("categoryId") String categoryId, @PathParam("categoryVersion") Float categoryVersion, Category input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "CategoryFacadeREST:edit(categoryId: {0}, categoryVersion: {1})", new Object[]{categoryId, categoryVersion});

        return edit_(categoryId, categoryVersion, input, uriInfo);
    }

    /*
     *
     */
    @DELETE
    @Path("{categoryId}")
    public Response remove(@PathParam("categoryId") String categoryId) {
        logger.log(Level.FINE, "CategoryFacadeREST:remove(categoryId: {0})", categoryId);

        return remove_(categoryId, null);
    }

    /*
     *
     */
    @DELETE
    @Path("{categoryId}:({categoryVersion})")
    public Response remove(@PathParam("categoryId") String categoryId, @PathParam("categoryVersion") Float categoryVersion) {
        logger.log(Level.FINE, "CategoryFacadeREST:remove(categoryId: {0}, categoryVersion: {1})", new Object[]{categoryId, categoryVersion});

        return remove_(categoryId, categoryVersion);
    }

    /*
     *
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@Context UriInfo uriInfo) throws BadUsageException {
        logger.log(Level.FINE, "CategoryFacadeREST:find()");

        MultivaluedMap<String, String> criteria = uriInfo.getQueryParameters();

        // Remove the output filters before running the query.
        Set<String> outputFields = FacadeRestUtil.getFieldSet(criteria);

        Set<Category> results = manager.find(criteria, Category.class);
        if (results == null || results.size() <= 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

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

    /*
     *
     */
    @GET
    @Path("{categoryId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findById(@PathParam("categoryId") String categoryId, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "CategoryFacadeREST:find(categoryId: {0})", categoryId);

        return find_(categoryId, null, uriInfo);
    }

    /*
     *
     */
    @GET
    @Path("{categoryId}:({categoryVersion})")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("categoryId") String categoryId, @PathParam("categoryVersion") Float categoryVersion, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "CategoryFacadeREST:find(categoryId: {0}, categoryVersion: {1})", new Object[]{categoryId, categoryVersion});

        return find_(categoryId, categoryVersion, uriInfo);
    }

    /*
     *
     */
    @GET
    @Path("admin/proto")
    @Produces({MediaType.APPLICATION_JSON})
    public Response proto() {
        logger.log(Level.FINE, "CategoryFacadeREST:proto()");

        return Response.ok(Category.createProto()).build();
    }

    /*
     *
     */
    @GET
    @Path("admin/count")
    @Produces({MediaType.TEXT_PLAIN})
    public Response count() {
        logger.log(Level.FINE, "CategoryFacadeREST:count()");

        int entityCount = manager.count();
        return Response.ok(String.valueOf(entityCount)).build();
    }

    /*
     *
     */
    private Response update_(String categoryId, Float categoryVersion, Category input, UriInfo uriInfo) {
        logger.log(Level.FINE, "CategoryFacadeREST:update_(categoryId: {0}, categoryVersion: {1})", new Object[]{categoryId, categoryVersion});

        if (input == null) {
            logger.log(Level.FINE, "input is required.");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (input.isValid() == false) {
            logger.log(Level.FINE, "invalid input.");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<Category> categories = manager.findById(Category.getDefaultCatalogId(), Category.getDefaultCatalogVersion(), categoryId, categoryVersion);
        Category category = (categories != null && categories.size() > 0) ? categories.get(0) : null;
        if (category == null) {
            logger.log(Level.FINE, "requested category [{0}, {1}] not found", new Object[]{categoryId, categoryVersion});
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        input.setCatalogId(Category.getDefaultCatalogId());
        input.setCatalogVersion(Category.getDefaultCatalogVersion());

        if (input.keysMatch(category)) {
            input.setHref(FacadeRestUtil.buildHref(uriInfo, RELATIVE_CONTEXT, input.getId(), input.getVersion()));
            manager.edit(input);
            return Response.status(Response.Status.CREATED).entity(input).build();
        }

        manager.remove(category);
        manager.create(input);

        input.setHref(FacadeRestUtil.buildHref(uriInfo, RELATIVE_CONTEXT, input.getId(), input.getVersion()));
        manager.edit(input);

        return Response.status(Response.Status.CREATED).entity(input).build();
    }

    /*
     *
     */
    private Response edit_(String categoryId, Float categoryVersion, Category input, UriInfo uriInfo) {
        logger.log(Level.FINE, "CategoryFacadeREST:edit_(categoryId: {0}, categoryVersion: {1})", new Object[]{categoryId, categoryVersion});

        if (input == null) {
            logger.log(Level.FINE, "input is required");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<Category> categories = manager.findById(Category.getDefaultCatalogId(), Category.getDefaultCatalogVersion(), categoryId, categoryVersion);
        Category category = (categories != null && categories.size() > 0) ? categories.get(0) : null;
        if (category == null) {
            logger.log(Level.FINE, "requested category [{0}, {1}] not found", new Object[]{categoryId, categoryVersion});
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        category.edit(input);
        if(category.isValid() == false) {
            logger.log(Level.FINE, "patched category would be invalid");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (input.getVersion() == null) {
            manager.edit(category);
            return Response.status(Response.Status.CREATED).entity(category).build();
        }

        if (input.getVersion() <= category.getVersion()) {
            logger.log(Level.FINE, "specified version ({0} must be higher than entity's version({1})", new Object[]{input.getVersion(), category.getVersion()});
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        manager.remove(category);

        category.setVersion(input.getVersion());
        category.setHref(FacadeRestUtil.buildHref(uriInfo, RELATIVE_CONTEXT, category.getId(), category.getVersion()));
        manager.create(category);

        return Response.status(Response.Status.CREATED).entity(category).build();
    }

    /*
     *
     */
    private Response remove_(String categoryId, Float categoryVersion) {
        logger.log(Level.FINE, "CategoryFacadeREST:remove_(categoryId: {0}, categoryVersion: {1})", new Object[]{categoryId, categoryVersion});

        List<Category> categories = manager.findById(Category.getDefaultCatalogId(), Category.getDefaultCatalogVersion(), categoryId, categoryVersion);
        if (categories == null || categories.size() <= 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        manager.remove(categories.get(0));
        return Response.ok().build();
    }

    /*
     *
     */
    private Response find_(String categoryId, Float categoryVersion, UriInfo uriInfo) {
        logger.log(Level.FINE, "CategoryFacadeREST:find_(categoryId: {0}, categoryVersion: {1})", new Object[]{categoryId, categoryVersion});

        List<Category> results = manager.findById(Category.getDefaultCatalogId(), Category.getDefaultCatalogVersion(), categoryId, categoryVersion);
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

}
