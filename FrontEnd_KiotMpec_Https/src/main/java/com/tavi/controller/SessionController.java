package com.tavi.controller;

import com.tavi.model.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/session")
public class SessionController {


    @GetMapping("/get-url")
    public ResponseEntity<String> getCurrentUrl(){
        try{
            String token = "token is created";

           Session.getSession(token);
            String url = Session.getCurrentUrl();
            if(url == null){
                return ResponseEntity.ok("/tong-quan");
            }else {
                return ResponseEntity.ok(url);
            }
        }catch (Exception ex){
            return ResponseEntity.ok("/tong-quan");
        }
    }

    @GetMapping("/clear-url")
    public ResponseEntity<String> setCurrentUrl(){
        try{
            Session.clearUrl();
            return ResponseEntity.ok("success");
        }catch (Exception ex){
            return ResponseEntity.ok("failed");
        }
    }

}
