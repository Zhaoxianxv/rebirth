package com.yfy.app.maintainnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.OnClick;
import com.yfy.app.album.AlbumOneActivity;
import com.yfy.app.album.SingePicShowActivity;
import com.yfy.app.maintainnew.bean.DepTag;
import com.yfy.app.maintainnew.bean.GoodBean;
import com.yfy.base.Base;
import com.yfy.base.activity.WcfActivity;
import com.yfy.dialog.ConfirmAlbumWindow;
import com.yfy.final_tag.*;
import com.yfy.jpush.Logger;
import com.yfy.net.loading.interf.WcfTask;
import com.yfy.net.loading.task.ExtraRunTask;
import com.yfy.net.loading.task.ParamsTask;
import com.yfy.permission.PermissionFail;
import com.yfy.permission.PermissionGen;
import com.yfy.permission.PermissionSuccess;
import com.yfy.permission.PermissionTools;
import com.yfy.rebirth.R;
import com.yfy.view.SQToolBar;
import com.yfy.view.multi.MultiPictureView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MaintainNewAddActivity extends WcfActivity {

    private static final String TAG = MaintainNewAddActivity.class.getSimpleName();
    private String classid ;
    private String apply_date = "";
    private String repair_place = "";
    private String repair_content = "";
    private String tell_string = "";

    private final static int PALCE = 0x1;
    private final static int CONTENT = 0x2;

    @Bind(R.id.maintian_add_item)
    MultiPictureView add_mult;
    @Bind(R.id.trouble_time)
    TextView trouble_time;
    @Bind(R.id.trouble_dep)
    TextView trouble_dep;
    @Bind(R.id.select_dep)
    TextView select_dep;

    @Bind(R.id.trouble_content)
    EditText trouble_content;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintain_new_add);
        initSQToolbar();
        initTypeDialog();
        initView();
        initAbsListView();
    }



    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(R.string.maintain_add);
        toolbar.addMenuText(1,R.string.submit);
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                send();
            }
        });
    }

    private void initView(){

//        String iphone=UserPreferences.getInstance().getTell();
//        if (StringJudge.isEmpty(iphone)){
//
//        }else{
//            tell.setText(iphone);
//        }
    }

    @Override
    public void onPageBack() {
        setResult(RESULT_OK);
        super.onPageBack();
    }

    @Override
    public boolean onSuccess(String result, WcfTask wcfTask) {
        Logger.e( "onSuccess: "+result );
        if (!isActivity()) return true;
        dismissProgressDialog();
        if (StringJudge.isSuccess(gson,result)) {
            toast("发送成功");
            onPageBack();
        }
        return false;
    }

    @Override
    public void onError(WcfTask wcfTask) {
        if (!isActivity()) return;
        dismissProgressDialog();
        toastShow(R.string.fail_do_not);
    }






    private void initAbsListView() {
        trouble_time.setText(DateUtils.getDateTime(getResources().getString(R.string.date_type_1)));
        apply_date= DateUtils.getDateTime(getResources().getString(R.string.date_type_4));
        add_mult.setAddClickCallback(new MultiPictureView.AddClickCallback() {
            @Override
            public void onAddClick(View view) {
//				Logger.e(TAG, "onAddClick: ");
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
//				Logger.e(TAG, "onItemClicked: "+index );
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


    private String office_name,office_id,good_name,good_id;

    private void send() {
        if (isSend()) {
            Object[] params = new Object[] {
                    Base.user.getSession_key(),
                    "0",
                    apply_date,
                    repair_content,
                    "",
                    Base.user.getName(),
                    office_name,
                    office_id,
                    good_name,
                    good_id,
                    "",
                    "",
                    classid
            };

            ParamsTask sendTask = new ParamsTask(params, TagFinal.MAINTIAN_ADD, TagFinal.MAINTIAN_ADD);
            ExtraRunTask wrapTask = new ExtraRunTask(sendTask);
            wrapTask.setExtraRunnable(extraRunnable);
            execute(wrapTask);
            showProgressDialog("");
//            UserPreferences.getInstance().saveTell(tell_string);
        }
    }

    private boolean isSend() {
        if (StringJudge.isEmpty(classid)) {
            toast("请选择报修部门");
            return false;
        }

        if (TextUtils.isEmpty(apply_date)) {
            toast("请输入申请时间");
            return false;
        }
//        repair_content = StringUtils.string2Json(trouble_content.getText().toString().trim());
        repair_content = trouble_content.getText().toString().trim();
        if (TextUtils.isEmpty(repair_content)) {
            toast("请输入维修备注");
            return false;
        }

        if (TextUtils.isEmpty(good_id)) {
            toast("请选择地点");
            return false;
        }
        if (StringJudge.isEmpty(add_mult.getList())) {
            toast("请拍照");
            return false;
        }



        return true;
    }

    private ExtraRunTask.ExtraRunnable extraRunnable = new ExtraRunTask.ExtraRunnable() {

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
            params[10] = Base64Utils.getZipTitleNotice(list);
            params[11] = Base64Utils.filesToZipBase64Notice(list);
            return params;
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PALCE:

                    office_id= data.getStringExtra(TagFinal.ID_TAG);
                    office_name=data.getStringExtra(TagFinal.NAME_TAG);
                    GoodBean bean=data.getParcelableExtra(TagFinal.OBJECT_TAG);
                    good_name=bean.getGoodsname();
                    select_dep.setText(StringUtils.stringToGetTextJoint("%1$s(%2$s)",office_name,good_name));
                    good_id=bean.getGoodsid();

                    classid=bean.getLogisticsclassid();
                    trouble_dep.setText(bean.getLogisticsclassname());
                    break;
                case CONTENT:
                    result = data.getExtras().getString("content");
                    trouble_content.setText(result);
                    break;
                case TagFinal.CAMERA:
                    addMult(FileCamera.photo_camera);
                    break;
                case TagFinal.PHOTO_ALBUM:
                    ArrayList<Photo> photo_a=data.getParcelableArrayListExtra(TagFinal.ALBUM_TAG);
                    if (photo_a==null)return;
                    if (photo_a.size()==0)return;
                    setMultList(photo_a);
//					Logger.e(TAG, "onActivityResult:imgPath  "+photo_a.get(0).toString());
                    break;
                case TagFinal.UI_TAG:
                    DepTag tag =data.getParcelableExtra(TagFinal.OBJECT_TAG);
                    classid=tag.getId();
                    trouble_dep.setText(tag.getName());

                    break;
            }
        }
    }





    @OnClick(R.id.trouble_dep)
    void setDep(){
//        Intent intent=new Intent(mActivity, ChoiceTagActivity.class);
//        startActivityForResult(intent,TagFinal.UI_TAG);
    }



    @OnClick(R.id.select_dep)
    void setS(){
        Intent intent=new Intent(mActivity, ChoiceOfficeActivity.class);
        startActivityForResult(intent,PALCE);
    }





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

    @PermissionSuccess(requestCode = TagFinal.CAMERA)
    private void takePhoto() {
        FileCamera camera=new FileCamera(mActivity);
        startActivityForResult(camera.takeCamera(), TagFinal.CAMERA);
    }
    @PermissionSuccess(requestCode = TagFinal.PHOTO_ALBUM)
    private void photoAlbum() {
        Intent intent;
        intent = new Intent(mActivity, AlbumOneActivity.class);
        Bundle b = new Bundle();
        b.putInt(TagFinal.ALBUM_LIST_INDEX, 0);
        b.putBoolean(TagFinal.ALBUM_SINGLE, false);
        intent.putExtras(b);
        startActivityForResult(intent,TagFinal.PHOTO_ALBUM);
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


    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
