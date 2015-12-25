/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsa.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Алексей
 */
@Path("/docs")
public class FakeDocuments {
    
    @GET
    @Produces({"application/xml", "application/json"})
    public String getEmptyDocs(){
        return "[]";
    }
}
