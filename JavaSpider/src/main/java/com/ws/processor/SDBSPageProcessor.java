package com.ws.processor;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.ws.model.SDBSModel;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static us.codecraft.webmagic.selector.Selectors.xpath;


/**
 * Created by laowang on 16-9-2.
 */
public class SDBSPageProcessor implements PageProcessor {
    private int i=0;
    private SDBSModel sdbsModel = new SDBSModel();
    private String url_Link = "http://sdbs\\.db\\.aist\\.go\\.jp/sdbs/cgi-bin/cre_result\\.cgi\\?STSI=147272012320828&cur_page=1&page_list=20&sdbsno=\\d+";
    private MongoClient mongoClient = new MongoClient();
    private DB db = mongoClient.getDB("mymongo");
    private DBCollection collection = db.getCollection("sdbs_collection");
    private Map<String,String> urlMap = new HashMap<String, String>();

    private Site site = Site.me().setUserAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/51.0.2704.79 Chrome/51.0.2704.79 Safari/537.36")
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setCycleRetryTimes(3)
            .addCookie("SDBS_ENTRANCE_CK","oRf6.Kte7UY")
            .addCookie("SDBS_IMG","eF9L3LGrjL2")
            .addCookie("SDBS_CK","2GpbpVwYEds")
            .addCookie("__utmt_cloud","1")
            .addCookie("__utmt_aist","1")
            .addCookie("SDBS_LANG","eng")
            .addCookie("__utma","200088940.943406916.1470626706.1472920327.1472955176.881")
            .addCookie("__utmb","200088940.186.9.1472956020970")
            .addCookie("__utmc","200088940")
            .addCookie("__utmz","200088940.1470626706.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)");

    private static Logger logger = Logger.getLogger(SDBSPageProcessor.class);



    @Override
    public void process(Page page) {
        System.out.println("----------开始工作----------");
        getProcessorUrl(page);
        System.out.println("----------工作结束----------");
    }

    @Override
    public Site getSite() {
        return site;
    }

