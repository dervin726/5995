package com.google.android.material.drawable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Xml;
import androidx.core.graphics.drawable.DrawableCompat;
import com.google.android.material.chip.Chip$$ExternalSyntheticApiModelOutline0;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public final class DrawableUtils {
    public static final int INTRINSIC_SIZE = -1;
    private static final int UNSPECIFIED_HEIGHT = -1;
    private static final int UNSPECIFIED_WIDTH = -1;

    private DrawableUtils() {
    }

    public static void setTint(Drawable drawable, int i) {
        boolean z = i != 0;
        if (Build.VERSION.SDK_INT == 21) {
            if (z) {
                drawable.setColorFilter(i, PorterDuff.Mode.SRC_IN);
                return;
            } else {
                drawable.setColorFilter(null);
                return;
            }
        }
        if (z) {
            DrawableCompat.setTint(drawable, i);
        } else {
            DrawableCompat.setTintList(drawable, null);
        }
    }

    public static PorterDuffColorFilter updateTintFilter(Drawable drawable, ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(drawable.getState(), 0), mode);
    }

    public static AttributeSet parseDrawableXml(Context context, int i, CharSequence charSequence) {
        int next;
        try {
            XmlResourceParser xml = context.getResources().getXml(i);
            do {
                next = xml.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
            if (next != 2) {
                throw new XmlPullParserException("No start tag found");
            }
            if (!TextUtils.equals(xml.getName(), charSequence)) {
                throw new XmlPullParserException("Must have a <" + ((Object) charSequence) + "> start tag");
            }
            return Xml.asAttributeSet(xml);
        } catch (IOException | XmlPullParserException e) {
            Resources.NotFoundException notFoundException = new Resources.NotFoundException("Can't load badge resource ID #0x" + Integer.toHexString(i));
            notFoundException.initCause(e);
            throw notFoundException;
        }
    }

    public static void setRippleDrawableRadius(RippleDrawable rippleDrawable, int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            rippleDrawable.setRadius(i);
            return;
        }
        try {
            Chip$$ExternalSyntheticApiModelOutline0.m$1().getDeclaredMethod("setMaxRadius", Integer.TYPE).invoke(rippleDrawable, Integer.valueOf(i));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalStateException("Couldn't set RippleDrawable radius", e);
        }
    }

    public static Drawable createTintableDrawableIfNeeded(Drawable drawable, ColorStateList colorStateList, PorterDuff.Mode mode) {
        return createTintableMutatedDrawableIfNeeded(drawable, colorStateList, mode, false);
    }

    public static Drawable createTintableMutatedDrawableIfNeeded(Drawable drawable, ColorStateList colorStateList, PorterDuff.Mode mode) {
        return createTintableMutatedDrawableIfNeeded(drawable, colorStateList, mode, Build.VERSION.SDK_INT < 23);
    }

    private static Drawable createTintableMutatedDrawableIfNeeded(Drawable drawable, ColorStateList colorStateList, PorterDuff.Mode mode, boolean z) {
        if (drawable == null) {
            return null;
        }
        if (colorStateList != null) {
            drawable = DrawableCompat.wrap(drawable).mutate();
            if (mode != null) {
                DrawableCompat.setTintMode(drawable, mode);
            }
        } else if (z) {
            drawable.mutate();
        }
        return drawable;
    }

    public static Drawable compositeTwoLayeredDrawable(Drawable drawable, Drawable drawable2) {
        return compositeTwoLayeredDrawable(drawable, drawable2, -1, -1);
    }

    public static Drawable compositeTwoLayeredDrawable(Drawable drawable, Drawable drawable2, int i, int i2) {
        Drawable scaledDrawableWrapper = drawable2;
        int topLayerIntrinsicWidth = i;
        int intrinsicHeight = i2;
        if (drawable == null) {
            return scaledDrawableWrapper;
        }
        if (scaledDrawableWrapper == null) {
            return drawable;
        }
        boolean z = (topLayerIntrinsicWidth == -1 || intrinsicHeight == -1) ? false : true;
        if (topLayerIntrinsicWidth == -1) {
            topLayerIntrinsicWidth = getTopLayerIntrinsicWidth(drawable, drawable2);
        }
        if (intrinsicHeight == -1) {
            intrinsicHeight = getTopLayerIntrinsicHeight(drawable, drawable2);
        }
        if (topLayerIntrinsicWidth > drawable.getIntrinsicWidth() || intrinsicHeight > drawable.getIntrinsicHeight()) {
            float f = topLayerIntrinsicWidth / intrinsicHeight;
            if (f >= drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight()) {
                int intrinsicWidth = drawable.getIntrinsicWidth();
                intrinsicHeight = (int) (intrinsicWidth / f);
                topLayerIntrinsicWidth = intrinsicWidth;
            } else {
                intrinsicHeight = drawable.getIntrinsicHeight();
                topLayerIntrinsicWidth = (int) (f * intrinsicHeight);
            }
        }
        if (Build.VERSION.SDK_INT >= 23) {
            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{drawable, scaledDrawableWrapper});
            layerDrawable.setLayerSize(1, topLayerIntrinsicWidth, intrinsicHeight);
            layerDrawable.setLayerGravity(1, 17);
            return layerDrawable;
        }
        if (z) {
            scaledDrawableWrapper = new ScaledDrawableWrapper(scaledDrawableWrapper, topLayerIntrinsicWidth, intrinsicHeight);
        }
        LayerDrawable layerDrawable2 = new LayerDrawable(new Drawable[]{drawable, scaledDrawableWrapper});
        int iMax = Math.max((drawable.getIntrinsicWidth() - topLayerIntrinsicWidth) / 2, 0);
        int iMax2 = Math.max((drawable.getIntrinsicHeight() - intrinsicHeight) / 2, 0);
        layerDrawable2.setLayerInset(1, iMax, iMax2, iMax, iMax2);
        return layerDrawable2;
    }

    private static int getTopLayerIntrinsicWidth(Drawable drawable, Drawable drawable2) {
        int intrinsicWidth = drawable2.getIntrinsicWidth();
        return intrinsicWidth != -1 ? intrinsicWidth : drawable.getIntrinsicWidth();
    }

    private static int getTopLayerIntrinsicHeight(Drawable drawable, Drawable drawable2) {
        int intrinsicHeight = drawable2.getIntrinsicHeight();
        return intrinsicHeight != -1 ? intrinsicHeight : drawable.getIntrinsicHeight();
    }

    public static int[] getCheckedState(int[] iArr) {
        for (int i = 0; i < iArr.length; i++) {
            int i2 = iArr[i];
            if (i2 == 16842912) {
                return iArr;
            }
            if (i2 == 0) {
                int[] iArr2 = (int[]) iArr.clone();
                iArr2[i] = 16842912;
                return iArr2;
            }
        }
        int[] iArrCopyOf = Arrays.copyOf(iArr, iArr.length + 1);
        iArrCopyOf[iArr.length] = 16842912;
        return iArrCopyOf;
    }

    public static int[] getUncheckedState(int[] iArr) {
        int[] iArr2 = new int[iArr.length];
        int i = 0;
        for (int i2 : iArr) {
            if (i2 != 16842912) {
                iArr2[i] = i2;
                i++;
            }
        }
        return iArr2;
    }

    public static void setOutlineToPath(Outline outline, Path path) {
        if (Build.VERSION.SDK_INT >= 30) {
            OutlineCompatR.setPath(outline, path);
            return;
        }
        if (Build.VERSION.SDK_INT >= 29) {
            try {
                OutlineCompatL.setConvexPath(outline, path);
            } catch (IllegalArgumentException unused) {
            }
        } else {
            if (Build.VERSION.SDK_INT < 21 || !path.isConvex()) {
                return;
            }
            OutlineCompatL.setConvexPath(outline, path);
        }
    }

    public static ColorStateList getColorStateListOrNull(Drawable drawable) {
        if (drawable instanceof ColorDrawable) {
            return ColorStateList.valueOf(((ColorDrawable) drawable).getColor());
        }
        if (Build.VERSION.SDK_INT < 29 || !Chip$$ExternalSyntheticApiModelOutline0.m$2(drawable)) {
            return null;
        }
        return Chip$$ExternalSyntheticApiModelOutline0.m196m((Object) drawable).getColorStateList();
    }

    private static class OutlineCompatR {
        private OutlineCompatR() {
        }

        static void setPath(Outline outline, Path path) {
            outline.setPath(path);
        }
    }

    private static class OutlineCompatL {
        private OutlineCompatL() {
        }

        static void setConvexPath(Outline outline, Path path) {
            outline.setConvexPath(path);
        }
    }
}
