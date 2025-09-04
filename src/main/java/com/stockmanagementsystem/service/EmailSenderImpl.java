package com.stockmanagementsystem.service;

import com.stockmanagementsystem.entity.LoginUser;
import com.stockmanagementsystem.entity.SysConfiguration;
import com.stockmanagementsystem.repository.SysConfigurationRepository;
import com.stockmanagementsystem.utils.EmailConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Properties;

@Service
@Slf4j
public class EmailSenderImpl implements  EmailSender{

    @Autowired
    SysConfigurationRepository configurationRepository;

    @Autowired
    LoginUser loginUser;

    @Override
    public Boolean sendMail(String subject, String message, String to, Integer orgId) {
       Boolean status;
        List<SysConfiguration> config=configurationRepository.findByIsDeleted(false);

        SysConfiguration host = config.stream().filter( key-> key.getConfigName().equals(EmailConstant.MAIL_SMTP_HOST)).findFirst().get();
        SysConfiguration port = config.stream().filter( key-> key.getConfigName().equals(EmailConstant.MAIL_SMTP_PORT)).findFirst().get();
        SysConfiguration sslEnable = config.stream().filter( key-> key.getConfigName().equals(EmailConstant.MAIL_SMTP_SSL_ENABLE)).findFirst().get();
        SysConfiguration username = config.stream().filter( key-> key.getConfigName().equals(EmailConstant.MAIL_SMTP_USERNAME)).findFirst().get();
        SysConfiguration password = config.stream().filter( key-> key.getConfigName().equals(EmailConstant.MAIL_SMTP_PASSWORD)).findFirst().get();
        SysConfiguration auth = config.stream().filter( key-> key.getConfigName().equals(EmailConstant.MAIL_SMTP_AUTH)).findFirst().get();

        Properties properties = System.getProperties();
        properties.setProperty(host.getConfigName(), host.getConfigValue());
        properties.setProperty(port.getConfigName(), port.getConfigValue());
        properties.setProperty(sslEnable.getConfigName(), sslEnable.getConfigValue());
        properties.setProperty(auth.getConfigName(), auth.getConfigValue());             // SMTP port

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username.getConfigValue(), password.getConfigValue());
            }
        });
        session.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(session);
        try{
            mimeMessage.setFrom();
            mimeMessage.setSubject(subject);
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            MimeMultipart mimeMultipart = new MimeMultipart();
            MimeBodyPart htmlPart = new MimeBodyPart();
            try{

                htmlPart.setContent( message, "text/html; charset=utf-8" );
                // this condintion should be use if file not received
//                if(file!=null)
//                {
//                    //TODO file attachment ........
//                    mimeFileBody.attachFile(file);
//                    mimeMultipart.addBodyPart(htmlPart);
//                    mimeMultipart.addBodyPart(mimeFileBody);
//
//                }
                    mimeMultipart.addBodyPart(htmlPart);

            } catch (Exception e) {
                return false;
            }
            mimeMessage.setContent(mimeMultipart );
            Transport.send(mimeMessage);

        }catch(Exception ex){
            return false;
        }

        return true;
    }
}

