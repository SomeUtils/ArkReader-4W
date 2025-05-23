package vip.cdms.arkreader.ui.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import lombok.Setter;
import lombok.val;
import vip.cdms.arkreader.R;

@Setter
public class FlexibleTextView extends AppCompatTextView {
    private float fontWeight;

    public FlexibleTextView(@NonNull Context context) {
        this(context, null);
    }

    public FlexibleTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public FlexibleTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //noinspection resource
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FlexibleTextView);
        fontWeight = attributes.getFloat(R.styleable.FlexibleTextView_textWeight, -1f);
        attributes.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (fontWeight == -1) {
            super.onDraw(canvas);
            return;
        }
        val strokeWidth = getPaint().getStrokeWidth();
        val style = getPaint().getStyle();
        getPaint().setStrokeWidth(fontWeight);
        getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        super.onDraw(canvas);
        getPaint().setStrokeWidth(strokeWidth);
        getPaint().setStyle(style);
    }
}
