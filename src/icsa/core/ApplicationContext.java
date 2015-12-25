/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsa.core;

import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;

/**
 *
 * @author Alexey
 */
public class ApplicationContext {
    public static List<DocumenJsonWrapper> documentsWrappers = new ArrayList();
    public static List<JSONObject> contacts = new ArrayList();
    public static String corvuNGDictionary;
    public static String globalDictionary;
}
