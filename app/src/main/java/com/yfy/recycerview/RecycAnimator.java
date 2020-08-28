package com.yfy.recycerview;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;


/**
 * Created by yfyandr on 2018/1/24.
 */

public class RecycAnimator extends DefaultItemAnimator {
private static final String TAG = "MyDefaultItemAnimator";

// 定义动画执行时的加速度
private AccelerateInterpolator mAccelerateInterpolator = new AccelerateInterpolator();
private DecelerateInterpolator mDecelerateInterpolator = new DecelerateInterpolator();

        ArgbEvaluator mColorEvaluator = new ArgbEvaluator();

/**
 * 定义正在执行Animator的ViewHolder的Map集合
 * 此集合会保存用户点击的ViewHolder对象，目的在于当用户不停的点击某一item时
 * 会先判断此ViewHolder种的itemView动画是否正在执行，如果正在执行则停止
 */
private ArrayMap<RecyclerView.ViewHolder, AnimatorInfo> mAnimatorMap = new ArrayMap<>();

/**
 * 复写canReuseUpdatedViewHolder方法并返回true，通知RecyclerView在执行动画时可以复用ViewHolder对象
 * @param viewHolder
 * @return
 */
@Override
public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
        return true;
        }

/**
 * 自定义getItemHolderInfo方法，将ViewHolder中的背景颜色和TextView的文本信息传入ColorTextInfo中
 * @param viewHolder
 * @param info
 * @return
 */


/**
 * 通过ViewHolder对象获取动画执行之前itemView中的背景颜色和文本信息
 * 初始化ColorTextInfo对象，并将背景颜色和文本信息进行赋值
 * @return
 */


/**
 * 通过ViewHolder对象获取动画执行之后itemView中的背景颜色和文本信息
 * 初始化ColorTextInfo对象，并将背景颜色和文本信息进行赋值
 * @return
 */


/**
 * 复写obtainHolderInfo，返回自定义的ItemHolderInfo对象
 * @return
 */
@Override
public ItemHolderInfo obtainHolderInfo() {
        Log.e(TAG, "obtainHolderInfo: ");
        return new ColorTextInfo();
        }

/**
 * 自定义ItemHolderInfo对象，持有两个变量，依次来表示每一个Item的背景颜色和文本信息
 */
private class ColorTextInfo extends ItemHolderInfo {
    int color;
    String text;
}

/**
 * 创建执行animateChange的动画Info对象，内部封装了所需要执行一个动画类的相关信息
 * 起始alpha属性动画，和起始旋转属性动画
 */
private class AnimatorInfo {
    Animator overallAnim;
    ObjectAnimator fadeToBlackAnim, fadeFromBlackAnim, oldTextRotator, newTextRotator;

    public AnimatorInfo(Animator overallAnim,
                        ObjectAnimator fadeToBlackAnim, ObjectAnimator fadeFromBlackAnim,
                        ObjectAnimator oldTextRotator, ObjectAnimator newTextRotator) {
        this.overallAnim = overallAnim;
        this.fadeToBlackAnim = fadeToBlackAnim;
        this.fadeFromBlackAnim = fadeFromBlackAnim;
        this.oldTextRotator = oldTextRotator;
        this.newTextRotator = newTextRotator;
    }
}


    /**
     * Custom change animation. Fade to black on the container background, then back
     * up to the new bg coolor. Meanwhile, the text rotates, switching along the way.
     * If a new change animation occurs on an item that is currently animating
     * a change, we stop the previous change and start the new one where the old
     * one left off.
     * 真正的执行change动画的方法：
     * 通过传入的preInfo和postInfo，分别将动画前后的背景色和文本信息设置到alpha属性动画和旋转属性动画中
     */

    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {
        super.endAnimation(item);
        if (!mAnimatorMap.isEmpty()) {
            final int numRunning = mAnimatorMap.size();
            for (int i = numRunning; i >= 0; i--) {
                if (item == mAnimatorMap.keyAt(i)) {
                    mAnimatorMap.valueAt(i).overallAnim.cancel();
                }
            }
        }
    }

    @Override
    public boolean isRunning() {
        return super.isRunning() || !mAnimatorMap.isEmpty();
    }

    @Override
    public void endAnimations() {
        super.endAnimations();
        if (!mAnimatorMap.isEmpty()) {
            final int numRunning = mAnimatorMap.size();
            for (int i = numRunning; i >= 0; i--) {
                mAnimatorMap.valueAt(i).overallAnim.cancel();
            }
        }
    }
}