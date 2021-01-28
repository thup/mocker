package org.nico.mocker.common;

import com.alibaba.fastjson.JSONObject;
import org.nico.mocker.consts.Constants;
import org.nico.mocker.model.ApiResult;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author tlibn
 * @Date 2020/6/12 16:36
 **/

@Component
public class TempDb {

    private JSONObject tempDb = new JSONObject();

    public Object addInfoValue(String sign,String key,Object value){
        JSONObject swagger_sign = getSwaggerSignJson(sign);
        JSONObject swaggerInfo = swagger_sign
                .getJSONObject(Constants.swagger_info);

        if(Objects.isNull(swaggerInfo)){
            swaggerInfo = new JSONObject();
            swagger_sign.put(Constants.swagger_info, swaggerInfo);
        }
        return swaggerInfo.put(key, value);
    }

    public Object addInfo(String sign,Object value){
        JSONObject swagger_sign = getSwaggerSignJson(sign);
        return swagger_sign.put(Constants.swagger_info, value);
    }

    public JSONObject getInfo(String sign){
        JSONObject swagger_sign = getSwaggerSignJson(sign);
        JSONObject swaggerInfo = swagger_sign
                .getJSONObject(Constants.swagger_info);

        if(Objects.isNull(swaggerInfo)){
            swaggerInfo = new JSONObject();
        }
        return swaggerInfo;
    }

    public Object getInfoValue(String sign, String key){
        return getInfo(sign).get(key);
    }

    public Object addApis(String sign, Object value){
        JSONObject swagger_sign = getSwaggerSignJson(sign);

        return swagger_sign.put(Constants.swagger_apis, value);
    }

    public Object addApisValue(String sign, String key,Object value){
        JSONObject swagger_sign = getSwaggerSignJson(sign);
        JSONObject swagger_apis = swagger_sign.getJSONObject(Constants.swagger_apis);
        if(Objects.isNull(swagger_apis)){
            swagger_apis = new JSONObject();
            swagger_sign.put(Constants.swagger_apis, swagger_apis);
        }

        return swagger_apis.put(key, value);
    }

    public JSONObject getApis(String sign){
        JSONObject swagger_sign = getSwaggerSignJson(sign);
        JSONObject swagger_apis = swagger_sign
                .getJSONObject(Constants.swagger_apis);
        return swagger_apis;
    }

    public ApiResult getApiResult(String sign){
        JSONObject swagger_sign = getSwaggerSignJson(sign);
        ApiResult swagger_apis = swagger_sign
                .getObject(Constants.swagger_apis, ApiResult.class);
        return swagger_apis;
    }

    public Object getApisValue(String sign, String key){
        return getApis(sign).get(key);
    }

    private JSONObject getSwaggerSignJson(String sign) {
        JSONObject swagger_sign = tempDb
                .getJSONObject(sign);
        if(Objects.isNull(swagger_sign)){
            swagger_sign = new JSONObject();
            tempDb.put(sign,swagger_sign);
        }

        return swagger_sign;
    }

    public JSONObject getTempDb(){
        return tempDb;
    }
}
