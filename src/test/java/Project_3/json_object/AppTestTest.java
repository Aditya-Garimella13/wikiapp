package Project_3.json_object;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AppTestTest {

	@Test
	public void json_is_present() throws IOException, ParseException
    {
    	App a1=new App();
    	String initialString = "{\"pageid\":01,\"*\":\"==See also==\\n*[[Lua (programming language)]]\\n*[[Squirrel (programming language)]]\\n\\n=\"}";
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        URL url=Mockito.mock(URL.class);
        HttpURLConnection conn=Mockito.mock(HttpURLConnection.class);
        Mockito.when(conn.getResponseCode()).thenReturn(200);
        Mockito.when(url.openConnection()).thenReturn(conn);
        Mockito.when(url.openStream()).thenReturn(targetStream);
        JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(initialString);
		assertEquals(json,a1.parseurl(url) );

    }
	@Test
    public void page_id_working() throws IOException, ParseException {
    	App a1=new App();
    	String initialString = "{\"pageid\":01,\"*\":\"==See also==\\n*[[Lua (programming language)]]\\n*[[Squirrel (programming language)]]\\n\\n=\"}";
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        URL url=Mockito.mock(URL.class);
        HttpURLConnection conn=Mockito.mock(HttpURLConnection.class);
        Mockito.when(conn.getResponseCode()).thenReturn(200);
        Mockito.when(url.openConnection()).thenReturn(conn);
        Mockito.when(url.openStream()).thenReturn(targetStream);
        JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(initialString);
		assertEquals(01,a1.pageid(json) );
//		List<String> a=new ArrayList<String>();
//		a.add("https://en.wikipedia.org/wiki/Lua_(programming_language)");
//		a.add("https://en.wikipedia.org/wiki/Squirrel_(programming_language)");
    }
	@Test
    public void page_id_notfound() throws IOException, ParseException {
    	App a1=new App();
    	String initialString = "{\"pageid\":null,\"*\":\"==See also==\\n*[[Lua (programming language)]]\\n*[[Squirrel (programming language)]]\\n\\n=\"}";
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        URL url=Mockito.mock(URL.class);
        HttpURLConnection conn=Mockito.mock(HttpURLConnection.class);
        Mockito.when(conn.getResponseCode()).thenReturn(200);
        Mockito.when(url.openConnection()).thenReturn(conn);
        Mockito.when(url.openStream()).thenReturn(targetStream);
        JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(initialString);
		NullPointerException ex=assertThrows(NullPointerException.class,()->a1.pageid(json));
		assertEquals("Key not found",ex.getMessage());
//		List<String> a=new ArrayList<String>();
//		a.add("https://en.wikipedia.org/wiki/Lua_(programming_language)");
//		a.add("https://en.wikipedia.org/wiki/Squirrel_(programming_language)");
    }
	@Test
	public void links_found() throws IOException, ParseException {
		App a1=new App();
    	String initialString = "{\"pageid\":01,\"*\":\"==See also==\\n*[[Lua (programming language)]]\\n*[[Squirrel (programming language)]]\\n\\n=\"}";
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        URL url=Mockito.mock(URL.class);
        HttpURLConnection conn=Mockito.mock(HttpURLConnection.class);
        Mockito.when(conn.getResponseCode()).thenReturn(200);
        Mockito.when(url.openConnection()).thenReturn(conn);
        Mockito.when(url.openStream()).thenReturn(targetStream);
        JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(initialString);
		List<String> a=new ArrayList<String>();
		a.add("https://en.wikipedia.org/wiki/Lua_(programming_language)");
		a.add("https://en.wikipedia.org/wiki/Squirrel_(programming_language)");
		assertEquals(a,a1.links(json));
	}
	@Test
	public void links_not_found() throws IOException, ParseException {
		App a1=new App();
    	String initialString = "{\"pageid\":null,\"*\":\"\"}";
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        URL url=Mockito.mock(URL.class);
        HttpURLConnection conn=Mockito.mock(HttpURLConnection.class);
        Mockito.when(conn.getResponseCode()).thenReturn(200);
        Mockito.when(url.openConnection()).thenReturn(conn);
        Mockito.when(url.openStream()).thenReturn(targetStream);
        JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(initialString);
		NullPointerException ex=assertThrows(NullPointerException.class,()->a1.links(json));
		assertEquals("links not found",ex.getMessage());
	}
	@Test
	public void null_json() {
		App a1=new App();
		NullPointerException ex=assertThrows(NullPointerException.class,()->a1.links(null));
		assertEquals("json object not found",ex.getMessage());
		NullPointerException ex2=assertThrows(NullPointerException.class,()->a1.pageid(null));
		assertEquals("no json object found",ex2.getMessage());
		
	}

}
