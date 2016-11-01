package com.het.sdk.demo.adapter.scene;

import android.content.Context;
import android.database.DataSetObserver;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.het.open.lib.api.HetSceneApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.open.lib.model.scene.UserSceneModel;
import com.het.open.lib.utils.StringUtils;
import com.het.sdk.demo.R;
import com.het.sdk.demo.widget.ColorToggleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户场景列表adapter
 *
 * @author galis
 */
public class UserSceneListAdapter extends BaseAdapter {

    private static String RUNNING = "1";

    private Context mActivity;

    private Animation operatingAnim;

    private List<UserSceneModel> mUserSceneList = new ArrayList<UserSceneModel>();


    public UserSceneListAdapter(Context activity, List<UserSceneModel> mUserSceneList ) {
        mActivity = activity;
        this.mUserSceneList=mUserSceneList;
        //添加顺时针转动动画
        operatingAnim = AnimationUtils.loadAnimation(mActivity, R.anim.scene_rotate_amim);
        LinearInterpolator lin = new LinearInterpolator();//匀速旋转的属性只能在代码里进行设置
        operatingAnim.setInterpolator(lin);
    }

    @Override
    public int getCount() {
        //wrapSceneList();
        return mUserSceneList.size();
    }

//    private void wrapSceneList() {
//        mUserSceneList.clear();
//        mUserSceneList.addAll(mSceneManager.getUserAllSceneModelList());
//        UserSceneModel draftModel = new UserSceneModel();
//        if (initIfHasDraft(draftModel)) {
//            mUserSceneList.add(draftModel);
//        }
//    }

//    private boolean initIfHasDraft(UserSceneModel draftModel) {
//        String draftPath = mActivity.getCacheDir() + File.separator + SceneDiyAcitivty.FILE_DRAFT;
//        File draft = new File(draftPath);
//        if (draft.exists()) {
//            try {
//                BufferedReader bufferedReader = new BufferedReader(new FileReader(draftPath));
//                String line = bufferedReader.readLine();
//                String[] data = line.split("\t");
//                if (data.length != 3) {
//                    return false;
//                }
//                draftModel.setSceneId("-1");
//                draftModel.setPictureUrl(formatArgs(data[0]));
//                draftModel.setSceneName(formatArgs(data[1]));
//                draftModel.setSummary(formatArgs(data[2]));
//
//                return true;
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }

    private String formatArgs(String arg) {
        return arg.equals("null") ? "" : arg;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }

    @Override
    public Object getItem(int position) {
        return mUserSceneList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.widget_user_scene_list_item, null);
            holder.simpleDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.scene_icon);
            holder.titleView = (TextView) convertView.findViewById(R.id.scene_title);
            holder.draft = (TextView) convertView.findViewById(R.id.scene_title_draft);
            holder.summary = (TextView) convertView.findViewById(R.id.scene_summary);
            holder.colorToggleButton = (ColorToggleButton) convertView.findViewById(R.id.scene_color_button);
            holder.mProgressBar = (ProgressBar) convertView.findViewById(R.id.mProgressBar);
            holder.scene_refresh = (Button) convertView.findViewById(R.id.scene_refresh);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final UserSceneModel userSceneModel = mUserSceneList.get(position);
        if (userSceneModel != null) {
            if (userSceneModel.getPictureUrl() != null) {
                holder.simpleDraweeView.setImageURI(Uri.parse(userSceneModel.getPictureUrl()));
            } else {
                holder.simpleDraweeView.setImageURI(Uri.parse("file://" + userSceneModel.getPictureUrl()));
            }
            if (userSceneModel.getRunStatus().equals("1")) {
//                holder.simpleDraweeView.setAlpha(255);
                holder.simpleDraweeView.startAnimation(operatingAnim);
                holder.draft.setVisibility(View.GONE);
                holder.scene_refresh.setVisibility(View.GONE);
                holder.colorToggleButton.setVisibility(View.VISIBLE);
                holder.colorToggleButton.initState(ColorToggleButton.OPEN);
            } else {
//                holder.simpleDraweeView.setAlpha(100);
                holder.simpleDraweeView.clearAnimation();
                if (!StringUtils.isNull(userSceneModel.getAddedStatus()) && userSceneModel.getAddedStatus().equals("2")) {
                    holder.draft.setVisibility(View.GONE);
                    holder.colorToggleButton.setVisibility(View.GONE);
                    holder.scene_refresh.setVisibility(View.VISIBLE);
                } else {
                    if (!StringUtils.isNull(userSceneModel.getValidity()) && userSceneModel.getValidity().equals("0")) {
                        holder.colorToggleButton.setVisibility(View.GONE);
                        holder.scene_refresh.setVisibility(View.GONE);
                        holder.draft.setVisibility(View.VISIBLE);
                    } else {
                        holder.draft.setVisibility(View.GONE);
                        holder.scene_refresh.setVisibility(View.GONE);
                        holder.colorToggleButton.setVisibility(View.VISIBLE);
                        holder.colorToggleButton.initState(ColorToggleButton.CLOSE);
                    }
                }
            }
            holder.summary.setText(userSceneModel.getSummary());
            holder.titleView.setText(userSceneModel.getSceneName() + "");
        }
        holder.colorToggleButton.setOnCheckedChangeListener(new ColorToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChange(ColorToggleButton button, boolean isChecked) {
                notifyDataSetChanged();
            }
        });

        holder.scene_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mProgressBar.setVisibility(View.VISIBLE);
                holder.scene_refresh.setVisibility(View.GONE);
                HetSceneApi.getInstance().sceneReAdaptation(new IHetCallback() {
                    @Override
                    public void onSuccess(int code, String msg) {
                        holder.mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        holder.mProgressBar.setVisibility(View.GONE);
                        holder.scene_refresh.setVisibility(View.VISIBLE);
                    }
                },null, null, userSceneModel.getSceneId());
