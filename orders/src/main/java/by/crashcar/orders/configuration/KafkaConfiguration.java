package by.crashcar.orders.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic newTopic(@Value("${topic.create-order}") String createOrderTopic) {
        return new NewTopic(createOrderTopic, 1, (short) 1);
    }
}
