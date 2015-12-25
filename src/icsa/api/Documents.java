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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

@Path("/docs/{keywords}")
public class Documents {

    static class SortDocWrapper{
        Double score;
        DocumenJsonWrapper wrapper;
    }
    
    @GET
    @Produces({"application/xml", "application/json"})
    public String getDocuments(@PathParam("keywords") String keywords){
        if (keywords == null || keywords.trim().length() == 0){
            return "";
        }
        List<SortDocWrapper> forSorting = new ArrayList<>();
        List<DocumenJsonWrapper> docs = ApplicationContext.documentsWrappers;
        for (Iterator<DocumenJsonWrapper> iterator = docs.iterator(); iterator.hasNext();) {
            DocumenJsonWrapper next = iterator.next();
            SortDocWrapper sw = new SortDocWrapper();
            sw.score = next.getScore(keywords);
            sw.wrapper = next;
            forSorting.add(sw);
        }
        
        Collections.sort(forSorting, new Comparator<SortDocWrapper>(){
            @Override
            public int compare(SortDocWrapper o1, SortDocWrapper o2) {
                return o2.score.compareTo(o1.score);
            }        
        });
        
        JSONArray jsonArray = new JSONArray();
        for (SortDocWrapper doc : forSorting) {
            if (doc.score >= 6){
                jsonArray.add(doc.wrapper.json);
            }
        }
   
        return jsonArray.toString();

    }
	
}
