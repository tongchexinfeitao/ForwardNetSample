package com.bw.forwardnetsample.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.forwardnetsample.R;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 二维码
 * 1、二维码的生成      将一个字符串转换成二维码图片
 * 2、二维码的识别  相机、相册、长按图片
 *
 * 流程：
 * 1、生成二维码
 * 从edt从拿到字符串，调用 CodeUtils.createImage
 *
 * 2、长按识别
 * 给imageview设置长按监听，调用 CodeUtils.analyzeByImageView，直接接收结果
 *
 * 3、相册识别
 * 第一步  调用 CodeUtils.analyzeByPhotos() 去打开相机
 * 第二步重写 onActivityResult 方法
 * 第三步 在 onActivityResult 方法中将结果交给 CodeUtils.onActivityResult 去处理
 *
 * 4、相机扫一扫识别
 * 第一步  调用 CodeUtils.analyzeByCamera() 去打开相机
 * 第二步重写 onActivityResult 方法
 * 第三步 在 onActivityResult 方法中将结果交给 CodeUtils.onActivityResult 去处理
 *
 * <p>
 * 坑点：
 * 1、忘记 init 初始化
 * 2、将 onActivityResult 错误重写成 onActivityReenter ，这样会接收不到结果
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

        // TODO: 2020/2/3 长按二维码图片识别该二维码     设置图片的长按监听
        mIvQrPicture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //通过CodeUtils.analyzeByImageView()   根据图片进行识别
                CodeUtils.analyzeByImageView(mIvQrPicture, new CodeUtils.AnalyzeCallback() {
                    @Override
                    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                        Toast.makeText(MainActivity.this, "长按识别成功 :" + result, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnalyzeFailed() {
                        Toast.makeText(MainActivity.this, "长按识别失败", Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            }
        });

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
                //相机扫一扫识别  通过重写 onActivityResult 方法接受结果
                CodeUtils.analyzeByCamera(this);
                break;
            case R.id.btn_qr_photos:
                //从相册识别  通过重写 onActivityResult 方法接受结果
                CodeUtils.analyzeByPhotos(this);
                break;
        }
    }


    // TODO: 2020/2/3 这个方法绝对不能用，他不能接受结果，会导致接收不到效果
    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    /**
     * 用来接收相机、相册识别的结果，千万不要写成  onActivityReenter
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //接收到识别的结果之后交给 CodeUtils.onActivityResult() 去处理
        CodeUtils.onActivityResult(this, requestCode, resultCode, data, new CodeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                Toast.makeText(MainActivity.this, "相册识别成功 :" + result, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAnalyzeFailed() {
                Toast.makeText(MainActivity.this, "相册识别失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
