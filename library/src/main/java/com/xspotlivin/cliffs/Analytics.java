package com.xspotlivin.cliffs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;

public interface Analytics {
    void trackScreen(@NonNull String screen);

    void tagEvent(@NonNull String name, @Nullable Map<String, String> attributes);
}
