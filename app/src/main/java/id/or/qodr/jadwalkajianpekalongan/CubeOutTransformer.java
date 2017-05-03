package id.or.qodr.jadwalkajianpekalongan;

import android.view.View;

/**
 * Created by adul on 27/04/17.
 */

public class CubeOutTransformer extends BaseTransformer  {

    @Override
    protected void onTransform(View view, float position) {
        // Cubeout
        view.setPivotX(position < 0f ? view.getWidth() : 0f);
        view.setPivotY(view.getHeight() * 0.5f);
        view.setRotationY(90f * position);

//        final float rotation = 180f * position;
//
//        view.setAlpha(rotation > 90f || rotation < -90f ? 0 : 1);
//        view.setPivotX(view.getWidth() * 0.5f);
//        view.setPivotY(view.getHeight() * 0.5f);
//        view.setRotationY(rotation);
        /* Zoomin
        final float scale = position < 0 ? position + 1f : Math.abs(1f - position);
        view.setScaleX(scale);
        view.setScaleY(scale);
        view.setPivotX(view.getWidth() * 0.5f);
        view.setPivotY(view.getHeight() * 0.5f);
        view.setAlpha(position < -1f || position > 1f ? 0f : 1f - (scale - 1f));
        */
    }

    //CubeOut
    @Override
    public boolean isPagingEnabled() {
        return true;
    }
}
