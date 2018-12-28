/*
 * Copyright (c) 2018 FishWaffle.
 */

package com.fishwaffle.natureremo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.fishwaffle.natureremo.controller.NatureRemo
import kotlin.concurrent.thread

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(FIRE_SETTING, ignoreCase = true)) {
            val bundle = intent.getBundleExtra(EXTRA_BUNDLE)

            val type = Type.valueOf(bundle.getString(BUNDLE_TYPE))
            when (type) {
                Type.SignalSend -> {
                    val signal = bundle.getString(BUNDLE_SIGNAL_ID)
                    thread {
                        NatureRemo.Signals_Signal_Send_Post(getToken(context), signal)
                    }
                }
                Type.AirConPowerOff -> {
                    val appliancesId = bundle.getString(BUNDLE_APPLIANCE_ID)
                    thread {
                        NatureRemo.Appliances_Appliance_AirConSettings_Post(getToken(context), appliancesId,
                                null, null, null, null,
                                "power-off")
                    }
                }
                Type.AirConSettings -> {
                    val appliancesId = bundle.getString(BUNDLE_APPLIANCE_ID)
                    val mode = bundle.getString(BUNDLE_MODE)
                    val temperature = if (bundle.containsKey(BUNDLE_TEMPERATURE)) bundle.getString(BUNDLE_TEMPERATURE) else null
                    val volume = if (bundle.containsKey(BUNDLE_AIR_VOLUME)) bundle.getString(BUNDLE_AIR_VOLUME) else null
                    val direction = if (bundle.containsKey(BUNDLE_AIR_DIRECTION)) bundle.getString(BUNDLE_AIR_DIRECTION) else null

                    thread {
                        NatureRemo.Appliances_Appliance_AirConSettings_Post(getToken(context), appliancesId,
                                temperature, mode, volume, direction,
                                "")
                    }
                }

            }
        }
    }
}