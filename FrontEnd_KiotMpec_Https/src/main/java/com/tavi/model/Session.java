package com.tavi.model;

import lombok.Data;

@Data
public class Session {

    private static Session session;

    private String token = null;

    private String currentUrl = null;

    private Session(){
    }

    private void setToken(String token){
        this.token = token;
    }

    private void setUrl(String url){
        this.currentUrl = url;
    }

    private String getUrl(){
        return this.currentUrl;
    }

    public static void setCurrentUrl(String currentUrl){
        session = new Session();
        session.setUrl(currentUrl);
    }

    public static String getCurrentUrl(){
        try {
            String rs = session.getUrl();
            return rs;
        }catch (Exception ex){
            return null;
        }
    }


    public static void clearUrl(){
        session.setUrl(null);
    }

    private String getToken(){
        try{
            return this.token ;
        }catch (Exception ex){
            return "";
        }
    }

    public static Boolean isValiadteToken(){
        try{
            if(session.getToken() != null){
                Boolean check = session.getToken().equals("");
                return !check;
            }else {
                return false;
            }

        }catch (Exception ex){
            return false;
        }
    }

    public static Session getSession(String token){
        if(session  != null){
            session.setToken(token);
            return session;
        }else{
            session = new Session();
            session.setToken(token);
            return session;
        }
    }

    public static void clearSession(){
        session.setToken(null);
        session.setUrl(null);
        session = null;
    }

}
