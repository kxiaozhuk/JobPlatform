package com.touchstone;

import com.touchstone.dao.JobDao;
import com.touchstone.entity.JobInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@WebAppConfiguration
public class SparkJobPlatformApplicationTests {
    private Logger logger = LoggerFactory.getLogger("touchstone");

    @Autowired
    private JobDao dao;

	@Test
	@Rollback
    public void testInsert(){
        JobInfo job = new JobInfo();
        job.setJobId(UUID.randomUUID().toString().replace("-",""));
        job.setJobNm("new spark job 001");
        job.setJobTyp("spark");
        job.setCreNm("zwh");
        job.setJobVer("1.0.0");
        job.setJarPath("/opt/touchstone/DWA/spark/ml/MachineLearning.jar");
        job.setSparkMode("yarn-client");
        job.setMainClass("com.touchstone.ml.spark.test");
        job.setAppArgs("ML201608290001");

        dao.updateJobEndSts("J201612290001",4);
        Assert.assertEquals(1,dao.insertJob(job));
        Assert.assertNotNull(dao.selectJobById(job.getJobId()));
    }

    @Test
    public void testSelectById(){

    }

}
