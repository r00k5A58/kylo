package com.thinkbiganalytics.nifi.core.api.precondition;

import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.controller.ControllerService;

/**
 * @author Sean Felten
 */
@Tags({"thinkbig", "feed", "precondition", "event", "trigger"})
@CapabilityDescription("")
public interface FeedPreconditionEventService extends ControllerService, PreconditionEventConsumer {

}
