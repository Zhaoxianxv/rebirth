package com.yfy.recycerview;//package com.yfy.view.recyclerview;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.animation.GlideAnimation;
//import com.bumptech.glide.request.target.SimpleTarget;
//import com.yfy.app.SingeBigPicShowActivity;
//import com.yfy.app.login.AstirPic;
//import com.yfy.final_tag.GlideRoundTransform;
//import com.yfy.foreign.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by yfyandr on 2017/11/7.
// */
//
//public class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private List<AstirPic> dataList;
//
//    private Context mContext;
//    public void setDataList(List<AstirPic> dataList) {
//
//        this.dataList = dataList;
//        getRandomHeights(dataList);
//    }
//
//    // 普通布局
//    private final int TYPE_ITEM = 1;
//    // 脚布局
//    private final int TYPE_FOOTER = 2;
//    // 当前加载状态，默认为加载完成
//    private int loadState = 2;
//    // 正在加载
//    public final int LOADING = 1;
//    // 加载完成
//    public final int LOADING_COMPLETE = 2;
//    // 加载到底
//    public final int LOADING_END = 3;
//
//    public LoadMoreAdapter(Context mContext, List<AstirPic> dataList) {
//        this.mContext=mContext;
//        this.dataList = dataList;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        // 最后一个item设置为FooterView
//        if (position + 1 == getItemCount()) {
//            return TYPE_FOOTER;
//        } else {
//            return TYPE_ITEM;
//        }
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        //进行判断显示类型，来创建返回不同的View
//        if (viewType == TYPE_ITEM) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
//            return new RecyclerViewHolder(view);
//
//        } else if (viewType == TYPE_FOOTER) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_refresh_footer, parent, false);
//            return new FootViewHolder(view);
//        }
//        return null;
//    }
//
//    List<Integer> lists;
//    private void getRandomHeights(List<AstirPic> datas) {
//        lists = new ArrayList<>();
//        for (int i = 0; i < datas.size(); i++) {
//            lists.add((int) (200 + Math.random() * 400));
//        }
//    }
//
//    private int heigh;
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof RecyclerViewHolder) {
//            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
//
//
//            Glide.with(mContext)
//                    .load(dataList.get(position).getPic())
//                    .asBitmap()//强制Glide返回一个Bitmap对象
//                    .into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
//                            int width = bitmap.getWidth();
//                            int height = bitmap.getHeight();
//                            heigh=height;
//                        }
//                    });
//
////            ViewGroup.LayoutParams params = recyclerViewHolder.tvItem.getLayoutParams();
////            params.height = heigh;//把随机的高度赋予item布局
////            holder.itemView.setLayoutParams(params);
//            Glide.with(mContext)
//                    .load(dataList.get(position).getPic())
//                    .error(R.mipmap.default_load_error_icon)
//                    .transform(new GlideRoundTransform(mContext))
//                    .placeholder(R.mipmap.app_icon_loading)
//                    .into(recyclerViewHolder.tvItem);
//
//
//            recyclerViewHolder.pic=dataList.get(position);
//
//        } else if (holder instanceof FootViewHolder) {
//            FootViewHolder footViewHolder = (FootViewHolder) holder;
//            switch (loadState) {
//                case LOADING: // 正在加载
//                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
//                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
//                    footViewHolder.llEnd.setVisibility(View.GONE);
//                    break;
//
//                case LOADING_COMPLETE: // 加载完成
//                    footViewHolder.pbLoading.setVisibility(View.INVISIBLE);
//                    footViewHolder.tvLoading.setVisibility(View.INVISIBLE);
//                    footViewHolder.llEnd.setVisibility(View.GONE);
//                    break;
//
//                case LOADING_END: // 加载到底
//                    footViewHolder.pbLoading.setVisibility(View.GONE);
//                    footViewHolder.tvLoading.setVisibility(View.GONE);
//                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
//                    break;
//
//                default:
//                    break;
//            }
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return dataList.size() + 1;
//    }
//
//    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView tvItem;
//        View layout;
//        AstirPic pic;
//        RecyclerViewHolder(View itemView) {
//            super(itemView);
//            tvItem = (ImageView) itemView.findViewById(R.id.image_icon);
//            layout = (View) itemView.findViewById(R.id.recycler_view_layout);
//            layout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    ActivityLauncher.toBrowserActivity(context ,news);
//
//                    Intent intent=new Intent(mContext, SingeBigPicShowActivity.class);
//                    intent.putExtra("pic",pic.getPic());
//                    mContext.startActivity(intent);
//                }
//            });
//        }
//    }
//
//    private class FootViewHolder extends RecyclerView.ViewHolder {
//
//        ProgressBar pbLoading;
//        TextView tvLoading;
//        LinearLayout llEnd;
//
//        FootViewHolder(View itemView) {
//            super(itemView);
//            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
//            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
//            llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
//
//        }
//    }
//
//    /**
//     * 设置上拉加载状态
//     *
//     * @param loadState 0.正在加载 1.加载完成 2.加载到底
//     */
//    public void setLoadState(int loadState) {
//        this.loadState = loadState;
//        notifyDataSetChanged();
//    }
//
//
//    /**
//     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
//     */
////    public static int loadIntoUseFitWidth(Context context, final String imageUrl, int errorImageId, final ImageView imageView) {
////
////        int h;
////
////        Glide.with(context)
////                .load(imageUrl)
////                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
////                .listener(new RequestListener<String, GlideDrawable>() {
////                    @Override
////                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
////                        return false;
////                    }
////
////                    @Override
////                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
////                        if (imageView == null) {
////                            return false;
////                        }
////                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
////                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
////                        }
////                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
////                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
////                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
////                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
////                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
////                        imageView.setLayoutParams(params);
////
////                        return false;
////                    }
////                })
////                .placeholder(R.mipmap.app_icon_loading)
////                .error(errorImageId)
////                .transform(new GlideRoundTransform(context))
////                .into(imageView);
////    }
//}