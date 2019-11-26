package com.hwgo.base.titleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hwgo.base.R;


/**
 * <br> ClassName:   SimpleTitleView
 * <br> Description: 一个简单的TitleView，包含中间的标题和左右两侧的按钮
 * <p>
 * <br> Date:        2018/3/1
 */
public class TitleView
        extends FrameLayout {

    private TextView mLeft_1;
    private TextView mLeft_2;
    private TextView mMiddle_1;
    private TextView mMiddle_2;
    private TextView mRight_1;
    private TextView mRight_2;
    private View mLl_middle;
    private TitleEventListener mTitleEventListener;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.titleview_simple_layout, this, true);
        mLeft_1 = findViewById(R.id.tv_titleview_left_1);
        mLeft_2 = findViewById(R.id.tv_titleview_left_2);
        mMiddle_1 = findViewById(R.id.tv_titleview_middle_1);
        mMiddle_2 = findViewById(R.id.tv_titleview_middle_2);
        mRight_1 = findViewById(R.id.tv_titleview_right_1);
        mRight_2 = findViewById(R.id.tv_titleview_right_2);
        mLl_middle = findViewById(R.id.ll_titleview_middle);
        //
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
            formatValue(typedArray);
            typedArray.recycle();
        }
        //
        mLeft_1.setOnClickListener(mOnClickListener);
        mLeft_2.setOnClickListener(mOnClickListener);
        mLl_middle.setOnClickListener(mOnClickListener);
        mRight_1.setOnClickListener(mOnClickListener);
        mRight_2.setOnClickListener(mOnClickListener);
    }

    private void formatValue(TypedArray typedArray) {
        {
            String middle = typedArray.getString(R.styleable.TitleView_titleview_middle_1_text);
            if (middle != null) {
                mMiddle_1.setText(middle);
            }
            int middle_textSize = typedArray.getDimensionPixelSize(R.styleable.TitleView_titleview_middle_1_textSize, -1);
            if (middle_textSize > 0) {
                mMiddle_1.setTextSize(TypedValue.COMPLEX_UNIT_PX, middle_textSize);
            }
            int middle_textColor = typedArray.getColor(R.styleable.TitleView_titleview_middle_1_textColor, Integer.MIN_VALUE);
            if (middle_textColor != Integer.MIN_VALUE) {
                mMiddle_1.setTextColor(middle_textColor);
            }
        }
        {
            String middle = typedArray.getString(R.styleable.TitleView_titleview_middle_2_text);
            if (middle != null) {
                mMiddle_2.setVisibility(View.VISIBLE);
                mMiddle_2.setText(middle);
            }
            int middle_textSize = typedArray.getDimensionPixelSize(R.styleable.TitleView_titleview_middle_2_textSize, -1);
            if (middle_textSize > 0) {
                mMiddle_2.setTextSize(TypedValue.COMPLEX_UNIT_PX, middle_textSize);
            }
            int middle_textColor = typedArray.getColor(R.styleable.TitleView_titleview_middle_2_textColor, Integer.MIN_VALUE);
            if (middle_textColor != Integer.MIN_VALUE) {
                mMiddle_2.setTextColor(middle_textColor);
            }
        }
        {
            int middle_paddingLeft = (int) typedArray.getDimension(R.styleable.TitleView_titleview_middle_paddingLeft, 0);
            int middle_paddingRight = (int) typedArray.getDimension(R.styleable.TitleView_titleview_middle_paddingRight, 0);
            if (middle_paddingLeft > 0 || middle_paddingRight > 0) {
                mLl_middle.setPadding(middle_paddingLeft, 0, middle_paddingRight, 0);
            }
        }
        {
            String left = typedArray.getString(R.styleable.TitleView_titleview_left_text);
            if (left != null) {
                mLeft_1.setText(left);
            }
            int left_textSize = typedArray.getDimensionPixelSize(R.styleable.TitleView_titleview_left_textSize, -1);
            if (left_textSize > 0) {
                mLeft_1.setTextSize(TypedValue.COMPLEX_UNIT_PX, left_textSize);
            }
            int left_paddingLeft = (int) typedArray.getDimension(R.styleable.TitleView_titleview_left_paddingLeft, 0);
            int left_paddingRight = (int) typedArray.getDimension(R.styleable.TitleView_titleview_left_paddingRight, 0);
            if (left_paddingLeft > 0 || left_paddingRight > 0) {
                mLeft_1.setPadding(left_paddingLeft, 0, left_paddingRight, 0);
            }
            int left_textColor = typedArray.getColor(R.styleable.TitleView_titleview_left_textColor, Integer.MIN_VALUE);
            if (left_textColor != Integer.MIN_VALUE) {
                mLeft_1.setTextColor(left_textColor);
            }
            Drawable left_drawable = typedArray.getDrawable(R.styleable.TitleView_titleview_left_drawable);
            Drawable left_background = typedArray.getDrawable(R.styleable.TitleView_titleview_left_background);
            if (left_background != null) {
                mLeft_1.setBackgroundDrawable(left_background);
            }
            setTextViewDrawable(mLeft_1, left_drawable, true);
        }
        {
            String left = typedArray.getString(R.styleable.TitleView_titleview_left_2_text);
            if (left != null) {
                mLeft_2.setText(left);
            }
            int left_textSize = typedArray.getDimensionPixelSize(R.styleable.TitleView_titleview_left_2_textSize, -1);
            if (left_textSize > 0) {
                mLeft_2.setTextSize(TypedValue.COMPLEX_UNIT_PX, left_textSize);
            }
            int left_paddingLeft = (int) typedArray.getDimension(R.styleable.TitleView_titleview_left_2_paddingLeft, 0);
            int left_paddingRight = (int) typedArray.getDimension(R.styleable.TitleView_titleview_left_2_paddingRight, 0);
            if (left_paddingLeft > 0 || left_paddingRight > 0) {
                mLeft_2.setPadding(left_paddingLeft, 0, left_paddingRight, 0);
            }
            int left_textColor = typedArray.getColor(R.styleable.TitleView_titleview_left_2_textColor, Integer.MIN_VALUE);
            if (left_textColor != Integer.MIN_VALUE) {
                mLeft_2.setTextColor(left_textColor);
            }
            Drawable left_drawable = typedArray.getDrawable(R.styleable.TitleView_titleview_left_2_drawable);
            Drawable left_background = typedArray.getDrawable(R.styleable.TitleView_titleview_left_2_background);
            if (left_background != null) {
                mLeft_2.setBackgroundDrawable(left_background);
            }
            setTextViewDrawable(mLeft_2, left_drawable, true);
        }
        {
            String right = typedArray.getString(R.styleable.TitleView_titleview_right_text);
            if (right != null) {
                mRight_1.setText(right);
            }
            int right_textSize = typedArray.getDimensionPixelSize(R.styleable.TitleView_titleview_right_textSize, -1);
            if (right_textSize > 0) {
                mRight_1.setTextSize(TypedValue.COMPLEX_UNIT_PX, right_textSize);
            }
            int right_paddingLeft = (int) typedArray.getDimension(R.styleable.TitleView_titleview_right_paddingLeft, 0);
            int right_paddingRight = (int) typedArray.getDimension(R.styleable.TitleView_titleview_right_paddingRight, 0);
            if (right_paddingLeft > 0 || right_paddingRight > 0) {
                mRight_1.setPadding(right_paddingLeft, 0, right_paddingRight, 0);
            }
            int right_textColor = typedArray.getColor(R.styleable.TitleView_titleview_right_textColor, Integer.MIN_VALUE);
            if (right_textColor != Integer.MIN_VALUE) {
                mRight_1.setTextColor(right_textColor);
            }
            Drawable right_drawable = typedArray.getDrawable(R.styleable.TitleView_titleview_right_drawable);
            Drawable right_background = typedArray.getDrawable(R.styleable.TitleView_titleview_right_background);
            if (right_background != null) {
                mRight_1.setBackgroundDrawable(right_background);
            }
            setTextViewDrawable(mRight_1, right_drawable, false);
        }
        {
            String right = typedArray.getString(R.styleable.TitleView_titleview_right_2_text);
            if (right != null) {
                mRight_2.setText(right);
            }
            int right_textSize = typedArray.getDimensionPixelSize(R.styleable.TitleView_titleview_right_2_textSize, -1);
            if (right_textSize > 0) {
                mRight_2.setTextSize(TypedValue.COMPLEX_UNIT_PX, right_textSize);
            }
            int right_paddingLeft = (int) typedArray.getDimension(R.styleable.TitleView_titleview_right_2_paddingLeft, 0);
            int right_paddingRight = (int) typedArray.getDimension(R.styleable.TitleView_titleview_right_2_paddingRight, 0);
            if (right_paddingLeft > 0 || right_paddingRight > 0) {
                mRight_2.setPadding(right_paddingLeft, 0, right_paddingRight, 0);
            }
            int right_textColor = typedArray.getColor(R.styleable.TitleView_titleview_right_2_textColor, Integer.MIN_VALUE);
            if (right_textColor != Integer.MIN_VALUE) {
                mRight_2.setTextColor(right_textColor);
            }
            Drawable right_drawable = typedArray.getDrawable(R.styleable.TitleView_titleview_right_2_drawable);
            Drawable right_background = typedArray.getDrawable(R.styleable.TitleView_titleview_right_2_background);
            if (right_background != null) {
                mRight_2.setBackgroundDrawable(right_background);
            }
            setTextViewDrawable(mRight_2, right_drawable, false);
        }
    }

    public TextView getLeft1View() {
        return mLeft_1;
    }

    public TextView getLeft2View() {
        return mLeft_2;
    }

    public TextView getMiddle1View() {
        return mMiddle_1;
    }

    public TextView getMiddle2View() {
        return mMiddle_2;
    }

    public TextView getRight1View() {
        return mRight_1;
    }

    public TextView getRight2View() {
        return mRight_2;
    }

    /**
     * <br> Description: 设置标题内容
     * <br> Author:      LongZefeng
     * <br> Date:        2018/3/7 17:15
     */
    public void setTitle(CharSequence title) {
        mMiddle_1.setText(title);
    }

    public void setSubTitle(CharSequence title) {
        mMiddle_2.setVisibility(View.VISIBLE);
        mMiddle_2.setText(title);
    }

    public void setMultiTitle(CharSequence title,
                              CharSequence subTitle) {
        setTitle(title);
        setSubTitle(subTitle);
    }

    /**
     * <br> Description: 设置双标题，并设置小标题的大小及颜色
     * <br> Author:      LongZefeng
     * <br> Date:        2018/3/7 11:37
     *
     * @param subTitleSizePx 字体大小的px值，小于0则不设置
     * @param subTitleColor  字体的颜色值，Integer.MIN_VALUE则不设置
     */
    public void setMultiTitle(CharSequence title,
                              CharSequence subTitle,
                              int subTitleSizePx,
                              @ColorInt int subTitleColor) {
        mMiddle_2.setTextSize(TypedValue.COMPLEX_UNIT_PX ,subTitleSizePx);
        mMiddle_2.setTextColor(subTitleColor);
        setMultiTitle(title, subTitle);
    }

    /**
     * <br> Description: 中间标题在左右两侧的控件之内设置Margins
     * <br> Author:      LongZefeng
     * <br> Date:        2018/3/29 15:12
     */
    public void autoSetMiddleMargins(int paddingPx) {
        int leftWidth = mLeft_1.getWidth() + mLeft_2.getWidth();
        int rightWidth = mRight_1.getWidth() + mRight_2.getWidth();
        int padding = leftWidth > rightWidth ? leftWidth : rightWidth;
        padding += paddingPx;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLl_middle.getLayoutParams();
        layoutParams.setMargins(padding, 0, padding, 0);
        mLl_middle.setLayoutParams(layoutParams);
    }

    /**
     * <br> Description: 中间标题设置在左右两侧的控件之内
     * <br> Author:      LongZefeng
     * <br> Date:        2018/3/29 15:12
     */
    public void autoSetMiddleMargins() {
        autoSetMiddleMargins(0);
    }

    /**
     * <br> Description: 在TextView的左侧或右侧设置图片
     * <br> Author:      LongZefeng
     * <br> Date:        2018/3/7 10:32
     */
    public static void setTextViewDrawable(TextView tv, Drawable drawable, boolean isLeft) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        tv.setCompoundDrawables(isLeft ? drawable : null, null, isLeft ? null : drawable, null);
    }
    
    public static void setTextViewDrawable(TextView tv, @DrawableRes int drawableId, boolean isLeft) {
        setTextViewDrawable(tv, tv.getResources().getDrawable(drawableId), isLeft);
    }

    private final OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mTitleEventListener == null) {
                return;
            }
            int id = v.getId();
            if (id == R.id.tv_titleview_left_1) {
                mTitleEventListener.onLeft1Click(v);
            } else if (id == R.id.ll_titleview_middle) {
                mTitleEventListener.onMiddleClick(v);
            } else if (id == R.id.tv_titleview_right_1) {
                mTitleEventListener.onRight1Click(v);
            } else if (id == R.id.tv_titleview_left_2) {
                mTitleEventListener.onLeft2Click(v);
            } else if (id == R.id.tv_titleview_right_2) {
                mTitleEventListener.onRight2Click(v);
            }
        }
    };

    public interface TitleEventListener {
        void onLeft1Click(View v);
        void onLeft2Click(View v);
        void onMiddleClick(View v);
        void onRight1Click(View v);
        void onRight2Click(View v);
    }

    public void setTitleEventListener(TitleEventListener l) {
        mTitleEventListener = l;
    }

}
