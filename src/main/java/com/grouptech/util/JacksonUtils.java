package com.grouptech.util;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * @author xiaolong.qiu on 2016/4/29.
 */


public class JacksonUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * 转换对象为json数据
     * @param object
     * @return
     */
    public static String  obj2Json(Object object){
        if(object == null) return "";
        try {
            mapper.getSerializationConfig().with(formatter);
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            Constant.logger.warn("write to json string error:" + object, e);
            return "";
        }
    }


    /**
     * 转换json数据为对象
     * @param json
     * @param clazz
     * @return
     */
    public static <T>  T  json2Obj(String json,Class<T> clazz){
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            mapper.getSerializationConfig().with(formatter);
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            Constant.logger.warn("parse json string error:" + json, e);
            return null;
        }
    }

    /**
     * 转换json数据为对象列表
     * @param json
     * @param classOfT
     * @return
     */
    public static <T>  List<T>  json2List(String json,Class<T> classOfT){
        List<T> objList = null;
        try {
            mapper.getSerializationConfig().with(formatter);
            JavaType t = mapper.getTypeFactory().constructCollectionType(List.class, classOfT);
            objList = mapper.readValue(json, t);
        } catch (Exception e) {
        }
        return objList;
    }

    public static List<Map>  json2ListMap(String json){
        List<Map> objList = null;
        try {
            mapper.getSerializationConfig().with(formatter);
            JavaType t = mapper.getTypeFactory().constructCollectionType(List.class, Map.class);
            objList = mapper.readValue(json, t);
        } catch (Exception e) {
        }
        return objList;
    }

    /**
     * 转换json数据为对象
     * @param json
     * @return
     */
    public static Map json2Map(String json){
        if(StringUtils.isNotEmpty(json)){
            return json2Obj(json, Map.class);
        }else{
            return null;
        }
    }
}
