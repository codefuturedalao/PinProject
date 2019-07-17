package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

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
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SDK使用默认路径
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        mMapView = (MapView) findViewById(R.id.bmapView);
        //调用定位初始化
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(MyLocationListener);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        initLocation();

        mLocationClient.start();

        //返回首页按钮跳转
        Button button1 = (Button) findViewById(R.id.back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(MapActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });

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

