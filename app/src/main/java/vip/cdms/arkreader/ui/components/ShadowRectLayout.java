package vip.cdms.arkreader.ui.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import lombok.var;
import vip.cdms.arkreader.R;

public class ShadowRectLayout extends LinearLayout {
    private final Paint mPaint;
    private final int mExtraPadding;
    private final int mOriginalPaddingLeft;
    private final int mOriginalPaddingTop;
    private final int mOriginalPaddingRight;
    private final int mOriginalPaddingBottom;

    public ShadowRectLayout(Context context) {
        this(context, null);
    }

    public ShadowRectLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowRectLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //noinspection resource
        var attributes = context.obtainStyledAttributes(attrs, R.styleable.ShadowRectLayout);
        var rectColor = attributes.getColor(R.styleable.ShadowRectLayout_rectColor, Color.TRANSPARENT);
        var shadowColor = attributes.getColor(R.styleable.ShadowRectLayout_shadowColor, Color.TRANSPARENT);
        var shadowRadius = attributes.getDimension(R.styleable.ShadowRectLayout_shadowRadius, 0f);
        var shadowDx = attributes.getDimension(R.styleable.ShadowRectLayout_shadowDx, -1);
        var shadowDy = attributes.getDimension(R.styleable.ShadowRectLayout_shadowDy, -1);
        attributes.recycle();

        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(rectColor);
        mPaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);

        mExtraPadding = (int) Math.ceil(shadowRadius + Math.max(Math.abs(shadowDx), Math.abs(shadowDy)));
        setPadding((mOriginalPaddingLeft = getPaddingLeft()) + mExtraPadding,
                (mOriginalPaddingTop = getPaddingTop()) + mExtraPadding,
                (mOriginalPaddingRight = getPaddingRight()) + mExtraPadding,
                (mOriginalPaddingBottom = getPaddingBottom()) + mExtraPadding);
        setTranslationX(-mExtraPadding);
        setTranslationY(-mExtraPadding);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int w = getMeasuredWidth() + mExtraPadding * 2;
//        int h = getMeasuredHeight() + mExtraPadding * 2;
//        setMeasuredDimension(w, h);
//    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        var left = getPaddingLeft() - mOriginalPaddingLeft;
        var top = getPaddingTop() - mOriginalPaddingTop;
        var right = getWidth() - getPaddingRight() + mOriginalPaddingRight;
        var bottom = getHeight() - getPaddingBottom() + mOriginalPaddingBottom;
        canvas.drawRect(left, top, right, bottom, mPaint);
        super.dispatchDraw(canvas);
    }
}
