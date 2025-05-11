package vip.cdms.arkreader.resource;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface OperatorModule {
    String getName();

    String getDescription();

    String getTagText();

    @TagColor
    String getTagColor();

    byte[] getEquipIcon();

    @StringDef({
            TagColor.BLUE,
            TagColor.GREEN,
            TagColor.GREY,
            TagColor.PURPLE,
            TagColor.RED,
            TagColor.YELLOW
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface TagColor {
        String BLUE = "blue";
        String GREEN = "green";
        String GREY = "grey";
        String PURPLE = "purple";
        String RED = "red";
        String YELLOW = "yellow";
    }
}
