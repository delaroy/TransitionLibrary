/*
 * Copyright (C) 2015 Andrey Kulikov (andkulikov@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.transitionseverywhere.utils;

import android.annotation.TargetApi;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.os.Build;

/**
 * Created by Andrey Kulikov on 17.08.15.
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class PathAnimatorCompat extends BasePointFAnimator {

    private PathMeasure mPathMeasure;
    private float mPathLength;

    private float[] mTempArray = new float[2];

    private PathAnimatorCompat(Object target, PointFProperty pointFProperty) {
        super(target, pointFProperty);
    }

    public static <T> PathAnimatorCompat ofPointF(T target, PointFProperty<T> property, Path path) {
        PathAnimatorCompat animator = null;
        if (target != null && property != null && path != null) {
            animator = new PathAnimatorCompat(target, property);
            animator.mPathMeasure = new PathMeasure(path, false);
            animator.mPathLength = animator.mPathMeasure.getLength();
        }
        return animator;
    }

    @Override
    protected void applyAnimatedFraction(PointF holder, float fraction) {
        if (fraction < 0) {
            fraction = 0;
        }
        if (fraction > 1) {
            fraction = 1;
        }
        mPathMeasure.getPosTan(fraction * mPathLength, mTempArray, null);
        holder.set(mTempArray[0], mTempArray[1]);
    }

}
