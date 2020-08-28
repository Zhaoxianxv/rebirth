package com.yfy.app.maintainnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.*;
import butterknife.Bind;
import com.yfy.app.album.AlbumOneActivity;
import com.yfy.app.album.SingePicShowActivity;
import com.yfy.app.maintainnew.adapter.ListViewSingeSelectAdapter;
import com.yfy.app.maintainnew.bean.Dobean;
import com.yfy.app.maintainnew.bean.MainRes;
import com.yfy.base.Base;
import com.yfy.base.activity.WcfActivity;
import com.yfy.db.UserPreferences;
import com.yfy.dialog.AbstractDialog;
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

public class MaintainDoingActivity extends WcfActivity {

    private static final String TAG = MaintainDoingActivity.class.getSimpleName();
    @Bind(R.id.edit_edit_text)
    EditText edit_content;
    @Bind(R.id.maintain_money_edit)
    EditText edit_money;
    @Bind(R.id.maintian_do_item)
    MultiPictureView add_mult;

    @Bind(R.id.maintain_money_layout)
    RelativeLayout money_layout;

    private String date="",dealstate="",id="";


    @Bind(R.id.deal_state_list)
    ListView state_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintain_do_edit);
        date= DateUtils.getDateTime("yyyy/MM/dd");
        getData();
        initSQToolbar();
        initTypeDialog();
        initAbsListView();
        initView();

    }



    private void getData(){
        id=getIntent().getStringExtra("data");
        getDealState();
    }

    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle("处理报修");
        toolbar.addMenuText(1,R.string.submit);
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                String content=edit_content.getText().toString();
                if (StringJudge.isEmpty(content)){
                    toastShow("请填写维修描述");
                }else{
                    choiceState(content);
                }

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

    private void initAbsListView() {
        add_mult.setAddClickCallback(new MultiPictureView.AddClickCallback() {
            @Override
            public void onAddClick(View view) {
//				Log.e(TAG, "onAddClick: ");
                album_select.showAtBottom();
            }
        });

        add_mult.setClickable(false);

        add_mult.setDeleteClickCallback(new MultiPictureView.DeleteClickCallback() {
            @Override
            public void onDeleted(@NotNull View view, int index) {
                add_mult.removeItem(index,true);
            }
        });
        add_mult.setItemClickCallback(new MultiPictureView.ItemClickCallback() {
            @Override
            public void onItemClicked(@NotNull View view, int index, @NotNull ArrayList<String> uris) {
//				Log.e(TAG, "onItemClicked: "+index );
                Intent intent=new Intent(mActivity, SingePicShowActivity.class);
                Bundle b=new Bundle();
                b.putString(TagFinal.ALBUM_SINGE_URI,uris.get(index));
                intent.putExtras(b);
                startActivity(intent);
            }
        });

    }



    private ListViewSingeSelectAdapter adapter;
    private List<Dobean> datas=new ArrayList<>();
    private void initView() {
//        edit_content.setText(UserPreferences.getInstance().getContent());
//        state_list.setAdapter(new);
        adapter=new ListViewSingeSelectAdapter(mActivity, datas);
        state_list.setAdapter(adapter);
        state_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取选中的参数
//                int index = state_list .getCheckedItemPosition();     // 即获取选中位置
                if(ListView.INVALID_POSITION != position) {
                    dealstate=datas.get(position).getOperatecode();
//                    if (datas.get(position).getOperatename().equals("外修")){
//
//                    }else{
//
//                    }
                    if (dealstate.equals("7")){
                        money_layout.setVisibility(View.VISIBLE);
                    }else{
                        money_layout.setVisibility(View.GONE);
                        edit_money.setText("0");
                    }
                }

            }
        });
    }




    private void choiceState(String content){
        if (StringJudge.isEmpty(dealstate)){
            toastShow("请选择维修状态");
            return;
        }
        String money=edit_money.getText().toString().trim();
        if (StringJudge.isEmpty(money)){
            money="0";
        }
        Object[] params = new Object[] {
                id,
                Base.user.getSession_key(),
                Base.user.getName(),
//                StringUtils.string2Json(content),
                content,
                date,//date
                dealstate,//1,完成2，拒绝
                0,//int
                money,//money
                "",//pictures
                "",//fileContext
                "",//delpictures
        };
        ParamsTask choice = new ParamsTask(params, TagFinal.MAINNEW_SYXX,TagFinal.MAINNEW_SYXX);
        ExtraRunTask wrapTask = new ExtraRunTask(choice);
        wrapTask.setExtraRunnable(extraRunnable);
        execute(wrapTask);
        showProgressDialog("");
    }



    private void getDealState(){
        Object[] params = new Object[] {
            Base.user.getSession_key(),
            id
        };
        ParamsTask deal = new ParamsTask(params, TagFinal.MAINNEW_GET_OPERATE,TagFinal.MAINNEW_GET_OPERATE);
        execute(deal);
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
            params[8] = Base64Utils.getZipTitleNotice(list);
            params[9] = Base64Utils.filesToZipBase64Notice(list);
            return params;
        }
    };


    @Override
    public void onPageBack() {
        setResult(RESULT_OK);
        super.onPageBack();
    }

    @Override
    public boolean onSuccess(String result, WcfTask wcfTask) {
        if (!isActivity())return false;
        dismissProgressDialog();
        Logger.e(result);
        String name=wcfTask.getName();
        if (name.equals(TagFinal.MAINNEW_GET_OPERATE)){
            MainRes res=gson.fromJson(result,MainRes.class);
            if (res.getResult().equals(TagFinal.TRUE)){
                datas=res.getOperate();
                adapter.setDatas(datas);
            }else{
                toast(res.getError_code());
            }
        }
        if (name.equals(TagFinal.MAINNEW_SYXX)){
            if (StringJudge.isSuccess(gson,result)){
                onPageBack();
            }else{
                toastShow(R.string.success_not_do);
            }
        }

        return false;
    }

    @Override
    public void onError(WcfTask wcfTask) {
        if (!isActivity())return ;
        dismissProgressDialog();
        toastShow(R.string.fail_do_not);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result;
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
            }
        }
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
