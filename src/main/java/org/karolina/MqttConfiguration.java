package org.karolina;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MqttConfiguration {
    @Value("${mosquitto.uri}")
    private String serverUri;

    @Value("${mosquitto.user}")
    private String username;

    @Value("${mosquitto.password}")
    private String password;

    public MqttPahoClientFactory mqttClientFactory() {
        var factory = new DefaultMqttPahoClientFactory();
        var options = getMqttConnectOptions();
        factory.setConnectionOptions(options);
        return factory;
    }

    private MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{serverUri});
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setCleanSession(true);
        return options;
    }

    @Bean
    public MqttClient mqttClient() throws MqttException {
        var options = getMqttConnectOptions();

        var mqttClient = new MqttClient(serverUri, "clientId", new MemoryPersistence());
        mqttClient.connect(options);
        return mqttClient;
    }

    @Bean
    public MessageChannel mqttOutBoundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutBoundChannel")
    public MessageHandler mqttOutBound(MqttClient mqttClient) {
        var messageHandler = new MqttPahoMessageHandler("serverOut", mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic("#");
        return messageHandler;
    }
}
