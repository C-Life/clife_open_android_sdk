package com.het.sdk.demo.ui.activity.device;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.het.UdpCore.keepalive.OnDeviceOnlineListener;
import com.het.open.lib.api.HetDeviceSubmitApi;
import com.het.open.lib.api.HetDeviceUpdateApi;
import com.het.open.lib.callback.IDeviceUpdateView;
import com.het.open.lib.callback.ISubmitUpdateView;
import com.het.open.lib.callback.IUdpModelParser;
import com.het.open.lib.model.DeviceModel;
import com.het.open.lib.utils.GsonUtil;
import com.het.open.lib.utils.LogUtils;
import com.het.open.lib.utils.StringUtils;
import com.het.sdk.demo.biz.hunidifie.packet.HumidifierDev;
import com.het.sdk.demo.biz.hunidifie.packet.HumidifierInPacket;
import com.het.sdk.demo.biz.hunidifie.packet.HumidifierOutPacket;
import com.het.sdk.demo.ui.activity.BaseActivity;
import com.het.sdk.demo.R;
import com.het.sdk.demo.model.humidifier.HumidifierConfigModel;
import com.het.sdk.demo.model.humidifier.HumidifierRunDataModel;
import com.het.sdk.demo.utils.Constants;
import com.het.sdk.demo.utils.HandlerUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 加湿器控制页面
 */
public class HumidifierDeviceControlActivity extends BaseActivity implements IUdpModelParser,View.OnClickListener {



    private DeviceModel deviceModel;
    private final String TAG = "加湿器控制页面";
    private final int SHOW_UPDATE_CONFIG = 0x01;
    private final int SHOW_UPDATE_RUN = 0x02;
    private LinearLayout mainLl;
    private RelativeLayout lightRe;
    private ImageView lightIv;
    private TextView lightTv;
    private RelativeLayout mistRe;
    private ImageView mistIv;
    private TextView mistTv;
    private RelativeLayout notificationRe;
    private Button orderBtn;
    private SeekBar orderSB;
    private LinearLayout closedLl;
    private LinearLayout openedLl;
    private Button togglePowerBtn;
    private Button openBtn;
    private TextView timeTv;
    private ArrayList<String> hourList = new ArrayList<String>();
    private ArrayList<String> minuteList = new ArrayList<String>();
    private ImageView humidifierIv;
    private ImageView redIv;
    private ImageView pinkIv;
    private ImageView blueIv;
    private ImageView skyblueIv;
    private ImageView greenIv;
    private ImageView buffIv;
    private ImageView yellowIv;
    private Timer timer;
    private Boolean isOpened = true;
    private int orderTime = 0;
    private Boolean orderButtonFlag = false;
    private TextView closed_TimeTv;
    private Button closed_orderBtn;
    private Boolean closedOrderButtonFlag = false;
    private final int Msg_device_runner = 1;
    private final int Msg_seekbar_orderTime = 2;
    private final int Msg_orderTime_close = 3;
    private final int Msg_orderTime_open = 4;
    private final int Msg_refreshUI = 5;
    private Context context;
    private OnDeviceOnlineListener onDeviceOnlineListener;
    private TimerTask task;
    // 记录用户行为的list
    private List<Integer> list = new ArrayList<Integer>();
    private Button hulueBtn;
    private TextView notificationTv;
    private boolean isLackWater = false;
    private HumidifierDev humidifierDev;
    private boolean isFrist = true;//是否第一次拿到运行数据

    private HandlerUtil.MessageListener mMessageListener = new HandlerUtil.MessageListener() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SHOW_UPDATE_CONFIG:
                    String configMsg = (String) msg.obj;



