package com.insurgencedev.fishinggenerator.listeners;

import com.insurgencedev.fishinggenerator.FishingGenerator;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.insurgencedev.insurgencesets.api.FragmentGenerator;
import org.insurgencedev.insurgencesets.api.ISetsAPI;
import org.insurgencedev.insurgencesets.api.contracts.IArmorSet;
import org.insurgencedev.insurgencesets.api.contracts.IPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PlayerFishingListener implements Listener {

    @Getter
    private static Map<Player, String> caughtFish = new HashMap<>();

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            Entity caught = event.getCaught();

            if (caught instanceof Item) {
                ItemStack item = ((Item) caught).getItemStack();

                if (isFish(item.getType())) {
                    Player player = event.getPlayer();
                    IPlayer cache = ISetsAPI.getCache(player);
                    IArmorSet armorSet = ISetsAPI.getArmorSetManager().getArmorSet(cache.getFragmentDataManager().getArmorSetFragmentGen());

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
                                caughtFish.put(player, item.getType().name().toLowerCase());
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
