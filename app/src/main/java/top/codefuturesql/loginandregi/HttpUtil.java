package top.codefuturesql.loginandregi;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class HttpUtil {
    //创建HttpClient对象
    public static HttpClient httpClient = new DefaultHttpClient();
    /**
     *
     * @param url   发送请求的url
     * @return  服务器响应字符串
     */
    public static String getRequest(final String url) throws Exception{
        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        //创建HttpGet请求
                        HttpGet get = new HttpGet(url);
                        //发送gei请求
                        HttpResponse httpResponse = httpClient.execute(get);
                        //如果服务器成功地返回响应
                        if (httpResponse.getStatusLine().getStatusCode() == 200){
                            //获取服务器响应字符串
                            String result = EntityUtils.toString(httpResponse.getEntity());
                            return  result;
                        }
                        //未成功响应
                        return null;
                    }
                }
        );
        new Thread(task).start();
        return  task.get();
    }

    /**
     *
     * @param url   统一资源定位符
     * @param rawParams post请求所带的参数
     * @return      用于生成JSON对象的字符串，若未响应，则返回null
     * @throws Exception
     */
    public static String postRequest(final String url,final Map<String,String> rawParams) throws Exception{
        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        HttpClient httpClientTemp = new DefaultHttpClient();
                        //创建HttpPost请求
                        HttpPost post = new HttpPost(url);
                        //如果传递参数个数比较多，可以对传递参数进行封装
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        for(String key : rawParams.keySet()){
                            //封装请求参数
                            params.add(new BasicNameValuePair(key,rawParams.get(key)));
                        }
                        //设置请求参数
                        post.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
                        //发送Post请求
                        HttpResponse httpResponse = httpClientTemp.execute(post);
                        Log.d("status",new Integer(httpResponse.getStatusLine().getStatusCode()).toString());
                        //如果服务器成功地返回响应
                        if (httpResponse.getStatusLine().getStatusCode() == 200){
                            //获取服务器响应字符串
                            String result = EntityUtils.toString(httpResponse.getEntity());
                            return  result;
                        }
                        //未成功响应
                        return null;

                    }
                }
        );
        new Thread(task).start();
        return  task.get();
    }

}
