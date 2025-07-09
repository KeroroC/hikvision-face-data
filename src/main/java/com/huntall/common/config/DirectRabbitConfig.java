package com.huntall.common.config;

import com.huntall.common.constant.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置
 * @author wangpeng
 * @since 2025年07月09日 15:51
 */
@Configuration
public class DirectRabbitConfig {

    /**
     * 队列
     * @return bean
     */
    @Bean
    public Queue HikIdCardQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参数优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        return new Queue(RabbitConstant.HIK_QUEUE_NAME, true, false, false);
    }

    /**
     * 直连交换机
     * @return bean
     */
    @Bean
    public DirectExchange HikDirectExchange() {
        return new DirectExchange(RabbitConstant.HIK_EXCHANGE_NAME, true, false);
    }

    /**
     * 队列和交换机绑定，并设置路由
     * @return bean
     */
    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(HikIdCardQueue()).to(HikDirectExchange()).with(RabbitConstant.HIK_ID_CARD_ROUTE_KEY);
    }

    /**
     * 自行配置消息转换器，不使用默认的反序列化，避免安全漏洞
     * @return bean
     */
    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
