package com.github.wohaopa.GTNHModify;

import static com.github.wohaopa.GTNHModify.RecipeTools.itemStacksToJsonArray;
import static com.github.wohaopa.GTNHModify.RecipeTools.objectsToJsonArray;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;

import com.google.gson.JsonObject;

public class RecipeHandlers {

    static Map<String, IRecipeHandler> handlers;

    public static Map<String, IRecipeHandler> getHandlers() {
        if (handlers != null) return handlers;
        handlers = new HashMap<>();
        Class<?>[] handlerClazz = RecipeHandlers.class.getDeclaredClasses();
        for (Class<?> clazz : handlerClazz) {
            try {
                if (Modifier.isAbstract(clazz.getModifiers())) continue;
                IRecipeHandler recipeHandler = (IRecipeHandler) clazz.newInstance();
                String[] names = recipeHandler.getNames();
                for (String name : names) handlers.put(name, recipeHandler);

            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return handlers;
    }

    public static class MinecraftShapedRecipe implements IRecipeHandler {

        @Override
        public String[] getNames() {
            return new String[] { "net.minecraft.item.crafting.ShapedRecipes",
                "net.minecraft.item.crafting.RecipesMapExtending" };
        }

        @Override
        public JsonObject recipeToJsonObject(IRecipe recipe, JsonObject jsonObject) {
            ShapedRecipes recipe1 = (ShapedRecipes) recipe;
            jsonObject.add("inputs", itemStacksToJsonArray(recipe1.recipeItems));
            jsonObject.add("outputs", itemStacksToJsonArray(new ItemStack[] { recipe.getRecipeOutput() }));
            return jsonObject;
        }
    }

    public static class MinecraftShapelessRecipe implements IRecipeHandler {

        @Override
        public String[] getNames() {
            return new String[] { "net.minecraft.item.crafting.ShapelessRecipes" };
        }

        @Override
        public JsonObject recipeToJsonObject(IRecipe recipe, JsonObject jsonObject) {
            ShapelessRecipes recipe1 = (ShapelessRecipes) recipe;
            jsonObject.add("inputs", itemStacksToJsonArray(recipe1.recipeItems.toArray(new ItemStack[0])));
            jsonObject.add("outputs", itemStacksToJsonArray(new ItemStack[] { recipe.getRecipeOutput() }));
            return jsonObject;
        }
    }

    private abstract static class DuckTypingRecipeHandler implements IRecipeHandler {

        protected Map<Class<?>, Method> methods = new HashMap<>();

        protected String methodName;

        public DuckTypingRecipeHandler(String methodName) {
            this.methodName = methodName;
        }

        protected Object[] getInputs(Object object) {
            return (Object[]) object;
        }

        @Override
        public JsonObject recipeToJsonObject(IRecipe recipe, JsonObject jsonObject) {

            try {
                jsonObject.add(
                    "inputs",
                    objectsToJsonArray(
                        getInputs(
                            methods.get(recipe.getClass())
                                .invoke(recipe))));
            } catch (IllegalAccessException | InvocationTargetException e) {
                jsonObject.addProperty("exception", e.getMessage());
            } catch (NullPointerException e) {
                try {
                    Method method = recipe.getClass()
                        .getMethod(methodName);
                    method.setAccessible(true);
                    methods.put(recipe.getClass(), method);
                    jsonObject.add("inputs", objectsToJsonArray(getInputs(method.invoke(recipe))));
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                    jsonObject.addProperty("exception", ex.getMessage());
                }

            }

            jsonObject.add("outputs", itemStacksToJsonArray(new ItemStack[] { recipe.getRecipeOutput() }));
            return jsonObject;
        }
    }

    public static class MinecraftForgeShapedOreRecipe extends DuckTypingRecipeHandler {

        public MinecraftForgeShapedOreRecipe() {
            super("getInput");
        }

        @Override
        public String[] getNames() {
            return new String[] { "net.minecraftforge.oredict.ShapedOreRecipe", "gregtech.api.util.GT_Shaped_Recipe",
                "appeng.recipes.game.ShapedRecipe" };
        }
    }

    public static class MinecraftForgeShapelessOreRecipe extends DuckTypingRecipeHandler {

        public MinecraftForgeShapelessOreRecipe() {
            super("getInput");
        }

        @Override
        protected Object[] getInputs(Object object) {
            return ((ArrayList<?>) object).toArray(new Object[0]);
        }

        @Override
        public String[] getNames() {
            return new String[] { "net.minecraftforge.oredict.ShapelessOreRecipe",
                "gregtech.api.util.GT_Shapeless_Recipe", "appeng.recipes.game.ShapelessRecipe", "ic2.core.AdvRecipe",
                "ic2.core.AdvShapelessRecipe" };
        }
    }

    public static class OnlyHaveOutput implements IRecipeHandler {

        @Override
        public String[] getNames() {
            return new String[] { "vazkii.botania.common.crafting.recipe.HelmRevealingRecipe" };
        }

        @Override
        public JsonObject recipeToJsonObject(IRecipe recipe, JsonObject jsonObject) {
            jsonObject.add("outputs", itemStacksToJsonArray(new ItemStack[] { recipe.getRecipeOutput() }));
            return jsonObject;
        }
    }

    public static class NullRecipe implements IRecipeHandler {

        @Override
        public String[] getNames() {
            return new String[] { "net.minecraft.item.crafting.RecipesArmorDyes",
                "net.minecraft.item.crafting.RecipeBookCloning", "net.minecraft.item.crafting.RecipesMapCloning",
                "net.minecraft.item.crafting.RecipeFireworks",
                "com.glodblock.github.loader.recipe.WirelessTerminalRecipe", "appeng.recipes.game.DisassembleRecipe",
                "appeng.recipes.game.FacadeRecipe", "thaumcraft.common.items.armor.RecipesRobeArmorDyes",
                "thaumcraft.common.items.armor.RecipesVoidRobeArmorDyes",
                "vazkii.botania.common.crafting.recipe.AesirRingRecipe",
                "vazkii.botania.common.crafting.recipe.AncientWillRecipe",
                "vazkii.botania.common.crafting.recipe.BlackHoleTalismanExtractRecipe",
                "vazkii.botania.common.crafting.recipe.CompositeLensRecipe",
                "vazkii.botania.common.crafting.recipe.CosmeticAttachRecipe",
                "vazkii.botania.common.crafting.recipe.CosmeticRemoveRecipe",
                "vazkii.botania.common.crafting.recipe.KeepIvyRecipe",
                "vazkii.botania.common.crafting.recipe.LensDyeingRecipe",
                "vazkii.botania.common.crafting.recipe.ManaGunLensRecipe",
                "vazkii.botania.common.crafting.recipe.ManaGunClipRecipe",
                "vazkii.botania.common.crafting.recipe.ManaGunRemoveLensRecipe",
                "vazkii.botania.common.crafting.recipe.PhantomInkRecipe",
                "vazkii.botania.common.crafting.recipe.RegenIvyRecipe",
                "vazkii.botania.common.crafting.recipe.SpecialFloatingFlowerRecipe",
                "vazkii.botania.common.crafting.recipe.SpellClothRecipe",
                "vazkii.botania.common.crafting.recipe.TerraPickTippingRecipe" };
        }

        @Override
        public JsonObject recipeToJsonObject(IRecipe recipe, JsonObject jsonObject) {
            return jsonObject;
        }
    }

}
