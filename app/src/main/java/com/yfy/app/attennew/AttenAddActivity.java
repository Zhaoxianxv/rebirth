/**
 * 
 */
package com.yfy.app.attennew;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.OnClick;
import com.yfy.app.album.AlbumOneActivity;
import com.yfy.app.album.SingePicShowActivity;
import com.yfy.app.attennew.bean.LeaveType;
import com.yfy.app.bean.DateBean;
import com.yfy.app.bean.NameId;
import com.yfy.app.net.*;
import com.yfy.app.net.atten.AttenTypeReq;
import com.yfy.base.Base;
import com.yfy.base.activity.WcfActivity;
import com.yfy.dialog.CPWListView;
import com.yfy.dialog.ConfirmAlbumWindow;
import com.yfy.dialog.ConfirmDateAndTimeWindow;
import com.yfy.dialog.DialogTools;
import com.yfy.final_tag.*;
import com.yfy.jpush.Logger;
import com.yfy.json.JsonParser;
import com.yfy.net.loading.interf.WcfTask;
import com.yfy.net.loading.task.ExtraRunTask;
import com.yfy.net.loading.task.ExtraRunTask.ExtraRunnable;
import com.yfy.net.loading.task.ParamsTask;
import com.yfy.permission.PermissionFail;
import com.yfy.permission.PermissionGen;
import com.yfy.permission.PermissionSuccess;
import com.yfy.permission.PermissionTools;
import com.yfy.rebirth.R;
import com.yfy.tool_textwatcher.MyWatcher;
import com.yfy.view.SQToolBar;
import com.yfy.view.multi.MultiPictureView;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author yfy1
 * @version 1.0
 * @Date 2016年1月14日
 * @Desprition
 */
public class AttenAddActivity extends WcfActivity implements Callback<ResEnv> {


	private static final String TAG = AttenAddActivity.class.getSimpleName();

	@Bind(R.id.atten_admin_type)//审批人
	TextView admin_name;
	@Bind(R.id.leave_date)//开始时间
	TextView leave_date;
	@Bind(R.id.atten_type)//类型
	TextView atten_type;
	@Bind(R.id.leave_duration)//天数
	EditText leave_duration;
	@Bind(R.id.leave_reason)//内容
	EditText leave_reason;
	@Bind(R.id.atten_add_mult)
	MultiPictureView add_mult;

	private final static int USERID = 9;
	private String start_time;
	private int userid = -1;

