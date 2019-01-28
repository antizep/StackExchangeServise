package StackExchangeServise.implementation;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

import org.apache.http.impl.client.HttpClientBuilder;
import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import StackExchangeServise.ResultModel;
import StackExchangeServise.services.SearshProvider;



@Service("stackExchange")
@Repository
public class SerashProviderStackExchange implements SearshProvider {

	public final static String URL_PROVIDER_STACK_EXCHANGE = "http://api.stackexchange.com/2.2/search?order=desc&sort=activity&intitle=%s&site=stackoverflow";
	private String error;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public String getError() {
		return error;
	}

	@Override
	public List<ResultModel> searsh(String searshString) {
		error = null;
		logger.info("searshString: " + searshString);
		
		List<ResultModel> resultModels = new ArrayList<>();
		try {
			searshString = URLEncoder.encode(searshString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			error = "Unsupported encoding";
			logger.error("Unsupported encoding", e);
			return null;
		}
		
		Formatter formatter = new Formatter();
		String searshUrl = formatter.format(URL_PROVIDER_STACK_EXCHANGE, searshString).toString();
		logger.debug("searsh URL: "+searshUrl);
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build());
		
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
		try {
			
			String document = restTemplate.getForObject(searshUrl, String.class);
			logger.debug("load document"+ document);
			JSONObject documentJSON = new JSONObject(document);
			JSONArray items = documentJSON.getJSONArray("items");
			
			for(int i=0; i<items.length();i++) {
				
				resultModels.add(itemJSONToResultModel(items.getJSONObject(i)));
				
			}
			
		} catch (JSONException e) {
			error = "bad response in search provider";
			logger.error("bad response in search provider", e);
		}catch (ResourceAccessException e) {
			
			error = "search provider not available";
			logger.error("search provider not available", e);
		}
		
		return resultModels;
	}
	
	private ResultModel itemJSONToResultModel(JSONObject item) throws JSONException {
		ResultModel resultModel = new ResultModel();
		logger.debug("item"+ item);
		resultModel.setTitle(item.getString("title"));
		
		resultModel.setWhoPosted(item.getJSONObject("owner").getString("display_name"));
		
	    Date date = new Date(item.getLong("creation_date")*1000);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z");
		
		resultModel.setDateQuestion(dateFormat.format(date));
		resultModel.setLink(item.getString("link"));
		resultModel.setAnswered(item.getBoolean("is_answered"));
	
		return resultModel;
	}

}
