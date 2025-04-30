package vip.cdms.arkreader.ui.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import vip.cdms.arkreader.databinding.FragmentScoreBinding;

public class ScoreFragment extends Fragment {
    private FragmentScoreBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentScoreBinding.inflate(inflater);
        NestedScrollView root = binding.getRoot();
//        root.post(() -> root.setBackground(createPolkaDotBackground(root.getWidth(), root.getHeight())));
        return root;
    }

//    public Drawable createPolkaDotBackground(int width, int height) {
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setColor(0x8800ff00);
//        int spacing = 30;
//        float dotRadius = 5f;
//        float clippedRadius = width * .6f;
//
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        for (int x = 0; x < width + spacing; x += spacing) {
//            for (int y = 0; y < height + spacing; y += spacing)
//                canvas.drawCircle(x, y, dotRadius -= .01f, paint);
//        }
//
//        Bitmap clippedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas clippedCanvas = new Canvas(clippedBitmap);
//        Path clippedPath = new Path();
//        clippedPath.addCircle(0, height, clippedRadius, Path.Direction.CW);
//        clippedCanvas.clipPath(clippedPath);
//        clippedCanvas.drawBitmap(bitmap, 0, 0, null);
//
//        return new BitmapDrawable(getResources(), clippedBitmap);
//    }
}