                    break;
                case SHOW_UPDATE_RUN:
                    String runMsg = (String) msg.obj;
                    updateRunDataUI(runMsg);
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidifie_layout);
        initView();
        deviceModel = (DeviceModel) getIntent().getSerializableExtra(Constants.DEVICE_MODEL);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 初始并启动更新数据
     */
    private void initUpdateData() {
        HetDeviceUpdateApi.getInstance().init(deviceModel, this, new IDeviceUpdateView() {
            @Override
            public void updateSuccess(int type, String json) {
                switch (type) {
                    case 1:
                        sendMsg(SHOW_UPDATE_CONFIG, json);
                        break;
                    case 2:
                        sendMsg(SHOW_UPDATE_RUN,  json);
                        break;


                }

            }

            @Override
            public void updateError(int code, String msg, int id) {
                LogUtils.e(TAG, code + msg);
            }
        });
        HetDeviceUpdateApi.getInstance().start();
    }

    /**
     * 初始提交数据
     */
    private void initSubmitData() {
        HetDeviceSubmitApi.getInstance().init(deviceModel, this, new ISubmitUpdateView() {
            @Override
            public void submitFailure(int code, String msg, String json) {
                showToast(code + msg);
            }

            @Override
            public void submitSuccess(String s) {
                showToast(s);
            }
        });
        HetDeviceSubmitApi.getInstance().start();
    }

    private void initData() {
        humidifierDev = new HumidifierDev();
        initUpdateData();
        initSubmitData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (deviceModel != null) {
            initData();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        HetDeviceUpdateApi.getInstance().stop();
        HetDeviceSubmitApi.getInstance().stop();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HetDeviceUpdateApi.getInstance().destory();
        HetDeviceSubmitApi.getInstance().destory();
    }

    private void initView() {
        getSupportActionBar().setTitle("加湿器设备控制");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        mainLl = (LinearLayout) findViewById(R.id.mainLll);
        closedLl = (LinearLayout) findViewById(R.id.closedLl);
        openedLl = (LinearLayout) findViewById(R.id.openedLl);
        togglePowerBtn = (Button) findViewById(R.id.togglePowerBtn);
        openBtn = (Button) findViewById(R.id.openBtn);
        lightRe = (RelativeLayout) findViewById(R.id.lightRe);
        lightIv = (ImageView) findViewById(R.id.lightIv);
        lightTv = (TextView) findViewById(R.id.lightTv);
        mistRe = (RelativeLayout) findViewById(R.id.mistRe);
        mistIv = (ImageView) findViewById(R.id.mistIv);
        mistTv = (TextView) findViewById(R.id.mistTv);
        timeTv = (TextView) findViewById(R.id.timeTv);
        orderSB = (SeekBar) findViewById(R.id.orderSB);
        orderBtn = (Button) findViewById(R.id.orderBtn);
        humidifierIv = (ImageView) findViewById(R.id.humidifierIv);
        redIv = (ImageView) findViewById(R.id.redIv);
        pinkIv = (ImageView) findViewById(R.id.pinkIv);
        blueIv = (ImageView) findViewById(R.id.blueIv);
        skyblueIv = (ImageView) findViewById(R.id.skyblueIv);
        greenIv = (ImageView) findViewById(R.id.greenIv);
        buffIv = (ImageView) findViewById(R.id.buffIv);
        yellowIv = (ImageView) findViewById(R.id.yellowIv);
        closed_TimeTv = (TextView) findViewById(R.id.closed_TimeTv);
        closed_orderBtn = (Button) findViewById(R.id.closed_orderBtn);
        hulueBtn = (Button) findViewById(R.id.hulueBtn);
        notificationTv = (TextView) findViewById(R.id.notificationTv);
        notificationRe = (RelativeLayout) findViewById(R.id.notificationRe);
        closed_TimeTv.setOnClickListener(this);
        closed_orderBtn.setOnClickListener(this);
        orderBtn.setOnClickListener(this);
        togglePowerBtn.setOnClickListener(this);
        openBtn.setOnClickListener(this);
        timeTv.setOnClickListener(this);
        mistRe.setOnClickListener(this);
        lightRe.setOnClickListener(this);
        redIv.setOnClickListener(this);
        pinkIv.setOnClickListener(this);
        blueIv.setOnClickListener(this);
        skyblueIv.setOnClickListener(this);
        greenIv.setOnClickListener(this);
        buffIv.setOnClickListener(this);
        yellowIv.setOnClickListener(this);
        hulueBtn.setOnClickListener(this);

        orderSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
//                if (humidifierDev != null) {
//                    list.clear();
//                    list.add(4);
//                    HumidifierConfigModel config = humidifierDev.setTimerPresetTime(String.valueOf(arg0.getProgress()), list);
//                    try {
//                        mSubmitProxy.submit(ModelUtils.Model2Json(config));
//                        if (arg0.getProgress() != 0){
//                            PromptUtil.showToast(context, "定时" + arg0.getProgress() + "分钟后关闭！");
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, final int arg1, boolean arg2) {
            }
        });
    }


    private HandlerUtil.StaticHandler mStableHandler = new HandlerUtil.StaticHandler(
            mMessageListener);

    private void sendMsg(int value, String msg) {
        if (mStableHandler != null) {
            mStableHandler.obtainMessage(value, msg).sendToTarget();
        }

    }

    /**
     * 下发数据给设备
     * @param json
     * @return
     */
    @Override
    public byte[] paserJson2Byte(String json) {
        HumidifierConfigModel conf = GsonUtil.getGsonInstance().fromJson(json, HumidifierConfigModel.class);
        byte[] bytes = HumidifierOutPacket.toConfigBytes(conf);
        return bytes;
    }

    /**
     * 收到设备数据解析
     * @param cmd
     * @param bytes
     * @return
     */
    @Override
    public String paserByte2Json(int cmd, byte[] bytes) {
        String obj = null;
        if (cmd == 1) {
            obj =GsonUtil.getGsonInstance().toJson(HumidifierInPacket.toConfigModel(bytes));
        } else if (cmd == 2) {
            Log.e("paserByte2Json", Arrays.toString(bytes));
            obj = GsonUtil.getGsonInstance().toJson(HumidifierInPacket.toRunModel(bytes));
            Log.e("paserByte2Json obj ", obj);
        }
        return obj;
    }

    /*
    * 刷新界面灯的颜色 code 1:redCode code 2:buffCode code 3:yellowCode code
    * 4:greenCode code 5:skyblueCode code 6:blueCode code 7:pinkCode
    */
    private void refreshLightColor(String Color) {
        int code = 0;
        if (StringUtils.isNum(Color)){
            code = Integer.parseInt(Color);
        }
        if (code == 1) {
            redIv.setBackgroundResource(R.mipmap.iv_red_selected);
            buffIv.setBackgroundResource(R.mipmap.iv_buff);
            yellowIv.setBackgroundResource(R.mipmap.iv_yellow);
            greenIv.setBackgroundResource(R.mipmap.iv_green);
            skyblueIv.setBackgroundResource(R.mipmap.iv_skyblue);
            blueIv.setBackgroundResource(R.mipmap.iv_blue);
            pinkIv.setBackgroundResource(R.mipmap.iv_pink);
            humidifierIv.setBackgroundResource(R.mipmap.humidifier_red_icon);
        } else if (code == 2) {
            buffIv.setBackgroundResource(R.mipmap.iv_buff_selected);
            redIv.setBackgroundResource(R.mipmap.iv_red);
            yellowIv.setBackgroundResource(R.mipmap.iv_yellow);
            greenIv.setBackgroundResource(R.mipmap.iv_green);
            skyblueIv.setBackgroundResource(R.mipmap.iv_skyblue);
            blueIv.setBackgroundResource(R.mipmap.iv_blue);
            pinkIv.setBackgroundResource(R.mipmap.iv_pink);
            humidifierIv.setBackgroundResource(R.mipmap.humidifier_orange_icon);
        } else if (code == 3) {
            yellowIv.setBackgroundResource(R.mipmap.iv_yellow_selected);
            redIv.setBackgroundResource(R.mipmap.iv_red);
            buffIv.setBackgroundResource(R.mipmap.iv_buff);
            greenIv.setBackgroundResource(R.mipmap.iv_green);
            skyblueIv.setBackgroundResource(R.mipmap.iv_skyblue);
            blueIv.setBackgroundResource(R.mipmap.iv_blue);
            pinkIv.setBackgroundResource(R.mipmap.iv_pink);
            humidifierIv.setBackgroundResource(R.mipmap.humidifier_yellow_icon);
        } else if (code == 4) {
            greenIv.setBackgroundResource(R.mipmap.iv_green_selected);
            redIv.setBackgroundResource(R.mipmap.iv_red);
            buffIv.setBackgroundResource(R.mipmap.iv_buff);
            yellowIv.setBackgroundResource(R.mipmap.iv_yellow);
            skyblueIv.setBackgroundResource(R.mipmap.iv_skyblue);
            blueIv.setBackgroundResource(R.mipmap.iv_blue);
            pinkIv.setBackgroundResource(R.mipmap.iv_pink);
            humidifierIv.setBackgroundResource(R.mipmap.humidifier_green_icon);
        } else if (code == 5) {
            skyblueIv.setBackgroundResource(R.mipmap.iv_skyblue_selected);
            redIv.setBackgroundResource(R.mipmap.iv_red);
            buffIv.setBackgroundResource(R.mipmap.iv_buff);
            yellowIv.setBackgroundResource(R.mipmap.iv_yellow);
            greenIv.setBackgroundResource(R.mipmap.iv_green);
            blueIv.setBackgroundResource(R.mipmap.iv_blue);
            pinkIv.setBackgroundResource(R.mipmap.iv_pink);
            humidifierIv.setBackgroundResource(R.mipmap.humidifier_cyan_icon);
        } else if (code == 6) {
            blueIv.setBackgroundResource(R.mipmap.iv_blue_selected);
            redIv.setBackgroundResource(R.mipmap.iv_red);
            buffIv.setBackgroundResource(R.mipmap.iv_buff);
            yellowIv.setBackgroundResource(R.mipmap.iv_yellow);
            greenIv.setBackgroundResource(R.mipmap.iv_green);
            skyblueIv.setBackgroundResource(R.mipmap.iv_skyblue);
            pinkIv.setBackgroundResource(R.mipmap.iv_pink);
            humidifierIv.setBackgroundResource(R.mipmap.humidifier_blue_icon);
        } else if (code == 7) {
            pinkIv.setBackgroundResource(R.mipmap.iv_pink_selected);
            redIv.setBackgroundResource(R.mipmap.iv_red);
            buffIv.setBackgroundResource(R.mipmap.iv_buff);
            yellowIv.setBackgroundResource(R.mipmap.iv_yellow);
            greenIv.setBackgroundResource(R.mipmap.iv_green);
            skyblueIv.setBackgroundResource(R.mipmap.iv_skyblue);
            blueIv.setBackgroundResource(R.mipmap.iv_blue);
            humidifierIv.setBackgroundResource(R.mipmap.humidifier_violet_icon);
        }
    }

    /*
     * UI打开状态，设备运行状态
     */
    private void runnable() {
        Animation animal_in1 = AnimationUtils.loadAnimation(this,
                R.anim.slide_up_in);
        Animation animal_out2 = AnimationUtils.loadAnimation(this,
                R.anim.slide_down_out);
        closedLl.startAnimation(animal_out2);
        openedLl.startAnimation(animal_in1);
        closedLl.setVisibility(View.GONE);
        openedLl.setVisibility(View.VISIBLE);
//        mCustomTitle.setBackgroundColor(getResources().getColor(
//                R.color.humidifier_bg_color));
        isOpened = true;
    }

    /*
     * UI关闭状态，设备关闭状态
     */
    private void closed() {
        Animation animal_in = AnimationUtils.loadAnimation(this,
                R.anim.slide_up_in);
        Animation animal_out = AnimationUtils.loadAnimation(this,
                R.anim.slide_down_out);
        closedLl.startAnimation(animal_in);
        openedLl.startAnimation(animal_out);
        openedLl.setVisibility(View.GONE);
        closedLl.setVisibility(View.VISIBLE);
//        mCustomTitle.setBackgroundColor(getResources().getColor(
//                R.color.close_bg_color));
        isOpened = false;
    }

    /**
     * 下发命令
     * @param json
     */
    private void submit(String json){
        try {
            HetDeviceSubmitApi.getInstance().submit(json);
        } catch (Exception e) {
            showToast(e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        if (humidifierDev== null || humidifierDev.getConfig()== null){
            return;
        }
        String json=null;
        HumidifierConfigModel config;
        switch (v.getId()){
            case   R.id.togglePowerBtn:
                refreshLightMode("3");
                refreshMistMode("3");
                closed();
                list.clear();
                list.add(0);
                list.add(1);
                list.add(4);
                config = humidifierDev.closeDevice(context, list);
                json =GsonUtil.getGsonInstance().toJson(config);
                submit(json);
                break;
            case R.id.openBtn:
                refreshLightMode("1");
                refreshMistMode("1");
                runnable();
                list.clear();
                list.add(0);
                list.add(1);
                config = humidifierDev.openDevice(list);
                json =GsonUtil.getGsonInstance().toJson(config);
                submit(json);
                break;
            case R.id.lightRe:
               config = humidifierDev.getConfig();
                if (config != null) {
                    String lightStr = config.getLight();
                    list.clear();
                    list.add(1);
                    HumidifierConfigModel humidifierConfig;
                    if ("1".equals(lightStr)) {
                        refreshLightMode("2");
                        humidifierConfig = humidifierDev.setBrightness(HumidifierDev.Brightness.BRIGHTNESS_HALF, list);
                    } else if ("2".equals(lightStr)) {
                        refreshLightMode("3");
                        humidifierConfig = humidifierDev.setBrightness(HumidifierDev.Brightness.BRIGHTNESS_CLOSE, list);
                        if ("2".equals(config.getMist())){
                            closed();
                        }
                    } else {
                        refreshLightMode("1");
                        humidifierConfig = humidifierDev.setBrightness(HumidifierDev.Brightness.BRIGHTNESS_FULL, list);
                    }
                    json =GsonUtil.getGsonInstance().toJson( humidifierConfig);
                    submit(json);
                }
                break;
            case R.id.mistRe:
               config = humidifierDev.getConfig();
                if (config != null){
                    HumidifierConfigModel humidifierConfig = humidifierDev.getConfig();
                    list.clear();
                    String mistStr = config.getMist();
                    String level = config.getLevel();
                    if ("1".equals(mistStr)){
                        if ("1".equals(level)){
                            list.add(2);
                            humidifierConfig = humidifierDev.setlevel("2");
                            humidifierConfig = humidifierDev.setMist("1", list);
                            refreshMistMode("2");
                        }else{
                            list.add(0);
                            humidifierConfig = humidifierDev.setMist("2",list);
                            refreshMistMode("3");
                            if ("3".equals(config.getLight())){
                                closed();
                            }
                        }
                    }else{
                        list.add(0);
                        list.add(2);
                        humidifierConfig = humidifierDev.setlevel("1");
                        humidifierConfig = humidifierDev.setMist("1", list);
                        refreshMistMode("1");
                    }
                    json =GsonUtil.getGsonInstance().toJson(humidifierConfig);
                    submit(json);

                }
                break;
            case R.id.redIv:
                list.clear();
                list.add(3);
                refreshLightColor("1");
                config = humidifierDev.setHumidifierColor("1", list);
                json =GsonUtil.getGsonInstance().toJson(config);
                submit(json);
                break;
            case R.id.pinkIv:
                list.clear();
                list.add(3);
                refreshLightColor("7");
                config = humidifierDev.setHumidifierColor("7", list);
                json =GsonUtil.getGsonInstance().toJson(config);
                submit(json);
                break;
            case R.id.blueIv:
                list.clear();
                list.add(3);
                refreshLightColor("6");
                config = humidifierDev.setHumidifierColor("6", list);
                json =GsonUtil.getGsonInstance().toJson(config);
                submit(json);
                break;
            case R.id.skyblueIv:
                list.clear();
                list.add(3);
                refreshLightColor("5");
                config = humidifierDev.setHumidifierColor("5", list);
                json =GsonUtil.getGsonInstance().toJson(config);
                submit(json);
                break;
            case R.id.greenIv:
                list.clear();
                list.add(3);
                refreshLightColor("4");
                config = humidifierDev.setHumidifierColor("4", list);
                json =GsonUtil.getGsonInstance().toJson(config);
                submit(json);
                break;
            case R.id.buffIv:
                list.clear();
                list.add(3);
                refreshLightColor("2");
                config= humidifierDev.setHumidifierColor("2", list);
                json =GsonUtil.getGsonInstance().toJson(config);
                submit(json);
                break;
            case R.id.yellowIv:
                list.clear();
                list.add(3);
                refreshLightColor("3");
                config = humidifierDev
                        .setHumidifierColor("3", list);
                json =GsonUtil.getGsonInstance().toJson(config);
                submit(json);
                break;
            case R.id.hulueBtn:
                notificationRe.setVisibility(View.GONE);
                break;

        }

    }

    /**
     * 更新运行数据UI
     * */
    private void updateRunDataUI(String json){
        HumidifierRunDataModel runDataModel = GsonUtil.getGsonInstance().fromJson(json, HumidifierRunDataModel.class);
        if (runDataModel!=null){
            humidifierDev.setRunData(runDataModel);
            humidifierDev.setConfig(run2Confg(runDataModel));
            if ("2".equals(runDataModel.getMist()) && "3".equals(runDataModel.getLight())) {
                if (isOpened){
                    closed();
                }
            } else {
//                closedLl.setVisibility(View.GONE);
//                openedLl.setVisibility(View.VISIBLE);
                if (!isOpened){
                    runnable();
                }
            }
            //灯亮度设置
            refreshLightMode(runDataModel.getLight());
            //等颜色设置
            refreshLightColor(runDataModel.getColor());
            //雾化设置
            refreshMistMode(runDataModel.getMist());
            if ("1".equals(runDataModel.getMist())){
                if ("1".equals(runDataModel.getLevel())){
                    refreshMistMode("1");
                }else if ("2".equals(runDataModel.getLevel())){
                    refreshMistMode("2");
                }else{
                    refreshMistMode("3");
                }
            }else{
                refreshMistMode("3");
            }
            //定时设置
            if (StringUtils.isNum(runDataModel.getRemainingTime())){
                orderSB.setProgress(Integer.parseInt(runDataModel.getRemainingTime()));
            }

            // 0正常 1异常
            if (!TextUtils.isEmpty(runDataModel.getWarningStatus())) {
                if (runDataModel.getWarningStatus().equals("1")) {
                    isLackWater = true;
                    notificationRe.setVisibility(View.VISIBLE);
                    hulueBtn.setVisibility(View.GONE);
                    notificationTv.setText(getResources().getString(R.string.water_shortage_notification1));
                } else if (runDataModel.getWarningStatus().equals("0")) {
                    if (isLackWater) {
                        notificationRe.setVisibility(View.GONE);
                    }
                }

            }

            if (!TextUtils.isEmpty(runDataModel.getRemainingTime())) {
                int remainingTime = Integer.valueOf(runDataModel.getRemainingTime());
                orderSB.setProgress(remainingTime);
            }
        }
    }

    /*
   * 刷新界面灯的模式 code 1:全亮 code 2:半亮 code 3:关闭
   */
    private void refreshLightMode(String Light) {
        int code = 0;
        if (StringUtils.isNum(Light)){
            code = Integer.parseInt(Light);
        }
        if (code == 1) {
            lightIv.setBackgroundResource(R.mipmap.iv_humidifier_lighting_on);
            lightTv.setText("全亮灯");
            humidifierIv.setAlpha(1f);
        } else if (code == 2) {
            lightIv.setBackgroundResource(R.mipmap.iv_humidifier_half_lighting_on);
            lightTv.setText("半亮灯");
            humidifierIv.setAlpha(0.5f);
        } else if (code == 3) {
            lightIv.setBackgroundResource(R.mipmap.humidifier_on);
            lightTv.setText("关灯");
            humidifierIv.setAlpha(0f);
        }
    }


    /*
     * 刷新界面灯的模式 code 1:全喷 code 2:半喷 code 3:关闭
     */
    private void refreshMistMode(String mist) {
        int code = 0;
        if (StringUtils.isNum(mist)){
            code = Integer.parseInt(mist);
        }
        if (code == 1) {
            mistIv.setBackgroundResource(R.mipmap.iv_mist_all);
            mistTv.setText("全雾化");
        } else if (code == 2 ) {
            mistIv.setBackgroundResource(R.mipmap.iv_mist_half);
            mistTv.setText("半雾化");
        } else if (code == 3) {
            mistIv.setBackgroundResource(R.mipmap.iv_mist_off);
            mistTv.setText("关闭");
        }
    }

    /**
     * 运行转配置数据
     * @param runDataModel
     * @return
     */
    private HumidifierConfigModel run2Confg (HumidifierRunDataModel runDataModel){
        HumidifierConfigModel humidifierConfigModel;
        if (humidifierDev.getConfig() == null){
            humidifierConfigModel = new HumidifierConfigModel();
        }else{
            humidifierConfigModel = humidifierDev.getConfig();
        }
        if (runDataModel!= null&&isFrist){
            humidifierConfigModel.setMist(runDataModel.getMist());
            humidifierConfigModel.setColor(runDataModel.getColor());
            humidifierConfigModel.setLight(runDataModel.getLight());
            humidifierConfigModel.setLevel(runDataModel.getLevel());
            humidifierConfigModel.setTimerPresetTime(runDataModel.getRemainingTime());
            isFrist = false;
        }
        return humidifierConfigModel;
    }

}
