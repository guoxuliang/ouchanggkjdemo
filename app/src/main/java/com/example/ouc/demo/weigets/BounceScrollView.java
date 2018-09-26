package com.example.ouc.demo.weigets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

public class BounceScrollView extends ScrollView {
     private View inner;// 孩子View
      private float y;
      // 点击时y坐标
      private Rect normal = new Rect();
      // 矩形(这里只是个形式，只是用于判断是否需要动画.)
      private boolean isCount = false;
      // 是否开始计算
      public BounceScrollView(Context context, AttributeSet attrs) {
          super(context, attrs);
      }


    /***
     * 根据 XML 生成视图工作完成.该函数在生成视图的最后调用，在所有子视图添加完之后. 即使子类覆盖了 onFinishInflate
     * 方法，也应该调用父类的方法，使该方法得以执行.
     */

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            inner = getChildAt(0);
        }
    }
    /***
     * 监听touch
     */

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner != null) {
            commOnTouchEvent(ev);
        }

        return super.onTouchEvent(ev);
    }
    /***
     * 触摸事件
     *
     * @param e
     */
    public void commOnTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break; case MotionEvent.ACTION_UP:
                    // 手指松开.
                 if (isNeedAnimation()) {
                     animation();
                     isCount = false;
                 }
                 break;
            /*** * 排除出第一次移动计算，因为第一次无法得知y坐标， 在MotionEvent.ACTION_DOWN中获取不到， * 因为此时是MyScrollView的touch事件传递到到了LIstView的孩子item上面.所以从第二次计算开始. * 然而我们也要进行初始化，就是第一次移动的时候让滑动距离归0. 之后记录准确了就正常执行. */


            case MotionEvent.ACTION_MOVE:
                final float preY = y;
                // 按下时的y坐标
                 float nowY = e.getY();
                 // 时时y坐标
                 int deltaY = (int) (preY - nowY);
                 // 滑动距离
                 if (!isCount) {
                     deltaY = 0;
                     // 在这里要归0.
                      }
                y = nowY;
                // 当滚动到最上或者最下时就不会再滚动，这时移动布局
                if (isNeedMove()) {
                    // 初始化头部矩形
                    if (normal.isEmpty()) {
                        // 保存正常的布局位置
                        normal.set(inner.getLeft(), inner.getTop(),
                                inner.getRight(), inner.getBottom());
                    }
                    inner.layout(inner.getLeft(), inner.getTop() - deltaY / 2,
                            inner.getRight(), inner.getBottom() - deltaY / 2);
                }
                isCount = true;
                break;

            default:
                break;
        }
    }
    public void animation() {
        TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
                normal.top);
        ta.setDuration(200);
        inner.startAnimation(ta);
        inner.layout(normal.left, normal.top, normal.right, normal.bottom);
        normal.setEmpty();
    }

    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    public boolean isNeedMove() {
        int offset = inner.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }






 }


