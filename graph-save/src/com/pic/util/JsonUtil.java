package com.pic.util;

import java.io.IOException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
/**
 * 
 * <p>json实用封装类</p>
 * @author towan
 * @Email  tongwenzide@163.com
 * 2015年1月21日下午9:43:21
 *
 * @version V1.0
 */
public final class JsonUtil {
	private static ObjectMapper mapper; 
	
	static {  
		mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	    //_mapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
	    //_mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm-ss S"));  
    }
	/**
	 * 对象转化为json
	 * @param obj 对象
	 * @return 字符串
	 * @throws IOException
	 * @author towan	
	 * @Email  tongwenzide@163.com
	 * 2015年1月21日下午9:45:29
	 */
	public static String toJson(Object obj) throws IOException{
		return mapper.writeValueAsString(obj);
	}
	public static <T> T toObject(String json, Class<T> cls) throws IOException{
		return mapper.readValue(json, cls);
	}

   
}
