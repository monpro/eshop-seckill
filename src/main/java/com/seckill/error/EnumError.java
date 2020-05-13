package com.seckill.error;

public enum EnumError implements CommonError{
    PARAMETER_INVALIDATION_ERROR(10001, "PARAMETER INVALIDATION"),
    UNKNOWN_ERROR(10002, "UNKNOWN ERROR"),
    USER_NOT_EXIST(20001, "USER NOT EXIST"),
    USER_LOGIN_FAIL(30001, "username or password is wrong"),
    USER_NOT_LOGIN(30002, "user not login"),

    STOCK_NOT_ENOUGH(40001, "stock is not enough");



    private EnumError(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    private int errorCode;
    private String errorMsg;

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;

        return this;
    }
}
