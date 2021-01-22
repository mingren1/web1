package com.athome.idubbo.service;

import com.alibaba.dubbo.rpc.service.GenericException;
import com.alibaba.dubbo.rpc.service.GenericService;

public class GenericServiceImpl implements GenericService {
    @Override
    public Object $invoke(String method, String[] parameterTypes, Object[] args) throws GenericException {
        if (method.equals("hi")) {
            return "hi, " + args[0];
        } else if (method.equals("hello")) {
            return "hello, " + args[0];
        }

        return "welcome";
    }
}
