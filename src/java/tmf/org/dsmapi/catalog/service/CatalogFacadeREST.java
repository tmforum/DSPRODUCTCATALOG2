package tmf.org.dsmapi.catalog.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
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

    @EJB
    CatalogFacade manager;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@QueryParam ("depth") int depth, @Context UriInfo uriInfo) throws BadUsageException {
        logger.log(Level.FINE, "--> CatalogFacadeREST::find(depth: {0})", depth);

        MultivaluedMap<String, String> criteria = uriInfo.getQueryParameters();
        Set<Catalog> results = manager.find(criteria, Catalog.class);

        if (depth > 0) {
            fetchChildren(results, depth);
        }

       Set<String> outputFields = FacadeRestUtil.getFieldSet(criteria);
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

    @GET
    @Path("{catalogId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findById(@PathParam("catalogId") String catalogId, @QueryParam ("depth") int depth, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "--> CatalogFacadeREST::find(catalogId: {0}, depth: {1})", new Object[]{catalogId, depth});

        List<Catalog> results = manager.findCatalogById(catalogId);
        if (results == null || results.size() <= 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Catalog catalog = results.get(0);
        if (catalog == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        catalog.fetchChildren(depth);

        Set<String> outputFields = FacadeRestUtil.getFieldSet(uriInfo.getQueryParameters());
        if (outputFields.isEmpty() || outputFields.contains(FacadeRestUtil.ALL_FIELDS)) {
            return Response.ok(catalog).build();
        }

        outputFields.add(FacadeRestUtil.ID_FIELD);
        ObjectNode node = FacadeRestUtil.createNodeViewWithFields(catalog, outputFields);
        return Response.ok(node).build();
    }

    @GET
    @Path("{catalogId}:({catalogVersion})")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findById(@PathParam("catalogId") String catalogId, @PathParam("catalogVersion") Float catalogVersion, @QueryParam ("depth") int depth, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "--> CatalogFacadeREST::find(catalogId: {0}, catalogVersion: {1}, depth: {2})", new Object[]{catalogId, catalogVersion, depth});

        Catalog catalog = manager.find(new CatalogId(catalogId, catalogVersion));
        if (catalog == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Set<String> outputFields = FacadeRestUtil.getFieldSet(uriInfo.getQueryParameters());
        if (outputFields.isEmpty() || outputFields.contains(FacadeRestUtil.ALL_FIELDS)) {
            return Response.ok(catalog).build();
        }

        outputFields.add(FacadeRestUtil.ID_FIELD);
        ObjectNode node = FacadeRestUtil.createNodeViewWithFields(catalog, outputFields);
        return Response.ok(node).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(Catalog input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "--> CatalogFacadeREST::create()");

        if (input == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        input.setId(null);
        input.setVersion(Catalog.getDefaultEntityVersion());
        manager.create(input);

        input.setHref(FacadeRestUtil.buildHref(uriInfo, "catalog", input.getId(), input.getVersion()));
        manager.edit(input);

        return Response.ok(input).build();
    }

    @PUT
    @Path("{catalogId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("catalogId") String catalogId, Catalog input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "--> CatalogFacadeREST::update(catalogId: {0})", catalogId);

        if (input == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Catalog catalog = manager.find(catalogId);
        if (catalog == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        input.setId(catalogId);
        manager.edit(input);
        return Response.ok(input).build();
    }

    @PUT
    @Path("{catalogId}:({catalogVersion})")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("catalogId") String catalogId, @PathParam("catalogVersion") Float catalogVersion, Catalog input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "--> CatalogFacadeREST::update(catalogId: {0}, catalogVersion: {1})", new Object[]{catalogId, catalogVersion});

        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @PATCH
    @Path("{catalogId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("catalogId") String catalogId, Catalog input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "--> CatalogFacadeREST::edit(catalogId: {0})", new Object[]{catalogId});

        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @PATCH
    @Path("{catalogId}:({catalogVersion})")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("catalogId") String catalogId, @PathParam("catalogVersion") Float catalogVersion, Catalog input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "--> CatalogFacadeREST::edit(catalogId: {0}, catalogVersion: {1})", new Object[]{catalogId, catalogVersion});

        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @DELETE
    @Path("{catalogId}")
    public Response remove(@PathParam("catalogId") String catalogId) {
        logger.log(Level.FINE, "--> CatalogFacadeREST::remove(catalogId: {0})", new Object[]{catalogId});

        Catalog catalog = manager.find(catalogId);
        if (catalog == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        manager.remove(catalog);
        return Response.ok().build();
    }

    @DELETE
    @Path("{catalogId}:({catalogVersion})")
    public Response remove(@PathParam("catalogId") String catalogId, @PathParam("catalogVersion") Float catalogVersion) {
        logger.log(Level.FINE, "--> CatalogFacadeREST::remove(catalogId: {0}, catalogVersion: {1})", new Object[]{catalogId, catalogVersion});

        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("admin/proto")
    @Produces({MediaType.APPLICATION_JSON})
    public Response proto() {
        logger.log(Level.FINE, "--> CatalogFacadeREST::proto()");

        return Response.ok(Catalog.createProto()).build();
    }

    @GET
    @Path("admin/count")
    @Produces({MediaType.TEXT_PLAIN})
    public String count() {
        logger.log(Level.FINE, "--> CatalogFacadeREST::count()");

        return String.valueOf(manager.count());
    }

    private void fetchChildren(Set<Catalog> catalogs, int depth) {
        for (Catalog catalog : catalogs) {
            catalog.fetchChildren(depth);
        }
    }

    private String buildHref(UriInfo uriInfo, String id, Float version) {
        if (id == null || id.length() <= 0) {
            return null;
        }

        if (version == null) {
            version = 1.0f;
        }

        String path = (uriInfo != null) ? uriInfo.getPath() : null;
        if (path == null) {
            return null;
        }

        return path + "/catalog/" + id + ":(" + version + ")";
    }

}
