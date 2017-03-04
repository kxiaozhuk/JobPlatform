package com.grouptech.util;

import com.grouptech.listener.SparkJobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import java.util.Properties;

/**
 * @author mahone on 2016/12/28.
 */
public class SchedulerUtils {
    private static StdSchedulerFactory gSchedulerFactory = new StdSchedulerFactory(); //创建全局调度对象

    public static Scheduler getScheduler(String owner) throws SchedulerException {
        Scheduler scheduler;
        scheduler = gSchedulerFactory.getScheduler(owner);
        if (scheduler == null) {
            Properties props = new Properties();
            props.put("org.quartz.scheduler.instanceName", owner);
            props.put("org.quartz.threadPool.threadCount", "10");
            gSchedulerFactory.initialize(props);
            scheduler = gSchedulerFactory.getScheduler();
        }
        return scheduler;
    }

    public static void startup(String owner) throws SchedulerException {
        Scheduler sched = getScheduler(owner);
        // 全局注册,所有Job都会起作用
        sched.getListenerManager().addJobListener(new SparkJobListener());
        if ( !sched.isStarted() ){
            sched.start();
        }
    }

    public static void shutdown(String owner) throws SchedulerException {
        Scheduler sched = getScheduler(owner);
        if ( !sched.isShutdown() ){
            sched.shutdown();
        }
    }

}
