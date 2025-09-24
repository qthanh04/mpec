package com.tavi.tavi_mrs.entities.json;

import lombok.Data;

import java.util.List;

@Data
public class EmailForm {

    private String header;

    private String content;

    private String urlAttachment;

    List<String> mail;


}
