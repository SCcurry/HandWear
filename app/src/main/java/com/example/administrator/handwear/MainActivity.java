package com.example.administrator.handwear;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button saomiao , duanzhen , changzhen , buting , tingxia;
    private TextView jibu , dianliang , lianjiezhuangtai;

    BluetoothAdapter bluetoothAdapter;
    List<BluetoothDevice> deviceList = new ArrayList<>();//用一个数组来保存扫描到的设备
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        //蓝牙管理，这是系统服务可以通过getSystemService(BLUETOOTH_SERVICE)的方法获取实例
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        //通过蓝牙管理实例获取适配器，然后通过扫描方法（scan）获取设备(device)
        bluetoothAdapter = bluetoothManager.getAdapter();


    }

    private void initView() {
        saomiao = (Button) findViewById(R.id.saomiao);
        duanzhen = (Button) findViewById(R.id.zhendong);
        changzhen = (Button) findViewById(R.id.changzhen);
        buting = (Button) findViewById(R.id.buting);
        tingxia = (Button) findViewById(R.id.tingxia);

        jibu = (TextView) findViewById(R.id.jibu);
        dianliang = (TextView) findViewById(R.id.dianliang);
        lianjiezhuangtai = (TextView) findViewById(R.id.lianjiezhuangtai);

        saomiao.setOnClickListener(this);
        duanzhen.setOnClickListener(this);
        changzhen.setOnClickListener(this);
        buting.setOnClickListener(this);
        tingxia.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.saomiao:
                //开始扫描前开启蓝牙
                Intent turn_on = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turn_on, 0);
                Toast.makeText(MainActivity.this, "蓝牙已经开启", Toast.LENGTH_SHORT).show();

                Thread scanThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("TAG", "run: saomiao ...");
                        saomiao();
                    }
                });
                scanThread.start();
                break;
            case R.id.zhendong:

                break;
            case R.id.changzhen:

                break;
            case R.id.buting:

                break;
            case R.id.tingxia:

                break;

        }
    }
    public void saomiao(){
        deviceList.clear();

        bluetoothAdapter.startLeScan(callback);
    }

    public BluetoothAdapter.LeScanCallback callback = new BluetoothAdapter.LeScanCallback()
    {
        @Override
        public void onLeScan(final BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            Log.i("TAG", "onLeScan: " + bluetoothDevice.getName() + "/t" + bluetoothDevice.getAddress() + "/t" + bluetoothDevice.getBondState());

            //重复过滤方法，列表中包含不该设备才加入列表中，并刷新列表
            if (!deviceList.contains(bluetoothDevice)) {
                //将设备加入列表数据中
                deviceList.add(bluetoothDevice);
            }

        }
    };

}