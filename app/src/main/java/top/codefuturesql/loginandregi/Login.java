package top.codefuturesql.loginandregi;

import org.json.JSONObject;

import java.util.Map;

public class Login {
    private static final String operation = "login";

    /**
     *
     * @param url       统一资源定位符
     * @param rawParams post请求所带的参数
     * @return          返回是否登陆成功
     */
    public static boolean login(final String url,final Map<String,String> rawParams){
        JSONObject jsonObject;
        try{
            rawParams.put("oper",operation);
            String result = HttpUtil.postRequest(url,rawParams);
            jsonObject =  new JSONObject(result);
            if (jsonObject.getInt("userId")>0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }



}
