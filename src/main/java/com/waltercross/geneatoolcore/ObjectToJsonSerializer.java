package com.waltercross.geneatoolcore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Date;

public class ObjectToJsonSerializer {

    public String serialize(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // You can use a custom module to change the formatter
        mapper.registerModule(new SimpleModule().addSerializer(Date.class,
            new CustomDateSerializer()));
        
        return mapper.writeValueAsString(o);
    }

}