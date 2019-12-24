# MultipleThreadPoolTaskScheduler
Spring-based multi-threaded pool timing task solution

可以使用该组件，标记不同的定时任务使用不同的线程池

- 资源细粒度管理
- 防止单一线程被某些重量任务耗尽
- 防止单一线程中其他任务被blocked
