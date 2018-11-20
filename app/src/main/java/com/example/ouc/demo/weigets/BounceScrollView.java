package com.example.ouc.demo.weigets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

//public class BounceScrollView extends ScrollView {
//     private View inner;// 孩子View
//      private float y;
//      // 点击时y坐标
//      private Rect normal = new Rect();
//      // 矩形(这里只是个形式，只是用于判断是否需要动画.)
//      private boolean isCount = false;
//      // 是否开始计算
//      public BounceScrollView(Context context, AttributeSet attrs) {
//          super(context, attrs);
//      }
//
//
//    /***
//     * 根据 XML 生成视图工作完成.该函数在生成视图的最后调用，在所有子视图添加完之后. 即使子类覆盖了 onFinishInflate
//     * 方法，也应该调用父类的方法，使该方法得以执行.
//     */
//
//    @SuppressLint("MissingSuperCall")
//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//        if (getChildCount() > 0) {
//            inner = getChildAt(0);
//        }
//    }
//    /***
//     * 监听touch
//     */
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        if (inner != null) {
//            commOnTouchEvent(ev);
//        }
//
//        return super.onTouchEvent(ev);
//    }
//
//
//    //不拦截，继续分发下去
////    @Override
////    public boolean onInterceptTouchEvent(MotionEvent ev) {
////        return false;
////    }
//    /***
//     * 触摸事件
//     *
//     * @param e
//     */
//    public void commOnTouchEvent(MotionEvent e) {
//        int action = e.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                break; case MotionEvent.ACTION_UP:
//                    // 手指松开.
//                 if (isNeedAnimation()) {
//                     animation();
//                     isCount = false;
//                 }
//                 break;
//            /*** * 排除出第一次移动计算，因为第一次无法得知y坐标， 在MotionEvent.ACTION_DOWN中获取不到， * 因为此时是MyScrollView的touch事件传递到到了LIstView的孩子item上面.所以从第二次计算开始. * 然而我们也要进行初始化，就是第一次移动的时候让滑动距离归0. 之后记录准确了就正常执行. */
//
//
//            case MotionEvent.ACTION_MOVE:
//                final float preY = y;
//                // 按下时的y坐标
//                 float nowY = e.getY();
//                 // 时时y坐标
//                 int deltaY = (int) (preY - nowY);
//                 // 滑动距离
//                 if (!isCount) {
//                     deltaY = 0;
//                     // 在这里要归0.
//                      }
//                y = nowY;
//                // 当滚动到最上或者最下时就不会再滚动，这时移动布局
//                if (isNeedMove()) {
//                    // 初始化头部矩形
//                    if (normal.isEmpty()) {
//                        // 保存正常的布局位置
//                        normal.set(inner.getLeft(), inner.getTop(),
//                                inner.getRight(), inner.getBottom());
//                    }
//                    inner.layout(inner.getLeft(), inner.getTop() - deltaY / 2,
//                            inner.getRight(), inner.getBottom() - deltaY / 2);
//                }
//                isCount = true;
//                break;
//
//            default:
//                break;
//        }
//    }
//    public void animation() {
//        TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
//                normal.top);
//        ta.setDuration(200);
//        inner.startAnimation(ta);
//        inner.layout(normal.left, normal.top, normal.right, normal.bottom);
//        normal.setEmpty();
//    }
//
//    public boolean isNeedAnimation() {
//        return !normal.isEmpty();
//    }
//
//    public boolean isNeedMove() {
//        int offset = inner.getMeasuredHeight() - getHeight();
//        int scrollY = getScrollY();
//        if (scrollY == 0 || scrollY == offset) {
//            return true;
//        }
//        return false;
//    }
// }
public class BounceScrollView extends ScrollView{

    private boolean mEnableTopRebound = true;
    private boolean mEnableBottomRebound = true;
    private OnReboundEndListener mOnReboundEndListener;
    private View mContentView;
    private Rect mRect = new Rect();

    public BounceScrollView(Context context) {
        super(context);
    }

    public BounceScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BounceScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /** after inflating view, we can get the width and height of view */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = getChildAt(0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mContentView == null) return;
        // to remember the location of mContentView
        mRect.set(mContentView.getLeft(), mContentView.getTop(), mContentView.getRight(), mContentView.getBottom());
    }

    public BounceScrollView setOnReboundEndListener(OnReboundEndListener onReboundEndListener){
        this.mOnReboundEndListener = onReboundEndListener;
        return this;

    }

    public BounceScrollView setEnableTopRebound(boolean enableTopRebound){
        this.mEnableTopRebound = enableTopRebound;
        return this;
    }

    public BounceScrollView setEnableBottomRebound(boolean mEnableBottomRebound){
        this.mEnableBottomRebound = mEnableBottomRebound;
        return this;
    }

    private int lastY;
    private boolean rebound = false;
    private int reboundDirection = 0; //<0 表示下部回弹 >0 表示上部回弹 0表示不回弹

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mContentView == null){
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastY = (int) ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                if (!isScrollToTop() && !isScrollToBottom()){
                    lastY = (int) ev.getY();
                    break;
                }
                //处于顶部或者底部
                int deltaY = (int) (ev.getY() - lastY);
                //deltaY > 0 下拉 deltaY < 0 上拉


                //disable top or bottom rebound
                if ((!mEnableTopRebound && deltaY > 0) || (!mEnableBottomRebound && deltaY < 0)){
                    break;
                }

                int offset = (int) (deltaY * 0.48);
                mContentView.layout(mRect.left, mRect.top + offset, mRect.right, mRect.bottom + offset);
                rebound = true;
                break;

            case MotionEvent.ACTION_UP:
                if (!rebound) break;
                reboundDirection = mContentView.getTop() - mRect.top;
                TranslateAnimation animation = new TranslateAnimation(0, 0, mContentView.getTop(), mRect.top);
                animation.setDuration(300);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (mOnReboundEndListener != null){
                            if (reboundDirection > 0){
                                mOnReboundEndListener.onReboundTopComplete();
                            }
                            if (reboundDirection < 0){
                                mOnReboundEndListener.onReboundBottomComplete();
                            }
                            reboundDirection = 0;
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mContentView.startAnimation(animation);
                mContentView.layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
                rebound = false;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setFillViewport(boolean fillViewport) {
        super.setFillViewport(true); //默认是填充ScrollView 或者再XML布局文件中设置fillViewport属性
    }

    /**
     * 判断当前ScrollView是否处于顶部
     */
    private boolean isScrollToTop(){
        return getScrollY() == 0;
    }

    /**
     * 判断当前ScrollView是否已滑到底部
     */
    private boolean isScrollToBottom(){
        return mContentView.getHeight() <= getHeight() + getScrollY();
    }

    /**
     * listener for top and bottom rebound
     * do your implement in the following methods
     */
    public interface OnReboundEndListener{

        void onReboundTopComplete();

        void onReboundBottomComplete();
    }
}

