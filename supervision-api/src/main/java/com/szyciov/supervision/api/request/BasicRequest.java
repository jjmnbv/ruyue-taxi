package com.szyciov.supervision.api.request;


import com.supervision.enums.CommandEnum;
import com.supervision.enums.InterfaceType;
import com.szyciov.supervision.enums.RequestType;


/**
 * Created by admin on 2017/7/6.
 */

public class BasicRequest {
    private String result;
    private InterfaceType interfaceType;
    private CommandEnum command;
    private RequestType requestType;
    private String token;

    public BasicRequest(String result, InterfaceType interfaceType, CommandEnum command, RequestType requestType, String token) {
        this.result = result;
        this.interfaceType = interfaceType;
        this.command = command;
        this.requestType = requestType;
        this.token = token;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public InterfaceType getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(InterfaceType interfaceType) {
        this.interfaceType = interfaceType;
    }

    public CommandEnum getCommand() {
        return command;
    }

    public void setCommand(CommandEnum command) {
        this.command = command;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "BasicRequest{" +
                "result='" + result + '\'' +
                ", interfaceType=" + interfaceType +
                ", command=" + command +
                ", requestType=" + requestType +
                ", token='" + token + '\'' +
                '}';
    }
}
