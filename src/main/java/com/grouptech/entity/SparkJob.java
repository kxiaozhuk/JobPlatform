package com.grouptech.entity;

import com.grouptech.util.Constant;
import com.grouptech.util.ISRRunnable;
import org.apache.spark.launcher.SparkLauncher;
import org.quartz.*;

/**
 * @author mahone on 2016/12/27.
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class SparkJob implements Job{
    private Process process;

    public Process getProcess() {
        return process;
    }

    public SparkJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String sparkMode = dataMap.getString("spark_mode");
        String jarPath = dataMap.getString("jar_path");
        String mainClass = dataMap.getString("main_class");
        String appArgs = dataMap.getString("app_args");

        SparkLauncher launcher = new SparkLauncher();
        launcher.setMaster(sparkMode)
                .setAppResource(jarPath) // Specify user app jar path
                .setMainClass(mainClass);

        if (appArgs.length() > 0) {
            String[] list = appArgs.split(",");
            // Set app args
            launcher.addAppArgs(list);
        }

        // Launch the app
        try {
            process = launcher.launch();
            Constant.logger.info("Hello World! SparkJob is executing.");
            // Get Spark driver log
            new Thread(new ISRRunnable(process.getErrorStream())).start();
            int exitCode = process.waitFor();
            Constant.logger.info("Finished! Exit code is " + exitCode);
        } catch (Exception e){
            Constant.logger.error("SparkJob process launch error.",e);
        }

    }
}
