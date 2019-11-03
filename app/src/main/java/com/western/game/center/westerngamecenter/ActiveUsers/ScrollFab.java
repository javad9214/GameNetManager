package com.western.game.center.westerngamecenter.ActiveUsers;


import android.content.Context;

import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class ScrollFab extends CoordinatorLayout.Behavior<FloatingActionButton> {


    public ScrollFab(Context context, AttributeSet attrs) {

    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout ;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {

        float translationY = Math.min(0 , dependency.getTranslationY() - dependency.getHeight());
        child.setTranslationY(translationY);

        return true;
    }
}
