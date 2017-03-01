drop table if exists dwadb.dwa_spark_job_info;

/*==============================================================*/
/* Table: job_info                                              */
/*==============================================================*/
create table dwadb.dwa_spark_job_info
(
  job_id char(32) NOT NULL DEFAULT '' COMMENT '作业ID',
  job_nm varchar(100) NOT NULL DEFAULT '' COMMENT '作业名称',
  cre_nm varchar(30) NOT NULL DEFAULT '' COMMENT '创建者',
  cre_dt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  job_typ varchar(10) NOT NULL DEFAULT '' COMMENT '作业类型',
  job_ver varchar(10) NOT NULL DEFAULT '' COMMENT '作业版本',
  jar_path varchar(500) NOT NULL DEFAULT '' COMMENT 'JAR路径',
  spark_mode varchar(20) NOT NULL DEFAULT '' COMMENT 'spark运行模式',
  main_class varchar(100) NOT NULL DEFAULT '' COMMENT 'spark主函数',
  app_args varchar(500) NOT NULL DEFAULT '' COMMENT '应用参数',
  begn_tm timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '开始时间',
  end_tm timestamp NULL DEFAULT NULL COMMENT '结束时间',
  -- cost_tms varchar(30) NOT NULL DEFAULT '' COMMENT '耗时',
  job_sts tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态',
  trg_id char(32) NOT NULL DEFAULT '' COMMENT '触发器ID',
  lst_mod_nm varchar(30) NOT NULL DEFAULT '' COMMENT '最后修改者',
  lst_mod_dt timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后修改日期',
   primary key (job_id)
);

alter table dwadb.dwa_spark_job_info comment 'Spark作业信息表';

drop table if exists dwadb.dwa_spark_trigger_info;

/*==============================================================*/
/* Table: job_info                                              */
/*==============================================================*/
create table dwadb.dwa_spark_trigger_info
(
  trg_id char(32) NOT NULL DEFAULT '' COMMENT '触发器ID',
  trg_nm varchar(100) NOT NULL DEFAULT '' COMMENT '触发器名称',
  cre_nm varchar(30) NOT NULL DEFAULT '' COMMENT '创建者',
  cre_dt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  run_cycl varchar(10) NOT NULL DEFAULT '' COMMENT '运行周期',
  run_day_tm time NOT NULL DEFAULT '00:00:00' COMMENT '每天几时分运行',
  run_day_week tinyint(4) NOT NULL DEFAULT '0' COMMENT '每周几运行',
  run_day_mon tinyint(4) NOT NULL DEFAULT '0' COMMENT '每月几号运行',
  begn_dt date NOT NULL DEFAULT '0000-00-00' COMMENT '开始运行日期',
  end_dt date NOT NULL DEFAULT '9999-12-31' COMMENT '结束运行日期',
  skip_dt date NOT NULL DEFAULT '1970-01-01' COMMENT '不运行日期',
  trg_sts tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态',
  lst_mod_nm varchar(30) NOT NULL DEFAULT '' COMMENT '最后修改者',
  lst_mod_dt timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后修改日期',
   primary key (trg_id)
);

alter table dwadb.dwa_spark_trigger_info comment 'Spark触发器信息表';

drop table if exists dwadb.dwa_spark_job_depender;

/*==============================================================*/
/* Table: job_info                                              */
/*==============================================================*/
create table dwadb.dwa_spark_job_depender
(
  job_id char(32) NOT NULL DEFAULT '' COMMENT '作业ID',
  dep_jid char(32) NOT NULL DEFAULT '' COMMENT '依赖作业ID',
  cre_nm varchar(30) NOT NULL DEFAULT '' COMMENT '创建者',
  cre_dt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  dep_sts tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态',
  skip_ind tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否跳过',
  lst_mod_nm varchar(30) NOT NULL DEFAULT '' COMMENT '最后修改者',
  lst_mod_dt timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后修改日期',
   primary key (job_id,dep_jid)
);

alter table dwadb.dwa_spark_job_depender comment 'Spark作业依赖表';

drop table if exists dwadb.dwa_log_info;

/*==============================================================*/
/* Table: log_info                                              */
/*==============================================================*/
create table dwadb.dwa_log_info
(
   id                   int not null auto_increment comment '����ID',
   cre_nm               varchar(30) not null default '' comment '������',
   log_tims             timestamp not null default CURRENT_TIMESTAMP comment 'ʱ���',
   flow_nm              varchar(30) not null default '' comment '����',
   id_no                char(32) not null default '' comment '��ʶID',
   mdu_nm               varchar(30) not null default '' comment 'ģ��',
   step_seq             int not null default 0 comment '���',
   log_lvl              varchar(30) not null default '' comment '����',
   log_cont             varchar(100) not null default '' comment '����',
   step_sts             varchar(10) not null default '' comment '״̬',
   primary key (id)
);

alter table dwadb.dwa_log_info comment '��־��¼��';
