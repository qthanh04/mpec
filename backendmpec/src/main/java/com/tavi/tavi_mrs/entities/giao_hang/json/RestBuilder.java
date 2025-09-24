package com.tavi.tavi_mrs.entities.giao_hang.json;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RestBuilder {

    private String service;
    private String uri;
    private Map<String, String> params = new HashMap<>();

    public static RestBuilder build(){
        return new RestBuilder();
    }

    public RestBuilder service(String service){
        this.service = service;
        return this;
    }

    public RestBuilder uri(String uri){
        this.uri = uri;
        return this;
    }

    public RestBuilder param(String key, String value){
        this.params.put(key, value);
        return this;
    }
}
