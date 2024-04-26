package com.github.wohaopa.GTNHModify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
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
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cpw.mods.fml.common.ModContainer;
import gregtech.api.util.GT_Recipe;

public class RecipeTools {

    public static Map<Integer, String> oreToName = new HashMap<>();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting()
        .create();
    private static final int SPLIT = 5;

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
            jsonObject.addProperty("id", Item.itemRegistry.getNameForObject(itemStack.getItem()));
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
        if (itemStacks != null) for (ItemStack itemStack : itemStacks) {
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
            jsonObject.addProperty(
                "id",
                fluidStack.getFluid()
                    .getName());
            jsonObject.addProperty("name", fluidStack.getLocalizedName());
            jsonObject.addProperty("size", fluidStack.amount);
        } catch (Exception exception) {
            jsonObject.addProperty("exception", exception.getMessage());
        }
        return jsonObject;
    }

    public static JsonArray fluidStacksToJsonArray(FluidStack[] fluidStacks) {
        JsonArray jsonArray = new JsonArray();
        if (fluidStacks != null) for (FluidStack fluidStack : fluidStacks) {
            jsonArray.add(fluidStackToJsonObject(fluidStack));
        }
        return jsonArray;
    }

    public static JsonArray objectsToJsonArray(Object[] objects) {
        JsonArray jsonArray = new JsonArray();
        if (objects != null) {
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

    private static void jsonToFile(JsonElement json, String path) {
        File file = new File(Minecraft.getMinecraft().mcDataDir, "GTNHModify/dumps/" + path + ".json");
        try {
            if (!file.getParentFile()
                .exists()) {
                file.getParentFile()
                    .mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            // 将格式化后的字符串写入文件
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            writer.write(gson.toJson(json));
            writer.flush();
            writer.close();
        } catch (Exception ignored) {}

    }

    public static void dumpMinecraftRecipe(Function<String, Void> callback) {
        JsonArray jsonArray = new JsonArray();
        JsonArray unknownClass = new JsonArray();
        Set<String> unknown = new HashSet<>();

        long a1 = System.currentTimeMillis();
        {
            List<IRecipe> recipes = new ArrayList<>(
                CraftingManager.getInstance()
                    .getRecipeList());

            int size = recipes.size();
            callback.apply("开始处理Minecraft配方！共计：" + size);
            int i = 0, step0 = size / SPLIT, step = step0;
            for (IRecipe recipe : recipes) {
                i++;
                if (i == step) {
                    callback.apply(String.format("已经完成：%d%%！", i * 100 / size));
                    step += step0;
                }

                JsonObject jsonObject = new JsonObject();

                String name = recipe.getClass()
                    .getName();
                jsonObject.addProperty("category", name);

                Map<String, IRecipeHandler> handlers = RecipeHandlers.getHandlers();
                IRecipeHandler recipeHandler = handlers.get(name);
                if (recipeHandler != null) recipeHandler.recipeToJsonObject(recipe, jsonObject);
                else {
                    if (recipe instanceof ShapedOreRecipe) handlers.get("net.minecraftforge.oredict.ShapedOreRecipe")
                        .recipeToJsonObject(recipe, jsonObject);
                    else if (recipe instanceof ShapelessOreRecipe)
                        handlers.get("net.minecraftforge.oredict.ShapelessOreRecipe")
                            .recipeToJsonObject(recipe, jsonObject);
                    else {
                        jsonObject.addProperty("exception", "unknown class");
                        unknown.add(
                            jsonObject.get("category")
                                .getAsString());
                        unknownClass.add(jsonObject);
                        if (recipe.getRecipeOutput() != null) try {
                            jsonObject.add("outputs", itemStackToJsonObject(recipe.getRecipeOutput()));
                        } catch (Exception ignored) {}
                    }
                }

                jsonArray.add(jsonObject);
            }
        }
        long a2 = System.currentTimeMillis();
        callback.apply(String.format("Minecraft配方处理完成！用时：%.4fs", (a2 - a1) / 1000.0));

        StringBuilder sb = new StringBuilder("未知类型：");
        unknown.stream()
            .sorted()
            .forEach(
                (s) -> sb.append(s)
                    .append("\n"));

        jsonToFile(jsonArray, "minecraft");
        jsonToFile(unknownClass, "unknown");

        callback.apply(sb.toString());
    }

    public static void dumpGregTechRecipe(Function<String, Void> callback) {
        JsonArray jsonArray = new JsonArray();

        long a1 = System.currentTimeMillis();
        {
            int size = GT_Recipes.recipes.size();
            callback.apply("开始处理GregTech配方！共计：" + size);
            int i = 0, step0 = size / SPLIT, step = step0;
            for (GT_Recipe gtRecipe : GT_Recipes.recipes) {
                i++;
                if (i == step) {
                    callback.apply(String.format("已经完成：%d%%！", i * 100 / size));
                    step += step0;
                }

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(
                    "category",
                    gtRecipe.getRecipeCategory() == null ? "null" : gtRecipe.getRecipeCategory().unlocalizedName);

                JsonArray owners = new JsonArray();
                for (ModContainer owner : gtRecipe.owners) {
                    owners.add(new JsonPrimitive(owner.getModId()));
                }

                jsonObject.add("owners", owners);
                jsonObject.add("inputs", itemStacksToJsonArray(gtRecipe.mInputs));
                jsonObject.add("outputs", itemStacksToJsonArray(gtRecipe.mOutputs));
                jsonObject.add("fluidInputs", fluidStacksToJsonArray(gtRecipe.mFluidInputs));
                jsonObject.add("fluidOutputs", fluidStacksToJsonArray(gtRecipe.mFluidOutputs));
                jsonObject.addProperty("duration", gtRecipe.mDuration);
                jsonObject.addProperty("EUt", gtRecipe.mEUt);
                jsonObject.addProperty("enabled", gtRecipe.mEnabled);
                jsonObject.addProperty("hidden", gtRecipe.mHidden);
                jsonObject.addProperty("fake", gtRecipe.mFakeRecipe);

                jsonArray.add(jsonObject);
            }
            long a2 = System.currentTimeMillis();
            callback.apply(String.format("GregTech配方处理完成！用时：%.4fs", (a2 - a1) / 1000.0));

            jsonToFile(jsonArray, "gregtech");

        }
    }

    public static void dumpForgeOreDictionary(Function<String, Void> callback) {
        JsonArray jsonArray = new JsonArray();

        long a1 = System.currentTimeMillis();
        {
            int size = OreDictionary.getOreNames().length;
            callback.apply("开始处理Forge矿物词典！共计：" + size);
            int i = 0, step0 = size / SPLIT, step = step0;
            for (String name : OreDictionary.getOreNames()) {
                i++;
                if (i == step) {
                    callback.apply(String.format("已经完成：%d%%！", i * 100 / size));
                    step += step0;
                }

                JsonObject jsonObject = new JsonObject();

                List<ItemStack> list = OreDictionary.getOres(name);
                RecipeTools.oreToName.put(list.hashCode(), name);

                jsonObject.addProperty("name", name);
                jsonObject.add("item", RecipeTools.itemStacksToJsonArray(list.toArray(new ItemStack[0])));
                jsonArray.add(jsonObject);
            }
        }
        long a2 = System.currentTimeMillis();
        callback.apply(String.format("Forge矿物词典处理完成！用时：%.4fs", (a2 - a1) / 1000.0));

        jsonToFile(jsonArray, "ore");
    }

    public static void dumpItems(Function<String, Void> callback) {
        JsonArray jsonArray = new JsonArray();

        long a1 = System.currentTimeMillis();
        {
            Set<String> items = Item.itemRegistry.getKeys();
            int size = items.size();
            callback.apply("开始处理Item！共计：" + size);
            int i = 0, step0 = size / SPLIT, step = step0;
            for (String name : items) {
                i++;
                if (i == step) {
                    callback.apply(String.format("已经完成：%d%%！", i * 100 / size));
                    step += step0;
                }

                JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("name", name);
                Item itemObj = (Item) Item.itemRegistry.getObject(name);
                jsonObject.addProperty(
                    "class",
                    itemObj.getClass()
                        .getName());

                ItemStack itemStack = new ItemStack(itemObj);

                try {
                    String str0 = itemStack.getDisplayName();
                    jsonObject.addProperty("display", str0);
                } catch (Exception e) {
                    jsonObject.addProperty("exception", e.getMessage());
                }

                /*
                 * Map<String, Object> damage = new HashMap<>();
                 * if (itemObj.getHasSubtypes()) {
                 * // itemObj.getMetadata()
                 * try {
                 * int length = 0;
                 * if (itemObj.getMaxDamage() != 0) length = itemObj.getMaxDamage();
                 * for (int j = 0; j < length; j++) {
                 * itemStack.setItemDamage(j);
                 * String str = itemStack.getDisplayName();
                 * if (str == null || str.equals(str0)) continue;
                 * if (!damage.containsKey(str)) {
                 * damage.put(str, j);
                 * } else {
                 * Object obj = damage.get(str);
                 * if (obj instanceof Integer integer) {
                 * obj = new ArrayList<Integer>();
                 * ((ArrayList<Integer>) obj).add(integer);
                 * damage.put(str, obj);
                 * }
                 * ((ArrayList<Integer>) obj).add(j);
                 * }
                 * }
                 * } catch (Exception e) {
                 * }
                 * }
                 * if (damage.size() != 0)
                 * jsonObject.add(
                 * "damage",
                 * gson.toJsonTree(damage)
                 * .getAsJsonObject());
                 */
                jsonArray.add(jsonObject);
            }
        }
        long a2 = System.currentTimeMillis();
        callback.apply(String.format("Item处理完成！用时：%.4fs", (a2 - a1) / 1000.0));

        jsonToFile(jsonArray, "items");
    }
}
