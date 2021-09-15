package com.dispatch.system.module.home.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dispatch.system.R;
import com.dispatch.system.base.BaseActivity;
import com.dispatch.system.base.BaseRvAdapter;
import com.dispatch.system.common.ExpressHelper;
import com.dispatch.system.constants.SPConstants;
import com.dispatch.system.entity.CheckRefuseBean;
import com.dispatch.system.entity.MemberBean;
import com.dispatch.system.event.AddStorageEvent;
import com.dispatch.system.http.ApiClient;
import com.dispatch.system.http.MyObserver;
import com.dispatch.system.utils.BusinessHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * 搜索会员结果
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/19
 */
public class SearchMemberAdapter extends BaseRvAdapter<SearchMemberAdapter.VH> {

    private List<MemberBean> dataList;
    /**
     * 快递编号
     */
    private String expressNum;
    private String business;

    private MediaPlayer mediaPlayer;

    public SearchMemberAdapter(Context context, List<MemberBean> dataList) {
        super(context);
        this.dataList = dataList;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }
    public void setBusiness(String business) {
        this.business = business;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(getView(R.layout.item_scan_result_user_message, parent));
    }

    private OnConfirmListener onConfirmListener;

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    public interface OnConfirmListener {
        boolean onConfirm();
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        MemberBean bean = dataList.get(position);
        holder.tvConfirm.setText(TextUtils.isEmpty(bean.getExpressNum()) ? "确定" : "已绑定");
        holder.tvName.setText(bean.getRealName());
        holder.tvNoVip.setVisibility(bean.isMember() ? View.GONE : View.VISIBLE);
        holder.tvPhone.setText(String.format("手机号码：%s", bean.getMobileNumber()));
        String houseNumber = bean.getHouseNumber();
        if (houseNumber == null) {
            houseNumber = "";
        }
        holder.tvAddress.setText(String.format("收货地址：%s %s", bean.getBuildingName(), houseNumber));
        holder.tvConfirm.setOnClickListener(v -> {
            if (onConfirmListener != null) {
                if (onConfirmListener.onConfirm()) {
                    return;
                }
            }

            String str = SPUtils.getInstance().getString(SPConstants.SP_ADD_STORAGE, "");
            if (!TextUtils.isEmpty(str)) {
                List<MemberBean> memberBeanList = new Gson().fromJson(str, new TypeToken<List<MemberBean>>() {
                }.getType());
                if (memberBeanList != null && !memberBeanList.isEmpty()) {
                    for (int i = 0; i < memberBeanList.size(); i++) {
                        if (Objects.equals(memberBeanList.get(i).getExpressNum(), expressNum)) {
                            ToastUtils.showLong("添加失败，单号已重复存在！");
                            return;
                        }
                    }
                }
            }

            if (mContext instanceof BaseActivity) {
                ((BaseActivity) mContext).showLoading();
            }
            ApiClient.getInstance().checkDelivery(expressNum, bean.getCode(), new MyObserver<CheckRefuseBean>() {
                @Override
                protected void getDisposable(Disposable d) {
                }

                @Override
                protected void onSuccess(CheckRefuseBean checkRefuseBean) {
                    if (mContext instanceof BaseActivity) {
                        ((BaseActivity) mContext).hideLoading();
                    }
                    if (checkRefuseBean.getData().isIsMemberRefuse()) {
                        ToastUtils.showLong(" 拒收件：" + checkRefuseBean.getData().getRefuseDesc());
                        bean.setExpressNum(expressNum);
                        if (TextUtils.isEmpty(business)) {
                            bean.setBusiness(BusinessHelper.getKey(ExpressHelper.getExpressName(expressNum)));
                        } else {
                            bean.setBusiness(business);
                        }
                        business = null;
                        EventBus.getDefault().post(new AddStorageEvent(bean));
                        notifyDataSetChanged();
                    } else {
                        holder.tvConfirm.setText("已绑定");
                        bean.setExpressNum(expressNum);
                        if (TextUtils.isEmpty(business)) {
                            bean.setBusiness(BusinessHelper.getKey(ExpressHelper.getExpressName(expressNum)));
                        } else {
                            bean.setBusiness(business);
                        }
                        business = null;
                        EventBus.getDefault().post(new AddStorageEvent(bean));
                        notifyDataSetChanged();

                        if ("DELIVERY".equals(checkRefuseBean.getData().getDeliveryMethod())) {
                            playMusic(false);
                        } else if ("SELF".equals(checkRefuseBean.getData().getDeliveryMethod())) {
                            playMusic(true);
                        }
                    }

                    handler.postDelayed(() -> {
                        KeyboardUtils.hideSoftInput((BaseActivity)mContext);
                        ((Activity) mContext).finish();
                    }, 500);
                }

                @Override
                protected void onError(String msg) {
                    if (mContext instanceof BaseActivity) {
                        ((BaseActivity) mContext).hideLoading();
                    }
                }
            });
        });
    }

    private void playMusic(boolean isSelf) {
        stopMusic();

        mediaPlayer = new MediaPlayer();
        AssetFileDescriptor file = mContext.getResources().openRawResourceFd(
                isSelf ? R.raw.self_delivery_volume : R.raw.sender_volume);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file
                    .getLength());
            // 异步准备
            mediaPlayer.prepare();
            mediaPlayer.start();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private static Handler handler = new Handler();

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvNoVip)
        TextView tvNoVip;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.tvPhone)
        TextView tvPhone;
        @BindView(R.id.tvConfirm)
        TextView tvConfirm;

        public VH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void updateUI(MemberBean bean, String expressNum) {

        }

    }
}

