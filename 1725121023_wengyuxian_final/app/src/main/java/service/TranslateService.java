package service;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import dto.PostData;
import dto.ResultData;
import okhttp3.*;

// 返回翻译结果的类
public class TranslateService {
    public ResultData getTranslateResult(PostData postData) {
        OkHttpClient client = new OkHttpClient();
        String q = "";
        String from = "";
        String to = "";
        String appid = "";
        String salt = "";
        String sign = "";
        try {
           q = java.net.URLEncoder.encode(postData.getQ(), "utf-8");
           from = java.net.URLEncoder.encode(postData.getFrom(), "utf-8");
           to = java.net.URLEncoder.encode(postData.getTo(), "utf-8");
           appid = java.net.URLEncoder.encode(postData.getAppid(),"utf-8");
           salt = java.net.URLEncoder.encode(postData.getSalt(),"utf-8");
           sign = java.net.URLEncoder.encode(postData.getSign(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String getURL = "https://fanyi-api.baidu.com/api/trans/vip/translate?q="+q+"&from="+
                from+"&to="+to+"&appid="+appid+
                "&salt="+salt+"&sign="+sign;
        System.out.println(getURL);
        Request request = new Request.Builder()
                .get()
                .url(getURL)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String translateRES = response.body().string();
            System.out.println("请求成功,返回值为:");
            System.out.println(translateRES);
            ResultData resultData = JSON.parseObject(translateRES, ResultData.class);
            System.out.println("JSON转Object成功!");
            System.out.println(resultData);
            return resultData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
