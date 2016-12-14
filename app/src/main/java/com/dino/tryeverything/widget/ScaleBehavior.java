package com.dino.tryeverything.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.animation.ValueAnimatorCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.dino.tryeverything.R;
import com.dino.tryeverything.utils.AnimatorUtils;

/**
 * Created by Dino on 11/7 0007.
 */

public class ScaleBehavior extends AppBarLayout.Behavior {

    private ValueAnimatorCompat mAnimator;
    private Context context;

    public ScaleBehavior(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        this.context = context;
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, AppBarLayout child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed) {
        if (getTopAndBottomOffset() > 0) {
            animateOffsetTo(coordinatorLayout, child, 0);
        } else {
            super.onStopNestedScroll(coordinatorLayout, child, target);
        }
    }

    private void animateOffsetTo(final CoordinatorLayout coordinatorLayout,
                                 final AppBarLayout child, final int offset) {
        ImageView imageView = (ImageView)coordinatorLayout.findViewById(R.id.image);
        AnimatorUtils.startRotation(context, imageView, 300, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
    }



}
