package com.zhaoyh.main;
import com.zhaoyh.utils.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by zhaoyh on 2017/5/31.
 */
public class OfferSpider implements PageProcessor {

    public static Site site = Site.me().setRetryTimes(5).setSleepTime(5000).setTimeOut(5000);

    public void process(Page page) {
        String pathText = "//table[@class=\"table table-hover\"]//tbody//tr//td/text()";
        String text = page.getHtml().xpath(pathText).nodes().toString();
        if (!StringUtils.isBlank(text)) {
            text = StringUtils.replaceSpecial(text);
        }
        System.out.println(text);
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 500; i++) {
            String url = "http://91offer.online/offerDetail?id=" + i;
            OfferSpider processor = new OfferSpider();
            //启动
            Spider.create(processor).addUrl(url).thread(1).run();
        }
    }
}
