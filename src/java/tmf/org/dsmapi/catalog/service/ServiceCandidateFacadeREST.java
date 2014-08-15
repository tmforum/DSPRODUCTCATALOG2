package tmf.org.dsmapi.catalog.service;

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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jackson.node.ObjectNode;
import tmf.org.dsmapi.catalog.ServiceCandidate;
import tmf.org.dsmapi.commons.exceptions.BadUsageException;

/**
 *
 * @author bahman.barzideh
 *
 */
@Stateless
@Path("tmf.org.dsmapi.catalog.serviceCandidate")
public class ServiceCandidateFacadeREST {

    @EJB
    ServiceCandidateFacade manager;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@Context UriInfo uriInfo) throws BadUsageException {
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("{serviceCandidateId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("serviceCandidateId") String serviceCandidateId, @Context UriInfo uriInfo) {
        ServiceCandidate serviceCandidate = manager.find(serviceCandidateId);
        if (serviceCandidate == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        serviceCandidate.fetchChildren(0);

        Set<String> outputFields = FacadeRestUtil.getFieldSet(uriInfo.getQueryParameters());
        if (outputFields.isEmpty() || outputFields.contains(FacadeRestUtil.ALL_FIELDS)) {
            return Response.ok(serviceCandidate).build();
        }

        outputFields.add(FacadeRestUtil.ID_FIELD);
        ObjectNode node = FacadeRestUtil.createNodeViewWithFields(serviceCandidate, outputFields);
        return Response.ok(node).build();
    }

    @GET
    @Path("catalog/{catalogId}/serviceCandidate/{serviceCandidateId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find (@PathParam("catalogId") String catalogId, @PathParam("serviceCandidateId") String serviceCandidateId, @Context UriInfo uriInfo) {
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("proto")
    @Produces({MediaType.APPLICATION_JSON})
    public Response proto() {
        return Response.ok(ServiceCandidate.createProto()).build();
    }

    @GET
    @Path("count")
    @Produces({MediaType.TEXT_PLAIN})
    public String count() {
        return String.valueOf(manager.count());
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(ServiceCandidate input) {
        if (input == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        input.setId(null);
        manager.create(input);

        return Response.ok(input).build();
    }

    @PUT
    @Path("{serviceCandidateId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("serviceCandidateId") String serviceCandidateId, ServiceCandidate input) {
        if (input == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        ServiceCandidate serviceCandidate = manager.find(serviceCandidateId);
        if (serviceCandidate == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        serviceCandidate.setId(serviceCandidateId);
        manager.edit(input);
        return Response.ok(input).build();
    }

    @DELETE
    @Path("{serviceCandidateId}")
    public Response remove(@PathParam("serviceCandidateId") String serviceCandidateId) {
        ServiceCandidate serviceCandidate = manager.find(serviceCandidateId);
        if (serviceCandidate == null) {
            return Response.status(Response.Status.NOT_FOUND).build ();
        }

        manager.remove(serviceCandidate);
        return Response.ok().build();
    }

}
