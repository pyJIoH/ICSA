/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsa.api;

import icsa.core.ApplicationContext;
import icsa.core.DocumenJsonWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Алексей
 */
@Path("/contacts")
public class Contacts {
    @GET
    @Produces({"application/xml", "application/json"})    
    public String getContacts(){
        JSONArray jsonArray = new JSONArray();
        for (JSONObject doc : ApplicationContext.contacts) {
            jsonArray.add(doc);
        }
        return jsonArray.toString();
        
    }
}
