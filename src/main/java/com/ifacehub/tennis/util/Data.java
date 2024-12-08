package com.ifacehub.tennis.util;

public class Data {
	public static String getMessageBody(String otp,String name) {
        return "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "  <meta charset=\"UTF-8\">" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "  <title>OTP Verification</title>" +
                "  <style>" +
                "    body {" +
                "      font-family: Arial, sans-serif;" +
                "      background-color: #f4f4f4;" +
                "      margin: 0;" +
                "      padding: 0;" +
                "    }" +
                "    .email-container {" +
                "      max-width: 600px;" +
                "      margin: 20px auto;" +
                "      background-color: #ffffff;" +
                "      border: 1px solid #ddd;" +
                "      border-radius: 8px;" +
                "      overflow: hidden;" +
                "      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);" +
                "    }" +
                "    .email-header {" +
                "      background-color: #007BFF;" +
                "      color: #ffffff;" +
                "      text-align: center;" +
                "      padding: 20px;" +
                "      font-size: 24px;" +
                "    }" +
                "    .email-body {" +
                "      padding: 20px;" +
                "      text-align: center;" +
                "    }" +
                "    .otp-code {" +
                "      font-size: 32px;" +
                "      font-weight: bold;" +
                "      color: #333333;" +
                "      margin: 20px 0;" +
                "      letter-spacing: 4px;" +
                "    }" +
                "    .email-footer {" +
                "      background-color: #f8f9fa;" +
                "      text-align: center;" +
                "      padding: 10px;" +
                "      font-size: 12px;" +
                "      color: #888888;" +
                "    }" +
                "    .button {" +
                "      display: inline-block;" +
                "      margin: 20px 0;" +
                "      padding: 10px 20px;" +
                "      font-size: 16px;" +
                "      color: #ffffff;" +
                "      background-color: #007BFF;" +
                "      text-decoration: none;" +
                "      border-radius: 4px;" +
                "    }" +
                "    .button:hover {" +
                "      background-color: #0056b3;" +
                "    }" +
                "    .small-text {" +
                "      font-size: 14px;" +
                "      color: #555555;" +
                "    }" +
                "  </style>" +
                "</head>" +
                "<body>" +
                "  <div class=\"email-container\">" +
                "    <div class=\"email-header\">" +
                "      OTP Verification" +
                "    </div>" +
                "    <div class=\"email-body\">" +
                "      <p>Dear "+name+",</p>" +
                "      <p>Please use the following One-Time Password (OTP) to verify your account:</p>" +
                "      <div class=\"otp-code\">123456</div>" +
                "      <p class=\"small-text\">This OTP is valid for the next 10 minutes.</p>" +
                "      <a href=\"#\" class=\"button\">Verify Now</a>" +
                "      <p class=\"small-text\">If you didn’t request this OTP, please ignore this email.</p>" +
                "    </div>" +
                "    <div class=\"email-footer\">" +
                "      &copy; 2024 Your Company Name. All rights reserved." +
                "    </div>" +
                "  </div>" +
                "</body>" +
                "</html>";
    }
}
