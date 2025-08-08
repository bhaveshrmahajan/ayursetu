package com.ayursetu.notification.services;

import com.ayursetu.notification.dto.SmsRequestDto;
import com.ayursetu.notification.exception.NotificationFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsServiceImpl implements SmsService {
    
    private final RestTemplate restTemplate;
    
    @Value("${sms.gateway.twilio.account-sid}")
    private String twilioAccountSid;
    
    @Value("${sms.gateway.twilio.auth-token}")
    private String twilioAuthToken;
    
    @Value("${sms.gateway.twilio.from-number}")
    private String twilioFromNumber;
    
    @Value("${sms.gateway.aws.access-key}")
    private String awsAccessKey;
    
    @Value("${sms.gateway.aws.secret-key}")
    private String awsSecretKey;
    
    @Value("${sms.gateway.aws.region}")
    private String awsRegion;
    
    @Value("${sms.gateway.provider:twilio}")
    private String smsProvider;
    
    @Override
    public void sendSms(SmsRequestDto smsRequest) {
        try {
            if (!validatePhoneNumber(smsRequest.getPhoneNumber())) {
                throw new NotificationFailedException("Invalid phone number: " + smsRequest.getPhoneNumber());
            }
            
            switch (smsProvider.toLowerCase()) {
                case "twilio":
                    sendSmsViaTwilio(smsRequest);
                    break;
                case "aws":
                    sendSmsViaAws(smsRequest);
                    break;
                default:
                    throw new NotificationFailedException("Unsupported SMS provider: " + smsProvider);
            }
            
            log.info("SMS sent successfully to: {}", smsRequest.getPhoneNumber());
            
        } catch (Exception e) {
            log.error("Failed to send SMS to: {}", smsRequest.getPhoneNumber(), e);
            throw new NotificationFailedException("SMS sending failed: " + e.getMessage());
        }
    }
    
    @Override
    public void sendBulkSms(SmsRequestDto[] smsRequests) {
        for (SmsRequestDto smsRequest : smsRequests) {
            try {
                sendSms(smsRequest);
            } catch (Exception e) {
                log.error("Failed to send bulk SMS to: {}", smsRequest.getPhoneNumber(), e);
                // Continue with other SMS even if one fails
            }
        }
    }
    
    @Override
    public void sendTemplatedSms(SmsRequestDto smsRequest) {
        try {
            if (smsRequest.getTemplateId() == null) {
                throw new NotificationFailedException("Template ID is required for templated SMS");
            }
            
            // Process template with data
            String processedMessage = processTemplate(smsRequest.getTemplateId(), smsRequest.getTemplateData());
            smsRequest.setMessage(processedMessage);
            
            sendSms(smsRequest);
            
        } catch (Exception e) {
            log.error("Failed to send templated SMS to: {}", smsRequest.getPhoneNumber(), e);
            throw new NotificationFailedException("Templated SMS sending failed: " + e.getMessage());
        }
    }
    
    @Override
    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        
        // Remove any non-digit characters except +
        String cleanedNumber = phoneNumber.replaceAll("[^\\d+]", "");
        
        // Basic phone number validation
        return cleanedNumber.matches("^\\+?[1-9]\\d{1,14}$");
    }
    
    @Override
    public void sendOtpSms(String phoneNumber, String otp) {
        SmsRequestDto smsRequest = new SmsRequestDto();
        smsRequest.setPhoneNumber(phoneNumber);
        smsRequest.setMessage("Your AyurSetu OTP is: " + otp + ". Valid for 10 minutes. Do not share this OTP.");
        smsRequest.setTemplateId("otp-template");
        smsRequest.setTemplateData(Map.of("otp", otp));
        
        sendTemplatedSms(smsRequest);
    }
    
    @Override
    public void sendAppointmentReminderSms(String phoneNumber, String appointmentDetails) {
        SmsRequestDto smsRequest = new SmsRequestDto();
        smsRequest.setPhoneNumber(phoneNumber);
        smsRequest.setMessage("Reminder: Your appointment is scheduled for " + appointmentDetails + ". AyurSetu");
        smsRequest.setTemplateId("appointment-reminder");
        smsRequest.setTemplateData(Map.of("appointmentDetails", appointmentDetails));
        
        sendTemplatedSms(smsRequest);
    }
    
    @Override
    public void sendOrderStatusSms(String phoneNumber, String orderStatus) {
        SmsRequestDto smsRequest = new SmsRequestDto();
        smsRequest.setPhoneNumber(phoneNumber);
        smsRequest.setMessage("Your order status: " + orderStatus + ". Track your order at ayursetu.com. AyurSetu");
        smsRequest.setTemplateId("order-status");
        smsRequest.setTemplateData(Map.of("orderStatus", orderStatus));
        
        sendTemplatedSms(smsRequest);
    }
    
    @Override
    public void sendPaymentReminderSms(String phoneNumber, String paymentDetails) {
        SmsRequestDto smsRequest = new SmsRequestDto();
        smsRequest.setPhoneNumber(phoneNumber);
        smsRequest.setMessage("Payment reminder: " + paymentDetails + ". Complete payment at ayursetu.com. AyurSetu");
        smsRequest.setTemplateId("payment-reminder");
        smsRequest.setTemplateData(Map.of("paymentDetails", paymentDetails));
        
        sendTemplatedSms(smsRequest);
    }
    
    @Override
    public void sendEmergencySms(String phoneNumber, String emergencyMessage) {
        SmsRequestDto smsRequest = new SmsRequestDto();
        smsRequest.setPhoneNumber(phoneNumber);
        smsRequest.setMessage("EMERGENCY: " + emergencyMessage + ". Contact emergency services immediately. AyurSetu");
        smsRequest.setPriority(true);
        
        sendSms(smsRequest);
    }
    
    // Private helper methods
    private void sendSmsViaTwilio(SmsRequestDto smsRequest) {
        try {
            String url = "https://api.twilio.com/2010-04-01/Accounts/" + twilioAccountSid + "/Messages.json";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(twilioAccountSid, twilioAuthToken);
            headers.set("Content-Type", "application/x-www-form-urlencoded");
            
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("To", smsRequest.getPhoneNumber());
            requestBody.put("From", twilioFromNumber);
            requestBody.put("Body", smsRequest.getMessage());
            
            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("SMS sent via Twilio to: {}", smsRequest.getPhoneNumber());
            } else {
                throw new NotificationFailedException("Twilio API returned error: " + response.getStatusCode());
            }
            
        } catch (Exception e) {
            log.error("Twilio SMS sending failed", e);
            throw new NotificationFailedException("Twilio SMS sending failed: " + e.getMessage());
        }
    }
    
    private void sendSmsViaAws(SmsRequestDto smsRequest) {
        try {
            // AWS SNS implementation would go here
            // This is a simplified version - in production, you'd use AWS SDK
            String url = "https://sns." + awsRegion + ".amazonaws.com/";
            
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/x-www-form-urlencoded");
            // Add AWS authentication headers here
            
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("Action", "Publish");
            requestBody.put("Message", smsRequest.getMessage());
            requestBody.put("PhoneNumber", smsRequest.getPhoneNumber());
            
            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("SMS sent via AWS SNS to: {}", smsRequest.getPhoneNumber());
            } else {
                throw new NotificationFailedException("AWS SNS API returned error: " + response.getStatusCode());
            }
            
        } catch (Exception e) {
            log.error("AWS SNS SMS sending failed", e);
            throw new NotificationFailedException("AWS SNS SMS sending failed: " + e.getMessage());
        }
    }
    
    private String processTemplate(String templateId, Map<String, Object> templateData) {
        // Simple template processing - in production, use a proper template engine
        String template = getTemplateById(templateId);
        
        if (templateData != null) {
            for (Map.Entry<String, Object> entry : templateData.entrySet()) {
                template = template.replace("{{" + entry.getKey() + "}}", entry.getValue().toString());
            }
        }
        
        return template;
    }
    
    private String getTemplateById(String templateId) {
        // In production, this would fetch from database or configuration
        Map<String, String> templates = Map.of(
            "otp-template", "Your AyurSetu OTP is: {{otp}}. Valid for 10 minutes. Do not share this OTP.",
            "appointment-reminder", "Reminder: Your appointment is scheduled for {{appointmentDetails}}. AyurSetu",
            "order-status", "Your order status: {{orderStatus}}. Track your order at ayursetu.com. AyurSetu",
            "payment-reminder", "Payment reminder: {{paymentDetails}}. Complete payment at ayursetu.com. AyurSetu"
        );
        
        return templates.getOrDefault(templateId, "Default template message");
    }
}






