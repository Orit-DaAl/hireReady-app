package com.hireReady.jobinterviewservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfig {
	@Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    
    @Value("${rabbitmq.queue.name}")
    private String queue;
    
    @Value("${rabbitmq.queue.statusUpdateName}")
    private String statusQueueName;

    @Value("${rabbitmq.routing.statusUpdateKey}")
    private String statusRoutingKey;
    
	@Bean
	public Queue interviewQueue() {
		return new Queue(queue, true);
	}
	
	@Bean
    public Queue statusUpdateQueue() {
        return new Queue(statusQueueName, true);
    }
	
	@Bean
    public DirectExchange interviewExchange() {
        return new DirectExchange(exchange);
    }

	
	@Bean
    public Binding interviewBinding(Queue interviewQueue, DirectExchange interviewExchange) {
        return BindingBuilder.bind(interviewQueue).to(interviewExchange).with(routingKey);
    }
	
	@Bean
    public Binding statusUpdateBinding() {
        return BindingBuilder.bind(statusUpdateQueue()).to(interviewExchange()).with(statusRoutingKey);
    }
	
	
	@Bean
	public MessageConverter jsonConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
