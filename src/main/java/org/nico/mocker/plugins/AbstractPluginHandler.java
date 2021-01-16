package org.nico.mocker.plugins;

import java.util.List;

import org.nico.mocker.model.Api;
import org.nico.mocker.model.ApiResult;
import org.nico.mocker.model.Plugin;


public interface AbstractPluginHandler {

	public List<Api> extract(Plugin plugin) throws Exception;

	public ApiResult parse(Plugin plugin) throws Exception;

	/**
	 *
	 * @param plugin
	 * @param type 1 取请求体 2 取响应体 null值 同时取请求体和响应体 默认为空
	 * @return
	 * @throws Exception
	 */
	public ApiResult parse(Plugin plugin, Integer type) throws Exception;
}
