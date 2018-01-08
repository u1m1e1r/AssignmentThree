package com.example.hp.broadcastthroughmanifest;

import android.content.Context;
import android.content.Intent;
import android.widget.Switch;

/**
 * Created by HP on 12/12/2017.
 */

public class SwitchEvent {
    Context c;
    Intent i;
    public SwitchEvent(Intent i, Context c){
        this.c = c;
        this.i = i;
    }
}
