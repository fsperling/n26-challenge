package de.n26.challenge.rest;


import de.n26.challenge.model.Transaction;
import de.n26.challenge.service.StatisticsManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author felix
 */

@Path("transactions")
public class TransactionResource { 
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(Transaction tr) {
        boolean validTransaction = StatisticsManager.getInstance().add(tr);
        if (validTransaction) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).build();
        }       
    }
    
}

