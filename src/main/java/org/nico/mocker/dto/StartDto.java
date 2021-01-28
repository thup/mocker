package org.nico.mocker.dto;/**
 * @Auther: tlibn
 * @Date: 2021/01/28/16:06
 * @Description:
 */

import lombok.Data;

/**
 *@Author tlibn
 *@Date 2021/1/28 16:06
 **/
@Data
public class StartDto {

    //swagger接口地址
    private String swaggerApiUrl;

    //swagger项目标识 默认为ceshiren
    private String swaggerSign;

    //1 取请求体 2 取响应体 null值 同时取请求体和响应体 默认为空
    private Integer type;
}
