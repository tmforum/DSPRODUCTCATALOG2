package org.tmf.dsmapi.catalog.hub.model.serviceCandidate;

import org.codehaus.jackson.annotate.JsonCreator;

/**
 *
 * @author bahman.barzideh
 */
public enum ServiceCandidateEventType {

    ServiceCandidateCreationNotification    ("ServiceCandidateCreationNotification"),
    ServiceCandidateUpdateNotification      ("ServiceCandidateUpdateNotification"),
    ServiceCandidateDeletionNotification    ("ServiceCandidateDeletionNotification"),
    ServiceCandidateValueChangeNotification ("ServiceCandidateValueChangeNotification");

    private String text;

    ServiceCandidateEventType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @JsonCreator
    public static ServiceCandidateEventType fromString(String text) {
        if (text == null) {
            return null;
        }

        for (ServiceCandidateEventType serviceCandidateEventType : ServiceCandidateEventType.values()) {
            if (text.equalsIgnoreCase(serviceCandidateEventType.text)) {
                return serviceCandidateEventType;
            }
        }

        return null;
    }
}
