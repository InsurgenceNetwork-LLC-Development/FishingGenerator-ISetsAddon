package com.insurgencedev.fishinggenerator;

import lombok.Getter;
import org.insurgencedev.insurgencesets.api.ISetsAPI;
import org.insurgencedev.insurgencesets.api.addon.ISetsAddon;
import org.insurgencedev.insurgencesets.api.addon.InsurgenceSetsAddon;

@ISetsAddon(
        name = "FishingGenerator", version = "1.0.0", author = "Hxtch", description = "Earn fragments from catching fishes"
)
public class GeneratorAddon extends InsurgenceSetsAddon {

    @Getter
    private static GenConfig config;

    @Override
    public void onAddonStart() {
        config = new GenConfig();
    }

    @Override
    public void onAddonReloadablesStart() {
        registerEvent(new PlayerFishingListener());
        ISetsAPI.getFragmentGeneratorManager().registerFragmentGenerator(new FishingGenerator());
    }

    @Override
    public void onAddonReload() {
    }

    @Override
    public void onAddonStop() {
    }
}