//                HetSceneApi.getInstance().sceneReAdaptation(new IHetCallbackCallback<UserSceneIdModel>() {
//                    @Override
//                    public void onSuccess(UserSceneIdModel userSceneIdModel, int id) {
//
//                        //更新完成后再请求全部场景的数据
//                       // mSceneManager.requestUserSceneList(mICallback);
//                    }
//
//                    @Override
//                    public void onFailure(int code, String msg, int id) {
//
//                        PromptUtil.showShortToast(mActivity, msg);
//                    }
//                }, null, null, userSceneModel.getSceneId(), -1);
            }
        });

        holder.colorToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mProgressBar.setVisibility(View.VISIBLE);
                holder.colorToggleButton.setVisibility(View.GONE);
                if (isSceneOpened(userSceneModel)) {
//                    mSceneManager.stopScene(userSceneModel.getUserSceneId(), new ICallback() {
//                        @Override
//                        public void onSuccess(Object o, int id) {
////                            mActivity.hideDialog();
//                            holder.mProgressBar.setVisibility(View.GONE);
//                            holder.colorToggleButton.setVisibility(View.VISIBLE);
//                            holder.colorToggleButton.close();
//                        }
//
//                        @Override
//                        public void onFailure(int code, String msg, int id) {
//                            holder.mProgressBar.setVisibility(View.GONE);
//                            holder.colorToggleButton.setVisibility(View.VISIBLE);
//                            PromptUtil.showShortToast(mActivity, TextUtils.isEmpty(msg) ? mActivity.getString(R.string.stop_scene_fail) : msg);
//                        }
//                    });
                } else {
//                    mSceneManager.startScence(userSceneModel.getUserSceneId(), new ICallback() {
//                        @Override
//                        public void onSuccess(Object o, int id) {
//                            holder.colorToggleButton.open();
//                            holder.mProgressBar.setVisibility(View.GONE);
//                            holder.colorToggleButton.setVisibility(View.VISIBLE);
//                            //开启完成后再请求全部场景的数据
//                            mSceneManager.requestUserSceneList(mICallback);
//                        }
//
//                        @Override
//                        public void onFailure(int code, String msg, int id) {
//                            holder.mProgressBar.setVisibility(View.GONE);
//                            holder.colorToggleButton.setVisibility(View.VISIBLE);
//                            PromptUtil.showShortToast(mActivity, TextUtils.isEmpty(msg) ? mActivity.getString(R.string.start_scene_fail) : msg);
//                        }
//                    });
                }
            }
        });

        return convertView;
    }

    private boolean isSceneOpened(UserSceneModel userSceneModel) {
        return userSceneModel.getRunStatus().equals(RUNNING);
    }

    class Holder {
        public SimpleDraweeView simpleDraweeView;
        public TextView titleView;
        public TextView summary;
        public TextView draft;
        public ColorToggleButton colorToggleButton;
        public ProgressBar mProgressBar;
        public Button scene_refresh;
    }

}
