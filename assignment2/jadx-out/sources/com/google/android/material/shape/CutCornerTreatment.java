package com.google.android.material.shape;

/* JADX INFO: loaded from: classes.dex */
public class CutCornerTreatment extends CornerTreatment {
    float size;

    public CutCornerTreatment() {
        this.size = -1.0f;
    }

    @Deprecated
    public CutCornerTreatment(float f) {
        this.size = f;
    }

    @Override // com.google.android.material.shape.CornerTreatment
    public void getCornerPath(ShapePath shapePath, float f, float f2, float f3) {
        shapePath.reset(0.0f, f3 * f2, 180.0f, 180.0f - f);
        double dSin = Math.sin(Math.toRadians(f));
        double d = f3;
        Double.isNaN(d);
        double d2 = f2;
        Double.isNaN(d2);
        double dSin2 = Math.sin(Math.toRadians(90.0f - f));
        Double.isNaN(d);
        Double.isNaN(d2);
        shapePath.lineTo((float) (dSin * d * d2), (float) (dSin2 * d * d2));
    }
}
