package org.nico.mocker.container;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.nico.mocker.common.TempDb;
import org.nico.mocker.consts.Constants;
import org.nico.mocker.dto.StartDto;
import org.nico.mocker.enums.PluginPathType;
import org.nico.mocker.model.Api;
import org.nico.mocker.model.ApiResult;
import org.nico.mocker.plugins.AbstractPluginHandler;
import org.nico.mocker.plugins.swagger.SwaggerPlugin;
import org.nico.mocker.plugins.swagger.SwaggerPluginHandler;
import org.nico.mocker.resp.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

@Slf4j
@Component
public class ApiContainer {

	@Autowired
	private TempDb tempDb;

	private static AbstractPluginHandler handler = new SwaggerPluginHandler();

	private static PathMatcher antMatcher = new AntPathMatcher();

	public ResultDto enable(StartDto startDto) {

		//http://127.0.0.1:8093/v2/api-docs
		String swaggerApiUrl = startDto.getSwaggerApiUrl();
		String swaggerSign = startDto.getSwaggerSign();
		if(StringUtils.isEmpty(swaggerApiUrl)){
			return ResultDto.fail("swaggerApiUrl不能为空");
		}
		if(StringUtils.isEmpty(swaggerSign)){
			swaggerSign = Constants.swagger_sign;
			startDto.setSwaggerSign(swaggerSign);
		}

		tempDb.addInfoValue(swaggerSign, "swaggerApiUrl", swaggerApiUrl);

		log.info("startDto {}", JSONObject.toJSONString(startDto));
		return ResultDto.success();
	}

	public Api getApi(StartDto startDto, String apiPath, String httpMethod) throws Exception {
		List<Api> apis = getApis(startDto);
		if(! CollectionUtils.isEmpty(apis)) {
			for(Api api: apis) {
				if(api.getMethod().toString().equalsIgnoreCase(httpMethod) && antMatcher.match(api.getPath(), apiPath)) {
					return api;
				}
			};
		}
		return null;
	}

	public List<Api> getApis(StartDto startDto) throws Exception{
		return tempDb.getApiResult(startDto.getSwaggerSign()).getApis();
	}

	public List<Api> parseApis(StartDto startDto) throws Exception{
		enable(startDto);

		startDto.setType(Constants.apiResult_type_response);

		ApiResult apiResult = parseApiResult(startDto);

		tempDb.addApis(startDto.getSwaggerSign(),apiResult);
		return apiResult.getApis();
	}

	public ApiResult parseApiResult(StartDto startDto) throws Exception{
		log.info("Parse startDto {}", JSONObject.toJSONString(startDto));
		SwaggerPlugin sp = new SwaggerPlugin();
		sp.setName("swagger");
		sp.setPath(startDto.getSwaggerApiUrl());
		sp.setPathType(PluginPathType.HTTP);
		ApiResult apiResult = handler.parse(sp, startDto.getType());
		return apiResult;
	}

	public ApiResult getApiResult(String sign){
		return tempDb.getApiResult(sign);
	}
	public JSONObject getSwaggerInfo(String sign){
		return tempDb.getInfo(sign);
	}

	public JSONObject getAll(){
		return tempDb.getTempDb();
	}


}
