package cn.xxt.componentzhqapplication.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.xxt.componentzhqapplication.R;
import cn.xxt.componentzhqapplication.module.recommand.RecommandBodyValue;
import cn.xxt.customsdk.adutil.Utils;
import cn.xxt.customsdk.imageloader.ImageLoaderManager;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * homeFragment的adapter
 * @author zhq
 * @date 2018/11/21 下午2:51
 */
public class CourseAdapter extends BaseAdapter {

    /** item类型的数量 */
    private static final int CARD_COUNT = 4;
    /** item类型：视频，单图片，多图片，viewPager */
    private static final int VIDEO_TYPE = 0x00;
    private static final int CARD_SINGLE_PIC = 0x02;
    private static final int CARD_MULTI_PIC = 0x01;
    private static final int CARD_VIEW_PAGER = 0x03;

    private Context context;

    private ViewHolder viewHolder;

    private LayoutInflater inflater;

    private ArrayList<RecommandBodyValue> dataSource;

    private ImageLoaderManager imageLoaderManager;

    public CourseAdapter(Context context, ArrayList<RecommandBodyValue> dataSource) {
        this.context = context;
        this.dataSource = dataSource;
        inflater = LayoutInflater.from(context);
        imageLoaderManager = ImageLoaderManager.getInstance(context);
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * item类型的总数量
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return CARD_COUNT;
    }

    /**
     * 返回当前item的类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        RecommandBodyValue bodyValue = dataSource.get(position);
        return bodyValue.type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        RecommandBodyValue value = dataSource.get(position);

        if (null == convertView) {
            switch (type) {
                case CARD_SINGLE_PIC:
                    //单图时 加载
                    viewHolder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.item_card_single_pic, parent,
                            false);
                    viewHolder.civLogo = convertView.findViewById(R.id.civ_logo);
                    viewHolder.tvTitle = convertView.findViewById(R.id.tv_title);
                    viewHolder.tvInfo = convertView.findViewById(R.id.tv_info);
                    viewHolder.tvFooter = convertView.findViewById(R.id.tv_footer);
                    viewHolder.ivSingleImage = convertView.findViewById(R.id.iv_single_img);
                    viewHolder.tvPrice = convertView.findViewById(R.id.tv_price);
                    viewHolder.tvFrom = convertView.findViewById(R.id.tv_from);
                    viewHolder.tvZan = convertView.findViewById(R.id.tv_zan);
                    break;

                case CARD_MULTI_PIC:
                    viewHolder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.item_card_multi_pic, parent,
                            false);
                    viewHolder.civLogo = convertView.findViewById(R.id.civ_logo);
                    viewHolder.tvTitle = convertView.findViewById(R.id.tv_title);
                    viewHolder.tvInfo = convertView.findViewById(R.id.tv_info);
                    viewHolder.tvFooter = convertView.findViewById(R.id.tv_footer);
                    viewHolder.tvPrice = convertView.findViewById(R.id.tv_price);
                    viewHolder.tvFrom = convertView.findViewById(R.id.tv_from);
                    viewHolder.tvZan = convertView.findViewById(R.id.tv_zan);
                    viewHolder.llMultiImage = convertView.findViewById(R.id.ll_multi_image);
                    break;



                default:
            }

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //填充数据
        switch (type) {
            case CARD_SINGLE_PIC:
                imageLoaderManager.displayImage(value.logo, viewHolder.civLogo);
                viewHolder.tvTitle.setText(value.title);
                viewHolder.tvInfo.setText(value.info);
                viewHolder.tvFooter.setText(value.text);
                viewHolder.tvPrice.setText(value.price);
                viewHolder.tvFrom.setText(value.from);
                viewHolder.tvZan.setText("点赞".concat(value.zan));

                imageLoaderManager.displayImage(value.url.get(0), viewHolder.ivSingleImage);
                break;

            case CARD_MULTI_PIC:
                imageLoaderManager.displayImage(value.logo, viewHolder.civLogo);
                viewHolder.tvTitle.setText(value.title);
                viewHolder.tvInfo.setText(value.info);
                viewHolder.tvFooter.setText(value.text);
                viewHolder.tvPrice.setText(value.price);
                viewHolder.tvFrom.setText(value.from);
                viewHolder.tvZan.setText("点赞" + value.zan);

                viewHolder.llMultiImage.removeAllViews();
                //动态添加多个ImageView
                for (String url : value.url) {
                    viewHolder.llMultiImage.addView(createImageView(url));
                }

                break;
            default:
        }

        return convertView;
    }

    /**
     * 动态创建并展示图片
     * @param url
     * @return
     */
    private ImageView createImageView(String url) {
        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.
                LayoutParams(Utils.dip2px(context, 100),
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.leftMargin = Utils.dip2px(context, 5);
        imageView.setLayoutParams(params);
        imageLoaderManager.displayImage(url, imageView);
        return imageView;
    }




    private static class ViewHolder {
        /** 所有card都有的控件 */
        private CircleImageView civLogo;
        private TextView tvTitle;
        private TextView tvInfo;
        private TextView tvFooter;

        /** Video card 的特有控件 */
        private RelativeLayout rlVideo;
        private ImageView ivShareImage;

        /** 另外三种card 都有的控件 */
        private TextView tvPrice;
        private TextView tvFrom;
        private TextView tvZan;

        /** multi image card 的特有控件 */
        private LinearLayout llMultiImage;

        /** single image card 的特有控件 */
        private ImageView ivSingleImage;

        /** view pager card 的特有控件 */
        private ViewPager viewPager;
    }
}
