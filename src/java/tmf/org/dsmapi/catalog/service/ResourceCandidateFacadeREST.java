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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jackson.node.ObjectNode;
import tmf.org.dsmapi.catalog.ResourceCandidate;
import tmf.org.dsmapi.commons.exceptions.BadUsageException;
import tmf.org.dsmapi.commons.jaxrs.PATCH;

/**
 *
 * @author bahman.barzideh
 *
 */
@Stateless
@Path("resourceCandidate")
public class ResourceCandidateFacadeREST {
    private static final Logger logger = Logger.getLogger(ResourceCandidate.class.getName());
    private static final String RELATIVE_CONTEXT = "resourceCandidate";

    @EJB
    private ResourceCandidateFacade manager;

    /*
     *
     */
    public ResourceCandidateFacadeREST() {
    }

    /*
     *
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(ResourceCandidate input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "ResourceCandidateFacadeREST:create()");

        if (input == null) {
            logger.log(Level.FINE, "input is required");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (input.isValid() == false) {
            logger.log(Level.FINE, "input is not valid");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        input.setCatalogId(ResourceCandidate.getDefaultCatalogId());
        input.setCatalogVersion(ResourceCandidate.getDefaultCatalogVersion());
        manager.create(input);

        input.setHref(FacadeRestUtil.buildHref(uriInfo, RELATIVE_CONTEXT, input.getId(), input.getVersion()));
        manager.edit(input);

        return Response.status(Response.Status.CREATED).entity(input).build();
    }

    /*
     *
     */
    @PUT
    @Path("{entityId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("entityId") String entityId, ResourceCandidate input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "ResourceCandidateFacadeREST:update(entityId: {0})", entityId);

        return update_(entityId, null, input, uriInfo);
    }

