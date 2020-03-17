package org.sang.common;

import java.io.Serializable;

public class ResponseData implements Serializable {
    private static final long serialVersionUID = 768549219446295665L;
    private Integer total;  //总条数
    private Object data; //当前页显示数据

    private Integer resultCode;//返回码

    private String message;//返回信息


    public ResponseData() {

    }

    public ResponseData(Integer resultCode, String message, Object data) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = data;
    }

    public ResponseData(Integer resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
