package com.example.carbonfootprint.helpers;

import com.example.carbonfootprint.model.NewsfeedModel;

import java.util.Comparator;

public class NewsfeedModelComparator implements Comparator<NewsfeedModel> {
    @Override
    public int compare(NewsfeedModel nf1, NewsfeedModel nf2) {
        return (int) (nf2.getTimestamp().getTime() - nf1.getTimestamp().getTime());
    }
}