	private List<LeaveType> leaveTypeList = new ArrayList<LeaveType>();
	private DateBean dateBean;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.atten_add_new);
		dateBean=new DateBean();
		dateBean.setValue( DateUtils.getDateTime("yyyy/MM/dd HH:mm"));
		dateBean.setName(DateUtils.getDateTime("yyyy年MM月dd日\t\tHH:mm"));
		initDialog();
		initDateDialog();
		initAll();
	}


	private void initAll() {
		initSQtoolbar();
		initViews();
		initAbsListView();
		initTypeDialog();
	}



	private void initSQtoolbar() {
		assert toolbar != null;
		toolbar.setTitle("请假");
		toolbar.addMenuText(1, "提交");
		toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
			@Override
			public void onClick(View view, int position) {
				if (isCanUpload()) {
					addLeave();
				}
			}
		});
	}

	private void initViews() {

		start_time =dateBean.getValue();
		leave_date.setText(dateBean.getName());

		admin_name.setText("请选择审批人");
		admin_name.setTextColor(Color.rgb(183,183,183));
		atten_type.setText("未选择请假类型");
		atten_type.setTextColor(Color.rgb(183,183,183));
        leave_duration.addTextChangedListener(timeday);


	}






    //输入天
    public MyWatcher timeday = new MyWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            iedit(s);
        }
    };

    public void iedit(Editable s) {
        String edit_String = s.toString();
        int posDot = edit_String.indexOf(".");
        if (posDot <= 0) return;
        if (edit_String.length() - posDot - 1 > 1) {
            s.delete(posDot + 2, edit_String.length());//限制1位小数
            DialogTools.getInstance().showDialog(mActivity, "", "限制1位小数");
        } else {
            String num = edit_String.substring(posDot + 1);
            if (StringJudge.isEmpty(num)) return;
            if (num.equals("0") || num.equals("5")) {
            } else {
                s.delete(posDot + 1, edit_String.length());//限制2位小数
                DialogTools.getInstance().showDialog(mActivity, "", "小数点后只能输入0或5");
            }
        }

    }




	private CPWListView cpwListView;
	List<String> txts=new ArrayList<>();
	private void initData(){
		if (StringJudge.isEmpty(leaveTypeList)){
			toast(R.string.success_not_details);
			return;
		}else{
			txts.clear();
			for(LeaveType s:leaveTypeList){
				txts.add(s.getTypename());
			}
		}
		cpwListView.setDatas(txts);
		cpwListView.showAtCenter();

	}
	private void initDialog(){
		cpwListView = new CPWListView(mActivity);
		cpwListView.setOnPopClickListenner(new CPWListView.OnPopClickListenner() {
			@Override
			public void onClick(int index) {
				kqtype = leaveTypeList.get(index).getTypeid();
				atten_type.setText(leaveTypeList.get(index).getTypename());
				atten_type.setTextColor(Color.rgb(24,24,24));
				cpwListView.dismiss();
			}
		});
	}



	private boolean isCanUpload() {
		duration = leave_duration.getText().toString().trim();
//		if (userid == -1) {
//			toastShow("请选择审批人");
//			return false;
//		}
		if (TextUtils.isEmpty(duration)) {
			toastShow("请填写请假天数");
			return false;
		}
		if (TextUtils.isEmpty(kqtype)) {
			toastShow("请选择请假类型");
			return false;
		}
//		reason = StringUtils.string2Json(leave_reason.getText().toString().trim());
		reason = leave_reason.getText().toString().trim();
		if (TextUtils.isEmpty(reason)) {
			toastShow("请填写请假理由");
			return false;
		}
		return true;
	}
	private String reason = "";
	private String kqtype = "";
	private String duration = "";
	private void addLeave() {
		Object[] params = new Object[]{
				Base.user.getSession_key(),
				userid,
				start_time,
				duration,
				reason,
				"",//table_plan,
				"",//file
				"",//file_image
				kqtype};
		ParamsTask addLeave = new ParamsTask(params, TagFinal.ATTENNEW_SUBMIT, TagFinal.ATTENNEW_SUBMIT);
		ExtraRunTask wrapTask = new ExtraRunTask(addLeave);
		wrapTask.setExtraRunnable(extraRunnable);
		showProgressDialog("");
		execute(wrapTask);

	}


	@Override
	public boolean onSuccess(String result, WcfTask wcfTask) {

		if (!isActivity())return false;
		dismissProgressDialog();

		String taskName = wcfTask.getName();
		if (taskName.equals(TagFinal.ATTENNEW_SUBMIT)) {
			if (StringJudge.isSuccess(gson,result)) {
				toastShow("申请成功,请等待审核");
				setResult(RESULT_OK);
				finish();
			} else {
				toastShow(JsonParser.getErrorCode(result));
			}
		}
		return false;
	}

	@Override
	public void onError(WcfTask wcfTask) {
		if (!isActivity())return ;
		dismissProgressDialog();
		String taskName = wcfTask.getName();
		if (taskName.equals(TagFinal.ATTENNEW_SUBMIT)) {
			toast(R.string.fail_do_not);
		}

	}



	private ExtraRunnable extraRunnable = new ExtraRunnable() {

		@Override
		public Object[] onExecute(Object[] params) {
			List<Photo> list=new ArrayList<>();
			int i=0;
			for (String uri:add_mult.getList()){
				Photo p=new Photo();
				i++;
				String picName =  String.valueOf(System.currentTimeMillis())+String.valueOf(i);
				p.setFileName(String.format("%1$s.jpg",picName));
				p.setPath(uri);
				list.add(p);
			}
			params[7] = Base64Utils.getZipTitleNotice(list);
			params[6] = Base64Utils.filesToZipBase64Notice(list);
			return params;
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
				case TagFinal.CAMERA:
					addMult(FileCamera.photo_camera);
					break;
				case TagFinal.PHOTO_ALBUM:
					ArrayList<Photo> photo_a=data.getParcelableArrayListExtra(TagFinal.ALBUM_TAG);
					if (photo_a==null)return;
					if (photo_a.size()==0)return;
					setMultList(photo_a);
					break;
				case USERID:
					NameId su=data.getParcelableExtra(TagFinal.OBJECT_TAG);
					admin_name.setText(su.getName());
					admin_name.setTextColor(Color.rgb(44,44,44));
					userid = Integer.parseInt(su.getId());
					break;

			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	//==============add icon============


	public void addMult(String uri){
		if (uri==null) return;
		add_mult.addItem(uri);
	}
	public void setMultList(List<Photo> list){
		for (Photo photo:list){
			if (photo==null) continue;
			addMult(photo.getPath());
		}
	}

	private void initAbsListView() {

		add_mult.setAddClickCallback(new MultiPictureView.AddClickCallback() {
			@Override
			public void onAddClick(View view) {
				Logger.e(TagFinal.ZXX, "onAddClick: ");
				album_select.showAtBottom();
			}
		});

		add_mult.setClickable(false);

		add_mult.setDeleteClickCallback(new MultiPictureView.DeleteClickCallback() {
			@Override
			public void onDeleted(@NotNull View view, int index) {
				add_mult.removeItem(index,true);
//                Logger.e(TAG, "onDeleted: "+add_mult.getList().size() );
			}
		});
		add_mult.setItemClickCallback(new MultiPictureView.ItemClickCallback() {
			@Override
			public void onItemClicked(@NotNull View view, int index, @NotNull ArrayList<String> uris) {
				Intent intent=new Intent(mActivity, SingePicShowActivity.class);
				Bundle b=new Bundle();
				b.putString(TagFinal.ALBUM_SINGE_URI,uris.get(index));
				intent.putExtras(b);
				startActivity(intent);
			}
		});

	}




	private ConfirmAlbumWindow album_select;
	private void initTypeDialog() {
		album_select = new ConfirmAlbumWindow(mActivity);
		album_select.setOne_select(getResources().getString(R.string.take_photo));
		album_select.setTwo_select(getResources().getString(R.string.album));
		album_select.setName(getResources().getString(R.string.upload_type));
		album_select.setOnPopClickListenner(new ConfirmAlbumWindow.OnPopClickListenner() {
			@Override
			public void onClick(View view) {
				switch (view.getId()) {
					case R.id.popu_select_one:
						PermissionTools.tryCameraPerm(mActivity);
						break;
					case R.id.popu_select_two:
						PermissionTools.tryWRPerm(mActivity);
						break;
				}
			}
		});
	}



	@OnClick(R.id.atten_admin_type)
	void setChoiceAdmin(){
		Intent intent = new Intent(mActivity, SubjectActivity.class);
		startActivityForResult(intent, USERID);
	}
	@OnClick(R.id.leave_date)
	void setChoiceTime(){
		date_dialog.showAtBottom();

	}

	private ConfirmDateAndTimeWindow date_dialog;
	private void initDateDialog(){
		date_dialog = new ConfirmDateAndTimeWindow(mActivity);
		date_dialog.setOnPopClickListenner(new ConfirmDateAndTimeWindow.OnPopClickListenner() {
			@Override
			public void onClick(View view) {
				switch (view.getId()) {
					case R.id.set:

						dateBean.setName(date_dialog.getTimeName());
						dateBean.setValue(date_dialog.getTimeValue());

						start_time =dateBean.getValue();
						leave_date.setText(dateBean.getName());

						date_dialog.dismiss();
						break;
					case R.id.cancel:
						date_dialog.dismiss();
						break;
				}

			}
		});
	}



	@OnClick(R.id.atten_type)
	void setChoiceType(){
		if (leaveTypeList.size() > 0) {
			initData();
		} else {
			getLeaveType();
		}
	}







	@PermissionSuccess(requestCode = TagFinal.CAMERA)
	private void takePhoto() {
		FileCamera camera = new FileCamera(mActivity);
		startActivityForResult(camera.takeCamera(), TagFinal.CAMERA);
	}

	@PermissionSuccess(requestCode = TagFinal.PHOTO_ALBUM)
	private void photoAlbum() {
		Intent intent = new Intent(mActivity, AlbumOneActivity.class);
		Bundle b = new Bundle();
		b.putInt(TagFinal.ALBUM_LIST_INDEX, 0);
		b.putBoolean(TagFinal.ALBUM_SINGLE, false);
		intent.putExtras(b);
		startActivityForResult(intent, TagFinal.PHOTO_ALBUM);
	}

	@PermissionFail(requestCode = TagFinal.CAMERA)
	private void showCamere() {
		Toast.makeText(getApplicationContext(), R.string.permission_fail_camere, Toast.LENGTH_SHORT).show();
	}

	@PermissionFail(requestCode = TagFinal.PHOTO_ALBUM)
	private void showTip1() {
		Toast.makeText(getApplicationContext(), R.string.permission_fail_album, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
	}

	/**
	 * --------------------------retrofit--------------------------
	 */

	public void getLeaveType()  {

		ReqEnv reqEnvelop = new ReqEnv();
		ReqBody reqBody = new ReqBody();
		AttenTypeReq request = new AttenTypeReq();
		//获取参数

		reqBody.type_atten = request;
		reqEnvelop.body = reqBody;
		Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().atten_type(reqEnvelop);
		call.enqueue(this);
	}


	@Override
	public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
		if (!isActivity())return;
		dismissProgressDialog();
		if (response.code()==500){
			try {
				String s=response.errorBody().string();
				Logger.e(TagFinal.ZXX, s);
			} catch (IOException e) {
				Logger.e(TagFinal.ZXX, "onResponse: IOException");
				e.printStackTrace();
			}
			toast("数据出差了");
		}
		ResEnv respEnvelope = response.body();
		if (respEnvelope != null) {
			ResBody b=respEnvelope.body;
			if (b.attendance_typeResponse!=null){
				String result=b.attendance_typeResponse.attendance_typeResult;
				leaveTypeList = JsonParser.getLeaveTypeList(result);
				initData();
			}
		}else{
			Logger.e(TagFinal.ZXX, "evn: null" );
		}
	}

	@Override
	public void onFailure(Call<ResEnv> call, Throwable t) {
		if (!isActivity())return;
        Logger.e("onFailure  :"+call.request().headers().toString());
		dismissProgressDialog();
		toast(R.string.fail_do_not);
	}


	@Override
	public boolean isActivity() {
		return AppLess.isTopActivy(TAG);
	}



}

