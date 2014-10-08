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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.tmf.dsmapi.catalog.LifecycleStatus;
import org.tmf.dsmapi.catalog.ServiceCandidate;
import org.tmf.dsmapi.commons.ParsedVersion;
import org.tmf.dsmapi.commons.QueryParameterParser;
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.commons.exceptions.IllegalLifecycleStatusException;
import org.tmf.dsmapi.commons.jaxrs.PATCH;

/**
 *
 * @author bahman.barzideh
 *
 */
@Stateless
@Path("serviceCandidate")
public class ServiceCandidateFacadeREST extends AbstractFacadeREST<ServiceCandidate> {
    private static final Logger logger = Logger.getLogger(ServiceCandidate.class.getName());

    @EJB
    private ServiceCandidateFacade manager;

    /*
     *
     */
    public ServiceCandidateFacadeREST() {
        super(ServiceCandidate.class);
    }

    /*
     *
     */
    @Override
    public Logger getLogger() {
        return logger;
    }

    /*
     *
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(ServiceCandidate input, @Context UriInfo uriInfo) throws IllegalLifecycleStatusException {
        logger.log(Level.FINE, "ServiceCandidateFacadeREST:create()");

        if (input == null) {
            logger.log(Level.FINE, "input is required");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        input.setCreateDefaults();

        if (input.isValid() == false) {
            logger.log(Level.FINE, "input is not valid");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (input.canLifecycleTransitionFrom (null) == false) {
            logger.log(Level.FINE, "invalid lifecycleStatus: {0}", input.getLifecycleStatus());
            throw new IllegalLifecycleStatusException(LifecycleStatus.transitionableStatues(null));
        }

        input.configureCatalogIdentifier();
        manager.create(input);

        input.setHref(buildHref(uriInfo, input.getId(), input.getParsedVersion()));
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
    public Response update(@PathParam("entityId") String entityId, ServiceCandidate input, @Context UriInfo uriInfo) throws IllegalLifecycleStatusException {
        logger.log(Level.FINE, "ServiceCandidateFacadeREST:update(entityId: {0})", entityId);

        return update_(entityId, null, input, uriInfo);
    }

    /*
     *
     */
    @PUT
    @Path("{entityId}:({entityVersion})")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("entityId") String entityId, @PathParam("entityVersion") ParsedVersion entityVersion, ServiceCandidate input, @Context UriInfo uriInfo) throws IllegalLifecycleStatusException {
        logger.log(Level.FINE, "ServiceCandidateFacadeREST:update(entityId: {0}, entityVersion: {1})", new Object[]{entityId, entityVersion});

        return update_(entityId, entityVersion, input, uriInfo);
    }

    /*
     *
     */
    @PATCH
    @Path("{entityId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("entityId") String entityId, ServiceCandidate input, @Context UriInfo uriInfo) throws IllegalLifecycleStatusException {
        logger.log(Level.FINE, "ServiceCandidateFacadeREST:edit(entityId: {0})", entityId);

        return edit_(entityId, null, input, uriInfo);
    }

    /*
     *
     */
    @PATCH
    @Path("{entityId}:({entityVersion})")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("entityId") String entityId, @PathParam("entityVersion") ParsedVersion entityVersion, ServiceCandidate input, @Context UriInfo uriInfo) throws IllegalLifecycleStatusException {
        logger.log(Level.FINE, "ServiceCandidateFacadeREST:edit(entityId: {0}, entityVersion: {1})", new Object[]{entityId, entityVersion});

        return edit_(entityId, entityVersion, input, uriInfo);
    }

    /*
     *
     */
    @DELETE
    @Path("{entityId}")
    public Response remove(@PathParam("entityId") String entityId) {
        logger.log(Level.FINE, "ServiceCandidateFacadeREST:remove(entityId: {0})", entityId);

        return remove_(entityId, null);
    }

    /*
     *
     */
    @DELETE
    @Path("{entityId}:({entityVersion})")
    public Response remove(@PathParam("entityId") String entityId, @PathParam("entityVersion") ParsedVersion entityVersion) {
        logger.log(Level.FINE, "ServiceCandidateFacadeREST:remove(entityId: {0}, entityVersion: {1})", new Object[]{entityId, entityVersion});

        return remove_(entityId, entityVersion);
    }

    /*
     *
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@QueryParam("depth") int depth, @Context UriInfo uriInfo) throws BadUsageException {
        logger.log(Level.FINE, "ServiceCandidateFacadeREST:find(depth: {0})", depth);

        QueryParameterParser queryParameterParser = new QueryParameterParser(uriInfo.getRequestUri().getQuery());

        // Remove known parameters before running the query.
        Set<String> outputFields = getFieldSet(queryParameterParser);
        queryParameterParser.removeTagWithValues("depth");

        Set<ServiceCandidate> entities = manager.find(queryParameterParser.getTagsWithValue());
        if (entities == null || entities.size() <= 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        getEnclosedEntities_ (entities, depth);

        if (outputFields.isEmpty() || outputFields.contains(ServiceConstants.ALL_FIELDS)) {
            return Response.ok(entities).build();
        }

       ArrayList<Object> outputEntities = selectFields(entities, outputFields);
       return Response.ok(outputEntities).build();
    }

    /*
     *
     */
    @GET
    @Path("{entityId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findById(@PathParam("entityId") String entityId, @QueryParam("depth") int depth, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "ServiceCandidateFacadeREST:find(entityId: {0}, depth: {1})", new Object[]{entityId, depth});

        return find_(entityId, null, depth, uriInfo);
    }

    /*
     *
     */
    @GET
    @Path("{entityId}:({entityVersion})")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("entityId") String entityId, @PathParam("entityVersion") ParsedVersion entityVersion, @QueryParam("depth") int depth, @Context UriInfo uriInfo) {
        logger.log(Level.FINE, "ServiceCandidateFacadeREST:find(entityId: {0}, entityVersion: {1}, depth: {2})", new Object[]{entityId, entityVersion, depth});

        return find_(entityId, entityVersion, depth, uriInfo);
    }

    /*
     *
     */
    @GET
    @Path("admin/proto")
    @Produces({MediaType.APPLICATION_JSON})
    public Response proto() {
        logger.log(Level.FINE, "ServiceCandidateFacadeREST:proto()");

        return Response.ok(ServiceCandidate.createProto()).build();
    }

    /*
     *
     */
    @GET
    @Path("admin/count")
    @Produces({MediaType.TEXT_PLAIN})
    public Response count() {
        logger.log(Level.FINE, "ServiceCandidateFacadeREST:count()");

        int entityCount = manager.count();
        return Response.ok(String.valueOf(entityCount)).build();
    }

    /*
     *
     */
    private Response update_(String entityId, ParsedVersion entityVersion, ServiceCandidate input, UriInfo uriInfo) throws IllegalLifecycleStatusException {
        logger.log(Level.FINE, "ServiceCandidateFacadeREST:update_(entityId: {0}, entityVersion: {1})", new Object[]{entityId, entityVersion});

        if (input == null) {
            logger.log(Level.FINE, "input is required.");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (input.isValid() == false) {
            logger.log(Level.FINE, "invalid input.");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<ServiceCandidate> entities = manager.findById(ServiceCandidate.ROOT_CATALOG_ID, ParsedVersion.ROOT_CATALOG_VERSION, entityId, entityVersion);
        ServiceCandidate entity = (entities != null && entities.size() > 0) ? entities.get(0) : null;
        if (entity == null) {
            logger.log(Level.FINE, "requested ServiceCandidate [{0}, {1}] not found", new Object[]{entityId, entityVersion});
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        validateLifecycleStatus(input, entity);

        input.configureCatalogIdentifier();

        if (input.keysMatch(entity)) {
            input.setHref(buildHref(uriInfo, input.getId(), input.getParsedVersion()));
            manager.edit(input);
            return Response.status(Response.Status.CREATED).entity(entity).build();
        }

        if (input.hasHigherVersionThan(entity) == false) {
            logger.log(Level.FINE, "specified version ({0}) must be higher than entity version ({1})", new Object[]{input.getVersion(), entity.getVersion()});
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        manager.remove(entity);
        manager.create(input);

        input.setHref(buildHref(uriInfo, input.getId(), input.getParsedVersion()));
        manager.edit(input);

        return Response.status(Response.Status.CREATED).entity(input).build();
    }

    /*
     *
     */
    private Response edit_(String entityId, ParsedVersion entityVersion, ServiceCandidate input, UriInfo uriInfo) throws IllegalLifecycleStatusException {
        logger.log(Level.FINE, "ServiceCandidateFacadeREST:edit_(entityId: {0}, entityVersion: {1})", new Object[]{entityId, entityVersion});

        if (input == null) {
            logger.log(Level.FINE, "input is required");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<ServiceCandidate> entities = manager.findById(ServiceCandidate.ROOT_CATALOG_ID, ParsedVersion.ROOT_CATALOG_VERSION, entityId, entityVersion);
        ServiceCandidate entity = (entities != null && entities.size() > 0) ? entities.get(0) : null;
        if (entity == null) {
            logger.log(Level.FINE, "requested ServiceCandidate [{0}, {1}] not found", new Object[]{entityId, entityVersion});
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        input.edit(entity);
        input.setCatalogId(entity.getCatalogId());
        input.setCatalogVersion(entity.getCatalogVersion());
        input.setId(entity.getId());

        if(input.isValid() == false) {
            logger.log(Level.FINE, "patched ServiceCandidate would be invalid");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        validateLifecycleStatus(input, entity);

        if (input.getVersion() == null) {
            input.setVersion(entity.getVersion());
            manager.edit(input);
            return Response.status(Response.Status.CREATED).entity(entity).build();
        }

        if (input.hasHigherVersionThan(entity) == false) {
            logger.log(Level.FINE, "specified version ({0}) must be higher than entity version ({1})", new Object[]{input.getVersion(), entity.getVersion()});
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        manager.remove(entity);

        input.setHref(buildHref(uriInfo, input.getId(), input.getParsedVersion()));
        manager.create(input);

        return Response.status(Response.Status.CREATED).entity(input).build();
    }

    /*
     *
     */
    private Response remove_(String entityId, ParsedVersion entityVersion) {
        logger.log(Level.FINE, "ServiceCandidateFacadeREST:remove_(entityId: {0}, entityVersion: {1})", new Object[]{entityId, entityVersion});

        List<ServiceCandidate> entities = manager.findById(ServiceCandidate.ROOT_CATALOG_ID, ParsedVersion.ROOT_CATALOG_VERSION, entityId, entityVersion);
        if (entities == null || entities.size() <= 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        manager.remove(entities.get(0));
        return Response.ok().build();
    }

    /*
     *
     */
    private Response find_(String entityId, ParsedVersion entityVersion, int depth, UriInfo uriInfo) {
        logger.log(Level.FINE, "ServiceCandidateFacadeREST:find_(entityId: {0}, entityVersion: {1}, depth: {2})", new Object[]{entityId, entityVersion, depth});

        List<ServiceCandidate> entities = manager.findById(ServiceCandidate.ROOT_CATALOG_ID, ParsedVersion.ROOT_CATALOG_VERSION, entityId, entityVersion);
        if (entities == null || entities.size() <= 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        ServiceCandidate entity = entities.get(0);
        entity.getEnclosedEntities(depth);

        QueryParameterParser queryParameterParser = new QueryParameterParser(uriInfo.getRequestUri().getQuery());
        Set<String> outputFields = getFieldSet(queryParameterParser);

        if (outputFields.isEmpty() || outputFields.contains(ServiceConstants.ALL_FIELDS)) {
            return Response.ok(entity).build();
        }

        Object outputEntity = selectFields(entity, outputFields);
        return Response.ok(outputEntity).build();
    }

    /*
     *
     */
    private void getEnclosedEntities_ (Set<ServiceCandidate> entities, int depth) {
        for (ServiceCandidate entity : entities) {
            entity.getEnclosedEntities(depth);
        }
    }

}
