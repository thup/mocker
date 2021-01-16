package org.nico.mocker.controller;

import org.nico.mocker.consts.RespCode;
import org.nico.mocker.container.ApiContainer;
import org.nico.mocker.resp.RespVo;
import org.springframework.web.bind.annotation.*;


@io.swagger.annotations.Api("swagger接口数据")
@RestController
@RequestMapping("/data")
public class SwaggerDataController {

	//使用动态uri时，过滤器无法正常调用？
	@GetMapping("/apiResult")
	public Object apiResult(
			@RequestParam(value = "type", required = false) Integer type){
		try {
			return ApiContainer.parseApiResult(type);
		} catch (Exception e) {
			e.printStackTrace();
			return RespVo.failure(RespCode.API_PRASE_ERR);
		}
	}

}
