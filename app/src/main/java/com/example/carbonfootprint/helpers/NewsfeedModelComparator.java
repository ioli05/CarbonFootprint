package com.example.carbonfootprint.helpers;

import com.example.carbonfootprint.model.NewsfeedModel;

import java.util.Comparator;

public class NewsfeedModelComparator implements Comparator<NewsfeedModel> {
    @Override
    public int compare(NewsfeedModel nf1, NewsfeedModel nf2) {
        long t1 = nf1.getTimestamp().getTime();
        long t2 = nf2.getTimestamp().getTime();
        if(t2 < t1)
            return 1;
        else if(t1 < t2)
            return -1;
        else
            return 0;
    }
}
