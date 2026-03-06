package androidx.transition;

import android.animation.ObjectAnimator;
import android.animation.TypeConverter;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.util.Property;

/* JADX INFO: loaded from: classes.dex */
class ObjectAnimatorUtils {
    static <T> ObjectAnimator ofPointF(T t, Property<T, PointF> property, Path path) {
        if (Build.VERSION.SDK_INT >= 21) {
            return Api21Impl.ofObject(t, property, path);
        }
        return ObjectAnimator.ofFloat(t, new PathProperty(property, path), 0.0f, 1.0f);
    }

    private ObjectAnimatorUtils() {
    }

    static class Api21Impl {
        private Api21Impl() {
        }

        static <T, V> ObjectAnimator ofObject(T t, Property<T, V> property, Path path) {
            return ObjectAnimator.ofObject(t, property, (TypeConverter) null, path);
        }
    }
}
