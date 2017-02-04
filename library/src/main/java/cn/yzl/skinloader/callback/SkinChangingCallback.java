package cn.yzl.skinloader.callback;

/**
 * Created by YZL on 2017/2/4.
 */

public interface SkinChangingCallback {
    void onStart();

    void onError(Exception e);

    void onComplete();

    public static DefaultSkinChagingCallback DEFAULT_SKIN_CHAGING_CALLBACK = new DefaultSkinChagingCallback();

    public class DefaultSkinChagingCallback implements SkinChangingCallback {

        @Override
        public void onStart() {

        }

        @Override
        public void onError(Exception e) {
            e.printStackTrace();
        }

        @Override
        public void onComplete() {

        }
    }

}
