package com.tavi.tavi_mrs.service.mail;

import com.tavi.tavi_mrs.entities.json.EmailForm;
import com.tavi.tavi_mrs.payload.user.EmailSend;

import javax.mail.MessagingException;
import java.util.List;

public interface MailService {

    boolean sendMail(String[] userMail, String header, String content);

    boolean sendMailWithAttachment(String[] listMail, String header, String content, List<String> attachFile) throws MessagingException;

    boolean sendHtmlMailPassWord(String userMail, String header, String content);

    boolean sendHtmlMail(String userMail, String header, String content);

//    boolean sendListMail(List<String> list, String header, String content);

    boolean sendListMail(EmailForm emailForm);

}
