package com.wangbin.uikit.widget;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;


/**
 * 手机号码自动格式化显示，前3位数字后跟一个空格，之后每4位数字后加一个空格。
 */
public class NumberFormatEditText extends AppCompatEditText {

    private String mRealNumber;

    public NumberFormatEditText(Context context) {
        super(context);
        init();
    }

    public NumberFormatEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public String getRealNumber() {
        return mRealNumber;
    }

    private void init() {
        setInputType(InputType.TYPE_CLASS_NUMBER);
        addTextChangedListener(new TextWatcher() {
            private int mVaryType = 0;
            private static final int VARY_ADD_ONE = 1;
            private static final int VARY_CLEAR_ONE = 2;
            private int mSelection = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (count == 0 && after == 1) {
                    mVaryType = VARY_ADD_ONE;
                    mSelection = start + 1;
                } else if (count == 1 && after == 0) {
                    mVaryType = VARY_CLEAR_ONE;
                    mSelection = start;
                } else {
                    mVaryType = 0;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String currText = s.toString();
                mRealNumber = currText.replaceAll("\\D", "");
                String destText = getFormatStrByNumber(mRealNumber);

                if (!currText.equals(destText)) {
                    if (destText == null) {
                        setText("");
                    } else {
                        int sel = destText.length();
                        if (mVaryType == VARY_ADD_ONE || mVaryType == VARY_CLEAR_ONE) {
                            sel = mSelection;

                            boolean nearBlank = false;
                            if (sel == 4 || (sel > 4 && (sel - 4) % 5 == 0)) {
                                nearBlank = true;
                            }
                            if (nearBlank) {
                                if (mVaryType == VARY_ADD_ONE) {
                                    sel++;
                                } else {
                                    sel--;
                                }
                            }

                            if (sel > destText.length()) {
                                sel = destText.length();
                            }
                        }
                        setText(destText);
                        setSelection(sel);
                    }
                } else {
                    if (mVaryType == VARY_CLEAR_ONE) {
                        int sel = mSelection;
                        if (sel == 4 || (sel > 4 && (sel - 4) % 5 == 0)) {
                            sel--;
                        }
                        if (sel > destText.length()) {
                            sel = destText.length();
                        }
                        setSelection(sel);
                    }
                }
            }
        });
    }

    private String getFormatStrByNumber(String realNumber) {
        String destText = null;

        if (realNumber == null) {
            return null;
        }

        if (realNumber.length() <= 3) {
            destText = realNumber;
        } else if (realNumber.length() <= 7) {
            destText = realNumber.substring(0, 3) + " " + realNumber.substring(3);
        } else {
            destText = realNumber.substring(0, 3);
            int len = 3;
            while (len < realNumber.length()) {
                int tail = len + 4;
                destText += " " + realNumber.substring(len,
                        tail <= realNumber.length() ? tail : realNumber.length());
                len += 4;
            }
        }

        return destText;
    }

}
