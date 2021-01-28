package org.nico.mocker.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.nico.mocker.consts.Constants;
import org.nico.mocker.consts.RespCode;
import org.nico.mocker.container.ApiContainer;
import org.nico.mocker.dto.StartDto;
import org.nico.mocker.resp.RespVo;
import org.nico.mocker.resp.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@io.swagger.annotations.Api("swagger接口数据")
@RestController
@RequestMapping("/config")
public class ConfigController {

	@Autowired
	private ApiContainer apiContainer;

	//使用动态uri时，过滤器无法正常调用？
	@GetMapping("/apiResult")
	public Object apiResult(
			@RequestParam(value = "swaggerSign", required = false) String swaggerSign,
			@RequestParam(value = "swaggerApiUrl") String swaggerApiUrl,
			@RequestParam(value = "type", required = false) Integer type){
		try {

			StartDto startDto = new StartDto();
			startDto.setSwaggerSign(swaggerSign);
			startDto.setSwaggerApiUrl(swaggerApiUrl);
			startDto.setType(type);
			apiContainer.enable(startDto);
			return apiContainer.parseApiResult(startDto);
		} catch (Exception e) {
			e.printStackTrace();
			return RespVo.failure(RespCode.API_PRASE_ERR);
		}
	}

	/**
	 *  http://127.0.0.1:10088/config/start?swagger-data-key=Y
	 *
	 *  {
	 *     "swaggerApiUrl":"http://127.0.0.1:8093/v2/api-docs",
	 *     "swaggerSign":"test111"
	 * }
	 * @param startDto
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/start")
	public ResultDto start(@RequestBody StartDto startDto) throws Exception {

		apiContainer.parseApis(startDto);
		return ResultDto.success();
	}

	/**
	 *  http://127.0.0.1:10088/config/apiData?swagger-data-key=Y&swaggerSign=ceshiren
	 * @param swaggerSign
	 * @return
	 */
	@GetMapping("/apiData")
	public ResultDto getData(
			@RequestParam(value = "swaggerSign", required = false) String swaggerSign){

		if(StringUtils.isEmpty(swaggerSign)){
			return ResultDto.success("成功",apiContainer.getAll());
		}

		JSONObject result = new JSONObject();

		result.put(Constants.swagger_apis,apiContainer.getApiResult(swaggerSign));
		result.put(Constants.swagger_info,apiContainer.getSwaggerInfo(swaggerSign));

		return ResultDto.success("成功",result);
	}

}
