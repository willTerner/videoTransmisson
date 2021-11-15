package com.ternerwill.resourceconnector.bean;

import lombok.Data;

@Data
public class Result {
    private int status;
    private String message;
    private Object data;

    private Result(int status, Object data){
        this.status = status;
        this.data = data;
    }

    private Result( int status, String message){
        this.status = status;
        this.message = message;
    }

    public static Result success(int status, Object data){
        return new Result(status,data);
    }

    public static Result fail( int status, String message){
        return new Result(status, message);
    }
}
