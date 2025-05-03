package vip.cdms.arkreader.ui.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import lombok.Setter;
import lombok.val;
import vip.cdms.arkreader.R;

public class ShadowRectLayout extends LinearLayout {
    private final Paint mPaint;

    @Setter
    private int mRectColor = Color.TRANSPARENT;
    @Setter
    private int mShadowColor = Color.TRANSPARENT;
    @Setter
    private float mShadowRadius = 0;
    @Setter
    private float mShadowDx = 0;
    @Setter
    private float mShadowDy = 0;

    private int mOriginalPaddingLeft;
    private int mOriginalPaddingTop;
    private int mOriginalPaddingRight;
    private int mOriginalPaddingBottom;

    public ShadowRectLayout(Context context) {
        this(context, null);
    }

    public ShadowRectLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowRectLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //noinspection resource
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ShadowRectLayout);
        mRectColor = attributes.getColor(R.styleable.ShadowRectLayout_rectColor, mRectColor);
        mShadowColor = attributes.getColor(R.styleable.ShadowRectLayout_shadowColor, mShadowColor);
        mShadowRadius = attributes.getDimension(R.styleable.ShadowRectLayout_shadowRadius, mShadowRadius);
        mShadowDx = attributes.getDimension(R.styleable.ShadowRectLayout_shadowDx, mShadowDx);
        mShadowDy = attributes.getDimension(R.styleable.ShadowRectLayout_shadowDy, mShadowDy);
        attributes.recycle();

        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        updatePaint();
        updatePosition();
    }

    public void updatePaint() {
        mPaint.setColor(mRectColor);
        mPaint.setShadowLayer(mShadowRadius, mShadowDx, mShadowDy, mShadowColor);
    }

    public void updatePosition() {
        int mExtraPadding = (int) Math.ceil(mShadowRadius + Math.max(Math.abs(mShadowDx), Math.abs(mShadowDy)));
        setPadding((mOriginalPaddingLeft = getPaddingLeft()) + mExtraPadding,
                (mOriginalPaddingTop = getPaddingTop()) + mExtraPadding,
                (mOriginalPaddingRight = getPaddingRight()) + mExtraPadding,
                (mOriginalPaddingBottom = getPaddingBottom()) + mExtraPadding);
        setTranslationX(-mExtraPadding);
        setTranslationY(-mExtraPadding);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        val left = getPaddingLeft() - mOriginalPaddingLeft;
        val top = getPaddingTop() - mOriginalPaddingTop;
        val right = getWidth() - getPaddingRight() + mOriginalPaddingRight;
        val bottom = getHeight() - getPaddingBottom() + mOriginalPaddingBottom;
        canvas.drawRect(left, top, right, bottom, mPaint);
        super.dispatchDraw(canvas);
    }
}
