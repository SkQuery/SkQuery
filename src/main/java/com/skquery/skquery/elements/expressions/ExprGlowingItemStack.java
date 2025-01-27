package com.skquery.skquery.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.skquery.skquery.annotations.Patterns;

@Patterns("[skquery] glowing %itemstacks%")
public class ExprGlowingItemStack extends SimplePropertyExpression<ItemStack, ItemStack> {

    @Override
    protected String getPropertyName() {
        return "glowy forme";
    }

    @Override
    public ItemStack convert(ItemStack itemStack) {
        if (itemStack.getType() == Material.BOW) itemStack.addUnsafeEnchantment(Enchantment.LURE, 69);
        else itemStack.addUnsafeEnchantment(Enchantment.INFINITY, 69);
        ItemMeta metadata = itemStack.getItemMeta();
	    metadata.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    itemStack.setItemMeta(metadata);
        return itemStack;
    }

    @Override
    public Class<? extends ItemStack> getReturnType() {
        return ItemStack.class;
    }
}
