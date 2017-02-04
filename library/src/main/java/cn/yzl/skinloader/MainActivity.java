package cn.yzl.skinloader;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.yzl.skinloader.base.BaseSkinActivity;
import cn.yzl.skinloader.callback.SkinChangingCallback;
import cn.yzl.skinloader.manager.SkinManager;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class MainActivity extends BaseSkinActivity {


    //文件路径
    String skinPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "skindemo" + File.separator + "skinplugin1.apk";
    String skinPkg = "cn.yzl.skinplugin1";
    @BindView(R.id.but1)
    Button but1;
    @BindView(R.id.but2)
    Button but2;
    @BindView(R.id.but3)
    Button but3;
    @BindView(R.id.main_view)
    LinearLayout mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.but1, R.id.but2, R.id.but3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but1:
                MainActivityPermissionsDispatcher.agreePermissionWithCheck(this);
                break;
            case R.id.but2:
                break;
            case R.id.but3:
                break;
        }
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void agreePermission() {
        SkinManager.getInstance().changeSkin(skinPath,skinPkg, new SkinChangingCallback.DefaultSkinChagingCallback());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void refusePermission() {
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void neverPermission() {
    }
}
