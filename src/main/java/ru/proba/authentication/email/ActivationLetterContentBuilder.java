package ru.proba.authentication.email;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ActivationLetterContentBuilder {
    public String buildActivationLetterHTML(String activationLink){
        return "<html>"
                + "<body style='font-family: Arial, sans-serif;'>"
                + "<h2 style='color: #333;'>Добро пожаловать в сервис Проба!</h2>"
                + "<p>Нажмите кнопку ниже для активации аккаунта:</p>"
                + "<a href='" + activationLink + "' "
                + "style='background-color: #4CAF50; color: white; padding: 10px 20px; "
                + "text-decoration: none; border-radius: 5px; display: inline-block;'>"
                + "Активировать аккаунт</a>"
                + "<p style='margin-top: 20px; color: #666; font-size: 12px;'>"
                + "Если Вы не запрашивали активацию - игнорируйте данное письмо.</p>"
                + "<p style='color: #666; font-size: 12px;'>Или скопируйте и вставьте в браузер ссылку:<br>"
                + "<a href='" + activationLink + "'>" + activationLink + "</a></p>"
                + "</body>"
                + "</html>";
    }
}
