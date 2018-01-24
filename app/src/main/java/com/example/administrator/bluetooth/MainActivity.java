package com.example.administrator.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public static final int REQUEST_OPEN_BT=0X01;//打开蓝牙
    public static final String TAG="MainACtivity";
    Button mBtnOpenBt;
    BluetoothAdapter mBluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取本地蓝牙适配器
        mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        //判断蓝牙功能是否存在
        if(mBluetoothAdapter==null){
            showToast("该设备不支持蓝牙功能...");
            return;
        }
        //获取模名字 MAC地址
        String name=mBluetoothAdapter.getName();
        String mac=mBluetoothAdapter.getAddress();
        Log.e(TAG,"名字:"+name+",mac:"+mac);

        //获取当前蓝牙的状态
        int state=mBluetoothAdapter.getState();
        switch (state){
            case BluetoothAdapter.STATE_ON://蓝牙已经打开
                showToast("蓝牙已经打开...");
                break;
            case BluetoothAdapter.STATE_TURNING_ON://蓝牙正在打开
                showToast("蓝牙正在打开...");
                break;
            case BluetoothAdapter.STATE_TURNING_OFF://蓝牙正在关闭
                showToast("蓝牙正在关闭...");
                break;
            case BluetoothAdapter.STATE_OFF://蓝牙已经关闭
                showToast("蓝牙已经关闭...");
                break;

        }



        mBtnOpenBt=(Button)this.findViewById(R.id.btn_open_bt);
        mBtnOpenBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //蓝牙处于关闭的时候--打开本地蓝牙设备
                 //判断蓝牙是否打开
                if(mBluetoothAdapter.isEnabled()){
                    showToast("蓝牙已经打开...");
                    //关闭蓝牙
                    boolean isClose=mBluetoothAdapter.disable();
                    Log.e(TAG,"蓝牙是否关闭："+isClose);

                }else{
                    //蓝牙关闭，需要打开
                    //boolean isOpen=mBluetoothAdapter.enable();
                    /*
                    调用系统api打开蓝牙
                     */
                    Intent open=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(open,REQUEST_OPEN_BT);
                }
            }
        });
    }

    public void showToast(String msg){
        Toast toast=Toast.makeText(this,msg,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(REQUEST_OPEN_BT==requestCode){
            if(requestCode==RESULT_CANCELED){
                showToast("请求失败");
            }
            else{
                showToast("请求成功。。。");
            }
        }
    }
}
