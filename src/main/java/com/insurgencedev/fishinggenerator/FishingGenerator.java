package com.insurgencedev.fishinggenerator;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.insurgencedev.insurgencesets.api.FragmentGenerator;
import org.insurgencedev.insurgencesets.api.ISetsAPI;
import org.insurgencedev.insurgencesets.libs.fo.Common;
import org.insurgencedev.insurgencesets.libs.fo.collection.SerializedMap;
import org.insurgencedev.insurgencesets.models.armorset.ArmorSet;
import org.insurgencedev.insurgencesets.models.fragment.Fragment;
import org.insurgencedev.insurgencesets.settings.ISetsPlayerCache;
import org.jetbrains.annotations.NotNull;

@Getter
public final class FishingGenerator extends FragmentGenerator {

    public static final String namespace = "IGen";

    public FishingGenerator() {
        super(namespace, "Fishing");
    }

    @Override
    public void handleGeneration(@NotNull Player player, @NotNull SerializedMap map) {
        boolean enabled = map.getBoolean("Enabled");

        if (enabled) {
            double chance = map.getDouble("Chance");
            double random = Math.random();

            if (random <= chance / 100) {
                ISetsPlayerCache cache = ISetsAPI.getCache(player);
                String selectedGen = cache.getArmorSetFragmentGen();
                ArmorSet armorSet = ISetsAPI.getArmorSetManager().findArmorSet(selectedGen);

                if (armorSet != null) {
                    Fragment fragment = armorSet.getFragment();
                    boolean isPhysical = map.getBoolean("Physical");
                    String message = map.getString("Give_Message");
                    int amount = getAmount(map);

                    if (isPhysical) {
                        fragment.giveOrUpdateFragment(player, amount, false);
                    } else {
                        cache.updateFragmentAmount(selectedGen, amount);
                    }

                    Common.tellNoPrefix(player, message.replace("{amount}", "" + amount));
                }
            }
        }
    }

    private int getAmount(SerializedMap map) {
        int defaultAmount = map.getInteger("Amount_To_Give");
        boolean isDynamic = map.getBoolean("Dynamic_Amount", false);
        Integer i = GeneratorAddon.getConfig().getFragmentAmount(PlayerFishingListener.getCaughtFish());

        return isDynamic && i != null ? i : defaultAmount;
    }
}
