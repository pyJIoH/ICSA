/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsa.api;

import icsa.core.ApplicationContext;
import icsa.core.DocumenJsonWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Алексей
 */

@Path("/docs2/{keywords}")
public class Documents2 {
    String url = "https://www.rocketrack.com/rest/api/latest/search?maxResults=12&field=summary,product,description&jql=%s";
    String authHedaer = "null";
    
    @GET
    @Produces({"application/xml", "application/json"})
    public String getJiraTickets(@PathParam("keywords") String keywords){
        String result = "";
        HttpsURLConnection con = null;
        try {
            String[] params = keywords.split("\\,");
            StringBuilder b = new StringBuilder();
            b.append("issuetype = Case ");
            b.append("AND status in ( Resolved, Closed) ");            
            b.append("AND reporter in (external_user) ");
            b.append("AND description ~ ").append(params[0].trim());
            
            for (int i = 1; i < params.length; i++) {
                b.append(" or ");
                b.append("description ~ ");
                b.append(params[i].trim());
            }
            URL myurl = new URL(String.format(url, URLEncoder.encode(b.toString(),"UTF-8")));
            con = (HttpsURLConnection)myurl.openConnection();
            String encoded = new String(authHedaer);
            con.setRequestProperty("Authorization", "Basic "+encoded);			

            InputStream ins = con.getInputStream();
            InputStreamReader isr = new InputStreamReader(ins);
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                result += inputLine;
            }
            in.close();		

        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
        finally{
            if (con != null){
                con.disconnect();
            }
        }
        JSONArray jsonResult = new JSONArray();
        if (result != null && !result.trim().isEmpty()){
            JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( result );
            JSONArray docsArray = jsonObject.getJSONArray("issues");
            for(int i = 0; i < docsArray.size(); i++){
                JSONObject doc = (JSONObject) docsArray.get(i);
                JSONObject newDoc = new JSONObject();
                newDoc.element("description", ((JSONObject)doc.get("fields")).get("description"));
                newDoc.element("title", ((JSONObject)doc.get("fields")).get("summary"));
                newDoc.element("type", "Customer_Case");
                newDoc.element("solution", "Solved");
                newDoc.element("resolution", "Resolved");
                newDoc.element("keywords", "");
                
                
                jsonResult.add(newDoc);
            }
        }

        return jsonResult.toString();
    }
    
    public static void main(String[] args){
        System.out.println(new Documents2().getJiraTickets("driver,mobile"));
    }
}
