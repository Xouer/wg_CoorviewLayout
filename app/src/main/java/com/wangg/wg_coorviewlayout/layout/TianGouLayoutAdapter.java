package com.wangg.wg_coorviewlayout.layout;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.wangg.wg_coorviewlayout.R;
import com.wangg.wg_coorviewlayout.bean.PictrueMessageContentBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/22.
 */
public class TianGouLayoutAdapter extends RecyclerView.Adapter<TianGouLayoutAdapter.MyViewHolder>{

    private  List<PictrueMessageContentBean> ListData;
    Map<String,Integer> heightMap = new HashMap<>();
    static Map<String,Integer> widthMap = new HashMap<>();

    public TianGouLayoutAdapter(List<PictrueMessageContentBean> listData){
        this.ListData = listData;
    }

    @Override
    public TianGouLayoutAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pictrue,
                parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TianGouLayoutAdapter.MyViewHolder holder, int position) {
//        Log.e("wg_log", "Listdata:" + ListData.get(position));
        final String url = ListData.get(position).getPictrueImage();
        final long imageId = ListData.get(position).getID();
        if(heightMap.containsKey(url)){
            int height = heightMap.get(url);
            FLog.i("kaede", url+ "'s height = " + height);
            if(height > 0){
                updateItemtHeight(height, holder.itemView);
                holder.mSimpleDraweeView.setImageURI(Uri.parse(url));
                return;
            }
        }

    //监听加载过程
    ControllerListener controllerListener = new BaseControllerListener<ImageInfo>(){

        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            if (imageInfo == null) {
                return;
            }

            QualityInfo qualityInfo = imageInfo.getQualityInfo();
            if (qualityInfo.isOfGoodEnoughQuality()){
                int heightTarget = (int) getTargetHeight(imageInfo.getWidth(),imageInfo.getHeight(),holder.itemView,url);
                FLog.i("kaede", "heightTarget = " + heightTarget);
                if (heightTarget<=0)return;
                heightMap.put(url,heightTarget);
                updateItemtHeight(heightTarget, holder.itemView);
            }
        }

        @Override
        public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
            super.onFailure(id, throwable);
            Log.e("wg_log", "图片加载失败：" + id);
        }
    };

    DraweeController controller = Fresco.newDraweeControllerBuilder()
            .setUri(Uri.parse(url))
            .setControllerListener(controllerListener)
            .setTapToRetryEnabled(true)   //点击重新加载图片
            .build();

    holder.mSimpleDraweeView.setController(controller);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("wg_log", "ID值=" + imageId );
            }
        });
    }

    private float getTargetHeight(float width,float height,View view, String url){
        View child = view.findViewById(R.id.drawee_img);
        float widthTarget;
        if (widthMap.containsKey(url)) widthTarget = widthMap.get(url);
        else {
            widthTarget = child.getMeasuredWidth();
            if (widthTarget>0){
                widthMap.put(url, (int) widthTarget);
            }
        }

        FLog.i("kaede","child.getMeasuredWidth() = " + widthTarget);
		/*int getWidth = child.getWidth();
		int getMeasuredWidth = child.getMeasuredWidth();
		int getLayoutParamsWidth = child.getLayoutParams().width;
		if (getWidth==0||getMeasuredWidth==0||getLayoutParamsWidth==0){
			FLog.i("kaede","child.getWidth() = " + getWidth);
			FLog.i("kaede","child.getMeasuredWidth() = " + getMeasuredWidth);
			FLog.i("kaede","child.getLayoutParams().width = " + getLayoutParamsWidth);
		}*/
        return height * (widthTarget /width);
    }


    private void updateItemtHeight(int height, View view) {
        CardView cardView = (CardView) view.findViewById(R.id.cardview);
        View child = view.findViewById(R.id.drawee_img);
        CardView.LayoutParams layoutParams = (CardView.LayoutParams) child.getLayoutParams();
        layoutParams.height = height;
        cardView.updateViewLayout(child,layoutParams);
    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private  SimpleDraweeView mSimpleDraweeView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mSimpleDraweeView =  (SimpleDraweeView) itemView.findViewById(R.id.drawee_img);

//            mSimpleDraweeView.setAspectRatio(0.9F);
//            mSimpleDraweeView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
        }
    }
}