    /*
     *
     */
    @PUT
    @Path("{entityId}:({entityVersion})")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("entityId") String entityId, @PathParam("entityVersion") Float entityVersion, ResourceCandidate input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "ResourceCandidateFacadeREST:update(entityId: {0}, entityVersion: {1})", new Object[]{entityId, entityVersion});

        return update_(entityId, entityVersion, input, uriInfo);
    }

    /*
     *
     */
    @PATCH
    @Path("{entityId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("entityId") String entityId, ResourceCandidate input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "ResourceCandidateFacadeREST:edit(entityId: {0})", entityId);

        return edit_(entityId, null, input, uriInfo);
    }

    /*
     *
     */
    @PATCH
    @Path("{entityId}:({entityVersion})")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("entityId") String entityId, @PathParam("entityVersion") Float entityVersion, ResourceCandidate input, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "ResourceCandidateFacadeREST:edit(entityId: {0}, entityVersion: {1})", new Object[]{entityId, entityVersion});

        return edit_(entityId, entityVersion, input, uriInfo);
    }

    /*
     *
     */
    @DELETE
    @Path("{entityId}")
    public Response remove(@PathParam("entityId") String entityId) {
        logger.log(Level.FINE, "ResourceCandidateFacadeREST:remove(entityId: {0})", entityId);

        return remove_(entityId, null);
    }

    /*
     *
     */
    @DELETE
    @Path("{entityId}:({entityVersion})")
    public Response remove(@PathParam("entityId") String entityId, @PathParam("entityVersion") Float entityVersion) {
        logger.log(Level.FINE, "ResourceCandidateFacadeREST:remove(entityId: {0}, entityVersion: {1})", new Object[]{entityId, entityVersion});

        return remove_(entityId, entityVersion);
    }

    /*
     *
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@QueryParam("depth") int depth, @Context UriInfo uriInfo) throws BadUsageException {
        logger.log(Level.FINE, "ResourceCandidateFacadeREST:find(depth: {0})", depth);

        MultivaluedMap<String, String> criteria = uriInfo.getQueryParameters();

        // Remove known parameters before running the query.
        Set<String> outputFields = FacadeRestUtil.getFieldSet(criteria);
        criteria.remove("depth");

        Set<ResourceCandidate> entities = manager.find(criteria, ResourceCandidate.class);
        if (entities == null || entities.size() <= 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        getEnclosedEntities_ (entities, depth);

        if (outputFields.isEmpty() || outputFields.contains(FacadeRestUtil.ALL_FIELDS)) {
            return Response.ok(entities).build();
        }

        outputFields.add(FacadeRestUtil.ID_FIELD);
        List<ObjectNode> nodeList = new ArrayList<ObjectNode>();
        for (ResourceCandidate entity : entities) {
            ObjectNode node = FacadeRestUtil.createNodeViewWithFields(entity, outputFields);
            nodeList.add(node);
        }

        return Response.ok(nodeList).build ();
    }

    /*
     *
     */
    @GET
    @Path("{entityId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findById(@PathParam("entityId") String entityId, @QueryParam("depth") int depth, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "ResourceCandidateFacadeREST:find(entityId: {0}, depth: {1})", new Object[]{entityId, depth});

        return find_(entityId, null, depth, uriInfo);
    }

    /*
     *
     */
    @GET
    @Path("{entityId}:({entityVersion})")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("entityId") String entityId, @PathParam("entityVersion") Float entityVersion, @QueryParam("depth") int depth, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "ResourceCandidateFacadeREST:find(entityId: {0}, entityVersion: {1}, depth: {2})", new Object[]{entityId, entityVersion, depth});

        return find_(entityId, entityVersion, depth, uriInfo);
    }

    /*
     *
     */
    @GET
    @Path("admin/proto")
    @Produces({MediaType.APPLICATION_JSON})
    public Response proto() {
        logger.log(Level.FINE, "ResourceCandidateFacadeREST:proto()");

        return Response.ok(ResourceCandidate.createProto()).build();
    }

    /*
     *
     */
    @GET
    @Path("admin/count")
    @Produces({MediaType.TEXT_PLAIN})
    public Response count() {
        logger.log(Level.FINE, "ResourceCandidateFacadeREST:count()");

        int entityCount = manager.count();
        return Response.ok(String.valueOf(entityCount)).build();
    }

    /*
     *
     */
    private Response update_(String entityId, Float entityVersion, ResourceCandidate input, UriInfo uriInfo) {
        logger.log(Level.FINE, "ResourceCandidateFacadeREST:update_(entityId: {0}, entityVersion: {1})", new Object[]{entityId, entityVersion});

        if (input == null) {
            logger.log(Level.FINE, "input is required.");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (input.isValid() == false) {
            logger.log(Level.FINE, "invalid input.");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<ResourceCandidate> entities = manager.findById(ResourceCandidate.getDefaultCatalogId(), ResourceCandidate.getDefaultCatalogVersion(), entityId, entityVersion);
        ResourceCandidate entity = (entities != null && entities.size() > 0) ? entities.get(0) : null;
        if (entity == null) {
            logger.log(Level.FINE, "requested ResourceCandidate [{0}, {1}] not found", new Object[]{entityId, entityVersion});
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        input.setCatalogId(ResourceCandidate.getDefaultCatalogId());
        input.setCatalogVersion(ResourceCandidate.getDefaultCatalogVersion());

        if (input.keysMatch(entity)) {
            input.setHref(FacadeRestUtil.buildHref(uriInfo, RELATIVE_CONTEXT, input.getId(), input.getVersion()));
            manager.edit(input);
            return Response.status(Response.Status.CREATED).entity(input).build();
        }

        manager.remove(entity);
        manager.create(input);

        input.setHref(FacadeRestUtil.buildHref(uriInfo, RELATIVE_CONTEXT, input.getId(), input.getVersion()));
        manager.edit(input);

        return Response.status(Response.Status.CREATED).entity(input).build();
    }

    /*
     *
     */
    private Response edit_(String entityId, Float entityVersion, ResourceCandidate input, UriInfo uriInfo) {
        logger.log(Level.FINE, "ResourceCandidateFacadeREST:edit_(entityId: {0}, entityVersion: {1})", new Object[]{entityId, entityVersion});

        if (input == null) {
            logger.log(Level.FINE, "input is required");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<ResourceCandidate> entities = manager.findById(ResourceCandidate.getDefaultCatalogId(), ResourceCandidate.getDefaultCatalogVersion(), entityId, entityVersion);
        ResourceCandidate entity = (entities != null && entities.size() > 0) ? entities.get(0) : null;
        if (entity == null) {
            logger.log(Level.FINE, "requested ResourceCandidate [{0}, {1}] not found", new Object[]{entityId, entityVersion});
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        input.edit(entity);
        input.setCatalogId(entity.getCatalogId());
        input.setCatalogVersion(entity.getCatalogVersion());
        input.setId(entity.getId());

        if(entity.isValid() == false) {
            logger.log(Level.FINE, "patched ResourceCandidate would be invalid");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (input.getVersion() == null) {
            input.setVersion(entity.getVersion());
            manager.edit(input);
            return Response.status(Response.Status.CREATED).entity(input).build();
        }

        if (input.getVersion() <= entity.getVersion()) {
            logger.log(Level.FINE, "specified version ({0}) must be higher than entity version ({1})", new Object[]{input.getVersion(), entity.getVersion()});
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        manager.remove(entity);

        input.setHref(FacadeRestUtil.buildHref(uriInfo, RELATIVE_CONTEXT, input.getId(), input.getVersion()));
        manager.create(input);

        return Response.status(Response.Status.CREATED).entity(input).build();
    }

    /*
     *
     */
    private Response remove_(String entityId, Float entityVersion) {
        logger.log(Level.FINE, "ResourceCandidateFacadeREST:remove_(entityId: {0}, entityVersion: {1})", new Object[]{entityId, entityVersion});

        List<ResourceCandidate> entities = manager.findById(ResourceCandidate.getDefaultCatalogId(), ResourceCandidate.getDefaultCatalogVersion(), entityId, entityVersion);
        if (entities == null || entities.size() <= 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        manager.remove(entities.get(0));
        return Response.ok().build();
    }

    /*
     *
     */
    private Response find_(String entityId, Float entityVersion, int depth, UriInfo uriInfo) {
        logger.log(Level.FINE, "ResourceCandidateFacadeREST:find_(entityId: {0}, entityVersion: {1}, depth: {2})", new Object[]{entityId, entityVersion, depth});

        List<ResourceCandidate> entities = manager.findById(ResourceCandidate.getDefaultCatalogId(), ResourceCandidate.getDefaultCatalogVersion(), entityId, entityVersion);
        if (entities == null || entities.size() <= 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        ResourceCandidate entity = entities.get(0);
        entity.getEnclosedEntities(depth);

        Set<String> outputFields = FacadeRestUtil.getFieldSet(uriInfo.getQueryParameters());
        if (outputFields.isEmpty() || outputFields.contains(FacadeRestUtil.ALL_FIELDS)) {
            return Response.ok(entity).build();
        }

        outputFields.add(FacadeRestUtil.ID_FIELD);
        ObjectNode node = FacadeRestUtil.createNodeViewWithFields(entity, outputFields);
        return Response.ok(node).build();
    }

    /*
     *
     */
    private void getEnclosedEntities_ (Set<ResourceCandidate> entities, int depth) {
        for (ResourceCandidate entity : entities) {
            entity.getEnclosedEntities(depth);
        }
    }

}