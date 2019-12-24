package com.acouchis.multisheduler;

import org.springframework.core.annotation.AliasFor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Scheduled

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface ScheduledThreadPool {
    @AliasFor("value")
    String name() default "";

    @AliasFor("name")
    String value() default "default";

    int size() default 1;


    String CRON_DISABLED = ScheduledTaskRegistrar.CRON_DISABLED;

    @AliasFor(annotation = Scheduled.class, attribute = "cron")
    String cron() default "";

    @AliasFor(annotation = Scheduled.class, attribute = "zone")
    String zone() default "";

    @AliasFor(annotation = Scheduled.class, attribute = "fixedDelay")
    long fixedDelay() default -1;

    @AliasFor(annotation = Scheduled.class, attribute = "fixedDelayString")
    String fixedDelayString() default "";

    @AliasFor(annotation = Scheduled.class, attribute = "fixedRate")
    long fixedRate() default -1;

    @AliasFor(annotation = Scheduled.class, attribute = "fixedRateString")
    String fixedRateString() default "";

    @AliasFor(annotation = Scheduled.class, attribute = "initialDelay")
    long initialDelay() default -1;

    @AliasFor(annotation = Scheduled.class, attribute = "initialDelayString")
    String initialDelayString() default "";
}

