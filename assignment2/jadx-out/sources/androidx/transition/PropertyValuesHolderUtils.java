package androidx.transition;

import android.animation.PropertyValuesHolder;
import android.animation.TypeConverter;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.util.Property;

/* JADX INFO: loaded from: classes.dex */
class PropertyValuesHolderUtils {
    static PropertyValuesHolder ofPointF(Property<?, PointF> property, Path path) {
        if (Build.VERSION.SDK_INT >= 21) {
            return Api21Impl.ofObject(property, path);
        }
        return PropertyValuesHolder.ofFloat(new PathProperty(property, path), 0.0f, 1.0f);
    }

    private PropertyValuesHolderUtils() {
    }

    static class Api21Impl {
        private Api21Impl() {
        }

        static <V> PropertyValuesHolder ofObject(Property<?, V> property, Path path) {
            return PropertyValuesHolder.ofObject(property, (TypeConverter) null, path);
        }
    }
}
