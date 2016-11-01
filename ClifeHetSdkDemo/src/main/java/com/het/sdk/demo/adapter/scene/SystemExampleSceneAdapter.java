package com.het.sdk.demo.adapter.scene;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.het.open.lib.model.scene.SystemSceneModel;
import com.het.open.lib.utils.Configs;
import com.het.open.lib.utils.HetSharePreferencesUtil;
import com.het.sdk.demo.R;
import com.het.sdk.demo.widget.ColorToggleButton;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by martin
 */
public class SystemExampleSceneAdapter extends BaseAdapter {

    private Context mActivity;
    private List<SystemSceneModel> systemSceneModelList=new ArrayList<>();
    private Animation operatingAnim;

    public SystemExampleSceneAdapter(Context activity,List<SystemSceneModel> systemSceneModelList) {
        mActivity = activity;
        this.systemSceneModelList=systemSceneModelList;
        //添加顺时针转动动画
        operatingAnim = AnimationUtils.loadAnimation(mActivity, R.anim.scene_rotate_amim);
        LinearInterpolator lin = new LinearInterpolator();//匀速旋转的属性只能在代码里进行设置
        operatingAnim.setInterpolator(lin);
    }

    @Override
    public int getCount() {
        return systemSceneModelList.size() > 2 ? 2 : systemSceneModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return systemSceneModelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.widget_user_scene_list_item, null);
            holder.simpleDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.scene_icon);
            holder.titleView = (TextView) convertView.findViewById(R.id.scene_title);
            holder.draft = (TextView) convertView.findViewById(R.id.scene_title_draft);
            holder.summary = (TextView) convertView.findViewById(R.id.scene_summary);
            holder.colorToggleButton = (ColorToggleButton) convertView.findViewById(R.id.scene_color_button);
            holder.mProgressBar = (ProgressBar) convertView.findViewById(R.id.mProgressBar);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        SystemSceneModel systemSceneModel = systemSceneModelList.get(i);
        Boolean flag = i == 0 ? HetSharePreferencesUtil.getBoolean(mActivity, Configs.TESTSYSTEMSCENE_ONE) : HetSharePreferencesUtil.getBoolean(mActivity, Configs.TESTSYSTEMSCENE_TWO);
        setData(i, flag, systemSceneModel, holder);

        return convertView;
    }

    private void setData(final int i, final boolean flag, SystemSceneModel systemSceneModel, final Holder holder) {
        if (systemSceneModel != null) {
            if (systemSceneModel.getPictureUrl() != null) {
                holder.simpleDraweeView.setImageURI(Uri.parse(systemSceneModel.getPictureUrl()));
            } else {
                holder.simpleDraweeView.setImageURI(Uri.parse("file://" + systemSceneModel.getPictureUrl()));
            }
            holder.summary.setText(systemSceneModel.getSummary());
            holder.titleView.setText(systemSceneModel.getSceneName() + "");

            if (!flag) {
                holder.colorToggleButton.initState(ColorToggleButton.OPEN);
                holder.simpleDraweeView.startAnimation(operatingAnim);
            } else {
                holder.colorToggleButton.initState(ColorToggleButton.CLOSE);
                holder.simpleDraweeView.clearAnimation();
            }
        }
        holder.colorToggleButton.setOnCheckedChangeListener(new ColorToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChange(ColorToggleButton button, boolean isChecked) {
                notifyDataSetChanged();
            }
        });

        holder.colorToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    holder.colorToggleButton.close();
                    holder.simpleDraweeView.clearAnimation();
                    if (i == 0) {
                        HetSharePreferencesUtil.putBoolean(mActivity, Configs.TESTSYSTEMSCENE_ONE, true);
                    } else {
                        HetSharePreferencesUtil.putBoolean(mActivity, Configs.TESTSYSTEMSCENE_TWO, true);
                    }
                } else {
                    holder.colorToggleButton.open();
                    holder.simpleDraweeView.startAnimation(operatingAnim);
                    if (i == 0) {
                        HetSharePreferencesUtil.putBoolean(mActivity, Configs.TESTSYSTEMSCENE_ONE, false);
                    } else {
                        HetSharePreferencesUtil.putBoolean(mActivity, Configs.TESTSYSTEMSCENE_TWO, false);
                    }
                }
            }
        });
    }

    class Holder {
        public SimpleDraweeView simpleDraweeView;
        public TextView titleView;
        public TextView summary;
        public TextView draft;
        public ColorToggleButton  colorToggleButton;
        public ProgressBar mProgressBar;
    }
}
