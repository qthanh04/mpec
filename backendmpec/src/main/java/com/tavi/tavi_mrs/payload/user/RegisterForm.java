package com.tavi.tavi_mrs.payload.user;

import lombok.Data;


@Data
public class RegisterForm {
    private String userName;

    private String passWord;

    private String email;
}
