package com.cf.util.utils;

import net.sf.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class StringJsonUtils {

    public static int parseIntFromUrlJson(RestTemplate restTemplate , String url, String elements) throws Exception {
        return parseIntValue(getResultFromUrl(restTemplate,url),elements);
    }

    public static double parseDoubleFromUrlJson(RestTemplate restTemplate ,String url,String elements) throws Exception {
        return parseDoubleValue(getResultFromUrl(restTemplate,url),elements);
    }

    public static String parseStringFromUrlJson(RestTemplate restTemplate ,String url,String elements) throws Exception {
        return parseStringValue(getResultFromUrl(restTemplate,url),elements);
    }

    public static JSONObject parseJSONObjectFromUrlJson(RestTemplate restTemplate , String url, String elements) throws Exception {
        return parseJSONObjectValue(getResultFromUrl(restTemplate,url),elements);
    }


    public static String getResultFromUrl(RestTemplate restTemplate,String url) throws Exception {
        ResponseEntity<String> responseEntity=restTemplate.getForEntity(url,String.class);
        if(responseEntity.getStatusCode()== HttpStatus.OK)
        {
            return responseEntity.getBody();
        }
        else
            throw  new Exception("error->"+url +" get code="+responseEntity.getStatusCode().value());
    }

    public static int  parseIntValue(String json, String elements) throws Exception {
        Object object = parseJson(json, elements);
        if(object instanceof Integer) return ((Integer)object).intValue();
        throw  new Exception("不是Integer");
    }

    public static double  parseDoubleValue(String json, String elements) throws Exception {
        Object object = parseJson(json, elements);
        if(object instanceof Double) return ((Double)object).doubleValue();
        throw  new Exception("不是Double");
    }

    public static String  parseStringValue(String json, String elements) throws Exception {
        Object object = parseJson(json, elements);
        if(object instanceof Double) return object.toString();
        throw  new Exception("不是String");
    }

    public static JSONObject  parseJSONObjectValue(String json, String elements) throws Exception {
        Object object = parseJson(json, elements);
        if(object instanceof JSONObject) return (JSONObject) object;
        throw  new Exception("不是JSONObject");
    }

    /**
     * 递归获取某一个制定json字段的值
     * @param json
     * @param elements
     * @return
     */
    private static Object  parseJson(String json, String elements) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        List<String> list = Arrays.asList(elements.split("->"));
        return parseJson(jsonObject, list,null);
    }

    /**
     * 递归获取某一个制定json字段的值
     * @param jsonObject
     * @param list
     * @return
     */
    private static Object parseJson(JSONObject jsonObject, List<String> list,Integer index) {
        if (CollectionUtils.isEmpty(list) || jsonObject==null)
            return null;
        index=index==null?0:index;
        String element = list.get(index);
        if (list.size() > index+1) {
            JSONObject result=null;
            if (element.indexOf(".") >= 0) {
                String[] arrayElems = element.split("\\.");
                result = jsonObject.getJSONArray(arrayElems[0]).getJSONObject(Integer.parseInt(arrayElems[1]));
            } else {
                result = jsonObject.getJSONObject(element);
            }
            return parseJson(result, list,index+1);
        } else {
            if (element.indexOf(".") >= 0) {
                String[] arrayElms = element.split("\\.");
                return jsonObject.getJSONArray(arrayElms[0]).getJSONObject(Integer.parseInt(arrayElms[1]));
            }
            return jsonObject.get(element);
        }
    }
}
