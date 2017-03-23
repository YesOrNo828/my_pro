package com.my.esindex;

import com.alibaba.fastjson.JSONObject;
import com.my.mail.MailUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 监控EC产出
 * 指标：前一天产出量；本月产出量
 * 维度：电商媒体；商铺；商品；评论；爬取时间
 * Created by yexianxun on 2016/8/28.
 */
public class MonitorEcCount {
    private static final Logger logger = LoggerFactory.getLogger(MonitorEcCount.class);
    public static void main(String[] args) {
        try {
            List<Date> specialList = getSpecialTime(new Integer[]{0, 9, 12, 15, 18, 21});
            specialList.stream().forEach(date -> new Timer().schedule(new MyCountTimeTask(), date, 1000 * 60 * 60 * 24));
            logger.info("监控启动完成...");
        } catch (Exception e) {
            logger.error("System start failed due to {}", e.toString());
            e.printStackTrace();
        }

    }

    public static List<Date> getSpecialTime(Integer[] hours) {
        Calendar calendarN = Calendar.getInstance();
        calendarN.setTime(new Date());
        int hNow = calendarN.get(Calendar.HOUR_OF_DAY);
        int i = -1;
        for (Integer hour : hours) {
            if (hNow < hour) {
                break;
            }
            i++;
        }
        List<Date> specialList = new ArrayList<>();
        for (int j = 0; j < hours.length; j++) {
            Integer hour = hours[j];
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            if (0 != hour) {
                if (i >= j) {
                    calendar.add(Calendar.DATE, 1);
                }
                calendar.set(Calendar.HOUR_OF_DAY, hour);
            } else {
                calendar.add(Calendar.DATE, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
            }
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            specialList.add(calendar.getTime());
        }

        return specialList;
    }

    static class MyCountTimeTask extends TimerTask {
        private static String sendTo;

        static {
            try {
                if (StringUtils.isBlank(sendTo)) {
                    sendTo = FileUtils.readFileToString(new File(MonthIndex.class.getResource("/").getFile() + "sendTo.txt")).trim();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            MyCountTimeTask myCountTimeTask = new MyCountTimeTask();
            myCountTimeTask.run();

        }



        public MyCountTimeTask() {
        }

        public MyCountTimeTask(String sendTo) {
            this.sendTo = StringUtils.isNotBlank(sendTo) ? sendTo : this.sendTo;
        }

        @Override
        public void run() {
            logger.info("开始监控...");
            List<Map<String, String>> list = new ArrayList<>();
            list.add(queryCountByType(MonthIndex.gome));
            list.add(queryCountByType(MonthIndex.suning));
            list.add(queryCountByType(MonthIndex.mia));
            list.add(queryCountByType(MonthIndex.vip));
            list.add(queryCountByType(WeekIndex.jd));
            list.add(queryCountByType(WeekIndex.tmall));
            list.add(queryCountByType(WeekIndex.taobao));
            list.add(queryCountByType(MonthIndex.mogujie));
            list.add(queryCountByType(MonthIndex.meilishuo));
            try {
                String mailContent = FileUtils.readFileToString(new File(MonthIndex.class.getResource("/").getFile() + "mail.txt")).trim();
                for (int i = 0; i < list.size(); i++) {
                    for (Map.Entry<String, String> entry : list.get(i).entrySet()) {
                        mailContent = mailContent.replaceAll(entry.getKey(), entry.getValue());
                    }
                }
                mailContent = mailContent.replace("nowTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                MailUtil.send(sendTo, "电商数据产出监控", mailContent);
                logger.info("send mail success :{}", sendTo);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Map<String, String> queryCountByType(String type) {
            Map<String, String> count = new HashMap();
            try {
                String index = getIndexName(type);
                String url = Http.url + index + "/goods,rate,shop/_search?search_type=count";
                if (MonthIndex.meilishuo.equals(type) || MonthIndex.mogujie.equals(type)) {
                    url = url.replace("goods", "item");
                }
                String result = Http.doPost(url, "");
                count.put(type + "_index", index);
                if (StringUtils.isBlank(result)) {
                    count.put(type + "_total", "0");
                    count.put(type + "_today_total", "0");
                    count.put(type + "_today_goods_total", "0");
                    count.put(type + "_today_rate_total", "0");
                    count.put(type + "_today_shops_total", "0");
                }
                long total = JSONObject.parseObject(result).getJSONObject("hits").getLong("total");
                count.put(type + "_total", String.valueOf(total));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                String endD = sdf.format(new Date());
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                if (cal.get(Calendar.HOUR_OF_DAY) == 0) {
                    cal.add(Calendar.DATE, -1);
                }
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                String startD = sdf.format(cal.getTime());
                String q = "&q=" + URLEncoder.encode("createTime:[" + startD + " TO " + endD + "]", "UTF-8");
                result = Http.doPost2(url + q, "");
                total = JSONObject.parseObject(result).getJSONObject("hits").getLong("total");
                count.put(type + "_today_total", String.valueOf(total));

                url = Http.url + index + "/goods/_search?search_type=count";
                if (MonthIndex.meilishuo.equals(type) || MonthIndex.mogujie.equals(type) || MonthIndex.gome.equals(type) ||
                        MonthIndex.vip.equals(type) || MonthIndex.mia.equals(type)) {
                    url = url.replace("goods", "item");
                }
                result = Http.doPost2(url + q, "");
                total = JSONObject.parseObject(result).getJSONObject("hits").getLong("total");
                count.put(type + "_today_goods_total", String.valueOf(total));

                url = Http.url + index + "/rate/_search?search_type=count";
                result = Http.doPost2(url + q, "");
                total = JSONObject.parseObject(result).getJSONObject("hits").getLong("total");
                count.put(type + "_today_rate_total", String.valueOf(total));

                url = Http.url + index + "/shop/_search?search_type=count";
                result = Http.doPost2(url + q, "");
                total = JSONObject.parseObject(result).getJSONObject("hits").getLong("total");
                count.put(type + "_today_shops_total", String.valueOf(total));
            } catch (Exception e) {
                System.err.println(type + ":error");
                e.printStackTrace();
            }
            return count;
        }
    }

    /**
     * 当前月索引
     *
     * @param type gome  suning
     * @return
     */
    public static String getIndexName(String type) {
        switch (type) {
            case "gome":
            case "suning":
            case "mia":
            case "vip":
            case "mogujie":
            case "meilishuo":
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(new Date());
                int m1 = calendar1.get(Calendar.MONTH);
                m1++;
                int year1 = calendar1.get(Calendar.YEAR);
                return "ec_" + type + "_" + year1 + "_m" + m1;
            case "jd":
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String date = sdf.format(new Date());
                return "ec_" + type + "_" + date;
            case "taobao":
            case "tmall":
                Calendar calendarw = Calendar.getInstance();
                calendarw.setTime(new Date());
                int week = calendarw.get(Calendar.WEEK_OF_YEAR);
                int yearw = calendarw.get(Calendar.YEAR);
                return "ec_" + type + "_" + yearw + "_w" + week;
        }
        return "";
    }
}
