package org.nico.mocker.controller;

import java.util.stream.Collectors;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.nico.mocker.consts.RespCode;
import org.nico.mocker.container.ApiContainer;
import org.nico.mocker.container.DataContainer;
import org.nico.mocker.dto.MockInfo;
import org.nico.mocker.dto.StartDto;
import org.nico.mocker.model.Api;
import org.nico.mocker.resp.RespVo;
import org.nico.mocker.service.MockerService;
import org.nico.mocker.utils.HttpContextUtils;
import org.nico.mocker.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@io.swagger.annotations.Api("Mocker测试入口")
@RestController
@RequestMapping("/m")
public class MockerController {

	@Autowired
	private MockerService mockerService;

	@Autowired
	private ApiContainer apiContainer;

	@GetMapping("/apis")
	public Object apis(@RequestParam(value = "swaggerSign", required = false) String swaggerSign,
					   @RequestParam(value = "swaggerApiUrl") String swaggerApiUrl,
					   @RequestParam(value = "type", required = false) Integer type){
		try {

			StartDto startDto = new StartDto();
			startDto.setSwaggerSign(swaggerSign);
			startDto.setSwaggerApiUrl(swaggerApiUrl);
			startDto.setType(type);
			apiContainer.enable(startDto);

			return apiContainer.getApis(startDto).stream().map(api -> {
				return api.getMethod() + " " + api.getPath();
			}).collect(Collectors.toSet());
		} catch (Exception e) {
			e.printStackTrace();
			return RespVo.failure(RespCode.API_PRASE_ERR);
		}
	}

	@RequestMapping(value = "/mock", produces = "application/json;charset=utf-8")
	public Object handle(
			@RequestParam(name = "_listSize", required = false) Integer listSize,
			@RequestParam(name = "_mapSize", required = false) Integer mapSize,
			@RequestParam(name = "_cycleSize", required = false) Integer cycleSize,
			@RequestParam(name = "_dateFormat", required = false) String dateFormat,
			@RequestParam(name = "_version", required = false) String version,
			HttpServletRequest request) {
		try {
			HttpContextUtils.setListSize(listSize);
			HttpContextUtils.setMapSize(mapSize);
			HttpContextUtils.setDateFormat(dateFormat);
			HttpContextUtils.setCycleSize(cycleSize);

			String apiPath = String.valueOf(request.getAttribute("requestUrl"));
			String httpMethod = String.valueOf(request.getAttribute("httpMethod"));
			if(DataContainer.isEnable() && StringUtils.isNotBlank(version)) {
				return DataContainer.getMockData(apiPath, version);
			}else {

				StartDto startDto = new StartDto();

				MockInfo mockInfo = StrUtils.getMockInfo(apiPath);
				startDto.setSwaggerSign(mockInfo.getSign());

				Api api = apiContainer.getApi(startDto, mockInfo.getRealUri(), httpMethod);
				if(api == null) {
					return RespVo.failure(RespCode.API_NOT_FOUND);
				}
				return mockerService.rendering(api);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return RespVo.failure(RespCode.API_PRASE_ERR);
		}
	}
}
