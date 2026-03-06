package androidx.dynamicanimation.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;

/* JADX INFO: loaded from: classes.dex */
public final class SpringForce implements Force {
    public static final float DAMPING_RATIO_HIGH_BOUNCY = 0.2f;
    public static final float DAMPING_RATIO_LOW_BOUNCY = 0.75f;
    public static final float DAMPING_RATIO_MEDIUM_BOUNCY = 0.5f;
    public static final float DAMPING_RATIO_NO_BOUNCY = 1.0f;
    public static final float STIFFNESS_HIGH = 10000.0f;
    public static final float STIFFNESS_LOW = 200.0f;
    public static final float STIFFNESS_MEDIUM = 1500.0f;
    public static final float STIFFNESS_VERY_LOW = 50.0f;
    private static final double UNSET = Double.MAX_VALUE;
    private static final double VELOCITY_THRESHOLD_MULTIPLIER = 62.5d;
    private double mDampedFreq;
    double mDampingRatio;
    private double mFinalPosition;
    private double mGammaMinus;
    private double mGammaPlus;
    private boolean mInitialized;
    private final DynamicAnimation.MassState mMassState;
    double mNaturalFreq;
    private double mValueThreshold;
    private double mVelocityThreshold;

    public SpringForce() {
        this.mNaturalFreq = Math.sqrt(1500.0d);
        this.mDampingRatio = 0.5d;
        this.mInitialized = false;
        this.mFinalPosition = Double.MAX_VALUE;
        this.mMassState = new DynamicAnimation.MassState();
    }

    public SpringForce(float f) {
        this.mNaturalFreq = Math.sqrt(1500.0d);
        this.mDampingRatio = 0.5d;
        this.mInitialized = false;
        this.mFinalPosition = Double.MAX_VALUE;
        this.mMassState = new DynamicAnimation.MassState();
        this.mFinalPosition = f;
    }

    public SpringForce setStiffness(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Spring stiffness constant must be positive.");
        }
        this.mNaturalFreq = Math.sqrt(f);
        this.mInitialized = false;
        return this;
    }

    public float getStiffness() {
        double d = this.mNaturalFreq;
        return (float) (d * d);
    }

    public SpringForce setDampingRatio(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("Damping ratio must be non-negative");
        }
        this.mDampingRatio = f;
        this.mInitialized = false;
        return this;
    }

    public float getDampingRatio() {
        return (float) this.mDampingRatio;
    }

    public SpringForce setFinalPosition(float f) {
        this.mFinalPosition = f;
        return this;
    }

    public float getFinalPosition() {
        return (float) this.mFinalPosition;
    }

    @Override // androidx.dynamicanimation.animation.Force
    public float getAcceleration(float f, float f2) {
        float finalPosition = f - getFinalPosition();
        double d = this.mNaturalFreq;
        double d2 = d * d;
        double d3 = d * 2.0d * this.mDampingRatio;
        double d4 = finalPosition;
        Double.isNaN(d4);
        double d5 = f2;
        Double.isNaN(d5);
        return (float) (((-d2) * d4) - (d3 * d5));
    }

    @Override // androidx.dynamicanimation.animation.Force
    public boolean isAtEquilibrium(float f, float f2) {
        return ((double) Math.abs(f2)) < this.mVelocityThreshold && ((double) Math.abs(f - getFinalPosition())) < this.mValueThreshold;
    }

    private void init() {
        if (this.mInitialized) {
            return;
        }
        if (this.mFinalPosition == Double.MAX_VALUE) {
            throw new IllegalStateException("Error: Final position of the spring must be set before the animation starts");
        }
        double d = this.mDampingRatio;
        if (d > 1.0d) {
            double d2 = this.mNaturalFreq;
            this.mGammaPlus = ((-d) * d2) + (d2 * Math.sqrt((d * d) - 1.0d));
            double d3 = this.mDampingRatio;
            double d4 = this.mNaturalFreq;
            this.mGammaMinus = ((-d3) * d4) - (d4 * Math.sqrt((d3 * d3) - 1.0d));
        } else if (d >= 0.0d && d < 1.0d) {
            this.mDampedFreq = this.mNaturalFreq * Math.sqrt(1.0d - (d * d));
        }
        this.mInitialized = true;
    }

    DynamicAnimation.MassState updateValues(double d, double d2, long j) {
        double dCos;
        double dPow;
        init();
        double d3 = j;
        Double.isNaN(d3);
        double d4 = d3 / 1000.0d;
        double d5 = d - this.mFinalPosition;
        double d6 = this.mDampingRatio;
        if (d6 > 1.0d) {
            double d7 = this.mGammaMinus;
            double d8 = this.mGammaPlus;
            double d9 = d5 - (((d7 * d5) - d2) / (d7 - d8));
            double d10 = ((d5 * d7) - d2) / (d7 - d8);
            dPow = (Math.pow(2.718281828459045d, d7 * d4) * d9) + (Math.pow(2.718281828459045d, this.mGammaPlus * d4) * d10);
            double d11 = this.mGammaMinus;
            double dPow2 = d9 * d11 * Math.pow(2.718281828459045d, d11 * d4);
            double d12 = this.mGammaPlus;
            dCos = dPow2 + (d10 * d12 * Math.pow(2.718281828459045d, d12 * d4));
        } else if (d6 == 1.0d) {
            double d13 = this.mNaturalFreq;
            double d14 = d2 + (d13 * d5);
            double d15 = d5 + (d14 * d4);
            dPow = Math.pow(2.718281828459045d, (-d13) * d4) * d15;
            double dPow3 = d15 * Math.pow(2.718281828459045d, (-this.mNaturalFreq) * d4);
            double d16 = this.mNaturalFreq;
            dCos = (d14 * Math.pow(2.718281828459045d, (-d16) * d4)) + (dPow3 * (-d16));
        } else {
            double d17 = 1.0d / this.mDampedFreq;
            double d18 = this.mNaturalFreq;
            double d19 = d17 * ((d6 * d18 * d5) + d2);
            double dPow4 = Math.pow(2.718281828459045d, (-d6) * d18 * d4) * ((Math.cos(this.mDampedFreq * d4) * d5) + (Math.sin(this.mDampedFreq * d4) * d19));
            double d20 = this.mNaturalFreq;
            double d21 = this.mDampingRatio;
            double d22 = (-d20) * dPow4 * d21;
            double dPow5 = Math.pow(2.718281828459045d, (-d21) * d20 * d4);
            double d23 = this.mDampedFreq;
            double dSin = (-d23) * d5 * Math.sin(d23 * d4);
            double d24 = this.mDampedFreq;
            dCos = d22 + (dPow5 * (dSin + (d19 * d24 * Math.cos(d24 * d4))));
            dPow = dPow4;
        }
        this.mMassState.mValue = (float) (dPow + this.mFinalPosition);
        this.mMassState.mVelocity = (float) dCos;
        return this.mMassState;
    }

    void setValueThreshold(double d) {
        double dAbs = Math.abs(d);
        this.mValueThreshold = dAbs;
        this.mVelocityThreshold = dAbs * VELOCITY_THRESHOLD_MULTIPLIER;
    }
}
