package org.tmf.dsmapi.catalog.hub.model.resourceCandidate;

import org.codehaus.jackson.annotate.JsonCreator;

/**
 *
 * @author bahman.barzideh
 */
public enum ResourceCandidateEventType {

    ResourceCandidateCreationNotification    ("ResourceCandidateCreationNotification"),
    ResourceCandidateUpdateNotification      ("ResourceCandidateUpdateNotification"),
    ResourceCandidateDeletionNotification    ("ResourceCandidateDeletionNotification"),
    ResourceCandidateValueChangeNotification ("ResourceCandidateValueChangeNotification");

    private String text;

    ResourceCandidateEventType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @JsonCreator
    public static ResourceCandidateEventType fromString(String text) {
        if (text == null) {
            return null;
        }

        for (ResourceCandidateEventType resourceCandidateEventType : ResourceCandidateEventType.values()) {
            if (text.equalsIgnoreCase(resourceCandidateEventType.text)) {
                return resourceCandidateEventType;
            }
        }

        return null;
    }
}
