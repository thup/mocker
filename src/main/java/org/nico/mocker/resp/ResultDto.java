package org.nico.mocker.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 响应返回类
 * @author tlibn
 * @description
 * @create 2019-08-02
 */
@ApiModel(value="基础返回类",description="基础返回类")
public class ResultDto<T> implements Serializable {

    private static final long serialVersionUID = -7472879865481412372L;

    /**
     * 返回码,由业务接口自己定义
     */
    @ApiModelProperty(value="业务状态码", example="00000",allowableValues = "00000,00002")
    private String returnCode = "00000";

    /**
     * 可为空,由业务接口设置
     */
    @ApiModelProperty(value="提示信息", example="成功",allowableValues = "成功,失败")
    private String message = "";

    /**
     * 元数据,Map类型.额外信息存储
     */
    @ApiModelProperty(value="额外信息存储", example="1,2",allowableValues = "1,2")
    private Map<String,Object> meta = null;

    /**
     * 响应结果数据,对象/array类型,对应后端的 Bean/List
     */

    @ApiModelProperty(value="具体响应数据")
    private T data = null;

    /**
     * 结果状态码 1 成功 0 失败
     */

    @ApiModelProperty(value="返回结果码", example="1",allowableValues = "1,0")
    private Integer resultCode;

    private Integer pageSize;       //每页多少条
    private Integer pageIndex;    //当前页面
    private Integer totalSize;      //总条数

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public static ResultDto newInstance(){
        return new ResultDto();
    }

    /**
     * 设置为成功状态
     */
    public void setAsSuccess() {
        this.resultCode = 1;
    }

    public static <T> ResultDto<T> warn(String message,T data){
        ResultDto<T> resultDto = new ResultDto<>();
        resultDto.setAsWarn();
        resultDto.setMessage(message);
        resultDto.setData(data);
        return resultDto;
    }

    public static <T> ResultDto<T> info(String message,T data){
        ResultDto<T> resultDto = new ResultDto<>();
        resultDto.setAsInfo();
        resultDto.setMessage(message);
        resultDto.setData(data);
        return resultDto;
    }

    public static <T> ResultDto<T> warn(String message){
        ResultDto<T> resultDto = new ResultDto<>();
        resultDto.setAsWarn();
        resultDto.setMessage(message);
        return resultDto;
    }


    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String,Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }

    public static ResultDto success(){
        ResultDto resultDto = new ResultDto();
        resultDto.setAsSuccess();
        resultDto.setMessage("成功");
        return resultDto;
    }

    public static ResultDto success(String message){
        ResultDto resultDto = new ResultDto();
        resultDto.setAsSuccess();
        resultDto.setMessage(message);
        return resultDto;
    }
    public static <T> ResultDto<T> success(String message,T data){
        ResultDto<T> resultDto = new ResultDto<>();
        resultDto.setAsSuccess();
        resultDto.setMessage(message);
        resultDto.setData(data);
        return resultDto;
    }

    public static <T> ResultDto<T> fail(String returnCode, String message,T data){
        ResultDto<T> resultDto = new ResultDto<>();
        resultDto.setAsFailure();
        resultDto.setReturnCode(returnCode);
        resultDto.setMessage(message);
        resultDto.setData(data);
        return resultDto;
    }
    public static <T> ResultDto<T> fail(String message,T data){
        ResultDto<T> resultDto = new ResultDto<>();
        resultDto.setAsFailure();
        resultDto.setMessage(message);
        resultDto.setData(data);
        return resultDto;
    }

    public static <T> ResultDto<T> info(String message){
        ResultDto<T> resultDto = new ResultDto<>();
        resultDto.setAsInfo();
        resultDto.setMessage(message);
        return resultDto;
    }

    /**
     * 设置为失败状态
     */
    public void setAsFailure() {
        this.resultCode = 0;
    }

    public static <T> ResultDto<T> fail(String returnCode, String message){
        ResultDto<T> resultDto = new ResultDto<>();
        resultDto.setAsFailure();
        resultDto.setReturnCode(returnCode);
        resultDto.setMessage(message);
        return resultDto;
    }

    public static <T> ResultDto<T> fail(String message){
        ResultDto<T> resultDto = new ResultDto<>();
        resultDto.setAsFailure();
        resultDto.setMessage(message);
        return resultDto;
    }

    /**
     * 设置为警告状态
     */
    public void setAsWarn() {
        this.resultCode = 2;
    }

    /**
     * 设置为提示状态
     */
    public void setAsInfo() {
        this.resultCode = 3;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 添加单个Meta内的数据
     * @param key
     * @param value
     */
    public void addMeta(String key, Object value) {
        if(null == this.meta){
            this.meta = new HashMap<String,Object>();
        }
        if(null != this.meta){
            this.meta.put(key, value);
        }
    }

    /**
     * 获取单个Meta的数据
     * @param key
     * @return
     */
    public Object getMetaValue(String key) {
        Object res = null;
        if(null == this.meta){
            this.meta = new HashMap<String,Object>();
        }
        if(null != this.meta){
            res = this.meta.get(key);
        }
        return res;
    }
}
