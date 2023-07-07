/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zstd;

import com.github.luben.zstd.Zstd;

/**
 * Created by yexianxun@corp.netease.com on 2023/6/6.
 */
public class Demo {
    public static void main(String[] args) {
        String string = "qwerwrqiwer-- --SQL-- --********************************************************************---- --Author: 刘创-- --CreateTime: 2023-02-10 10:48:33-- -- sloth8?-- --********************************************************************---- SET 'table.dynamic-table-options.enabled'='true';-- SET 'execution.runtime-mode' = 'streaming';-- SET 'pipeline.operator-chaining' = 'false';-- CREATE CATALOG arctic_catalog WITH (--   'type'='arctic',--   'metastore.url'='thrift://10.196.85.29:18312/arctic_nisp',--   'default-database'='credit'-- );-- CREATE TABLE log_data (--     ts AS proctime() -- ) LIKE arctic_catalog.credit.`dws_yidun_account_rt_feature_min`;-- CREATE VIEW join_info AS-- SELECT--     log_data.product_id as log_productid, --     log_data.account as log_account, --     log_data.product_number as log_productnumber, --     log_data.product_name as log_productname, --     log_data.window_start as log_windowstart,--     log_data.`hour` as log_hour,--     dim_table.product_id, --     dim_table.account, --  dim_table.publish_hour,--  dim_table.uuid_set_cnt,--  dim_table.text_uuid_set_cnt,--  dim_table.img_uuid_set_cnt,--  dim_table.device_risk_score,--  dim_table.ip_risk_score,--  dim_table.ip_seq,--  dim_table.city_seq,--  dim_table.province_seq,--  dim_table.sender_seq,--  dim_table.receiver_seq,--  dim_table.text_seq,--  dim_table.img_md5_seq,--  dim_table.time_seq,--  dim_table.img_aspect_ratio_seq,--  dim_table.cv_adrisk_score_seq,--  dim_table.nlp_adrisk_score_seq,--  dim_table.cv_quality_score_seq,--  dim_table.cv_face_score_seq,--  dim_table.img_base64_score_seq,--  dim_table.img_ocr_score_seq,--  dim_table.text_score_seq,--  dim_table.window_start,--     PROCTIME() as row_time-- FROM -- log_data /*+OPTIONS('arctic.read.mode'='log','scan.startup.mode'='latest','properties.group.id'='fengxin-test-flink-read-arctic')*/-- INNER JOIN-- arctic_catalog.credit.`dws_yidun_account_rt_feature_min` --     /*+OPTIONS('lookup.cache.max-rows'='10000', 'rocksdb.writing-threads'='10')*/ --     FOR SYSTEM_TIME AS OF log_data.ts dim_table-- ON log_data.product_id = dim_table.product_id-- and log_data.account = dim_table.account-- WHERE dim_table.`hour` >= '2023-04-18-12';-- CREATE TABLE sink_print(--   log_productid bigint, --   log_account string,--   product_id bigint,--   account string,--   log_windowstart string,--   window_start string--  ) with('connector' = 'print');-- INSERT INTO sink_print select log_productid, log_account, product_id, account, log_windowstart, window_start from join_info;--SQL--********************************************************************----Author: 叶贤勋--CreateTime: 2023-03-23 16:13:42--Comment: 请输入业务注释信息-- sloth7:-- /home/sloth2/tmp_yxx/ne-flink-1.14/bin/flink run -d -m yarn-cluster -yqu root.sloth -ynm v2##sloth##arctic_dim -p 1 -yjm 4096 -ytm 4096 -ys 1 -yD yarn.tags unique-id@105094_sloth,job-id@105094,product@sloth,tag-group@dist,sys@sloth_SQL_yexianxun@corp.netease.com,105094_sloth_yexianxun@corp.netease.com_20230412164317449 -yD sloth.context.class com.netease.sloth.flink.sql.context.SlothContext -yD yarn.per-job-cluster.include-user-jar ORDER -yD classloader.resolve-order parent-first -yD pipeline.operator-chaining false -yD taskmanager.memory.managed.fraction 0 -yt /home/sloth2/submitter//sloth/yarnship/105094/zz_sloth -C file:///home/sloth2/tmp_yxx/icu4j-67.1.jar -c com.netease.sloth.flink.sql.NotebookEntryPoint /home/sloth2/tmp_yxx/plugin/plugin_ne-flink-1.14.0-1.0.7_scala2.12_hive2.1.1_arctic0.5.0-SNAPSHOT-release-3.9.4-1.4.7.jar --config.url http://10.196.84.148:8989/web/ops/v1/engine/plugin/job/callback/info?jobId=105094--********************************************************************--create catalog arctic with (    'type'='arctic',    'metastore.url'='thrift://10.196.98.23:18112/trino_online_env_hive'    -- 'arctic.version'='0.3.1'    );create table datagen(    id int,  --s_i_id    age int, --s_w_id    ts TIMESTAMP,    pt as proctime()) with (    'connector'='datagen', 'rows-per-second'='10'    ,'fields.id.min'='1'    ,'fields.id.max'='100'    ,'fields.age.min'='1'    ,'fields.age.max'='100');create table printt(-- s_w_id int -- ,-- s_i_id int   ,-- s_quantity int   ,-- s_ytd  decimal(16, 8)    ,-- s_order_cnt  int   ,-- s_remote_cnt int   ,-- s_data string    ,-- s_dist_01  string  , -- s_dist_02  string    ,-- s_dist_03  string    ,-- s_dist_04  string  ,-- s_dist_05  string    ,-- s_dist_06  string    ,-- s_dist_07  string  , -- s_dist_08  string    ,-- s_dist_09  string  , s_dist_10 string) with (    'connector'='print');insert into printt select -- d.s_w_id-- ,-- d.s_i_id,-- d.s_quantity,-- d.s_ytd,-- d.s_order_cnt,-- d.s_remote_cnt,-- d.s_data,-- d.s_dist_01,-- d.s_dist_02,-- d.s_dist_03,-- d.s_dist_04,-- d.s_dist_05,-- d.s_dist_06,-- d.s_dist_07,-- d.s_dist_08,-- d.s_dist_09,d.s_dist_10 from datagen a left join arctic.arctic_100w_0411_noupsert.stock /*+OPTIONS('lookup.cache.max-rows'='10003', 'rocksdb.auto-compactions'='false', 'rocksdb.writing-threads'='10')*/  for system_time as of a.pt d on a.id = d.s_i_id and a.age=d.s_w_id;-- --SQL-- --********************************************************************---- --Author: 刘创-- --CreateTime: 2023-02-10 10:48:33-- -- sloth8?-- --********************************************************************---- SET 'table.dynamic-table-options.enabled'='true';-- SET 'execution.runtime-mode' = 'streaming';-- SET 'pipeline.operator-chaining' = 'false';-- CREATE CATALOG arctic_catalog WITH (--   'type'='arctic',--   'metastore.url'='thrift://10.196.85.29:18312/arctic_nisp',--   'default-database'='credit'-- );-- CREATE TABLE log_data (--     ts AS proctime() -- ) LIKE arctic_catalog.credit.`dws_yidun_account_rt_feature_min`;-- CREATE VIEW join_info AS-- SELECT--     log_data.product_id as log_productid, --     log_data.account as log_account, --     log_data.product_number as log_productnumber, --     log_data.product_name as log_productname, --     log_data.window_start as log_windowstart,--     log_data.`hour` as log_hour,--     dim_table.product_id, --     dim_table.account, --  dim_table.publish_hour,--  dim_table.uuid_set_cnt,--  dim_table.text_uuid_set_cnt,--  dim_table.img_uuid_set_cnt,--  dim_table.device_risk_score,--  dim_table.ip_risk_score,--  dim_table.ip_seq,--  dim_table.city_seq,--  dim_table.province_seq,--  dim_table.sender_seq,--  dim_table.receiver_seq,--  dim_table.text_seq,--  dim_table.img_md5_seq,--  dim_table.time_seq,--  dim_table.img_aspect_ratio_seq,--  dim_table.cv_adrisk_score_seq,--  dim_table.nlp_adrisk_score_seq,--  dim_table.cv_quality_score_seq,--  dim_table.cv_face_score_seq,--  dim_table.img_base64_score_seq,--  dim_table.img_ocr_score_seq,--  dim_table.text_score_seq,--  dim_table.window_start,--     PROCTIME() as row_time-- FROM -- log_data /*+OPTIONS('arctic.read.mode'='log','scan.startup.mode'='latest','properties.group.id'='fengxin-test-flink-read-arctic')*/-- INNER JOIN-- arctic_catalog.credit.`dws_yidun_account_rt_feature_min` --     /*+OPTIONS('lookup.cache.max-rows'='10000', 'rocksdb.writing-threads'='10')*/ --     FOR SYSTEM_TIME AS OF log_data.ts dim_table-- ON log_data.product_id = dim_table.product_id-- and log_data.account = dim_table.account-- WHERE dim_table.`hour` >= '2023-04-18-12';-- CREATE TABLE sink_print(--   log_productid bigint, --   log_account string,--   product_id bigint,--   account string,--   log_windowstart string,--   window_start string--  ) with('connector' = 'print');-- INSERT INTO sink_print select log_productid, log_account, product_id, account, log_windowstart, window_start from join_info;--SQL--********************************************************************----Author: 叶贤勋--CreateTime: 2023-03-23 16:13:42--Comment: 请输入业务注释信息-- sloth7:-- /home/sloth2/tmp_yxx/ne-flink-1.14/bin/flink run -d -m yarn-cluster -yqu root.sloth -ynm v2##sloth##arctic_dim -p 1 -yjm 4096 -ytm 4096 -ys 1 -yD yarn.tags unique-id@105094_sloth,job-id@105094,product@sloth,tag-group@dist,sys@sloth_SQL_yexianxun@corp.netease.com,105094_sloth_yexianxun@corp.netease.com_20230412164317449 -yD sloth.context.class com.netease.sloth.flink.sql.context.SlothContext -yD yarn.per-job-cluster.include-user-jar ORDER -yD classloader.resolve-order parent-first -yD pipeline.operator-chaining false -yD taskmanager.memory.managed.fraction 0 -yt /home/sloth2/submitter//sloth/yarnship/105094/zz_sloth -C file:///home/sloth2/tmp_yxx/icu4j-67.1.jar -c com.netease.sloth.flink.sql.NotebookEntryPoint /home/sloth2/tmp_yxx/plugin/plugin_ne-flink-1.14.0-1.0.7_scala2.12_hive2.1.1_arctic0.5.0-SNAPSHOT-release-3.9.4-1.4.7.jar --config.url http://10.196.84.148:8989/web/ops/v1/engine/plugin/job/callback/info?jobId=105094--********************************************************************--create catalog arctic with (    'type'='arctic',    'metastore.url'='thrift://10.196.98.23:18112/trino_online_env_hive'    -- 'arctic.version'='0.3.1'    );create table datagen(    id int,  --s_i_id    age int, --s_w_id    ts TIMESTAMP,    pt as proctime()) with (    'connector'='datagen', 'rows-per-second'='10'    ,'fields.id.min'='1'    ,'fields.id.max'='100'    ,'fields.age.min'='1'    ,'fields.age.max'='100');create table printt(-- s_w_id int -- ,-- s_i_id int   ,-- s_quantity int   ,-- s_ytd  decimal(16, 8)    ,-- s_order_cnt  int   ,-- s_remote_cnt int   ,-- s_data string    ,-- s_dist_01  string  , -- s_dist_02  string    ,-- s_dist_03  string    ,-- s_dist_04  string  ,-- s_dist_05  string    ,-- s_dist_06  string    ,-- s_dist_07  string  , -- s_dist_08  string    ,-- s_dist_09  string  , s_dist_10 string) with (    'connector'='print');insert into printt select -- d.s_w_id-- ,-- d.s_i_id,-- d.s_quantity,-- d.s_ytd,-- d.s_order_cnt,-- d.s_remote_cnt,-- d.s_data,-- d.s_dist_01,-- d.s_dist_02,-- d.s_dist_03,-- d.s_dist_04,-- d.s_dist_05,-- d.s_dist_06,-- d.s_dist_07,-- d.s_dist_08,-- d.s_dist_09,d.s_dist_10 from datagen a left join arctic.arctic_100w_0411_noupsert.stock /*+OPTIONS('lookup.cache.max-rows'='10003', 'rocksdb.auto-compactions'='false', 'rocksdb.writing-threads'='10')*/  for system_time as of a.pt d on a.id = d.s_i_id and a.age=d.s_w_id;-- --SQL-- --********************************************************************---- --Author: 刘创-- --CreateTime: 2023-02-10 10:48:33-- -- sloth8?-- --********************************************************************---- SET 'table.dynamic-table-options.enabled'='true';-- SET 'execution.runtime-mode' = 'streaming';-- SET 'pipeline.operator-chaining' = 'false';-- CREATE CATALOG arctic_catalog WITH (--   'type'='arctic',--   'metastore.url'='thrift://10.196.85.29:18312/arctic_nisp',--   'default-database'='credit'-- );-- CREATE TABLE log_data (--     ts AS proctime() -- ) LIKE arctic_catalog.credit.`dws_yidun_account_rt_feature_min`;-- CREATE VIEW join_info AS-- SELECT--     log_data.product_id as log_productid, --     log_data.account as log_account, --     log_data.product_number as log_productnumber, --     log_data.product_name as log_productname, --     log_data.window_start as log_windowstart,--     log_data.`hour` as log_hour,--     dim_table.product_id, --     dim_table.account, --  dim_table.publish_hour,--  dim_table.uuid_set_cnt,--  dim_table.text_uuid_set_cnt,--  dim_table.img_uuid_set_cnt,--  dim_table.device_risk_score,--  dim_table.ip_risk_score,--  dim_table.ip_seq,--  dim_table.city_seq,--  dim_table.province_seq,--  dim_table.sender_seq,--  dim_table.receiver_seq,--  dim_table.text_seq,--  dim_table.img_md5_seq,--  dim_table.time_seq,--  dim_table.img_aspect_ratio_seq,--  dim_table.cv_adrisk_score_seq,--  dim_table.nlp_adrisk_score_seq,--  dim_table.cv_quality_score_seq,--  dim_table.cv_face_score_seq,--  dim_table.img_base64_score_seq,--  dim_table.img_ocr_score_seq,--  dim_table.text_score_seq,--  dim_table.window_start,--     PROCTIME() as row_time-- FROM -- log_data /*+OPTIONS('arctic.read.mode'='log','scan.startup.mode'='latest','properties.group.id'='fengxin-test-flink-read-arctic')*/-- INNER JOIN-- arctic_catalog.credit.`dws_yidun_account_rt_feature_min` --     /*+OPTIONS('lookup.cache.max-rows'='10000', 'rocksdb.writing-threads'='10')*/ --     FOR SYSTEM_TIME AS OF log_data.ts dim_table-- ON log_data.product_id = dim_table.product_id-- and log_data.account = dim_table.account-- WHERE dim_table.`hour` >= '2023-04-18-12';-- CREATE TABLE sink_print(--   log_productid bigint, --   log_account string,--   product_id bigint,--   account string,--   log_windowstart string,--   window_start string--  ) with('connector' = 'print');-- INSERT INTO sink_print select log_productid, log_account, product_id, account, log_windowstart, window_start from join_info;--SQL--********************************************************************----Author: 叶贤勋--CreateTime: 2023-03-23 16:13:42--Comment: 请输入业务注释信息-- sloth7:-- /home/sloth2/tmp_yxx/ne-flink-1.14/bin/flink run -d -m yarn-cluster -yqu root.sloth -ynm v2##sloth##arctic_dim -p 1 -yjm 4096 -ytm 4096 -ys 1 -yD yarn.tags unique-id@105094_sloth,job-id@105094,product@sloth,tag-group@dist,sys@sloth_SQL_yexianxun@corp.netease.com,105094_sloth_yexianxun@corp.netease.com_20230412164317449 -yD sloth.context.class com.netease.sloth.flink.sql.context.SlothContext -yD yarn.per-job-cluster.include-user-jar ORDER -yD classloader.resolve-order parent-first -yD pipeline.operator-chaining false -yD taskmanager.memory.managed.fraction 0 -yt /home/sloth2/submitter//sloth/yarnship/105094/zz_sloth -C file:///home/sloth2/tmp_yxx/icu4j-67.1.jar -c com.netease.sloth.flink.sql.NotebookEntryPoint /home/sloth2/tmp_yxx/plugin/plugin_ne-flink-1.14.0-1.0.7_scala2.12_hive2.1.1_arctic0.5.0-SNAPSHOT-release-3.9.4-1.4.7.jar --config.url http://10.196.84.148:8989/web/ops/v1/engine/plugin/job/callback/info?jobId=105094--********************************************************************--create catalog arctic with (    'type'='arctic',    'metastore.url'='thrift://10.196.98.23:18112/trino_online_env_hive'    -- 'arctic.version'='0.3.1'    );create table datagen(    id int,  --s_i_id    age int, --s_w_id    ts TIMESTAMP,    pt as proctime()) with (    'connector'='datagen', 'rows-per-second'='10'    ,'fields.id.min'='1'    ,'fields.id.max'='100'    ,'fields.age.min'='1'    ,'fields.age.max'='100');create table printt(-- s_w_id int -- ,-- s_i_id int   ,-- s_quantity int   ,-- s_ytd  decimal(16, 8)    ,-- s_order_cnt  int   ,-- s_remote_cnt int   ,-- s_data string    ,-- s_dist_01  string  , -- s_dist_02  string    ,-- s_dist_03  string    ,-- s_dist_04  string  ,-- s_dist_05  string    ,-- s_dist_06  string    ,-- s_dist_07  string  , -- s_dist_08  string    ,-- s_dist_09  string  , s_dist_10 string) with (    'connector'='print');insert into printt select -- d.s_w_id-- ,-- d.s_i_id,-- d.s_quantity,-- d.s_ytd,-- d.s_order_cnt,-- d.s_remote_cnt,-- d.s_data,-- d.s_dist_01,-- d.s_dist_02,-- d.s_dist_03,-- d.s_dist_04,-- d.s_dist_05,-- d.s_dist_06,-- d.s_dist_07,-- d.s_dist_08,-- d.s_dist_09,d.s_dist_10 from datagen a left join arctic.arctic_100w_0411_noupsert.stock /*+OPTIONS('lookup.cache.max-rows'='10003', 'rocksdb.auto-compactions'='false', 'rocksdb.writing-threads'='10')*/  for system_time as of a.pt d on a.id = d.s_i_id and a.age=d.s_w_id;-- --SQL-- --********************************************************************---- --Author: 刘创-- --CreateTime: 2023-02-10 10:48:33-- -- sloth8?-- --********************************************************************---- SET 'table.dynamic-table-options.enabled'='true';-- SET 'execution.runtime-mode' = 'streaming';-- SET 'pipeline.operator-chaining' = 'false';-- CREATE CATALOG arctic_catalog WITH (--   'type'='arctic',--   'metastore.url'='thrift://10.196.85.29:18312/arctic_nisp',--   'default-database'='credit'-- );-- CREATE TABLE log_data (--     ts AS proctime() -- ) LIKE arctic_catalog.credit.`dws_yidun_account_rt_feature_min`;-- CREATE VIEW join_info AS-- SELECT--     log_data.product_id as log_productid, --     log_data.account as log_account, --     log_data.product_number as log_productnumber, --     log_data.product_name as log_productname, --     log_data.window_start as log_windowstart,--     log_data.`hour` as log_hour,--     dim_table.product_id, --     dim_table.account, --  dim_table.publish_hour,--  dim_table.uuid_set_cnt,--  dim_table.text_uuid_set_cnt,--  dim_table.img_uuid_set_cnt,--  dim_table.device_risk_score,--  dim_table.ip_risk_score,--  dim_table.ip_seq,--  dim_table.city_seq,--  dim_table.province_seq,--  dim_table.sender_seq,--  dim_table.receiver_seq,--  dim_table.text_seq,--  dim_table.img_md5_seq,--  dim_table.time_seq,--  dim_table.img_aspect_ratio_seq,--  dim_table.cv_adrisk_score_seq,--  dim_table.nlp_adrisk_score_seq,--  dim_table.cv_quality_score_seq,--  dim_table.cv_face_score_seq,--  dim_table.img_base64_score_seq,--  dim_table.img_ocr_score_seq,--  dim_table.text_score_seq,--  dim_table.window_start,--     PROCTIME() as row_time-- FROM -- log_data /*+OPTIONS('arctic.read.mode'='log','scan.startup.mode'='latest','properties.group.id'='fengxin-test-flink-read-arctic')*/-- INNER JOIN-- arctic_catalog.credit.`dws_yidun_account_rt_feature_min` --     /*+OPTIONS('lookup.cache.max-rows'='10000', 'rocksdb.writing-threads'='10')*/ --     FOR SYSTEM_TIME AS OF log_data.ts dim_table-- ON log_data.product_id = dim_table.product_id-- and log_data.account = dim_table.account-- WHERE dim_table.`hour` >= '2023-04-18-12';-- CREATE TABLE sink_print(--   log_productid bigint, --   log_account string,--   product_id bigint,--   account string,--   log_windowstart string,--   window_start string--  ) with('connector' = 'print');-- INSERT INTO sink_print select log_productid, log_account, product_id, account, log_windowstart, window_start from join_info;--SQL--********************************************************************----Author: 叶贤勋--CreateTime: 2023-03-23 16:13:42--Comment: 请输入业务注释信息-- sloth7:-- /home/sloth2/tmp_yxx/ne-flink-1.14/bin/flink run -d -m yarn-cluster -yqu root.sloth -ynm v2##sloth##arctic_dim -p 1 -yjm 4096 -ytm 4096 -ys 1 -yD yarn.tags unique-id@105094_sloth,job-id@105094,product@sloth,tag-group@dist,sys@sloth_SQL_yexianxun@corp.netease.com,105094_sloth_yexianxun@corp.netease.com_20230412164317449 -yD sloth.context.class com.netease.sloth.flink.sql.context.SlothContext -yD yarn.per-job-cluster.include-user-jar ORDER -yD classloader.resolve-order parent-first -yD pipeline.operator-chaining false -yD taskmanager.memory.managed.fraction 0 -yt /home/sloth2/submitter//sloth/yarnship/105094/zz_sloth -C file:///home/sloth2/tmp_yxx/icu4j-67.1.jar -c com.netease.sloth.flink.sql.NotebookEntryPoint /home/sloth2/tmp_yxx/plugin/plugin_ne-flink-1.14.0-1.0.7_scala2.12_hive2.1.1_arctic0.5.0-SNAPSHOT-release-3.9.4-1.4.7.jar --config.url http://10.196.84.148:8989/web/ops/v1/engine/plugin/job/callback/info?jobId=105094--********************************************************************--create catalog arctic with (    'type'='arctic',    'metastore.url'='thrift://10.196.98.23:18112/trino_online_env_hive'    -- 'arctic.version'='0.3.1'    );create table datagen(    id int,  --s_i_id    age int, --s_w_id    ts TIMESTAMP,    pt as proctime()) with (    'connector'='datagen', 'rows-per-second'='10'    ,'fields.id.min'='1'    ,'fields.id.max'='100'    ,'fields.age.min'='1'    ,'fields.age.max'='100');create table printt(-- s_w_id int -- ,-- s_i_id int   ,-- s_quantity int   ,-- s_ytd  decimal(16, 8)    ,-- s_order_cnt  int   ,-- s_remote_cnt int   ,-- s_data string    ,-- s_dist_01  string  , -- s_dist_02  string    ,-- s_dist_03  string    ,-- s_dist_04  string  ,-- s_dist_05  string    ,-- s_dist_06  string    ,-- s_dist_07  string  , -- s_dist_08  string    ,-- s_dist_09  string  , s_dist_10 string) with (    'connector'='print');insert into printt select -- d.s_w_id-- ,-- d.s_i_id,-- d.s_quantity,-- d.s_ytd,-- d.s_order_cnt,-- d.s_remote_cnt,-- d.s_data,-- d.s_dist_01,-- d.s_dist_02,-- d.s_dist_03,-- d.s_dist_04,-- d.s_dist_05,-- d.s_dist_06,-- d.s_dist_07,-- d.s_dist_08,-- d.s_dist_09,d.s_dist_10 from datagen a left join arctic.arctic_100w_0411_noupsert.stock /*+OPTIONS('lookup.cache.max-rows'='10003', 'rocksdb.auto-compactions'='false', 'rocksdb.writing-threads'='10')*/  for system_time as of a.pt d on a.id = d.s_i_id and a.age=d.s_w_id;";
        System.out.println(string.getBytes().length);
        byte[] compressed = Zstd.compress(string.getBytes());
        System.out.println(compressed.length);
        String decompressed = new String(Zstd.decompress(compressed, string.getBytes().length));
        if (decompressed.equals(string)) {
            System.out.println("They are the same.");
        }
    }
}