package image.wangpeng.com.coordinatorlayoutapplication;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private Toolbar mToolbar;
    private AppBarLayout appbar;
    private TextView mTargetNick;
    private TextView mTargetSign;
    private CircleImageView mTargetHead;
    private float mSelfHeight = 0;//用以判断是否得到正确的宽高值
    private float mTitleScale;
    private float mSubScribeScale;
    private float mHeadImgScale;
    private float mSubScribeScaleX;
    private int initHeight;
    private int toolbarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        appbar = findViewById(R.id.appbar);
        mTargetNick = findViewById(R.id.nick_name);
        mTargetHead = findViewById(R.id.head_img);
        mTargetSign = findViewById(R.id.sign);
        initHeight = DensityUtil.dip2px(this, 210);
        toolbarHeight = DensityUtil.dip2px(this, 56);
        appbar.addOnOffsetChangedListener(this);
        setSupportActionBar(mToolbar);
        //设置是否有返回箭头
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        Log.i("DependentBehavior", "verticalOffset:" + verticalOffset);
        if (mSelfHeight == 0) {
            mSelfHeight = mTargetNick.getHeight();
            float distanceTitle = mTargetNick.getTop() - (toolbarHeight - mTargetNick.getHeight()) / 2.0f;
            float distanceSubscribe = mTargetSign.getY() - (toolbarHeight - mTargetSign.getHeight()) / 2.0f;
            float distanceHeadImg = mTargetHead.getY() - (toolbarHeight - mTargetHead.getHeight()) / 2.0f;
            float distanceSubscribeX = DensityUtil.getDipWidth(appBarLayout.getContext()) / 2.0f - (mTargetSign.getWidth() / 2.0f);
            mTitleScale = distanceTitle / (initHeight - toolbarHeight);
            mSubScribeScale = distanceSubscribe / (initHeight - toolbarHeight);
            mHeadImgScale = distanceHeadImg / (initHeight - toolbarHeight);
            mSubScribeScaleX = distanceSubscribeX / (initHeight - toolbarHeight);
            Log.i("DependentBehavior s", "distanceTitle:" + distanceTitle + " distanceSubscribe:" + distanceSubscribe + " distanceHeadImg:" + distanceHeadImg);
        }
        float scale = 1.0f - (-verticalOffset * 1.0f) / (initHeight - toolbarHeight);
        Log.i("DependentBehavior s", "scale:" + scale + " initHeight:" + initHeight + " toolbarHeight:" + toolbarHeight);
        mTargetHead.setScaleX(scale);
        mTargetHead.setScaleY(scale);
        mTargetHead.setTranslationY(mHeadImgScale * verticalOffset);
        mTargetNick.setTranslationY(mTitleScale * verticalOffset);
        mTargetSign.setTranslationY(mSubScribeScale * verticalOffset);
        mTargetSign.setTranslationX(-mSubScribeScaleX * verticalOffset);
    }
}
