package edu.dosw.rideci.infraestructure.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.MessageConverter;



@Configuration
public class RabbitConfig {
    public static final String STATISTICS_CREATED_QUEUE = "stadistics.sync.queue";

    public static final String STATISTICS_TRAVEL_QUEUE =  "statistics.sync.travel.queue";

    //------------------------------------
    //Conexion con Profile
    public static final String PROFILE_CREATED_ROUTING_KEY = "profile.created";
    public static final String EXCHANGE_PROFILE = "profile.exchange";
    //------------------------------------
    //Conexion con Travel
    public static final String TRAVEL_CREATED_ROUTING_KEY = "travel.completed";
    public static final String EXCHANGE_TRAVEL = "travel.exchange";


    // ------------------- Colas -------------------
    @Bean
    public Queue statisticsCreatedQueue() {
        return new Queue(STATISTICS_CREATED_QUEUE, true);
    }

    @Bean
    public Queue travelCompletedQueue() {
        return new Queue(STATISTICS_TRAVEL_QUEUE, true);
    }

    // ------------------- Exchanges -------------------
    //profile
    @Bean
    public TopicExchange profileExchange() {
        return new TopicExchange(EXCHANGE_PROFILE, true, false);
    }

    //travel
    @Bean
    public TopicExchange travelExchange() {
        return new TopicExchange(EXCHANGE_TRAVEL, true, false);
    }

    // ------------------- Bindings -------------------
    // Profile created
    @Bean
    public Binding bindingProfileCreated(Queue statisticsCreatedQueue, TopicExchange profileExchange) {
        return BindingBuilder.bind(statisticsCreatedQueue)
                .to(profileExchange)
                .with(PROFILE_CREATED_ROUTING_KEY);
    }

    // Travel completed
    @Bean
    public Binding bindingTravelCompleted(Queue travelCompletedQueue, TopicExchange travelExchange) {
        return BindingBuilder.bind(travelCompletedQueue)
                .to(travelExchange)
                .with("TRAVEL_CREATED_ROUTING_KEY");
    }

    // ------------------- Message Converter -------------------
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}   