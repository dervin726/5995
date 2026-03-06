package androidx.constraintlayout.core.motion.utils;

/* JADX INFO: loaded from: classes.dex */
public class SpringStopEngine implements StopEngine {
    private static final double UNSET = Double.MAX_VALUE;
    private float mLastTime;
    private double mLastVelocity;
    private float mMass;
    private float mPos;
    private double mStiffness;
    private float mStopThreshold;
    private double mTargetPos;
    private float mV;
    double mDamping = 0.5d;
    private boolean mInitialized = false;
    private int mBoundaryMode = 0;

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public String debug(String str, float f) {
        return null;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public float getVelocity() {
        return 0.0f;
    }

    void log(String str) {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        String str2 = ".(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ") " + stackTraceElement.getMethodName() + "() ";
        System.out.println(str2 + str);
    }

    public void springConfig(float f, float f2, float f3, float f4, float f5, float f6, float f7, int i) {
        this.mTargetPos = f2;
        this.mDamping = f6;
        this.mInitialized = false;
        this.mPos = f;
        this.mLastVelocity = f3;
        this.mStiffness = f5;
        this.mMass = f4;
        this.mStopThreshold = f7;
        this.mBoundaryMode = i;
        this.mLastTime = 0.0f;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public float getVelocity(float f) {
        return this.mV;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public float getInterpolation(float f) {
        compute(f - this.mLastTime);
        this.mLastTime = f;
        return this.mPos;
    }

    public float getAcceleration() {
        double d = this.mStiffness;
        double d2 = this.mDamping;
        double d3 = this.mPos;
        double d4 = this.mTargetPos;
        Double.isNaN(d3);
        double d5 = (-d) * (d3 - d4);
        double d6 = this.mV;
        Double.isNaN(d6);
        return ((float) (d5 - (d2 * d6))) / this.mMass;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public boolean isStopped() {
        double d = this.mPos;
        double d2 = this.mTargetPos;
        Double.isNaN(d);
        double d3 = d - d2;
        double d4 = this.mStiffness;
        double d5 = this.mV;
        double d6 = this.mMass;
        Double.isNaN(d5);
        Double.isNaN(d5);
        Double.isNaN(d6);
        return Math.sqrt((((d5 * d5) * d6) + ((d4 * d3) * d3)) / d4) <= ((double) this.mStopThreshold);
    }

    private void compute(double d) {
        double d2 = this.mStiffness;
        double d3 = this.mDamping;
        double d4 = this.mMass;
        Double.isNaN(d4);
        int iSqrt = (int) ((9.0d / ((Math.sqrt(d2 / d4) * d) * 4.0d)) + 1.0d);
        double d5 = iSqrt;
        Double.isNaN(d5);
        double d6 = d / d5;
        int i = 0;
        while (i < iSqrt) {
            float f = this.mPos;
            double d7 = f;
            double d8 = this.mTargetPos;
            Double.isNaN(d7);
            double d9 = (-d2) * (d7 - d8);
            float f2 = this.mV;
            double d10 = d2;
            double d11 = f2;
            Double.isNaN(d11);
            double d12 = d9 - (d11 * d3);
            float f3 = this.mMass;
            double d13 = d3;
            double d14 = f3;
            Double.isNaN(d14);
            double d15 = d12 / d14;
            double d16 = f2;
            Double.isNaN(d16);
            double d17 = d16 + ((d15 * d6) / 2.0d);
            double d18 = f;
            Double.isNaN(d18);
            double d19 = f3;
            Double.isNaN(d19);
            double d20 = ((((-((d18 + ((d6 * d17) / 2.0d)) - d8)) * d10) - (d17 * d13)) / d19) * d6;
            double d21 = f2;
            Double.isNaN(d21);
            double d22 = f2;
            Double.isNaN(d22);
            float f4 = (float) (d22 + d20);
            this.mV = f4;
            double d23 = f;
            Double.isNaN(d23);
            float f5 = (float) (d23 + ((d21 + (d20 / 2.0d)) * d6));
            this.mPos = f5;
            int i2 = this.mBoundaryMode;
            if (i2 > 0) {
                if (f5 < 0.0f && (i2 & 1) == 1) {
                    this.mPos = -f5;
                    this.mV = -f4;
                }
                float f6 = this.mPos;
                if (f6 > 1.0f && (i2 & 2) == 2) {
                    this.mPos = 2.0f - f6;
                    this.mV = -this.mV;
                }
            }
            i++;
            d2 = d10;
            d3 = d13;
        }
    }
}
