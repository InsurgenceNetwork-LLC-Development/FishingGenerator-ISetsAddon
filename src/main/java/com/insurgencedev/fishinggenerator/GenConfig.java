package com.insurgencedev.fishinggenerator;

import lombok.Getter;
import org.insurgencedev.insurgencesets.api.addon.AddonConfig;
import org.insurgencedev.insurgencesets.libs.fo.Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class GenConfig extends AddonConfig {

    private final Map<String, Integer> fishFragmentAmounts;

    public GenConfig() {
        fishFragmentAmounts = new HashMap<>();
        loadAddonConfig("config.yml", "config.yml");
    }

    @Override
    protected void onLoad() {
        fishFragmentAmounts.clear();
        List<String> pair = new ArrayList<>(getStringList("Custom_Amounts"));
        for (String str : pair) {
            String[] arr = str.split(":");
            String name = arr[0];
            int amount = Integer.parseInt(arr[1]);
            fishFragmentAmounts.put(name.toLowerCase(), amount);
        }
    }

    public Integer getFragmentAmount(String name) {;
        return fishFragmentAmounts.get(name);
    }
}
