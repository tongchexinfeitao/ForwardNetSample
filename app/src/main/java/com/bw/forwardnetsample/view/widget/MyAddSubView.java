package com.bw.forwardnetsample.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.forwardnetsample.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 加减器 组合控件
 *
 * 核心的两个地方
 * 1、setNum方法用于设置加减器数量               别人告诉我数量
 * 2、加减器被点击之后，通过接口通知外界自身数量的变化           我告诉别人数量
 *
 */
public class MyAddSubView extends LinearLayout {
    @BindView(R.id.tv_sub)
    TextView mTvSub;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.tv_add)
    TextView mTvAdd;

    //数量
    private int num = 1;

    public MyAddSubView(Context context) {
        super(context);
    }

    //两个参数的构造器
    public MyAddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.add_sub_view_layout, this);
        // TODO: 2020/2/7  注意他没有帮我们生成  Butterknife.bind
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_sub, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //减号
            case R.id.tv_sub:
                if (num > 1) {
                    //数量
                    num--;
                    //修改中间文本
                    mTvNum.setText("" + num);
                    //通知外界数量发生了改变
                    if (onNumChangeListener != null) {
                        onNumChangeListener.onNumChange(num);
                    }
                } else {
                    Toast.makeText(getContext(), "不能再少了", Toast.LENGTH_SHORT).show();
                }
                break;
            //加号
            case R.id.tv_add:
                //数量
                num++;
                //修改中间文本
                mTvNum.setText("" + num);
                //通知外界数量发生了改变
                if (onNumChangeListener != null) {
                    onNumChangeListener.onNumChange(num);
                }
                break;
        }
    }

    /**
     * 设置加减器的数量
     */
    public void setNum(int num) {
        this.num = num;
        mTvNum.setText("" + num);
    }

    public void setOnNumChangeListener(MyAddSubView.onNumChangeListener onNumChangeListener) {
        this.onNumChangeListener = onNumChangeListener;
    }

    onNumChangeListener onNumChangeListener;

    /**
     * 用于通知外接，加减器的数量发生了变化
     */
    public interface onNumChangeListener {
        void onNumChange(int number);
    }

}
