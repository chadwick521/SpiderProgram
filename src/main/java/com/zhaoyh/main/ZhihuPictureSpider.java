package com.zhaoyh.main;

import com.zhaoyh.utils.DownloadUtils;
import com.zhaoyh.utils.ValidatorUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyh on 2017/07/31.
 * 知乎图片下载
 */
public class ZhihuPictureSpider implements PageProcessor {

    private String basePath = null;
    private static String z_c0 = null;

    private static Site site = Site.me().setRetryTimes(5).setSleepTime(5000).setTimeOut(5000)
            .addCookie("Domain", "zhihu.com")
            .addCookie("z_c0", z_c0)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36");

    /**
     * 开始处理
     * @param page
     */
    public void process(Page page) {
        List<String> urlList  = page.getHtml().xpath("//div[@class=RichContent-inner]//img/@data-original").all();
        String questionTitle = page.getHtml().xpath("//h1[@class=QuestionHeader-title]/text()").toString();
        System.out.println("问题题目：" + questionTitle);

        List<String> picUrlList = new ArrayList<String>();
        for (int i = 0; i < urlList.size(); i = i + 2) {
            String url = urlList.get(i);
            if (ValidatorUtils.isLegalUrl(url)) {
                picUrlList.add(urlList.get(i));
            }
        }
        System.out.println("图片个数：" + picUrlList.size());
        try {
            downLoadPics(picUrlList, questionTitle, this.basePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载图片
     * @param imgUrls
     * @param title
     * @param filePath
     * @return
     * @throws Exception
     */
    private static boolean downLoadPics(List<String> imgUrls, String title, String filePath) throws Exception {
        boolean isSuccess = true;
        //标题
        String storagePath = filePath + File.separator + title;
        File fileDir = new File(storagePath);
        if (imgUrls.size() > 0 && !fileDir.exists()) {
            fileDir.mkdirs();
        }
        int i = 1;

        //循环下载图片
        for (String imgUrl : imgUrls) {
            System.out.println("开始下载下载 " + imgUrl + " " + i);
            String ext = imgUrl.substring(imgUrl.lastIndexOf(".") + 1);
            String fullPath = storagePath + File.separator + "pic_" + System.currentTimeMillis() + "_" + i + "." + ext;
            DownloadUtils.downloadPictureFromUrl(imgUrl, fullPath);
            i++;
        }
        return isSuccess;
    }

    /**
     * 获取站点
     * @return
     */
    public Site getSite() {
        return site;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public static void main(String[] args) {
        //配置
        String z_c0 = "Mi4wQUFDQW9vOHBBQUFBZ01KbTd4ZXlDeGNBQUFCaEFsVk41d3VBV1FBUm1zc2M1dU9KeXpIY3ZwZllncWdGZ25aVkN3|1498971879|c61a46257668240d9b285097111e215739fcfc9d";
        String basePath = "/Users/zhaoyonghui/Desktop";
        String answerUrl =  "https://www.zhihu.com/question/38408540/answer/205775286";

        //初始化
        ZhihuPictureSpider.z_c0 = z_c0;
        ZhihuPictureSpider zhihuProcessor = new ZhihuPictureSpider();
        zhihuProcessor.setBasePath(basePath);

        //启动
        Spider spider = Spider.create(zhihuProcessor);
        spider.addUrl(answerUrl);
        spider.thread(2);
        spider.run();
    }

}
