package vip.cdms.arkreader.ui.main.fragments;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import lombok.val;
import vip.cdms.arkreader.data.ResourceAccessor;
import vip.cdms.arkreader.data.ResourceHelper;
import vip.cdms.arkreader.databinding.FragmentScoreBinding;

public class ScoreFragment extends Fragment {
    private FragmentScoreBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentScoreBinding.inflate(inflater);
        val root = binding.getRoot();

        // FIXME: delete (for test)
        new Thread(() -> {
            val cover = ResourceHelper.decodeImage(
                    ResourceAccessor.INSTANCE.getScore()
                            .getSortedEvents()[0]
                            .getCoverImage()
            );
            binding.getRoot().post(() -> {
                val drawable = new BitmapDrawable(getResources(), cover);
                binding.getRoot().setBackground(drawable);
            });
        }).start();

//        root.post(() -> root.setBackground(createPolkaDotBackground(root.getWidth(), root.getHeight())));
        return root;
    }

//    public Drawable createPolkaDotBackground(int width, int height) {
//        var paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setColor(0x8800ff00);
//        var spacing = 30;
//        var dotRadius = 5f;
//        var clippedRadius = width * .6f;
//
//        var bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        var canvas = new Canvas(bitmap);
//        for (int x = 0; x < width + spacing; x += spacing) {
//            for (int y = 0; y < height + spacing; y += spacing)
//                canvas.drawCircle(x, y, dotRadius -= .01f, paint);
//        }
//
//        var clippedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        var clippedCanvas = new Canvas(clippedBitmap);
//        var clippedPath = new Path();
//        clippedPath.addCircle(0, height, clippedRadius, Path.Direction.CW);
//        clippedCanvas.clipPath(clippedPath);
//        clippedCanvas.drawBitmap(bitmap, 0, 0, null);
//
//        return new BitmapDrawable(getResources(), clippedBitmap);
//    }
}
