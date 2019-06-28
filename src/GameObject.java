public abstract class GameObject {
    private double mTimeCounter;
    private double mPeriod;
    private boolean mHasChanges;

    public GameObject(int aPeriod) {
        mPeriod = aPeriod;
        mTimeCounter = 0;
        mHasChanges = false;
    }

    public void update(int adTime) {
        mTimeCounter += adTime;
        if (mTimeCounter >= mPeriod) {
            mTimeCounter %= mPeriod;
            mHasChanges = true;
            move();
        }
    }

    public void changePeriod(double aFactor) {
        mPeriod *= aFactor;
        if (mPeriod == 0) mPeriod = 1;
    }

    public boolean isChanged() {
        boolean result = mHasChanges;
        mHasChanges = false;
        return result;
    }

    protected void move() {
    }
}