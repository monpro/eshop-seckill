package com.seckill.response;

public class CommonResponseType {

    private String status;

    private Object data;

    public static CommonResponseType create(Object result){
        return CommonResponseType.create(result, "success");
    }

    public static CommonResponseType create(Object result, String status){
        CommonResponseType type = new CommonResponseType();
        type.setStatus(status);
        type.setData(result);

        return type;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
