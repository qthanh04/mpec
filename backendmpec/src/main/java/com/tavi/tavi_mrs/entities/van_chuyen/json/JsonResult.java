package com.tavi.tavi_mrs.entities.van_chuyen.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
public class JsonResult {
    private boolean success;
    private Object data;
    private String message;
    private static final String CONTENT_TYPE = "application/json; charset=utf-8";

    public static ResponseEntity<JsonResult> success(Object data){
        return ResponseEntity.ok(new JsonResult(true, data, "OK"));
    }
    public static ResponseEntity<com.tavi.tavi_mrs.entities.json.JsonResult> found(Object data) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(CONTENT_TYPE)).body(com.tavi.tavi_mrs.entities.json.JsonResult.build("found", data));
    }

    public static ResponseEntity<JsonResult> uploaded(Object data){
        return ResponseEntity.ok(new JsonResult(true, data, "uploaded"));
    }

    public static ResponseEntity<JsonResult> updated(Object data){
        return ResponseEntity.ok(new JsonResult(true, data, "updated"));
    }

    public static ResponseEntity<JsonResult> deleted(){
        return ResponseEntity.ok(new JsonResult(true, null, "deleted"));
    }

    public static ResponseEntity<JsonResult> badRequest(String mess){
        return ResponseEntity.ok(new JsonResult(false, null, mess));
    }

    public static ResponseEntity<JsonResult> error(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new JsonResult(false, null, ex.toString()));
    }
}
