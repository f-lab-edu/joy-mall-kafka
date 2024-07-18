package com.mini.joymall.sale.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mini.joymall.order.dto.OrderItemDTO;
import com.mini.joymall.order.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class SalesProductListener {
    private final RedissonClient redissonClient;
    private final SalesProductService salesProductService;
    private final Gson gson = new Gson();

    private static final String LOCK_KEY_PREFIX = "salesProduct:";

    @KafkaListener(topics = "new-stock-decrease", groupId = "stock-decrease-group")
    public void decreaseStock(ConsumerRecord<String, String> record) {
        Type orderItemSetType = new TypeToken<List<OrderItemDTO>>(){}.getType();
        List<OrderItemDTO> orderItems = gson.fromJson(record.value(), orderItemSetType);
        for (OrderItemDTO orderItemDTO : orderItems) {
            String lockKey = LOCK_KEY_PREFIX + orderItemDTO.getSalesProductId();
            RLock lock = redissonClient.getLock(lockKey);

            try {
                boolean acquireLock = lock.tryLock(10, 1, TimeUnit.SECONDS);

                if (!acquireLock) {
                    throw new RuntimeException("SalesProduct Lock 획득 실패");
                }
                salesProductService.decreaseStock(orderItemDTO);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Lock 획득 중 인터럽트 발생");
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
    }
}
