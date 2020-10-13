package com.example.kikkiapp.Model;

import com.plumillonforge.android.chipview.Chip;

public class ChipModel implements Chip {
    private String mName;

    public ChipModel(String name) {
        mName = name;
    }

    @Override
    public String getText() {
        return mName;
    }
}
