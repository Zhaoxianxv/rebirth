package com.yfy.app;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.CompoundButton;
import butterknife.Bind;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.net.*;
import com.yfy.base.Base;
import com.yfy.base.activity.BaseActivity;
import com.yfy.db.GreenDaoManager;
import com.yfy.db.UserPreferences;
import com.yfy.final_tag.*;
import com.yfy.jpush.Logger;
import com.yfy.rebirth.R;
import com.yfy.recycerview.DividerGridItemDecoration;
import com.yfy.recycerview.OnRecyclerItemClickListener;
import com.yfy.recycerview.RecycAnimator;
import com.yfy.view.SQToolBar;
import retrofit2.Call;
import retrofit2.Response;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainSetViewActivity extends BaseActivity {

	private static final String TAG = MainSetViewActivity.class.getSimpleName();

	private MainSetAdapter adapter;





	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_set_view);

		initSQtoobar("设置");
		initRecycler();




	}

    private void initSQtoobar(String title) {
        assert toolbar!=null;
        toolbar.setTitle(title);
        toolbar.addMenuText(TagFinal.ONE_INT,R.string.ok);
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        });

    }



//	private void isDbset(){
//		List<MainIndex> db_index=GreenDaoManager.getInstance().loadAllMainIndex();
//		String index_s=UserPreferences.getInstance().getIndex();
//
//		if (StringJudge.isEmpty(db_index)){
//
//		}else{
//			if (db_index.size()!=30){
//				GreenDaoManager.getInstance().clearMainIndex();
//				UserPreferences.getInstance().saveIndex("");
//				UserPreferences.getInstance().saveFIRST(TagFinal.FALSE);
//				db_index.clear();
//				index_s="";
//			}
//		}
//		Logger.e(UserPreferences.getInstance().getFIRST());
//
//		if (UserPreferences.getInstance().getFIRST().equals(TagFinal.TRUE)){
//			if (StringJudge.isEmpty(db_index)){
//				for (int i=0;i<30;i++){
//					MainIndex mainIndex=new MainIndex();
//					mainIndex.setKey(StringUtils.getTextJoint("%1$d",i));
//					mainIndex.setNum(i);
//					GreenDaoManager.getInstance().saveMainIndex(mainIndex);
//				}
//			}
//			db_index=GreenDaoManager.getInstance().loadAllMainIndex();
//			Collections.sort(db_index, new Comparator<MainIndex>() {
//				@Override
//				public int compare(MainIndex p1, MainIndex p2) {
//					if(p1.getNum() > p2.getNum()){
//						return -1;
//					}
//					if(p1.getNum() == p2.getNum()){
//						return 0;
//					}
//					return 1;
//					//可以按User对象的其他属性排序，只要属性支持compareTo方法
//				}
//			});
//			for (MainIndex one:db_index){
//				dbIndex(ConvertObjtect.getInstance().getInt(one.getKey()));
//			}
//		}else{
//			if (StringJudge.isEmpty(index_s)){
//				for (int i=0;i<30;i++){
//					dbIndex(i);
//				}
//				StringBuilder sb=new StringBuilder();
//				for (HomeIntent h:funs){
//					sb.append(h.getIndex()).append(",");
//				}
//				if (sb.length()>2){
//					UserPreferences.getInstance().saveIndex(sb.substring(0,sb.length()-1));
//				}
//			}else{
//				list = Arrays.asList(index_s.split(","));
//				for (String index:list){
//					dbIndex(ConvertObjtect.getInstance().getInt(index));
//				}
//			}
//		}
//		adapter.setDataList(funs);
//		adapter.setLoadState(2);
//	}








	RecyclerView recyclerView;
	public RecyclerView.OnItemTouchListener onitem;
	public DividerGridItemDecoration gray_line=new DividerGridItemDecoration();
	public DividerGridItemDecoration white_line=new DividerGridItemDecoration(Color.parseColor("#ffffff"));
