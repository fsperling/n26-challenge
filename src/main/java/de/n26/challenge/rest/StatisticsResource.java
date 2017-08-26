package de.n26.challenge.rest;

import de.n26.challenge.model.Statistics;
import de.n26.challenge.service.StatisticsManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author felix
 */

@Path("statistics")
public class StatisticsResource {
    
    @GET
    public Response get() {
        Statistics stats = StatisticsManager.getInstance().getTransactionStats();
        return Response.ok(stats, MediaType.APPLICATION_JSON).build();
    }
    
}

