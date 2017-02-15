package com.zhouwei.customadapter;

import com.zhouwei.customadapter.model.DetailEntry;
import com.zhouwei.customadapter.model.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwei on 17/2/15.
 */

public class DataMocker {

    public static List<Entry> mockData(){
        List<Entry> entries = new ArrayList<>();
        Entry entry;
        for(int i=1;i<50;i++){
            entry = new Entry();
            if(i % 2 == 1){
                entry.type = Entry.TYPE_TEXT;
                entry.content = "This is Item "+i;
            }else{
                entry.type = Entry.TYPE_IMAGE;
                entry.imageUrl = "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg";
            }

            entries.add(entry);
        }
        return entries;
    }


    public static List<Entry> mockMoreData(){
        List<Entry> entries = new ArrayList<>();
        Entry entry;
        for(int i=50;i<100;i++){
            entry = new Entry();
            if(i % 2 == 0){
                entry.type = Entry.TYPE_TEXT;
                entry.content = "This is Item "+i;
            }else{
                entry.type = Entry.TYPE_IMAGE;
                entry.imageUrl = "http://ww3.sinaimg.cn/large/610dc034gw1fbou2xsqpaj20u00u0q4h.jpg";
            }

            entries.add(entry);
        }
        return entries;
    }

    public static List<String> getData(){
        List<String> data = new ArrayList<>();
        for(int i=0;i<20;i++){
            data.add("item "+i);
        }
        return data;
    }

    public static String[] images = {"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };

    public static List<DetailEntry> mockStaggerData(){
        List<DetailEntry> detailEntries = new ArrayList<>();
        for(int i=0;i<100;i++){
            DetailEntry entry = new DetailEntry();
            entry.title = "this is item "+i;
            if(i % 3 == 0){
                entry.imageUrl = "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg";

            }else{
                entry.imageUrl = "http://img2.3lian.com/2014/f2/37/d/40.jpg";
            }

            detailEntries.add(entry);
        }
        return detailEntries;
    }
}
