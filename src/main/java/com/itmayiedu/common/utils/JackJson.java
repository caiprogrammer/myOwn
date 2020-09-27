package com.itmayiedu.common.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;





import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/** 
 * @author yangmeng
 * @version 1.0
 * @datetime 2010-8-11 下午03:23:18 
 * 类说明 
 */
public class JackJson {
	/**
	 * 对象转json串
	 * @param obj
	 * @return
	 */
	public static String getBasetJsonData(Object obj){
		String str = "";
		if(obj != null){
			ObjectMapper mapper = new ObjectMapper();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mapper.setDateFormat(sdf);
			// 忽略不识别的属性
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			try {
				str = mapper.writeValueAsString(obj);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return str;
    } 
	
	/**
	 * json数组文本串转集合
	 * @param json
	 * @return
	 */
	public static List getListByJsonArray(String json){
		List<LinkedHashMap<String, Object>> list=null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mapper.setDateFormat(sdf);
			// 忽略不识别的属性
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			list = mapper.readValue(json, List.class); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static Map getMapByJsonString(String jsonStr){
		HashMap m=null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mapper.setDateFormat(sdf);
			// 忽略不识别的属性
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			m = mapper.readValue(jsonStr, HashMap.class); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	/**
	 * json字符串转Bean对象
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T getObjectByJson(String json, Class<T> clazz){
		T obj = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mapper.setDateFormat(sdf);
			// 忽略不识别的属性
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			obj=mapper.readValue(json, clazz);  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * json字符串返回List
	 * @param json 字符串
	 * @param clazz 返回list的类型
	 * @return
	 */
	public static <T> List<T> getListByJson(String json, Class<T> clazz) {
		List<T> list = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mapper.setDateFormat(sdf);
			// 忽略不识别的属性
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			
			JavaType valueType = mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
			list = mapper.readValue(json, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * json字符串返回Map
	 * @param json 字符串
	 * @param keycls Map的key类型
	 * @param valcls Map的value类型
	 * @return
	 */
	public static <K,V> Map<K,V> getMapByJson(String json, Class<K> keycls, Class<V> valcls){
		Map<K, V> map=null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mapper.setDateFormat(sdf);
			// 忽略不识别的属性
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			JavaType jvt = mapper.getTypeFactory().constructParametricType(HashMap.class, keycls, valcls);
			map = mapper.readValue(json, jvt); 
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * json字符串转Bean对象，可下划线转驼峰标识
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T jsonToObjectByCaml(String json, Class<T> clazz) {
		T obj = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mapper.setDateFormat(sdf);
			mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
			// 忽略不识别的属性
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			obj=mapper.readValue(json, clazz);  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * 对象转字符串，属性为空或Null时不转义
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> String toJsonNotEmpty(T obj) {
		String json = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mapper.setDateFormat(sdf);
			// 设置属性为空或Null时不转义
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			// 忽略不识别的属性
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			json = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 查询json对象
	 * @param json
	 * @return
	 */
	public static JsonQuery buildQuery(String json) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		
		try {
			return new JsonQuery(json);
		} catch (IOException e) {
			throw new IllegalArgumentException("解释json参数失败", e);
		}
	}

	/**
	 * json查询对象
	 * @author tangyuntao
	 *
	 */
	public static class JsonQuery {
		
		private JsonNode jsonNode = null;
		
		protected JsonQuery(String json) throws IOException {
			ObjectMapper objectMapper = new ObjectMapper();
			jsonNode = objectMapper.readTree(json);
		}
		
		/**
		 * 获取值
		 * @param fieldName
		 * @return
		 */
		public String getText(String fieldName) {
			if (this.jsonNode == null) {
				return "";
			}
			return this.jsonNode.findPath(fieldName).asText();
		}
		
		/**
		 * 根据具体路径获取值
		 * @return
		 */
		public String atText(String pathExp) {
			if (this.jsonNode == null) {
				return "";
			}
			return this.jsonNode.at(pathExp).asText();
		}
		
		/**
		 * 获取json子串
		 * @param pathExp
		 * @return
		 */
		public String atSubJson(String pathExp) {
			if (this.jsonNode == null) {
				return "";
			}
			return this.jsonNode.at(pathExp).toString();
		}
	}
	
	/**
	 * 样例
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//--- json中的查询-----------//
		String ss = "{\"status\":100,\"sysmBusinessResult\":{\"status\":500,\"domainResponse\":{\"responseCode\":\"insertCount:0, updateCount:1, deleteCount:0, invalidCount0\",\"responseDesc\":\"域对象新增有0条,更新有1条,删除有0条，无效数据有0条.\"}}}";
		JsonQuery jsonQuery = JackJson.buildQuery(ss);
		// 查找方式取值
		String status1 = jsonQuery.getText("status");
		System.out.println(status1);
		// 根据路径方式取值
		String status2 = jsonQuery.atText("/sysmBusinessResult");
		System.out.println(status2);
		// 获取json子串
		String substr = jsonQuery.atSubJson("/sysmBusinessResult");
		System.out.println(substr);
		
		/*
		System.out.println("集合转json串-------------------------------");
		Date d1 = new Date();
		Map map = new HashMap();
		map.put("a", "tes1");
		map.put("b", "12");
		List list = new ArrayList();
		list.add(map);
		Date d2 = new Date();
		//System.out.println("装载对象：" + StringUtil.getTimeInMillis(d1, d2));
		
		Date d3 = new Date();
		String str = getBasetJsonData(list);
		Date d4 = new Date();
	    System.out.println("转换json：" + str);
	    
	    String json = "[{\"address\": \"address2\",\"name\":\"haha2\",\"id\":2,\"email\":\"email2\"},"+"{\"address\":\"address\",\"name\":\"haha\",\"id\":1,\"email\":\"email\"}]";
	    List lis=getListByJsonArray(json);
	    System.out.println("json数组文本串转集合-------------------------------总共有"+lis.size()+"对象!\n");
	    for(int i=0;i<lis.size();i++){
	    	System.out.println("--------------对象_"+i+"-----------------------");
	    	LinkedHashMap<String, Object> m=(LinkedHashMap<String, Object>)lis.get(i);
	    	for(Iterator<String> it=m.keySet().iterator();it.hasNext();){
	    		String key=it.next();
	    		System.out.println(key+"===="+m.get(key));
	    	}
	    	
	    }
	    
	    /*System.out.println("\njson串转对象-------------------------------");
	    json="{\"itemCode\":\"aa\",\"itemName\":\"bb\",\"itemValue\":\"cc\"}";
	    Object obj=getObjectByJson(json,EnumItem.class);
	    EnumItem e=(EnumItem)obj;
	    System.out.println(e.getItemCode()+"--"+e.getItemName()+"--"+e.getItemValue());
		String str="{\"max\":100,\"min\":0}";
		Map m=JackJson.getMapByJsonString(str);
		System.out.println("max:"+m.get("max"));
		System.out.println("min:"+m.get("min"));
		*/
	}
}

