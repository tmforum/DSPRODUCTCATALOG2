package tmf.org.dsmapi.catalog.service;

import java.net.URI;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jackson.node.ObjectNode;
import tmf.org.dsmapi.catalog.Catalog;
import tmf.org.dsmapi.catalog.CatalogId;
import tmf.org.dsmapi.commons.exceptions.BadUsageException;
import tmf.org.dsmapi.commons.jaxrs.PATCH;

/**
 *
 * @author bahman.barzideh
 *
 */
@Stateless
@Path("catalog")
public class CatalogFacadeREST {
    private static final Logger logger = Logger.getLogger(Catalog.class.getName());
    private static final String RELATIVE_CONTEXT = "catalog";

    @EJB
    CatalogFacade manager;

    /*
     *
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(Catalog input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "CatalogFacadeREST:create()");

        if (input == null) {
           logger.log(Level.FINE, "input is required");
           return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (input.isValid() == false) {
            logger.log(Level.FINE, "input is not valid");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        manager.create(input);

        input.setHref(buildHref_(uriInfo, input.getId(), input.getVersion()));
        manager.edit(input);

        return Response.status(Response.Status.CREATED).entity(input).build();
    }

    /*
     *
     */
    @PUT
    @Path("{catalogId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("catalogId") String catalogId, Catalog input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "CatalogFacadeREST:update(catalogId: {0})", catalogId);

        return update_(catalogId, null, input, uriInfo);
    }

