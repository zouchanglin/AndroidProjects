package cn.tim.glide;


import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.BaseRequestOptions;

@GlideExtension
public class MyGlideExtension {
    private MyGlideExtension(){}

    /**
     * 全局统一配置
     * @param options options
     */
    @GlideOption
    public static BaseRequestOptions<?> injectOptions(BaseRequestOptions<?> options) {
        return options.placeholder(R.drawable.ic_loading)
                .timeout(5000)
                .error(R.drawable.ic_load_failed);
    }

    /**
     * 圆形图片扩展
     * @param options options
     * @return circle options
     */
    @GlideOption
    public static BaseRequestOptions<?> circleOptions(BaseRequestOptions<?> options) {
        return injectOptions(options)
                .circleCrop();
    }
}
