package Project_3.json_object;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Hello world!
 *
 */
public class App 
{
	Logger logger = Logger.getLogger(App.class.getName());
	public JSONObject parseurl(URL url) {
		logger.info("This function has a parameter of url and is used to fetch a json");
		String inline = "";
		App a1=new App();
		JSONObject json=null;
		try
		{
			//Parse URL into HttpURLConnection in order to open the connection in order to get the JSON data
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			//Set the request to GET or POST as per the requirements
			conn.setRequestMethod("GET");
			//Use the connect method to create the connection bridge
			conn.connect();
			//Get the response status of the Rest API
			int responsecode = conn.getResponseCode();
			
			//Iterating condition to if response code is not 200 then throw a runtime exception
			//else continue the actual process of getting the JSON data
				//Scanner functionality will read the JSON data from the stream
				Scanner sc = new Scanner(url.openStream());
				while(sc.hasNext())
				{
					inline+=sc.nextLine();
				}
				sc.close();
			JSONParser parser = new JSONParser();
			json = (JSONObject) parser.parse(inline);
			conn.disconnect();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return json;
	}
	public long pageid(JSONObject json) {
		logger.info("This function has a parameter of json object and is used to fetch pageid");
		long page_id=0;
		if(json==null) {
			throw new NullPointerException("no json object found");
		}
		else {
			JSONObjectIterator ji=new JSONObjectIterator();
			ji.handleJSONObject(json);
			if(ji.keyValuePairs.get("pageid")==null) {
				throw new NullPointerException("Key not found");
			}
			page_id=(long) ji.keyValuePairs.get("pageid");
		}
		return page_id;
	}
	public List<String> links(JSONObject json)  {
		logger.info("This function has a parameter of json object and is used to fetch the see all links");
		if(json==null) {
			throw new NullPointerException("json object not found");
		}
		List<String> links=new ArrayList<String>();
//			JSONObject j2=(JSONObject) json.get("query");
//			JSONObject j3=(JSONObject) j2.get("pages");
//			JSONObject j4=(JSONObject) j3.get("1808130");
//			List a=(List) j4.get("revisions");
//			JSONObject j5=(JSONObject) a.get(0);
			JSONObjectIterator ji=new JSONObjectIterator();
			ji.handleJSONObject(json);
			
			String j6=(String) ji.keyValuePairs.get("*");
			String[] a1=j6.split("\n");
			int i=0;
			for(i=0;i<a1.length;i++) {
				if("==See also==".equals(a1[i])) {
					int j=1;
					while(!("".equals(a1[i+j]))) {
						links.add(a1[i+j]);
						j++;
					}
					break;
				}
			}
			if(links.size()==0) {
				throw new NullPointerException("links not found");
			}
			else {
			for(i=0;i<links.size();i++) {
				links.set(i,links.get(i).substring(3,links.get(i).length()-2));
				links.set(i,links.get(i).replace(' ','_'));
				links.set(i,"https://en.wikipedia.org/wiki/"+links.get(i));
			}
			}
			 try {
			      FileWriter myWriter = new FileWriter("filename.txt");
			      for(i=0;i<links.size();i++) {
			    	  myWriter.write(links.get(i)+"\n");
			      }
			     
			      myWriter.close();
			      System.out.println("Successfully wrote to the file.");
			    } catch (IOException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			    }
		return links;
		
	}
}
class JSONObjectIterator {

    public Map<String, Object> keyValuePairs;

    public JSONObjectIterator() {
        keyValuePairs = new HashMap<>();
    }

    public void handleValue(String key, Object value) {
         if (value instanceof JSONObject) {
            handleJSONObject((JSONObject) value);
        }
        keyValuePairs.put(key, value);
    }

    public void handleJSONObject(JSONObject jsonObject) {
        Set jsonObjectIterator = jsonObject.keySet();
        Iterator<String> joi=jsonObjectIterator.iterator();
        joi.forEachRemaining(key -> {
            Object value = jsonObject.get(key);
            handleValue(key, value);
        });
    }
}
