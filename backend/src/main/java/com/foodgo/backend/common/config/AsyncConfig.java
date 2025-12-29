package com.foodgo.backend.common.config;

import com.foodgo.backend.common.constant.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configuration for async processing
 * Enables async execution for non-blocking operations like notifications
 */
@Slf4j
@Configuration
@EnableAsync
public class AsyncConfig {

  /**
   * Thread pool executor for async notification processing
   */
  @Bean(name = "notificationExecutor")
  public Executor notificationExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(AppConstants.ASYNC_CORE_POOL_SIZE);
    executor.setMaxPoolSize(AppConstants.ASYNC_MAX_POOL_SIZE);
    executor.setQueueCapacity(AppConstants.ASYNC_QUEUE_CAPACITY);
    executor.setThreadNamePrefix("notification-");
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setAwaitTerminationSeconds(60);
    executor.initialize();
    
    log.info("Async notification executor initialized with corePoolSize={}, maxPoolSize={}, queueCapacity={}", 
        executor.getCorePoolSize(), executor.getMaxPoolSize(), executor.getQueueCapacity());
    
    return executor;
  }

  /**
   * Thread pool executor for general async tasks
   */
  @Bean(name = "taskExecutor")
  public Executor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(AppConstants.ASYNC_CORE_POOL_SIZE + 1); // Slightly larger for general tasks
    executor.setMaxPoolSize(AppConstants.ASYNC_MAX_POOL_SIZE * 2); // Double for general tasks
    executor.setQueueCapacity(AppConstants.ASYNC_QUEUE_CAPACITY * 2); // Double queue capacity
    executor.setThreadNamePrefix("async-task-");
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setAwaitTerminationSeconds(60);
    executor.initialize();
    
    log.info("Async task executor initialized with corePoolSize={}, maxPoolSize={}, queueCapacity={}", 
        executor.getCorePoolSize(), executor.getMaxPoolSize(), executor.getQueueCapacity());
    
    return executor;
  }
}

