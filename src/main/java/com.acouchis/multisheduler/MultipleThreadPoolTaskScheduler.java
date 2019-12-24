package com.acouchis.multisheduler;

import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.scheduling.support.TaskUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by gaopeng09 on 2019-12-23
 */
public class MultipleThreadPoolTaskScheduler implements TaskScheduler {
    private volatile Map<String, ScheduledExecutorService> scheduledExecutorServiceMap = new HashMap<>();


    @Override
    public ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
        ScheduledExecutorService scheduledExecutor = getScheduledExecutor(task);
        return new ReschedulingRunnable(task, trigger, scheduledExecutor, TaskUtils.getDefaultErrorHandler(true)).schedule();
    }

    private Runnable defaultErrorHandlingTask(Runnable task, boolean isRepeatingTask) {
        return TaskUtils.decorateTaskWithErrorHandler(task, null, isRepeatingTask);
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Date startTime) {
        ScheduledExecutorService executor = getScheduledExecutor(task);
        long initialDelay = startTime.getTime() - System.currentTimeMillis();
        try {
            return executor.schedule(defaultErrorHandlingTask(task, false), initialDelay, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Date startTime, long period) {
        ScheduledExecutorService executor = getScheduledExecutor(task);
        long initialDelay = startTime.getTime() - System.currentTimeMillis();
        try {
            return executor.scheduleAtFixedRate(defaultErrorHandlingTask(task, true), initialDelay, period, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long period) {
        ScheduledExecutorService executor = getScheduledExecutor(task);
        try {
            return executor.scheduleAtFixedRate(defaultErrorHandlingTask(task, true), 0, period, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Date startTime, long delay) {
        ScheduledExecutorService executor = getScheduledExecutor(task);
        long initialDelay = startTime.getTime() - System.currentTimeMillis();
        try {
            return executor.scheduleWithFixedDelay(defaultErrorHandlingTask(task, true), initialDelay, delay, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, long delay) {
        ScheduledExecutorService executor = getScheduledExecutor(task);
        try {
            return executor.scheduleWithFixedDelay(defaultErrorHandlingTask(task, true), 0, delay, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }


    private synchronized ScheduledExecutorService getScheduledExecutor(Runnable runnable) {

        final int[] size = {1};
        String name = "";
        if (runnable instanceof ScheduledMethodRunnable) {

            ScheduledThreadPool annotation =
                    ((ScheduledMethodRunnable) runnable).getMethod().getDeclaredAnnotation(ScheduledThreadPool.class);

            if (null != annotation) {
                name = annotation.name();
                size[0] = annotation.size();
            }
        }
        if (scheduledExecutorServiceMap.containsKey(name)) {
            return scheduledExecutorServiceMap.get(name);
        }


        return scheduledExecutorServiceMap.computeIfAbsent(name, t -> new ScheduledThreadPoolExecutor(size[0]));

    }

}
