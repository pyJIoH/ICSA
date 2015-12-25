package icsa.core;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
@ApplicationPath("/api")
@WebListener
public class App extends Application implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        loadDocuments(sce);
        loadProductDictionary(sce);
        loadGlobalDictionary(sce);    
        loadContacts(sce);        
    }
    
    private void loadProductDictionary(ServletContextEvent sce){
        File f = new File(sce.getServletContext().getRealPath("resources/product_dictionary.txt"));
        if (f.exists()){
            try {
                ApplicationContext.corvuNGDictionary = new String(Files.readAllBytes(f.toPath()));
            }catch(Exception ignore){
                
            }
        }
    }
    
    private void loadDocuments(ServletContextEvent sce){
        if (ApplicationContext.documentsWrappers == null){
            ApplicationContext.documentsWrappers = new ArrayList<>();
        }else{
            ApplicationContext.documentsWrappers.clear();
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        File f = new File(sce.getServletContext().getRealPath("resources/cases.json"));
        if (f.exists()){
            
            try {
                String jsonString = new String(Files.readAllBytes(f.toPath()));
                JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( jsonString );
                JSONArray docsArray = jsonObject.getJSONArray("docs");
                for(int i = 0; i < docsArray.size(); i++){
                    JSONObject doc = (JSONObject) docsArray.get(i);
                    ApplicationContext.documentsWrappers.add(new DocumenJsonWrapper(doc));
                }
            } catch (Exception ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void loadGlobalDictionary(ServletContextEvent sce) {
        File f = new File(sce.getServletContext().getRealPath("resources/global_dictionary.txt"));
        if (f.exists()){
            try {
                ApplicationContext.globalDictionary = new String(Files.readAllBytes(f.toPath()));
            }catch(Exception ignore){
                
            }
        }
    }

    private void loadContacts(ServletContextEvent sce) {
        if (ApplicationContext.contacts == null){
            ApplicationContext.contacts = new ArrayList<>();
        }else{
            ApplicationContext.contacts.clear();
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        File f = new File(sce.getServletContext().getRealPath("resources/contacts.json"));
        if (f.exists()){
            
            try {
                String jsonString = new String(Files.readAllBytes(f.toPath()));
                JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( jsonString );
                JSONArray docsArray = jsonObject.getJSONArray("contacts");
                for(int i = 0; i < docsArray.size(); i++){
                    JSONObject doc = (JSONObject) docsArray.get(i);
                    ApplicationContext.contacts.add(doc);
                }
            } catch (Exception ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }

    }

}
