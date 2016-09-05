import com.ws.processor.SDBSPageProcessor;
import org.apache.log4j.PropertyConfigurator;
import us.codecraft.webmagic.Spider;

/**
 * Created by laowang on 16-9-2.
 */
public class SDBSTest {
    public static void main(String []args){
        PropertyConfigurator.configure(ClassLoader.getSystemResourceAsStream("log4j.properties"));
        for(int i=6101;i<=20000;i++){
            Spider.create(new SDBSPageProcessor())
                    .addUrl("http://sdbs.db.aist.go.jp/sdbs/cgi-bin/cre_result.cgi?STSI=147272012320828&cur_page=1&page_list=20&sdbsno="+i)
                    .thread(2)
                    .run();
        }

    }
}
