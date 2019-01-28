package StackExchangeServise.implementation;



import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Formatter;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import StackExchangeServise.ResultModel;
import StackExchangeServise.services.SearshProvider;


@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("StackExchangeServise")
@EnableAutoConfiguration 

public class StackExchangeServiseTest {

	@Autowired 
	private ApplicationContext context;
	private String searshString;
	

	@Before
	public void setUp() throws Exception { 
		searshString = "java";
	}

	@Test
	public void integration() throws ClientProtocolException, IOException {

		Formatter formatter = new Formatter();
		String searshUrl = formatter.format(SerashProviderStackExchange.URL_PROVIDER_STACK_EXCHANGE, searshString).toString();
		HttpUriRequest request = new HttpGet( searshUrl );

		HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

		assertThat(httpResponse.getStatusLine().getStatusCode(),equalTo(HttpStatus.SC_OK));

		String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
		assertEquals( MediaType.APPLICATION_JSON_VALUE, mimeType );

		JSONObject documentJsonObject = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));

		assertTrue(documentJsonObject.has("items"));
		Object items = documentJsonObject.opt("items");
		assertEquals(items.getClass(), JSONArray.class);
		JSONArray itemsArray =(JSONArray) items;

		int randIndex =(int) (Math.random() * itemsArray.length());
		JSONObject randItemJ = itemsArray.getJSONObject(randIndex);

		assertTrue(randItemJ.has("title"));
		assertEquals(randItemJ.get("title").getClass(),String.class);

		assertTrue(randItemJ.has("creation_date"));
		assertEquals(randItemJ.get("creation_date").getClass(),Integer.class);

		assertTrue(randItemJ.has("link"));
		assertEquals(randItemJ.get("link").getClass(),String.class);

		assertTrue(randItemJ.has("is_answered"));
		assertEquals(randItemJ.get("is_answered").getClass(),Boolean.class);

		assertTrue(randItemJ.has("owner"));
		assertEquals(randItemJ.get("owner").getClass(),JSONObject.class);

		JSONObject owner = randItemJ.getJSONObject("owner");

		assertTrue(owner.has("display_name"));
		assertEquals(owner.get("display_name").getClass(), String.class);		   

	}
	
	@Test
	public void testSearsh() {
		SearshProvider provider = context.getBean("stackExchange",SearshProvider.class);
		List<ResultModel> resultsList = provider.searsh(searshString);
		
		assertNotNull(resultsList);
		assertNull(provider.getError());
		
		
	}

}

