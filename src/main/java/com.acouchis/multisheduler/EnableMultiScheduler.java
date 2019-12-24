package com.acouchis.multisheduler;

import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@EnableScheduling
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(MultipleThreadPoolScheduledConfiguration.class)
public @interface EnableMultiScheduler {
}