    /*
     *
     */
    @PUT
    @Path("{catalogId}:({catalogVersion})")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("catalogId") String catalogId, @PathParam("catalogVersion") Float catalogVersion, Catalog input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "CatalogFacadeREST:update(catalogId: {0}, catalogVersion: {1})", new Object[]{catalogId, catalogVersion});

        return update_(catalogId, catalogVersion, input, uriInfo);
    }

    /*
     *
     */
    @PATCH
    @Path("{catalogId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("catalogId") String catalogId, Catalog input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "CatalogFacadeREST::edit(catalogId: {0})", new Object[]{catalogId});

        return edit_(catalogId, null, input, uriInfo);
    }

    /*
     *
     */
    @PATCH
    @Path("{catalogId}:({catalogVersion})")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("catalogId") String catalogId, @PathParam("catalogVersion") Float catalogVersion, Catalog input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "CatalogFacadeREST:edit(catalogId: {0}, catalogVersion: {1})", new Object[]{catalogId, catalogVersion});

        return edit_(catalogId, catalogVersion, input, uriInfo);
    }

    /*
     *
     */
    @DELETE
    @Path("{catalogId}")
    public Response remove(@PathParam("catalogId") String catalogId) {
        logger.log(Level.FINE, "CatalogFacadeREST:remove(catalogId: {0})", new Object[]{catalogId});

        return remove_(catalogId, null);
    }

    /*
     *
     */
    @DELETE
    @Path("{catalogId}:({catalogVersion})")
    public Response remove(@PathParam("catalogId") String catalogId, @PathParam("catalogVersion") Float catalogVersion) {
        logger.log(Level.FINE, "CatalogFacadeREST:remove(catalogId: {0}, catalogVersion: {1})", new Object[]{catalogId, catalogVersion});

        return remove_(catalogId, catalogVersion);
    }

    /*
     *
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@QueryParam("depth") int depth, @Context UriInfo uriInfo) throws BadUsageException {
        logger.log(Level.FINE, "CatalogFacadeREST:find(depth: {0})", depth);

        MultivaluedMap<String, String> criteria = uriInfo.getQueryParameters();

        // Remove the output filters before running the query.
        Set<String> outputFields = FacadeRestUtil.getFieldSet(criteria);

        Set<Catalog> results = manager.find(criteria, Catalog.class);
        if (results == null || results.size() <= 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (depth > 0) {
            fetchChildren_(results, depth);
        }

       if (outputFields.isEmpty() || outputFields.contains(FacadeRestUtil.ALL_FIELDS)) {
           return Response.ok(results).build();
       }

       outputFields.add(FacadeRestUtil.ID_FIELD);
       List<ObjectNode> nodeList = new ArrayList<ObjectNode>();
       for (Catalog catalog : results) {
           ObjectNode node = FacadeRestUtil.createNodeViewWithFields(catalog, outputFields);
           nodeList.add(node);
       }

       return Response.ok(nodeList).build();
    }

    /*
     *
     */
    @GET
    @Path("{catalogId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findById(@PathParam("catalogId") String catalogId, @QueryParam("depth") int depth, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "CatalogFacadeREST:find(catalogId: {0}, depth: {1})", new Object[]{catalogId, depth});

        return find_(catalogId, null, depth, uriInfo);
    }

    /*
     *
     */
    @GET
    @Path("{catalogId}:({catalogVersion})")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findById(@PathParam("catalogId") String catalogId, @PathParam("catalogVersion") Float catalogVersion, @QueryParam("depth") int depth, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "CatalogFacadeREST:find(catalogId: {0}, catalogVersion: {1}, depth: {2})", new Object[]{catalogId, catalogVersion, depth});

        return find_(catalogId, catalogVersion, depth, uriInfo);

    }

    /*
     *
     */
    @GET
    @Path("admin/proto")
    @Produces({MediaType.APPLICATION_JSON})
    public Response proto() {
        logger.log(Level.FINE, "CatalogFacadeREST:proto()");

        return Response.ok(Catalog.createProto()).build();
    }

    @GET
    @Path("admin/count")
    @Produces({MediaType.TEXT_PLAIN})
    public Response count() {
        logger.log(Level.FINE, "CatalogFacadeREST:count()");

        int entityCount = manager.count();
        return Response.ok(String.valueOf(entityCount)).build();
    }

    /*
     *
     */
    private Response update_(String catalogId, Float catalogVersion, Catalog input, UriInfo uriInfo) {
        logger.log(Level.FINE, "CatalogFacadeREST:update_(catalogId: {0}, catalogVersion: {1})", new Object[]{catalogId, catalogVersion});

        if (input == null) {
            logger.log(Level.FINE, "input is required.");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (input.isValid() == false) {
            logger.log(Level.FINE, "invalid input.");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<Catalog> catalogs = manager.findCatalogById(catalogId, catalogVersion);
        Catalog catalog = (catalogs != null && catalogs.size() > 0) ? catalogs.get(0) : null;
        if (catalog == null) {
            logger.log(Level.FINE, "requested catalog [{0}, {1}] not found", new Object[]{catalogId, catalogVersion});
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (input.keysMatch(catalog)) {
            input.setHref(buildHref_(uriInfo, input.getId(), input.getVersion()));
            manager.edit(input);
            return Response.status(Response.Status.CREATED).entity(input).build();
        }

        manager.remove(catalog);
        manager.create(input);

        input.setHref(buildHref_(uriInfo, input.getId(), input.getVersion()));
        manager.edit(input);

        return Response.status(Response.Status.CREATED).entity(input).build();
    }

    /*
     *
     */
    private Response edit_(String catalogId, Float catalogVersion, Catalog input, UriInfo uriInfo) {
        logger.log(Level.FINE, "CatalogFacadeREST:edit_(catalogId: {0}, catalogVersion: {1})", new Object[]{catalogId, catalogVersion});

        if (input == null) {
            logger.log(Level.FINE, "input is required");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<Catalog> catalogs = manager.findCatalogById(catalogId, catalogVersion);
        Catalog catalog = (catalogs != null && catalogs.size() > 0) ? catalogs.get(0) : null;
        if (catalog == null) {
            logger.log(Level.FINE, "requested catalog [{0}, {1}] not found", new Object[]{catalogId, catalogVersion});
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        catalog.edit(input);
        if(catalog.isValid() == false) {
            logger.log(Level.FINE, "patched catalog would be invalid");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (input.getVersion() == null) {
            manager.edit(catalog);
            return Response.status(Response.Status.CREATED).entity(catalog).build();
        }

        if (input.getVersion() <= catalog.getVersion()) {
            logger.log(Level.FINE, "specified version ({0} must be higher than entity's version({1})", new Object[]{input.getVersion(), catalog.getVersion()});
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        manager.remove(catalog);

        catalog.setVersion(input.getVersion());
        catalog.setHref(buildHref_(uriInfo, catalog.getId(), catalog.getVersion()));
        manager.create(catalog);

        return Response.status(Response.Status.CREATED).entity(catalog).build();
    }

    /*
     *
     */
    private Response remove_(String catalogId, Float catalogVersion) {
        logger.log(Level.FINE, "CatalogFacadeREST:remove_(catalogId: {0}, catalogVersion: {1})", new Object[]{catalogId, catalogVersion});

        List<Catalog> catalogs = manager.findCatalogById(catalogId, catalogVersion);
        if (catalogs == null || catalogs.size() <= 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        manager.remove(catalogs.get(0));
        return Response.ok().build();
    }

    /*
     *
     */
    private Response find_(String catalogId, Float catalogVersion, int depth, UriInfo uriInfo) {
        logger.log(Level.FINE, "CategoryFacadeREST:find_(catalogId: {0}, catalogVersion: {1}, depth: {2})", new Object[]{catalogId, catalogVersion, depth});

        List<Catalog> results = manager.findCatalogById(catalogId, catalogVersion);
        if (results == null || results.size() <= 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Catalog catalog = results.get(0);

        if (depth > 0) {
            catalog.fetchChildren(depth);
        }

        Set<String> outputFields = FacadeRestUtil.getFieldSet(uriInfo.getQueryParameters());
        if (outputFields.isEmpty() || outputFields.contains(FacadeRestUtil.ALL_FIELDS)) {
            return Response.ok(catalog).build();
        }

        outputFields.add(FacadeRestUtil.ID_FIELD);
        ObjectNode node = FacadeRestUtil.createNodeViewWithFields(catalog, outputFields);
        return Response.ok(node).build();
    }

    /*
     *
     */
    private void fetchChildren_(Set<Catalog> catalogs, int depth) {
        for (Catalog catalog : catalogs) {
            catalog.fetchChildren(depth);
        }
    }

    /*
     * 
     */
    private String buildHref_(UriInfo uriInfo, String id, Float version) {
        URI uri = (uriInfo != null) ? uriInfo.getBaseUri() : null;
        String basePath = (uri != null) ? uri.toString() : null;
        if (basePath == null) {
            return null;
        }

        if (basePath.endsWith("/") == false) {
            basePath += "/";
        }

        basePath += CatalogFacadeREST.RELATIVE_CONTEXT + "/";
        if (id == null || id.length() <= 0) {
            return (basePath);
        }

        basePath += id;
        if (version == null) {
            return basePath;
        }

        return basePath + ":(" + version + ")";
    }

}
