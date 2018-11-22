package cn.xxt.componentzhqapplication.module.recommand;

import java.util.ArrayList;

import cn.xxt.componentzhqapplication.module.BaseModel;

public class RecommandBodyValue extends BaseModel{

    public int type;
    public String logo;
    public String title;
    public String info;
    public String price;
    public String text;
    public String site;
    public String from;
    public String zan;
    public ArrayList<String> url;

    /** 视频专用 */
//    public String thumb;
//    public String resource;
//    public String resourceID;
//    public String adid;
//    public ArrayList<Monitor> startMonitor;
}