//	public void initRecycler(){
//
//		recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//		recyclerView.setItemAnimator(new RecycAnimator());
//		//添加分割线
//		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//			// 用来标记是否正在向上滑动
//			private boolean isSlidingUpward = false;
//			@Override
//			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//				super.onScrollStateChanged(recyclerView, newState);
//				GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
//				// 当不滑动时
//				if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//					// 获取最后一个完全显示的itemPosition
//
//
//				}
//			}
//			@Override
//			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//				super.onScrolled(recyclerView, dx, dy);
//				// 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
//				isSlidingUpward = dy > 0;
//			}
//		});
//
//		recyclerView.addItemDecoration(gray_line);
//		adapter = new MainSetAdapter(this);
//		recyclerView.setAdapter(adapter);
//		mItemTouchHelper = new ItemTouchHelper(callback);
//		onitem = new OnRecyclerItemClickListener(recyclerView) {
//			@Override
//			public void onItemClick(RecyclerView.ViewHolder vh) {
//			}
//
//			@Override
//			public void onItemLongClick(RecyclerView.ViewHolder vh) {
//				//判断被拖拽的是否是前两个，如果不是则执行拖拽
//				//如果item不是最后一个，则执行拖拽
//				if (vh.getLayoutPosition() != funs.size() - 1) {
//					mItemTouchHelper.startDrag(vh);
//					//获取系统震动服务
//					Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);//震动70毫秒
//					vib.vibrate(70);
//				}
//			}
//		};
//
//		//编辑可拖动木块
//		main_edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				if (isChecked){
//					main_edit.setText("完成");
//					main_edit.setTextColor(Color.parseColor("#407b00"));
//					main_contetn.setVisibility(View.VISIBLE);
//					clear_all.setVisibility(View.GONE);
//					set_point.setVisibility(View.GONE);
////					bottom_layout.setBackgroundResource(R.color.white);
//					recyclerView.addOnItemTouchListener(onitem);
//					mItemTouchHelper.attachToRecyclerView(recyclerView);
//					recyclerView.setBackgroundResource(R.color.BrulyWood);
//					recyclerView.removeItemDecoration(gray_line);
//					recyclerView.addItemDecoration(white_line);
//					recyclerView.setFocusable(false);
//				}else{
//					main_edit.setText("自定义排序");//
//					main_edit.setTextColor(Color.parseColor("#858585"));
//					UserPreferences.getInstance().saveFIRST(TagFinal.FALSE);
//					main_contetn.setVisibility(View.GONE);
//					clear_all.setVisibility(View.VISIBLE);
//					set_point.setVisibility(View.VISIBLE);
//					bottom_layout.setBackgroundResource(R.color.white);
//					recyclerView.removeOnItemTouchListener(onitem);
//					recyclerView.setBackgroundResource(R.color.white);
//					recyclerView.removeItemDecoration(white_line);
//					recyclerView.addItemDecoration(gray_line);
//					recyclerView.setFocusable(true);
//					StringBuilder sb=new StringBuilder();
//					for (HomeIntent h:funs){
//						sb.append(h.getIndex()).append(",");
//					}
//					if (sb.length()>2){
//						UserPreferences.getInstance().saveIndex(sb.substring(0,sb.length()-1));
//					}
//					isDbset();
//				}
//			}
//		});
//	}
	public ItemTouchHelper.Callback callback=new ItemTouchHelper.Callback() {
		/**
		 * 是否处理滑动事件 以及拖拽和滑动的方向 如果是列表类型的RecyclerView的只存在UP和DOWN，
		 * 如果是网格类RecyclerView则还应该多有LEFT和RIGHT
		 * @return
		 */
		@Override
		public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
			if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
				final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
				final int swipeFlags = 0;
				return makeMovementFlags(dragFlags, swipeFlags);
			} else {
				final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
				final int swipeFlags = 0;
//                    final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
				return makeMovementFlags(dragFlags, swipeFlags);
			}
		}

		@Override
		public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
			//得到当拖拽的viewHolder的Position
			int fromPosition = viewHolder.getAdapterPosition();
			//拿到当前拖拽到的item的viewHolder
			int toPosition = target.getAdapterPosition();
			Logger.e(TagFinal.ZXX, "onMove: "+fromPosition+" " +toPosition);
			Logger.e(TagFinal.ZXX, "Count : "+adapter.getItemCount());
			if (fromPosition < toPosition) {
				for (int i = fromPosition; i < toPosition; i++) {
					Collections.swap(funs, i, i + 1);
				}
			} else {
				for (int i = fromPosition; i > toPosition; i--) {
					Collections.swap(funs, i, i - 1);
				}
			}
			adapter.notifyItemMoved(fromPosition, toPosition);
			//保存顺序

			return true;
		}

		@Override
		public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
			int position = viewHolder.getAdapterPosition();
			adapter.notifyItemRemoved(position);
			funs.remove(position);
		}

		/**
		 * 重写拖拽可用
		 * @return
		 */
		@Override
		public boolean isLongPressDragEnabled() {
			return false;
		}

		/**
		 * 长按选中Item的时候开始调用
		 *
		 * @param viewHolder
		 * @param actionState
		 */
		@Override
		public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
			if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
				viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
			}
			super.onSelectedChanged(viewHolder, actionState);
		}
		/**
		 * 手指松开的时候还原
		 */
		@Override
		public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
			super.clearView(recyclerView, viewHolder);
			viewHolder.itemView.setBackgroundColor(0);
		}
	};
	public ItemTouchHelper mItemTouchHelper=new ItemTouchHelper(callback);
















//    public void getMainItem() {
//        ReqEnv reqEnv = new ReqEnv();
//        ReqBody reqBody = new ReqBody();
//        ProcessGetTypeReq req = new ProcessGetTypeReq();
//        //获取参数
//        if (Base.user==null){
//            req.setSession_key("gus");
//        }else{
//            req.setSession_key(Base.user.getSession_key());
//        }
//
//        reqBody.processGetTypeReq = req;
//        reqEnv.body = reqBody;
//        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().process_get_type(reqEnv);
//        call.enqueue(this);
//        showProgressDialog("");
//        Logger.e(reqEnv.toString());
//    }


    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        ResEnv respEnvelope = response.body();
        List<String> names=StringUtils.listToStringSplitCharacters(call.request().headers().toString().trim(), "/");
        String name=names.get(names.size()-1);
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;
//            if (b.processGetTypeRes !=null){
//                String result=b.processGetTypeRes.result;
////				Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
//                BaseRes res= gson.fromJson(result,BaseRes.class);
//                if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
//                    dismissProgressDialog();
////                    initGetViewItem(res);
//                }else{
//                    switch (res.getError_code()){
//                        case Base.error_code:
//                            toast("登录过期");
//                            Base.user=null;
//                            GreenDaoManager.getInstance().clearUser();
//                            getMainItem();
//                            break;
//                        default:
//                            dismissProgressDialog();
//                            toast(res.getError_code());
//                            break;
//                    }
//                }
//
//            }

        }else{
            dismissProgressDialog();
            Logger.e(StringUtils.getTextJoint("%1$s:%2$d",name,response.code()));
            toastShow(StringUtils.getTextJoint("数据错误:%1$d",response.code()));
        }

    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        Logger.e("onFailure  :"+call.request().headers().toString());
        dismissProgressDialog();
    }

    @Override
	public boolean isActivity() {
		return AppLess.isTopActivy(TAG);
	}


}
