package com.touchstone.listener;

import com.touchstone.service.PlatformService;
import com.touchstone.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 服务启动执行初始化
 * @author zhuwenhong on 2016/12/29.
 */
@Component
public class StartupRunner implements CommandLineRunner {
    @Autowired
    PlatformService pfService;

    @Override
    public void run(String... args) throws Exception {
        Constant.logger.info("[StartupRunner] Service start running, scheduler begin initial.");
        try {
            pfService.initial();
        } catch (Exception e){
            Constant.logger.error("[StartupRunner] Scheduler initial failed.");
            throw new RuntimeException(e);
        }
    }
}
