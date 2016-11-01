package com.het.sdk.demo.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.het.open.lib.model.DeviceSubModel;
import com.het.open.lib.utils.StringUtils;
import com.het.sdk.demo.R;

import java.util.List;

/**
 * 设备小类列表适配器
 */
public class AdapterDeviceSubtypeList extends BaseAdapter {

    private List<DeviceSubModel> deviceModels;
    private Context context;
    private LayoutInflater mInflater;

    public AdapterDeviceSubtypeList(List<DeviceSubModel> deviceModels,
                                    Context context) {
        this.deviceModels = deviceModels;
        this.context = context;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return deviceModels.size() > 0 ? deviceModels.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return deviceModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_choose_bind_device_item,
                    null);
            holder = new ChildViewHolder();
            holder.tvwName = (TextView) convertView.findViewById(R.id.tvw_device_name);
            holder.tvwMac = (TextView) convertView.findViewById(R.id.tvw_mac);
            holder.simpleDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.iv_product_face);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        DeviceSubModel deviceModel = deviceModels.get(position);
        String imageUrl = deviceModel.getProductIcon();
        if (!StringUtils.isNull(imageUrl)) {
            if (holder.simpleDraweeView != null) {
                Uri uri = Uri.parse(imageUrl);
                holder.simpleDraweeView.setImageURI(uri);
            }
        }
        String name = deviceModel.getDeviceSubtypeName();
        if (!StringUtils.isNull(name)) {
            holder.tvwName.setText(name);
            holder.tvwName.invalidate();
        }
        holder.tvwMac.setVisibility(View.VISIBLE);
        String deviceName = deviceModel.getProductName()+"（"+deviceModel.getModuleName()+")";
        if (!StringUtils.isNull(deviceName)) {
            holder.tvwMac.setText(deviceName);
            holder.tvwMac.invalidate();
        }
        return convertView;
    }

    private class ChildViewHolder {
        SimpleDraweeView simpleDraweeView;
        TextView tvwName;
        TextView tvwMac;
    }

}
