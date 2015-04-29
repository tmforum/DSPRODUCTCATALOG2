package org.netbeans.rest.application.config;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author pierregauthier
 *
 */
@ApplicationPath("api/catalogManagement/v2")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        packages ("org.codehaus.jackson.jaxrs");

        register(org.tmf.dsmapi.jaxrs.resource.hub.CatalogHubResource.class);
                
        register(org.tmf.dsmapi.catalog.service.catalog.CatalogFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.category.CategoryFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.category.CategoryInCatalogIdFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.category.CategoryInCatalogIdVersionFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.productOffering.ProductOfferingFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.productOffering.ProductOfferingInCatalogIdFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.productOffering.ProductOfferingInCatalogIdVersionFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.productSpecification.ProductSpecificationFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.productSpecification.ProductSpecificationInCatalogIdFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.productSpecification.ProductSpecificationInCatalogIdVersionFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.resourceCandidate.ResourceCandidateFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.resourceCandidate.ResourceCandidateInCatalogIdFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.resourceCandidate.ResourceCandidateInCatalogIdVersionFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.resourceSpecification.ResourceSpecificationFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.resourceSpecification.ResourceSpecificationInCatalogIdFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.resourceSpecification.ResourceSpecificationInCatalogIdVersionFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.serviceCandidate.ServiceCandidateFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.serviceCandidate.ServiceCandidateInCatalogIdFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.serviceCandidate.ServiceCandidateInCatalogIdVersionFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.serviceSpecification.ServiceSpecificationFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.serviceSpecification.ServiceSpecificationInCatalogIdFacadeREST.class);
        register(org.tmf.dsmapi.catalog.service.serviceSpecification.ServiceSpecificationInCatalogIdVersionFacadeREST.class);
        register(org.tmf.dsmapi.commons.jaxrs.provider.BadUsageExceptionMapper.class);
        register(org.tmf.dsmapi.commons.jaxrs.provider.JacksonConfigurator.class);
        register(org.tmf.dsmapi.commons.jaxrs.provider.JsonMappingExceptionMapper.class);
        register(org.tmf.dsmapi.commons.jaxrs.provider.UnknowResourceExceptionMapper.class);
    }

}
