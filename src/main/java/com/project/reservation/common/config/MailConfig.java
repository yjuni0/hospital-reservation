package com.project.reservation.common.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;

@Configuration
public class MailConfig {


    private final Dotenv dotenv = Dotenv.load();
    @Value("${spring.mail.host}")
    private String mailHost;
    @Value("${spring.mail.port}")
    private String mailPort;

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(mailHost);	//네이버의 경우 smtp.naver.com
        javaMailSender.setUsername(dotenv.get("MAIL_USERNAME"));	//메일
        javaMailSender.setPassword(dotenv.get("MAIL_PASSWORD"));		//패스워드

        javaMailSender.setPort(Integer.parseInt(mailPort));	//ssl의 경우 465? 네이버의 경우 465?

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug", "true");
        properties.setProperty("mail.smtp.starttls.trust", mailHost);	//네이버의 경우 smtp.naver.com 변경
        properties.setProperty("mail.smtp.starttls.enable","true");	            //starttls <-> ssl로 변경도 확인
        return properties;
    }
}
