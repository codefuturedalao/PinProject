package top.codefuturesql.loginandregi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class FuncUtil {
    /*
     * 向服务器端发送用户的半径，更新数据库信息
     *
     * @param radius 用户服务半径
     * @return 设置半径是否成功
     */

    public static boolean setRadius(int radius) {
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject;
        String name = getName();
        try {
            map.put("name", name);
            map.put("radius", Integer.toString(radius));
            map.put("oper", "setRadius");
            String result = HttpUtil.postRequest(HttpUtil.ServeUrl, map);
            jsonObject = new JSONObject(result);
            System.out.println("" + jsonObject.getInt("userId"));
            if (jsonObject.getInt("userId") > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean updateRadius(int radius) {
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject;
        String name = getName();
        try {
            map.put("name", name);
            map.put("radius", Integer.toString(radius));
            map.put("oper", "alterRadius");
            String result = HttpUtil.postRequest(HttpUtil.ServeUrl, map);
            jsonObject = new JSONObject(result);
            System.out.println("" + jsonObject.getInt("userId"));
            if (jsonObject.getInt("userId") > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean updatePosition(float longitude, float latitude) {
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject;
        String name = getName();
        try {
            map.put("name", name);
            map.put("longitude", Float.toString(longitude));
            map.put("latitude", Float.toString(latitude));
            map.put("oper", "alterPosition");
            String result = HttpUtil.postRequest(HttpUtil.ServeUrl, map);
            jsonObject = new JSONObject(result);
            System.out.println("" + jsonObject.getInt("userId"));
            if (jsonObject.getInt("userId") > 0) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public static boolean setRaius(int radius) {
        Map<String, String> map = new HashMap<>();
        try {
            map.put("radius", String.valueOf(radius));
            HttpUtil.postRequest(HttpUtil.ServeUrl, map);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean sendMessage(String message) {
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject;
        String name = getName();
        try {
            map.put("name", name);
            map.put("message", message);
            map.put("oper", "sendMessage");
            String result = HttpUtil.postRequest(HttpUtil.ServeUrl, map);
            jsonObject = new JSONObject(result);
            System.out.println("" + jsonObject.getInt("userId"));
            if (jsonObject.getInt("userId") > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean sendAlarm(String alarm, int interval, float longitude, float latitude) {
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject;
        String name = getName();
        try {
            map.put("name", name);
            map.put("alarm", alarm);
            map.put("interval", Integer.toString(interval));
            map.put("longitude", Float.toString(longitude));
            map.put("latitude", Float.toString(latitude));
            map.put("oper", "sendAlarm");
            String result = HttpUtil.postRequest(HttpUtil.ServeUrl, map);
            jsonObject = new JSONObject(result);
            System.out.println("" + jsonObject.getInt("userId"));
            if (jsonObject.getInt("userId") > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Message[] getMessage() {
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject;
        Message[] mes;
        String name = getName();
        try {
            map.put("name", name);
            map.put("oper", "getMessage");
            String result = HttpUtil.postRequest(HttpUtil.ServeUrl, map);
            jsonObject = new JSONObject(result);
            int length = jsonObject.length();
            mes = new Message[length / 4];
            for (int i = 0; i < jsonObject.length(); i += 4) {
                String message = jsonObject.getString("message" + (i / 4 + 1));
                String sendtime = jsonObject.getString("sendtime" + (i / 4 + 1));
                double longitude = jsonObject.getDouble("longitude" + (i / 4 + 1));
                double latitude = jsonObject.getDouble("latitude" + (i / 4 + 1));
                mes[i / 4] = new Message(message, sendtime, longitude, latitude);
//                System.out.println("" + message[(i/4)]);
//                System.out.println("" + sendtime[(i/4)]);
//                System.out.println("" + longitude[(i/4)]);
//                System.out.println("" + latitude[(i/4)]);
            }
            return mes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Alarm[] getAlarm() {
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject;
        Alarm[] ala;
        String name = getName();
        try {
            map.put("name", name);
            map.put("oper", "getAlarm");
            String result = HttpUtil.postRequest(HttpUtil.ServeUrl, map);
            jsonObject = new JSONObject(result);
            int length = jsonObject.length();
            ala = new Alarm[length / 4];
            for (int i = 0; i < jsonObject.length(); i += 4) {
                String alarm = jsonObject.getString("alarm" + (i / 4 + 1));
                String sendtime = jsonObject.getString("sendtime" + (i / 4 + 1));
                double longitude = jsonObject.getDouble("longitude" + (i / 4 + 1));
                double latitude = jsonObject.getDouble("latitude" + (i / 4 + 1));
                ala[i / 4] = new Alarm(alarm, sendtime, longitude, latitude);
//                System.out.println("" + alarm[(i/4)]);
//                System.out.println("" + sendtime[(i/4)]);
//                System.out.println("" + longitude[(i/4)]);
//                System.out.println("" + latitude[(i/4)]);
            }
            System.out.println("the alarm's object'length is " + ala.length);
            return ala;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //test
  //  private static String getName() {
  //      return "Jackson";
  //  }

    public static String getName(){
        SQLiteDatabase db = DatabaseUtil.dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select username from account ",null);
        if (cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndex("username"));
            cursor.close();
            return username;
        }
        cursor.close();
        return "";

    }
    public static void setName(String username,String password){
        DatabaseUtil.createDatabase();
        DatabaseUtil.insertData(username,password);
    }
}
