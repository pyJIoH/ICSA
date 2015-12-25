/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsa.api;

import icsa.core.ApplicationContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author Алексей
 */
@Path("/dictionary")
public class Dictionary {
    @GET
    public String getProductDictionary(){
        return ApplicationContext.globalDictionary;
    }
    
}
