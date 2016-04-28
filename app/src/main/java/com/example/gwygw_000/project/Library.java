package com.example.gwygw_000.project;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.util.HashMap;

/**
 * Created by gwygw_000 on 2016/4/27.
 */
public class Library {

    public HashMap<String, String> teamLogo;

    public static LruCache<String, Bitmap> mImgMemoryCache = new LruCache<String, Bitmap>((int)Runtime.getRuntime().maxMemory() / 1024 / 8) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount() / 1024;
        }
    };

    public Library () {
        teamLogo = new HashMap<String, String>();
        teamLogo.put("WAS", "http://content.sportslogos.net/logos/6/219/full/5671_washington_wizards-primary-2016.png");
        teamLogo.put("CHA", "http://content.sportslogos.net/logos/6/5120/full/1926_charlotte__hornets_-primary-2015.png");
        teamLogo.put("ATL", "http://content.sportslogos.net/logos/6/220/full/9168_atlanta_hawks-primary-2016.png");
        teamLogo.put("MIA", "http://content.sportslogos.net/logos/6/214/full/burm5gh2wvjti3xhei5h16k8e.gif");
        teamLogo.put("ORL", "http://content.sportslogos.net/logos/6/217/full/wd9ic7qafgfb0yxs7tem7n5g4.gif");
        teamLogo.put("NY", "http://content.sportslogos.net/logos/6/216/full/2nn48xofg0hms8k326cqdmuis.gif");
        teamLogo.put("PHI", "http://content.sportslogos.net/logos/6/218/full/7034_philadelphia_76ers-primary-2016.png");
        teamLogo.put("BKN", "http://content.sportslogos.net/logos/6/3786/full/137_brooklyn-nets-primary-2013.png");
        teamLogo.put("BOS", "http://content.sportslogos.net/logos/6/213/full/slhg02hbef3j1ov4lsnwyol5o.png");
        teamLogo.put("TOR", "http://content.sportslogos.net/logos/6/227/full/4578_toronto_raptors-primary-2016.png");
        teamLogo.put("CHI", "http://content.sportslogos.net/logos/6/221/full/hj3gmh82w9hffmeh3fjm5h874.png");
        teamLogo.put("CLE", "http://content.sportslogos.net/logos/6/222/full/e4701g88mmn7ehz2baynbs6e0.png");
        teamLogo.put("IND", "http://content.sportslogos.net/logos/6/224/full/3083.gif");
        teamLogo.put("DET", "http://content.sportslogos.net/logos/6/223/full/3079.gif");
        teamLogo.put("MIL", "http://content.sportslogos.net/logos/6/225/full/8275_milwaukee_bucks-primary-2016.png");
        teamLogo.put("MIN", "http://content.sportslogos.net/logos/6/232/full/zq8qkfni1g087f4245egc32po.gif");
        teamLogo.put("UTA", "http://content.sportslogos.net/logos/6/234/full/m2leygieeoy40t46n1qqv0550.gif");
        teamLogo.put("OKC", "http://content.sportslogos.net/logos/6/2687/full/khmovcnezy06c3nm05ccn0oj2.gif");
        teamLogo.put("POR", "http://content.sportslogos.net/logos/6/239/full/bahmh46cyy6eod2jez4g21buk.gif");
        teamLogo.put("DEN", "http://content.sportslogos.net/logos/6/229/full/xeti0fjbyzmcffue57vz5o1gl.gif");
        teamLogo.put("MEM", "http://content.sportslogos.net/logos/6/231/full/793.gif");
        teamLogo.put("HOU", "http://content.sportslogos.net/logos/6/230/full/8xe4813lzybfhfl14axgzzqeq.gif");
        teamLogo.put("NO", "http://content.sportslogos.net/logos/6/4962/full/2681_new_orleans_pelicans-primary-2014.png");
        teamLogo.put("SA", "http://content.sportslogos.net/logos/6/233/full/827.gif");
        teamLogo.put("DAL", "http://content.sportslogos.net/logos/6/228/full/ifk08eam05rwxr3yhol3whdcm.png");
        teamLogo.put("GS", "http://content.sportslogos.net/logos/6/235/full/qhhir6fj8zp30f33s7sfb4yw0.png");
        teamLogo.put("LAL","http://content.sportslogos.net/logos/6/237/full/uig7aiht8jnpl1szbi57zzlsh.gif");
        teamLogo.put("LAC","http://content.sportslogos.net/logos/6/236/full/5462_los_angeles_clippers-primary-2016.png");
        teamLogo.put("PHO","http://content.sportslogos.net/logos/6/238/full/4370_phoenix_suns-primary-2014.png");
        teamLogo.put("SAC","http://content.sportslogos.net/logos/6/240/full/4043_sacramento_kings-primary-2017.png");





    }
}
