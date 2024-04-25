package com.github.wohaopa.GTNHModify;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.glodblock.github.loader.recipe.WirelessTerminalRecipe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import appeng.recipes.game.DisassembleRecipe;
import appeng.recipes.game.FacadeRecipe;
import appeng.recipes.game.ShapedRecipe;
import appeng.recipes.game.ShapelessRecipe;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Shaped_Recipe;
import gregtech.api.util.GT_Shapeless_Recipe;
import ic2.core.AdvRecipe;
import ic2.core.AdvShapelessRecipe;

public class RecipeTools {

    public static Map<Integer, String> oreToName = new HashMap<>();

    public static JsonObject nbtToJson(NBTTagCompound nbtTagCompound) {
        JsonObject jsonObject = new JsonObject();
        Set<String> keys = nbtTagCompound.func_150296_c();

        for (String key : keys) {
            NBTBase value = nbtTagCompound.getTag(key);
            if (value instanceof NBTTagCompound nbtTagCompound1) {
                jsonObject.add(key, nbtToJson(nbtTagCompound1));
            } else if (value instanceof NBTTagList nbtTagList) {
                jsonObject.addProperty(key, nbtTagList.toString());
            } else if (value instanceof NBTTagString nbtTagString) {
                jsonObject.addProperty(key, nbtTagString.func_150285_a_());
            } else if (value instanceof NBTTagInt nbtTagInt) {
                jsonObject.addProperty(key, nbtTagInt.func_150287_d());
            } else if (value instanceof NBTTagLong nbtTagLong) {
                jsonObject.addProperty(key, nbtTagLong.func_150291_c());
            } else if (value instanceof NBTTagDouble nbtTagDouble) {
                jsonObject.addProperty(key, nbtTagDouble.func_150286_g());
            } else if (value instanceof NBTTagFloat nbtTagFloat) {
                jsonObject.addProperty(key, nbtTagFloat.func_150288_h());
            } else if (value instanceof NBTTagByte nbtTagByte) {
                jsonObject.addProperty(key, nbtTagByte.func_150290_f());
            } else {
                jsonObject.addProperty(key, value.toString());
            }
        }

        return jsonObject;
    }

    public static JsonObject itemStackToJsonObject(ItemStack itemStack) {
        JsonObject jsonObject = new JsonObject();
        if (itemStack == null) {
            jsonObject.addProperty("id", "null");
            return jsonObject;
        }
        try {
            jsonObject.addProperty("id", itemStack.getUnlocalizedName());
            jsonObject.addProperty("name", itemStack.getDisplayName());
            jsonObject.addProperty("damage", itemStack.getItemDamage());
            jsonObject.addProperty("size", itemStack.stackSize);
            if (itemStack.hasTagCompound()) {
                jsonObject.add("nbt", nbtToJson(itemStack.getTagCompound()));
            }
        } catch (Exception exception) {
            jsonObject.addProperty("exception", exception.getMessage());
        }

        return jsonObject;
    }

    public static JsonArray itemStacksToJsonArray(ItemStack[] itemStacks) {
        JsonArray jsonArray = new JsonArray();
        if (itemStacks != null && itemStacks.length > 0) for (ItemStack itemStack : itemStacks) {
            jsonArray.add(itemStackToJsonObject(itemStack));
        }
        return jsonArray;
    }

    public static JsonObject fluidStackToJsonObject(FluidStack fluidStack) {
        JsonObject jsonObject = new JsonObject();

        if (fluidStack == null) {
            jsonObject.addProperty("id", "null");
            return jsonObject;
        }
        try {
            jsonObject.addProperty("id", fluidStack.getUnlocalizedName());
            jsonObject.addProperty("name", fluidStack.getLocalizedName());
            jsonObject.addProperty("size", fluidStack.amount);
        } catch (Exception exception) {
            jsonObject.addProperty("exception", exception.getMessage());
        }
        return jsonObject;
    }

    public static JsonArray fluidStacksToJsonArray(FluidStack[] fluidStacks) {
        JsonArray jsonArray = new JsonArray();
        if (fluidStacks != null && fluidStacks.length > 0) for (FluidStack fluidStack : fluidStacks) {
            jsonArray.add(fluidStackToJsonObject(fluidStack));
        }
        return jsonArray;
    }

    public static JsonArray objectsToJsonArray(Object[] objects) {
        JsonArray jsonArray = new JsonArray();
        if (objects != null && objects.length > 0) {
            for (Object o : objects) {
                if (o == null) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("id", "null");
                    jsonArray.add(jsonObject);
                    continue;
                }

                String name = o.getClass()
                    .getName();
                if (name.equals("net.minecraftforge.oredict.OreDictionary$UnmodifiableArrayList")
                    && oreToName.containsKey(o.hashCode())) {
                    JsonObject jsonObject = new JsonObject();

                    jsonObject.addProperty("id", oreToName.get(o.hashCode()));
                    jsonObject.addProperty("ore", true);
                    jsonArray.add(jsonObject);

                } else if (name.equals("net.minecraft.item.ItemStack")) {
                    jsonArray.add(itemStackToJsonObject((ItemStack) o));
                } else {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("exception", "unknown");
                    jsonObject.addProperty("className", name);
                    jsonObject.addProperty("details", o.toString());
                    jsonArray.add(jsonObject);
                }
            }

        }

