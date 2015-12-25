/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsa.core;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Алексей
 */
public class DocumenJsonWrapper {
    String keywords;
    public JSONObject json;
    public DocumenJsonWrapper(JSONObject obj){
        json = obj;
        if (json != null){
            keywords = json.getString("keywords");
        }
    }
    
    public double getScore(String tags){
        String[] arrTags = tags.split("\\,");
        for (int i = 0; i < arrTags.length; i++) {
            arrTags[i] = arrTags[i].trim().toLowerCase();
        }
        List listTags = Arrays.asList(arrTags);
        
        double score = 0;
        if (keywords != null){
            String[] keywordsRecords = keywords.toLowerCase().split("\\,");
                //use distance algorithm to check similarity of strings
            for (Iterator iterator = listTags.iterator(); iterator.hasNext();) {
                String tag = (String) iterator.next();
                
                if (tag == null || tag.trim().length() == 0){
                    continue;
                }

                int tempScore = 0;
                for (int i = 0; i < keywordsRecords.length; i++){
                    String[] pair = keywordsRecords[i].trim().split("\\:");
                    int localScore = Integer.parseInt(pair[1]);
                    int distance = StringUtils.getLevenshteinDistance(tag, pair[0]);

                    if (distance <= 1){
                        tempScore += (double)localScore * (distance == 0 ? 1 : 1/(double)distance/(double)distance);
                    }
                }


                if (tempScore == 0){
                    score = 0;
                    break;
                }else{
                    score += tempScore;
                }
                //if distance big then score grows slowly
                //score += (double)localScore * (distance == 0 ? 1 : 1/(double)distance/(double)distance);
            }
                
                
                /* simple algorithm
                if (listTags.contains(pair[0])){
                    score += localScore;
                }
                */
        }
        return score;
    }
    
    public static void main(String[] params){
        DocumenJsonWrapper wrapper = new DocumenJsonWrapper(null);
        wrapper.keywords = "Single Sign On:10,CorVu NG:9,CorVu NG web:9,username:3,password:3";
        System.err.print(wrapper.getScore("hello, world, username"));
    }
}
