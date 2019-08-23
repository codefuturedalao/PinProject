package com.example.android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import top.codefuturesql.loginandregi.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import top.codefuturesql.loginandregi.FuncUtil;

public class MapActivity extends AppCompatActivity {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    public LocationClient mLocationClient;
    BitmapDescriptor bitmapDescriptor;
    private double lat;
    private double lon;
    //
    private int type = BaiduMap.MAP_TYPE_NORMAL;
    private Boolean isFirstLocate = true;
    private boolean geoAlarm = false;
    private boolean geoMessage = true;

    //构造地图数据，显示定位
    private BDAbstractLocationListener MyLocationListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            //设置定位图层配置信息
            MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(
                    MyLocationConfiguration.LocationMode.NORMAL, false, bitmapDescriptor);
            mBaiduMap.setMyLocationConfiguration(myLocationConfiguration);
            //新建地理坐标点
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            if (isFirstLocate) {
                isFirstLocate = false;
                //设置地图最大缩放比例
                MapStatus.Builder builder = new MapStatus.Builder().target(ll).zoom(16f);
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
            if (geoMessage) {
                addPoint();
            }
            FuncUtil.updatePosition((float)location.getLongitude(),(float)location.getLatitude());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SDK使用默认路径
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mMapView = (MapView) findViewById(R.id.bmapView);
        //调用定位初始化
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(MyLocationListener);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        initLocation();

        mLocationClient.start();

        /*
        返回首页按钮跳转
        Button button1 = (Button) findViewById(R.id.back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(MapActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
*/
        //重新定位，刷新页面
        Button button2 = (Button) findViewById(R.id.relocation);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();
                intent2.setClass(MapActivity.this, MapActivity.class);
                startActivity(intent2);
            }

        });

        ImageButton button3 = (ImageButton) findViewById(R.id.geoAlarm);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (geoMessage == true) {
                    geoMessage = false;
                    geoAlarm = true;
                } else
                    geoAlarm = true;
            }
        });

        ImageButton button4 = (ImageButton) findViewById(R.id.geoMessage);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (geoAlarm = true) {
                    geoAlarm = false;
                    geoMessage = true;
                }
            }
        });
        //定义地图单击事件方法
        BaiduMap.OnMapClickListener listener = new BaiduMap.OnMapClickListener() {
            public void onMapClick(LatLng pot) {
                mBaiduMap.clear();
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon);
                OverlayOptions option = new MarkerOptions()
                        .position(pot)
                        .icon(bitmap);
                TextView tw = new TextView(getApplicationContext());
                tw.setText("经度：" + pot.longitude + "\n纬度:" + pot.latitude);
                InfoWindow mInfoWindow = new InfoWindow(tw, pot, 100);
                mBaiduMap.addOverlay(option);
                mBaiduMap.showInfoWindow(mInfoWindow);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {

                return false;
            }

        };
        mBaiduMap.setOnMapClickListener(listener);
    }

    //定义实时更新当前位置方法
    private void initLocation() {
        mBaiduMap.setMyLocationEnabled(true);
        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");// 设置坐标类型
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);// 打开gps
        option.setIgnoreKillProcess(false);
        mLocationClient.setLocOption(option); //设置locationClientOption
        mLocationClient.setLocOption(option);
    }

    // 按照经纬度在地图上显示点
    public void addPoint() {
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
        List<pointAddInfoModel> points = new ArrayList<pointAddInfoModel>();
        Message[] message = FuncUtil.getMessage();
        int length = message.length;
        LatLng[] point = new LatLng[length];
        pointAddInfoModel[] infoModel = new pointAddInfoModel[length];
        OverlayOptions[] option = new OverlayOptions[length];
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_message_point);
        for (int i = 0; i < length; i++) {
            point[i] = new LatLng(message[i].latitude, message[i].latitude);
            infoModel[i].setPoints(point[i]);
            infoModel[i].setInfo(message[i].message);
            points.add(infoModel[i]);
            option[i] = new MarkerOptions().position(point[i]).icon(bitmap).title(message[i].message);
            options.add(option[i]);
        }
        mBaiduMap.addOverlays(options);
        showPopAndMarker(points);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            //marker被点击时回调的方法
            //若响应点击事件，返回true，否则返回false
            //默认返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getApplicationContext(), getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private Bitmap drawbitmap() {
        // TODO Auto-generated method stub
        Bitmap photo = BitmapFactory.decodeResource(this.getResources(), R.drawable.info_bubble);
        int width = photo.getWidth();
        int hight = photo.getHeight();
        Bitmap newb = Bitmap.createBitmap(width, hight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newb);// 初始化和方框一样大小的位图
        Paint photoPaint = new Paint(); // 建立画笔
        canvas.drawBitmap(photo, 0, 0, photoPaint);
        canvas.save();
        canvas.restore();
        return newb;
    }

    private Bitmap drawtext(Bitmap bitmap3, String info) {
        // TODO Auto-generated method stub
        int width = bitmap3.getWidth(), hight = bitmap3.getHeight();
        Bitmap icon = Bitmap.createBitmap(width, hight, Bitmap.Config.ARGB_8888); //建立一个空的BItMap
        Canvas canvas = new Canvas(icon);//初始化画布绘制的图像到icon上
        Paint photoPaint = new Paint(); //建立画笔
        photoPaint.setDither(true); //获取跟清晰的图像采样
        photoPaint.setFilterBitmap(true);//过滤一些
        Rect src = new Rect(0, 0, bitmap3.getWidth(), bitmap3.getHeight());//创建一个指定的新矩形的坐标
        Rect dst = new Rect(0, 0, width, hight);//创建一个指定的新矩形的坐标
        canvas.drawBitmap(bitmap3, src, dst, photoPaint);//将photo 缩放或则扩大到 dst使用的填充区photoPaint
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);//设置画笔
        textPaint.setTextSize(28.0f);//字体大小
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);//采用默认的宽度
        textPaint.setColor(Color.BLACK);//采用的颜色
        //textPaint.setShadowLayer(3f, 1, 1,this.getResources().getColor(android.R.color.background_dark));//影音的设置
        canvas.drawText(info, 23, 32, textPaint);//绘制上去字，开始未知x,y采用那只笔绘制
        canvas.save();
        canvas.restore();
        return icon;
    }

    public void showPopAndMarker(List<pointAddInfoModel> points) {
        // 创建InfoWindow展示的view
        BitmapDescriptor bitmap;

        for (int i = 0; i < points.size(); i++) {
            Bitmap bitmap3 = null;
            Bitmap bitmap4 = null;
            try {
                bitmap3 = drawbitmap();
                bitmap4 = drawtext(bitmap3, points.get(i).getInfo());
                bitmap = BitmapDescriptorFactory.fromBitmap(bitmap4);
                OverlayOptions option = new MarkerOptions().position(points.get(i).getPoint()).icon(bitmap);
                mBaiduMap.addOverlay(option);
            } finally {
                bitmap3.recycle();
                bitmap4.recycle();
                bitmap3 = null;
                bitmap4 = null;
            }
        }
    }


    //管理地图生命周期

    protected void onDestory() {
        super.onDestroy();
        //销毁mLocationClient,mMapview对象
        mLocationClient.stop();
        mMapView.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}

