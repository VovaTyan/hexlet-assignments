package exercise;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;

@Configuration
public class RabbitMqConfig {

    // BEGIN
    @Bean
    Queue queue() {
        return new Queue("queue", false);
    }

    // Создаём обменник с именем "exchange"
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    // Подключаем очередь с именем (топиком) "queue" к обменнику "exchange"
    // Сообщения с ключом "key" будут направлены в очередь "queue"
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("key");
    }
    // END
}
