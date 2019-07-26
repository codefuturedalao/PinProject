package top.codefuturesql.loginandregi;

import com.baidu.mapapi.model.LatLng;

import java.io.Serializable;

public class pointAddInfoModel implements Serializable {
    private String info;
    private LatLng point;


    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public LatLng getPoint() {
        return point;
    }
    public void setPoints(LatLng point) {
        this.point = point;
    }
}
