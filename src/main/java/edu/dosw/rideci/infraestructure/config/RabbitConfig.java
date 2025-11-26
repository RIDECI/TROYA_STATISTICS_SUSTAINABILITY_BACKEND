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
    public static final String STADISTICS_CREATED_QUEUE = "stadistics.sync.queue";

    //------------------------------------
    //Conexion con Profile
    public static final String PROFILE_CREATED_ROUTING_KEY = "profile.created";
    public static final String EXCHANGE_PROFILE = "profile.exchange";
    //------------------------------------
    //Conexion con Travel
    public static final String TRAVEL_CREATED_ROUTING_KEY = "travel.created";
    public static final String EXCHANGE_TRAVEL = "travel.exchange";


    @Bean
    public Queue statisticsCreatedQueue() {
        return new Queue(STADISTICS_CREATED_QUEUE, true);
    }

    //-----------------------------------------------------------------
    //Conexion con microservicio de Profile
    @Bean
    public TopicExchange profileExchange() {
        return new TopicExchange(EXCHANGE_PROFILE, true, false);
    }
    
    @Bean
    public Binding bindingProfileCreated(Queue stadisticsCreatedQueue, TopicExchange profileExchange) {
        return BindingBuilder.bind(stadisticsCreatedQueue).to(profileExchange).with(PROFILE_CREATED_ROUTING_KEY);
    }
    //-----------------------------------------------------------------

    //-----------------------------------------------------------------
    //Conexion con microservicio de Travel
    @Bean
    public TopicExchange travelExchange() {
        return new TopicExchange(EXCHANGE_TRAVEL, true, false);
    }
    
    @Bean
    public Binding bindingTravelCreated(Queue stadisticsCreatedQueue, TopicExchange travelExchange) {
        return BindingBuilder.bind(stadisticsCreatedQueue).to(travelExchange).with(TRAVEL_CREATED_ROUTING_KEY);
    }
    //-----------------------------------------------------------------

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}   