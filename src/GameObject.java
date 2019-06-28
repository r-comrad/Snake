public abstract class GameObject {
    private double mTimeCounter;
    private double mPeriod;

    public GameObject(int aPeriod) {
        mPeriod = aPeriod;
        mTimeCounter = 0;
    }

    public void update(int adTime) {
        mTimeCounter += adTime;
        if (mTimeCounter >= mPeriod) {
            mTimeCounter %= mPeriod;
            move();
        }
    }

    public void changePeriod(double aFactor) {
        mPeriod *= aFactor;
        if (mPeriod == 0) mPeriod = 1;
    }

    protected abstract void move();
}