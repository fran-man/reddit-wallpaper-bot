package com.franm.wallpaperbot.Format;

import java.util.ArrayList;

public interface OutputFormatter<T> {
    public String format(ArrayList<T> results);
}
