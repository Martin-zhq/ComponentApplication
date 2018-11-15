package cn.xxt.customsdk.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import cn.xxt.customsdk.R;

/**
 * 初始化UniverseImageLoader，并用来加载网络图片
 * @author zhq
 * @date 2018/11/15 上午11:43
 */
public class ImageLoaderManager {

    /** 表明加载图片时最多有4条线程 */
    private static final int THREAD_COUNT = 4;
    /** 表明图片加载的优先级 */
    private static final int PRIORITY = 2;
    /** 图片缓存的大小 */
    private static final int DISK_CACHE_SIZE = 50 * 1024;
    /** 图片请求超时时间 */
    private static final int CONNECTION_TIME_OUT = 5 * 1000;
    /** 图片读取的超时时间 */
    private static final int READ_TIME_OUT = 30 * 1000;

    private static ImageLoader imageLoader = null;

    private static ImageLoaderManager instance = null;

    public static ImageLoaderManager getInstance(Context context) {
        if (null == instance) {
            synchronized (ImageLoaderManager.class) {
                if (null == instance) {
                    instance = new ImageLoaderManager(context);
                }
            }
        }

        return instance;
    }

    private ImageLoaderManager(Context context) {

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration
                .Builder(context)
                //图片下载线程最多数量
                .threadPoolSize(THREAD_COUNT)
                //线程优先级
                .threadPriority(Thread.NORM_PRIORITY - PRIORITY)
                //防止缓存多套尺寸图片
                .denyCacheImageMultipleSizesInMemory()
                // 弱引用，内存不足时，系统会回收
                .memoryCache(new WeakMemoryCache())
                //分配硬盘缓存大小
                .diskCacheSize(DISK_CACHE_SIZE)
                //使用MD5进行缓存文件命名
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                //图片下载顺序
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                //默认的图片加载option
                .defaultDisplayImageOptions(getDefaultOptions())
                //设置图片下载器
                .imageDownloader(new BaseImageDownloader(context, CONNECTION_TIME_OUT,
                        READ_TIME_OUT))
                //debug环境下会输出日志
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(configuration);
        imageLoader = ImageLoader.getInstance();
    }

    /**
     * 实现默认的图片展示options
     * @return
     */
    private DisplayImageOptions getDefaultOptions() {
        DisplayImageOptions options = new DisplayImageOptions
                .Builder()
                .showImageForEmptyUri(R.drawable.default_user_avatar)
                .showImageOnFail(R.drawable.default_user_avatar)
                //设置图片可缓存在内存中
                .cacheInMemory(true)
                //设置图片可缓存在硬盘中
                .cacheOnDisk(true)
                //使用的图片解码类型
                .bitmapConfig(Bitmap.Config.RGB_565)
                //图片解码配置
                .decodingOptions(new BitmapFactory.Options())
                .build();

        return options;
    }

    /**
     * 供外部调用展示图片的方法
     * @param url
     * @param imageView
     * @param options
     * @param loadingListener
     */
    public void displayImage(String url, ImageView imageView, DisplayImageOptions options,
                             ImageLoadingListener loadingListener) {
        if (null != imageLoader) {
            imageLoader.displayImage(url, imageView, options, loadingListener);
        }
    }

    /**
     * 进行重载，可以不设置options
      * @param url
     * @param imageView
     * @param loadingListener
     */
    public void displayImage(String url, ImageView imageView, ImageLoadingListener loadingListener) {
        displayImage(url, imageView, null, loadingListener);
    }

    /**
     * 最简单的调用，只设置图片和url
     * @param url
     * @param imageView
     */
    public void displayImage(String url, ImageView imageView) {
        displayImage(url, imageView, null);
    }
}
