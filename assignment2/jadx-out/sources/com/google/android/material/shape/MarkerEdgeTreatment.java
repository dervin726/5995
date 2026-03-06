package com.google.android.material.shape;

/* JADX INFO: loaded from: classes.dex */
public final class MarkerEdgeTreatment extends EdgeTreatment {
    private final float radius;

    @Override // com.google.android.material.shape.EdgeTreatment
    boolean forceIntersection() {
        return true;
    }

    public MarkerEdgeTreatment(float f) {
        this.radius = f - 0.001f;
    }

    @Override // com.google.android.material.shape.EdgeTreatment
    public void getEdgePath(float f, float f2, float f3, ShapePath shapePath) {
        double d = this.radius;
        double dSqrt = Math.sqrt(2.0d);
        Double.isNaN(d);
        float f4 = (float) ((d * dSqrt) / 2.0d);
        float fSqrt = (float) Math.sqrt(Math.pow(this.radius, 2.0d) - Math.pow(f4, 2.0d));
        double d2 = this.radius;
        double dSqrt2 = Math.sqrt(2.0d);
        Double.isNaN(d2);
        double d3 = d2 * dSqrt2;
        double d4 = this.radius;
        Double.isNaN(d4);
        shapePath.reset(f2 - f4, ((float) (-(d3 - d4))) + fSqrt);
        double d5 = this.radius;
        double dSqrt3 = Math.sqrt(2.0d);
        Double.isNaN(d5);
        double d6 = d5 * dSqrt3;
        double d7 = this.radius;
        Double.isNaN(d7);
        shapePath.lineTo(f2, (float) (-(d6 - d7)));
        double d8 = this.radius;
        double dSqrt4 = Math.sqrt(2.0d);
        Double.isNaN(d8);
        double d9 = d8 * dSqrt4;
        double d10 = this.radius;
        Double.isNaN(d10);
        shapePath.lineTo(f2 + f4, ((float) (-(d9 - d10))) + fSqrt);
    }
}
