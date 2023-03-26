package org.karolina.account.application;

import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.karolina.account.application.port.NotificationServiceUseCase;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountNotificationService implements NotificationServiceUseCase {
    private final MqttClient mqttClient;
    public void sendNotification(String messageContent, long accountId) {
        String topic = "bank/accounts/" + accountId + "/notifications";
        MqttMessage message = new MqttMessage(messageContent.getBytes());
        message.setQos(2);
        try {
            mqttClient.publish(topic, message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
