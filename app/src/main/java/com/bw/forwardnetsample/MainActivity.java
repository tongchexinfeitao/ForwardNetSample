package com.bw.forwardnetsample;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 二维码
 * 1、二维码的生成      将一个字符串转换成二维码图片
 * 2、二维码的识别  相机、相册、长按图片
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edt_qr_content)
    EditText mEdtQrContent;
    @BindView(R.id.btn_qr_camera)
    Button mBtnQrCamera;
    @BindView(R.id.btn_qr_photos)
    Button mBtnQrPhotos;
    @BindView(R.id.iv_qr_picture)
    ImageView mIvQrPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Butterknife初始化的地方
        ButterKnife.bind(this);

        // TODO: 2020/2/3 必须对二维码库进行初始化
        CodeUtils.init(this);

    }

    @OnClick({R.id.btn_qr_create, R.id.btn_qr_camera, R.id.btn_qr_photos})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_qr_create:
                //1、拿到字符串
                String s = mEdtQrContent.getText().toString();
                //2、生成二维码
                Bitmap image = CodeUtils.createImage(s, 200, 200, null);
                //3、将生成的二维码图片展示到imageview上
                mIvQrPicture.setImageBitmap(image);

                break;
            case R.id.btn_qr_camera:

                break;
            case R.id.btn_qr_photos:
                break;
        }
    }
}
