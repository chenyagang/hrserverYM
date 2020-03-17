package org.sang.common;


/**
 * 数据有效性枚举类
 *
 * @author luyujian
 * @version C01 2015-2-26
 * @since JESHING
 */
public enum ResultCodeEnum {

    /**
     * 200：成功
     */
    SUCCESS(1, "成功"),


    /**
     * 400：失败
     */
    FAIL(-1, "失败"),


    /**
     * 10001：一周内不能编辑
     */
    CANNOT_EDIT_IN_WEEK(10001, "一周内不能编辑");


    /**
     * 编号
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;

    private ResultCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public ResponseData getResponse(Object object) {
        return new ResponseData(code, desc, object);
    }

    public ResponseData getResponse() {
        return new ResponseData(code, desc);
    }

}
