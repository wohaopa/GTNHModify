package com.github.wohaopa.GTNHModify;

import net.minecraft.item.crafting.IRecipe;

import com.google.gson.JsonObject;

public interface IRecipeHandler {

    String[] getNames();

    JsonObject recipeToJsonObject(IRecipe recipe, JsonObject jsonObject);
}