    private void getProcessorUrl(Page page){
        //请求页为首页
        if(page.getUrl().regex(url_Link).match()){
            if(page.getHtml().xpath("//*html/body/center/table/tbody/tr[3]/td[4]/text()").all().size()>0){
                //获取SDBSNO
                List<String> listSDBSNO = page.getHtml().xpath("//*html/body/center/table/tbody/tr[3]/td[1]/a/text()").all();
               // String SDBSNO = xpath("//*a/text()").selectElement(listSDBSNO.get(0)).toString();
                String SDBSNO = listSDBSNO.get(0);
                sdbsModel.setSDBSNO(Integer.valueOf(SDBSNO));
                System.out.println(SDBSNO);

                //获取Molecular Formula
                List<String> listMole_Formula = page.getHtml().xpath("//*html/body/center/table/tbody/tr[3]/td[4]/text()").all();
                //String Mole_Formula = xpath("//*td[4]/text()").selectElement(listMole_Formula.get(0)).toString();
                String Mole_Formula = listMole_Formula.get(0);
                sdbsModel.setMole_Formula(Mole_Formula);
                System.out.println(Mole_Formula);

                //获取Molecular Weight
                List<String> listMole_Weight = page.getHtml().xpath("//*html/body/center/table/tbody/tr[3]/td[5]/text()").all();
                //String Mole_Weight = xpath("//*td[5]/text()").selectElement(listMole_Weight.get(0)).toString();
                String Mole_Weight = listMole_Weight.get(0);
                sdbsModel.setMole_Weight(Mole_Weight);
                System.out.println(Mole_Weight);

                //获取MS
                if(page.getHtml().xpath("//*html/body/center/table/tbody/tr[3]/td[7]/a").all().size()>0){
                    List<String> listMS = page.getHtml().xpath("//*html/body/center/table/tbody/tr[3]/td[7]/").all();
                    String MS = xpath("//*a").selectElement(listMS.get(0)).attr("href");
                    System.out.println(MS);
                    page.addTargetRequest(MS);
                    urlMap.put(MS,"MS");
                }else{
                    String MS = "N";
                    sdbsModel.setMS(MS);
                    i++;
                    System.out.println(MS);
                }
                //获取CNMR
                if(page.getHtml().xpath("//*html/body/center/table/tbody/tr[3]/td[8]/a").all().size()>0){
                    List<String> listCNMR = page.getHtml().xpath("//*html/body/center/table/tbody/tr[3]/td[8]").all();
                    String CNMR = xpath("//*a").selectElement(listCNMR.get(0)).attr("href");
                    System.out.println(CNMR);
                    page.addTargetRequest(CNMR);
                    urlMap.put(CNMR,"CNMR");
                }else{
                    String CNMR = "N";
                    i++;
                    sdbsModel.setCNMR(CNMR);
                    System.out.println(CNMR);
                }
                //获取HNMR
                if(page.getHtml().xpath("//*html/body/center/table/tbody/tr[3]/td[9]/a").all().size()>0){
                    List<String> listHNMR = page.getHtml().xpath("//*html/body/center/table/tbody/tr[3]/td[9]").all();
                    String HNMR = xpath("//*a").selectElement(listHNMR.get(0)).attr("href");
                    System.out.println(HNMR);
                    page.addTargetRequest(HNMR);
                    urlMap.put(HNMR,"HNMR");
                }else{
                    String HNMR = "N";
                    i++;
                    sdbsModel.setHNMR(HNMR);
                    System.out.println(HNMR);
                }
                if(page.getHtml().xpath("//*html/body/center/table/tbody/tr[3]/td[10]/a").all().size()>0){
                    List<String> listIR = page.getHtml().xpath("//*html/body/center/table/tbody/tr[3]/td[10]").all();
                    String IR = xpath("//*a").selectElement(listIR.get(0)).attr("href");
                    page.addTargetRequest(IR);
                    System.out.println(IR);
                    urlMap.put(IR,"IR");
                }else{
                    String IR = "N";
                    i++;
                    sdbsModel.setIR(IR);
                    System.out.println(IR);
                }
                if(page.getHtml().xpath("//*html/body/center/table/tbody/tr[3]/td[11]/a").all().size()>0){
                    List<String> listRaman = page.getHtml().xpath("//*html/body/center/table/tbody/tr[3]/td[11]").all();
                    String Raman = xpath("//*a").selectElement(listRaman.get(0)).attr("href");
                    System.out.println(Raman);
                    page.addTargetRequest(Raman);
                    urlMap.put(Raman,"Raman");
                }else{
                    String Raman = "N";
                    i++;
                    sdbsModel.setRaman(Raman);
                    System.out.println(Raman);
                }

                if(page.getHtml().xpath("//*html/body/center/table/tbody/tr[3]/td[12]/a").all().size()>0){
                    List<String> listESR = page.getHtml().xpath("//*html/body/center/table/tbody/tr[3]/td[12]").all();
                    String ESR = xpath("//*a").selectElement(listESR.get(0)).attr("href");
                    System.out.println(ESR);
                    page.addTargetRequest(ESR);
                    urlMap.put(ESR,"ESR");
                }else{
                    String ESR = "N";
                    i++;
                    sdbsModel.setESR(ESR);
                    System.out.println(ESR);
                }
                //获取Compound Name
                List<String> listCOM_Name = page.getHtml().xpath("//*html/body/center/table/tbody/tr[3]/td[14]/text()").all();
              //  String COM_Name = xpath("//*td[14]/text()").select(listMole_Weight.get(0));
                String COM_Name = listCOM_Name.get(0);
                sdbsModel.setCompound_Name(COM_Name);
                System.out.println(COM_Name);

            }
        }else{
            List<String> listUtils = page.getHtml().xpath("//*html/frameset").all();
            String imgUrl = xpath("//*frame[@name=\"image\"]").selectElement(listUtils.get(0)).attr("src");
            imgUrl="http://sdbs.db.aist.go.jp/sdbs/cgi-bin"+imgUrl.substring(1,imgUrl.length());
            String getUrl = urlMap.get(page.getUrl().toString());
                if (getUrl.equals("MS")) {
                    sdbsModel.setMS(imgUrl);
                    i++;
                } else if (getUrl.equals("CNMR")) {
                    sdbsModel.setCNMR(imgUrl);
                    i++;
                } else if (getUrl.equals("HNMR")) {
                    sdbsModel.setHNMR(imgUrl);
                    i++;
                } else if (getUrl.equals("IR")) {
                    sdbsModel.setIR(imgUrl);
                    i++;
                } else if (getUrl.equals("Raman")) {
                    sdbsModel.setRaman(imgUrl);
                    i++;
                } else if (getUrl.equals("ESR")) {
                    i++;
                    sdbsModel.setESR(imgUrl);
                }
            System.out.println(getUrl+":"+imgUrl);

        }

//        System.out.println(i);
        if(i==6){
            DBObject object = (BasicDBObject) JSON.parse(sdbsModel.toJson());
            collection.insert(object);
            if(mongoClient!=null)
                mongoClient.close();
        }



    }

}
