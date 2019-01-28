package StackExchangeServise.services;

import java.net.UnknownHostException;
import java.util.List;

import StackExchangeServise.ResultModel;

public interface SearshProvider {
	String getError();
	List<ResultModel> searsh(String searshString);

} 