        return jsonArray;
    }

    public static JsonObject recipeTOJsonObject(IRecipe recipe) {
        JsonObject jsonObject = new JsonObject();

        String name = recipe.getClass()
            .getName();
        jsonObject.addProperty("category", name);
        switch (name) {
            case "net.minecraft.item.crafting.ShapedRecipes", "net.minecraft.item.crafting.RecipesMapExtending" -> {
                ShapedRecipes recipe1 = (ShapedRecipes) recipe;
                jsonObject.add("inputs", itemStacksToJsonArray(recipe1.recipeItems));
                jsonObject.add("outputs", itemStacksToJsonArray(new ItemStack[] { recipe1.getRecipeOutput() }));
            }
            case "net.minecraft.item.crafting.ShapelessRecipes" -> {
                ShapelessRecipes recipe1 = (ShapelessRecipes) recipe;
                jsonObject.add("inputs", itemStacksToJsonArray(recipe1.recipeItems.toArray(new ItemStack[0])));
                jsonObject.add("outputs", itemStacksToJsonArray(new ItemStack[] { recipe1.getRecipeOutput() }));
            }
            case "net.minecraftforge.oredict.ShapedOreRecipe" -> {
                ShapedOreRecipe recipe1 = (ShapedOreRecipe) recipe;

                jsonObject.add("inputs", objectsToJsonArray(recipe1.getInput()));
                jsonObject.add("outputs", itemStacksToJsonArray(new ItemStack[] { recipe1.getRecipeOutput() }));
            }
            case "net.minecraftforge.oredict.ShapelessOreRecipe" -> {
                ShapelessOreRecipe recipe1 = (ShapelessOreRecipe) recipe;

                jsonObject.add(
                    "inputs",
                    objectsToJsonArray(
                        recipe1.getInput()
                            .toArray(new Object[0])));
                jsonObject.add("outputs", itemStacksToJsonArray(new ItemStack[] { recipe1.getRecipeOutput() }));
            }

            case "net.minecraft.item.crafting.RecipesArmorDyes", "net.minecraft.item.crafting.RecipeBookCloning", "net.minecraft.item.crafting.RecipesMapCloning", "net.minecraft.item.crafting.RecipeFireworks" -> {}
            case "gregtech.api.util.GT_Shapeless_Recipe" -> {
                GT_Shapeless_Recipe recipe1 = (GT_Shapeless_Recipe) recipe;
                jsonObject.add(
                    "inputs",
                    objectsToJsonArray(
                        recipe1.getInput()
                            .toArray(new Object[0])));
                jsonObject.add("outputs", itemStacksToJsonArray(new ItemStack[] { recipe1.getRecipeOutput() }));
            }
            case "gregtech.api.util.GT_Shaped_Recipe" -> {
                GT_Shaped_Recipe recipe1 = (GT_Shaped_Recipe) recipe;
                jsonObject.add("inputs", objectsToJsonArray(recipe1.getInput()));
                jsonObject.add("outputs", itemStacksToJsonArray(new ItemStack[] { recipe1.getRecipeOutput() }));

            }
            case "com.glodblock.github.loader.recipe.WirelessTerminalRecipe" -> {
                WirelessTerminalRecipe recipe1 = (WirelessTerminalRecipe) recipe;
            }
            case "appeng.recipes.game.DisassembleRecipe" -> {
                DisassembleRecipe recipe1 = (DisassembleRecipe) recipe;
            }
            case "appeng.recipes.game.ShapedRecipe" -> {
                ShapedRecipe recipe1 = (ShapedRecipe) recipe;
                jsonObject.add("inputs", objectsToJsonArray(recipe1.getInput()));
                jsonObject.add("outputs", itemStacksToJsonArray(new ItemStack[] { recipe1.getRecipeOutput() }));
            }
            case "appeng.recipes.game.FacadeRecipe" -> {
                FacadeRecipe recipe1 = (FacadeRecipe) recipe;
            }
            case "appeng.recipes.game.ShapelessRecipe" -> {
                ShapelessRecipe recipe1 = (ShapelessRecipe) recipe;
                jsonObject.add(
                    "inputs",
                    objectsToJsonArray(
                        recipe1.getInput()
                            .toArray(new Object[0])));
                jsonObject.add("outputs", itemStacksToJsonArray(new ItemStack[] { recipe1.getRecipeOutput() }));
            }
            case "ic2.core.AdvRecipe" -> {
                AdvRecipe recipe1 = (AdvRecipe) recipe;
                jsonObject.add("inputs", objectsToJsonArray(recipe1.input));
                jsonObject.add("outputs", itemStacksToJsonArray(new ItemStack[] { recipe1.getRecipeOutput() }));

            }
            case "ic2.core.AdvShapelessRecipe" -> {
                AdvShapelessRecipe recipe1 = (AdvShapelessRecipe) recipe;
                jsonObject.add("inputs", objectsToJsonArray(recipe1.input));
                jsonObject.add("outputs", itemStacksToJsonArray(new ItemStack[] { recipe1.getRecipeOutput() }));

            }
            default -> jsonObject.addProperty("exception", "unknown class");

        }
        return jsonObject;
    }

    public static JsonObject recipeTOJsonObject(GT_Recipe gtRecipe) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("inputs", itemStacksToJsonArray(gtRecipe.mInputs));
        jsonObject.add("outputs", itemStacksToJsonArray(gtRecipe.mOutputs));
        jsonObject.add("fluidInputs", fluidStacksToJsonArray(gtRecipe.mFluidInputs));
        jsonObject.add("fluidOutputs", fluidStacksToJsonArray(gtRecipe.mFluidOutputs));
        jsonObject.addProperty("duration", gtRecipe.mDuration);
        jsonObject.addProperty("EUt", gtRecipe.mEUt);
        jsonObject.addProperty("enabled", gtRecipe.mEnabled);
        jsonObject.addProperty("hidden", gtRecipe.mHidden);
        jsonObject.addProperty("fake", gtRecipe.mFakeRecipe);
        jsonObject.addProperty(
            "category",
            gtRecipe.getRecipeCategory() == null ? "null" : gtRecipe.getRecipeCategory().unlocalizedName);

        return jsonObject;
    }
}
