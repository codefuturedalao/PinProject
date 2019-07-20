package top.codefuturesql.loginandregi;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class FuncUtil {
    /**
     * 向服务器端发送用户的半径，更新数据库信息
     * @param radius 用户服务半径
     * @return 设置半径是否成功
     */
    public static boolean setRadius(int radius){
        Map<String,String> map = new HashMap<>();
        JSONObject jsonObject;
        String name = getName();
        try {
            map.put("name",name);
            map.put("radius",Integer.toString(radius));
            map.put("oper","setRadius");
            String result = HttpUtil.postRequest(HttpUtil.ServeUrl,map);
            jsonObject =  new JSONObject(result);
            System.out.println("" + jsonObject.getInt("userId"));
            if (jsonObject.getInt("userId")>0){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean updateRadius(int radius){
        Map<String,String> map = new HashMap<>();
        JSONObject jsonObject;
        String name = getName();
        try {
            map.put("name",name);
            map.put("radius",Integer.toString(radius));
            map.put("oper","alterRadius");
            String result = HttpUtil.postRequest(HttpUtil.ServeUrl,map);
            jsonObject =  new JSONObject(result);
            System.out.println("" + jsonObject.getInt("userId"));
            if (jsonObject.getInt("userId")>0){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean updatePosition(float longitude,float latitude){
        Map<String,String> map = new HashMap<>();
        JSONObject jsonObject;
        String name = getName();
        try {
            map.put("name",name);
            map.put("longitude",Float.toString(longitude));
            map.put("latitude",Float.toString(latitude));
            map.put("oper","alterPosition");
            String result = HttpUtil.postRequest(HttpUtil.ServeUrl,map);
            jsonObject =  new JSONObject(result);
            System.out.println("" + jsonObject.getInt("userId"));
            if (jsonObject.getInt("userId")>0){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean sendMessage(String message){
        Map<String,String> map = new HashMap<>();
        JSONObject jsonObject;
        String name = getName();
        try {
            map.put("name",name);
            map.put("message",message);
            map.put("oper","sendMessage");
            String result = HttpUtil.postRequest(HttpUtil.ServeUrl,map);
            jsonObject =  new JSONObject(result);
            System.out.println("" + jsonObject.getInt("userId"));
            if (jsonObject.getInt("userId")>0){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean sendAlarm(String alarm,int interval,float longitude,float latitude){
        Map<String,String> map = new HashMap<>();
        JSONObject jsonObject;
        String name = getName();
        try {
            map.put("name",name);
            map.put("alarm",alarm);
            map.put("interval",Integer.toString(interval));
            map.put("longitude",Float.toString(longitude));
            map.put("latitude",Float.toString(latitude));
            map.put("oper","sendAlarm");
            String result = HttpUtil.postRequest(HttpUtil.ServeUrl,map);
            jsonObject =  new JSONObject(result);
            System.out.println("" + jsonObject.getInt("userId"));
            if (jsonObject.getInt("userId")>0){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static void getMessage(String []message,String [] sendtime,double []longitude,double []latitude){
        Map<String,String> map = new HashMap<>();
        JSONObject jsonObject;
        String name = getName();
        try {
            map.put("name",name);
            map.put("oper","getMessage");
            String result = HttpUtil.postRequest(HttpUtil.ServeUrl,map);
            jsonObject =  new JSONObject(result);
            int length = jsonObject.length();
            message = new String[length/4];
            sendtime = new String[length/4];
            longitude = new double[length/4];
            latitude = new double[length/4];
            for(int i = 0;i<jsonObject.length();i+=4){
                message[(i/4)] = jsonObject.getString("message"+(i/4+1));
                sendtime[(i/4)] = jsonObject.getString("sendtime"+(i/4+1));
                longitude[(i/4)] = jsonObject.getDouble("longitude"+(i/4+1));
                latitude[(i/4)] = jsonObject.getDouble("latitude"+(i/4+1));
//                System.out.println("" + message[(i/4)]);
//                System.out.println("" + sendtime[(i/4)]);
//                System.out.println("" + longitude[(i/4)]);
//                System.out.println("" + latitude[(i/4)]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getAlarm(String []alarm,String [] sendtime,double []longitude,double []latitude){
        Map<String,String> map = new HashMap<>();
        JSONObject jsonObject;
        String name = getName();
        try {
            map.put("name",name);
            map.put("oper","getAlarm");
            String result = HttpUtil.postRequest(HttpUtil.ServeUrl,map);
            jsonObject =  new JSONObject(result);
            int length = jsonObject.length();
            alarm = new String[length/4];
            sendtime = new String[length/4];
            longitude = new double[length/4];
            latitude = new double[length/4];
            for(int i = 0;i<jsonObject.length();i+=4){
                alarm[(i/4)] = jsonObject.getString("alarm"+(i/4+1));
                sendtime[(i/4)] = jsonObject.getString("sendtime"+(i/4+1));
                longitude[(i/4)] = jsonObject.getDouble("longitude"+(i/4+1));
                latitude[(i/4)] = jsonObject.getDouble("latitude"+(i/4+1));
//                System.out.println("" + alarm[(i/4)]);
//                System.out.println("" + sendtime[(i/4)]);
//                System.out.println("" + longitude[(i/4)]);
//                System.out.println("" + latitude[(i/4)]);
            }
            System.out.println("the alarm's object'length is "+alarm.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //test
    private static String getName(){
        return "Jackson";
    }
}
