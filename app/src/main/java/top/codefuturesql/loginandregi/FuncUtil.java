package top.codefuturesql.loginandregi;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class FuncUtil {
    /**
     * 向服务器端发送用户的半径，更新数据库信息
     * @param radius 用户服务半径
     * @return 设置半径是否成功
     */
    public static boolean setRaius(int radius){
        Map<String,String> map = new HashMap<>();
        try {
            map.put("radius",String(radius));
            String result = HttpUtil.postRequest(HttpUtil.ServeUrl,map);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static sendMessage(String message){
        SimpleDateFormat nowTime = new SimpleDateFormat("yyyy-MM-dd- HH-mm-ss");
        this is from cgb;
        this is from cgb again;
    }
}
