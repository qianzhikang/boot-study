package com.qzk.boot.restful.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzk.boot.restful.model.Reader;

/**
 * @Description TODO
 * @Date 2022-03-10-19-47
 * @Author Courage
 */
public class Demo {
    public static void main(String[] args) throws JsonProcessingException {
        Reader reader = new Reader("qzk",21);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(reader);

        Reader reader1 =  objectMapper.readValue(jsonStr,Reader.class);

        System.out.println("o->j : " + jsonStr);
        System.out.println("j->o : " + reader1);
    }
}
