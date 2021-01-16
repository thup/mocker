package org.nico.mocker.model;

import io.swagger.models.Info;
import lombok.Data;
import org.nico.mocker.enums.HttpMethod;

import java.util.List;
import java.util.Map;

@Data
public class ApiResult {

	protected Info info;

	private String basePath;

	private String host;

	/**
	 * api列表数据.
	 */
	private List<Api> apis;

}
