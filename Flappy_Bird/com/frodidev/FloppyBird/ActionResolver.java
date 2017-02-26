package com.frodidev.FloppyBird;

public interface ActionResolver {
    boolean isSignedIn();

    void loadInterAds();

    void showAchievement();

    void showInterAds();

    void showScores();

    void signIn();

    void signOut();

    void submitScore(long j);

    void unlockAchievementGPGS(String str);
}
