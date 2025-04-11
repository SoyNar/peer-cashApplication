package com.peercash.PeerCashproject.Service.IService;

import java.util.Map;

public interface IEmailService
{
     void sendEmail(String to, String subject,String templatename, Map<String, Object> body);
}
