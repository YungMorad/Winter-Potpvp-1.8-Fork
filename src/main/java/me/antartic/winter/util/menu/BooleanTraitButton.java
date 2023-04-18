package me.antartic.winter.util.menu;

import com.google.common.collect.ImmutableList;

import me.antartic.winter.util.menu.Button;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.InventoryView;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public final class BooleanTraitButton<T> extends Button {

    private final T target;
    private final String trait;
    private final BiConsumer<T, Boolean> writeFunction;
    private final Function<T, Boolean> readFunction;
    private final Consumer<T> saveFunction;

    public BooleanTraitButton(T target, String trait, BiConsumer<T, Boolean> writeFunction, Function<T, Boolean> readFunction) {
        this(target, trait, writeFunction, readFunction, (i) -> {});
    }

    public BooleanTraitButton(T target, String trait, BiConsumer<T, Boolean> writeFunction, Function<T, Boolean> readFunction, Consumer<T> saveFunction) {
        this.target = target;
        this.trait = trait;
        this.writeFunction = writeFunction;
        this.readFunction = readFunction;
        this.saveFunction = saveFunction;
    }

    @Override
    public String getName(Player player) {
        return ChatColor.AQUA + "Edit " + trait;
    }

    @Override
    public List<String> getDescription(Player player) {
        return ImmutableList.of(
            ChatColor.GRAY + "* " + ChatColor.WHITE + "Current: " + ChatColor.AQUA + (readFunction.apply(target) ? "On" : "Off"),
            "",
            ChatColor.AQUA.toString() + "[Click to toggle]"
        );
    }

    @Override
    public Material getMaterial(Player player) {
        return readFunction.apply(target) ? Material.ENCHANTED_BOOK : Material.BOOK;
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType) {
        boolean current = readFunction.apply(target);

        writeFunction.accept(target, !current);
        saveFunction.accept(target);

        player.sendMessage(ChatColor.GREEN + "Set " + trait + " trait to " + (current ? "off" : "on"));
    }

}
