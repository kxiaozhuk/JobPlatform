package com.grouptech.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mahone on 2016/12/28.
 */
public class Constant {
    public static final String OWNER = "zwh";

    public static final String JOB_GROUP_NAME = "spark";

    public static Logger logger = LoggerFactory.getLogger("groupptech");

    public static final int JOB_RUN_STATUS = 3;

    public static final int JOB_FAIL_STATUS = -3;

    public static final int JOB_DONE_STATUS = 4;

    public static final int JOB_STOP_STATUS = -4;

}
