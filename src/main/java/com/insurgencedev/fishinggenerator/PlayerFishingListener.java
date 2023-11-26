package com.insurgencedev.fishinggenerator;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.insurgencedev.insurgencesets.api.FragmentGenerator;
import org.insurgencedev.insurgencesets.api.ISetsAPI;
import org.insurgencedev.insurgencesets.libs.fo.Common;
import org.insurgencedev.insurgencesets.models.armorset.ArmorSet;
import org.insurgencedev.insurgencesets.settings.ISetsPlayerCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PlayerFishingListener implements Listener {

    @Getter
    private static String caughtFish;

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            Entity caught = event.getCaught();

            if (caught instanceof Item) {
                ItemStack item = ((Item) caught).getItemStack();

                if (isFish(item.getType())) {
                    Player player = event.getPlayer();
                    ISetsPlayerCache cache = ISetsAPI.getCache(player);
                    ArmorSet armorSet = ISetsAPI.getArmorSetManager().findArmorSet(cache.getArmorSetFragmentGen());

                    if (armorSet != null) {
                        final String type = armorSet.getFragmentGeneration().getString("Type");
                        final String source = armorSet.getFragmentGeneration().getString("Source");
                        final FragmentGenerator generator = ISetsAPI.getFragmentGeneratorManager().findFragmentGenerator(type, source);

                        if (generator == null) {
                            return;
                        }

                        if (type.equals(FishingGenerator.namespace)) {
                            List<String> disabledWorlds = armorSet.getFragmentGeneration().getStringList("Disabled_Worlds");
                            if (!disabledWorlds.contains(player.getWorld().getName())) {
                                caughtFish = item.getType().name().toLowerCase();
                                generator.handleGeneration(player, armorSet.getFragmentGeneration());
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isFish(Material material) {
        return material == Material.COD || material == Material.SALMON || material == Material.PUFFERFISH || material == Material.TROPICAL_FISH;
    }
}
