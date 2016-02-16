package com.lianle.utils.JUtils.file;

/**
 * Description: <br>
 *
 * @author <a href=mailto:lianle1@jd.com>连乐</a>
 * @date 2016/2/14 17:09
 */
public class TestDownloadMain {
    public static void main(String[] args) {
        /*DownloadInfo bean = new DownloadInfo("http://i7.meishichina.com/Health/UploadFiles/201109/2011092116224363.jpg");
        System.out.println(bean);
        BatchDownloadFile down = new BatchDownloadFile(bean);
        new Thread(down).start();*/

//        DownloadUtils.download("http://i7.meishichina.com/Health/UploadFiles/201109/2011092116224363.jpg", "a.jpg", "d:\\", 3);
//        DownloadUtils.download("http://5280bt.com/pic/bA/The_Ghouls.torrent", "The_Ghouls.torrent", "d:\\", 3);
//        DownloadUtils.download("http://5280bt.com/pic/bA/Suburra.torrent", "Suburra.torrent", "d:\\", 10);
        DownloadUtils.download("http://5280bt.com/pic/bA/Ant_Man.torrent", "Ant_Man.torrent", "d:\\", 3);

        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$");
    }
}
