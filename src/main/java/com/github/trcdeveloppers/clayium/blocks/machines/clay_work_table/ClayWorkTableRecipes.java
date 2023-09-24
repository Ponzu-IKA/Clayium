package com.github.trcdeveloppers.clayium.blocks.machines.clay_work_table;

import com.github.trcdeveloppers.clayium.items.ClayiumItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class ClayWorkTableRecipes {
    static final NonNullList<ClayWorkTableRecipe> recipes = NonNullList.create();

    public static void init() {
        addRecipe(new ItemStack(Items.CLAY_BALL), new ItemStack(ClayiumItems.getItem("clay_stick")),
            ClayWorkTableMethod.ROLLING_HAND, 4);
        addRecipe(new ItemStack(ClayiumItems.getItem("clay_plate"), 3), new ItemStack(ClayiumItems.getItem("large_clay_ball")),
            ClayWorkTableMethod.ROLLING_HAND, 40);
        addRecipe(new ItemStack(ClayiumItems.getItem("large_clay_ball")), new ItemStack(ClayiumItems.getItem("clay_cylinder")),
            ClayWorkTableMethod.ROLLING_HAND, 4);
        addRecipe(new ItemStack(ClayiumItems.getItem("clay_cylinder")), new ItemStack(ClayiumItems.getItem("clay_needle")),
            ClayWorkTableMethod.ROLLING_HAND, 3);

        addRecipe(new ItemStack(ClayiumItems.getItem("clay_plate")), new ItemStack(ClayiumItems.getItem("clay_blade")),
            ClayWorkTableMethod.PUNCH, 10);
        //todo: raw_clay_slicerの実装
//        addRecipe(new ItemStack(ClayiumItems.getItem("clay_disc")), new ItemStack(ClayiumItems.getItem("raw_clay_slicer")),
//            ClayWorkTableMethod.PUNCH, 15);
        addRecipe(new ItemStack(ClayiumItems.getItem("large_clay_ball")), new ItemStack(ClayiumItems.getItem("clay_disc")),
            ClayWorkTableMethod.PUNCH, 30);

        addRecipe(new ItemStack(ClayiumItems.getItem("clay_plate"), 6), new ItemStack(ClayiumItems.getItem("large_clay_plate")),
            ClayWorkTableMethod.ROLLING_PIN, 10);
        addRecipe(new ItemStack(ClayiumItems.getItem("clay_plate"), 6), new ItemStack(ClayiumItems.getItem("clay_blade")),
            new ItemStack(Items.CLAY_BALL, 2), ClayWorkTableMethod.ROLLING_PIN, 10);
//        addRecipe(new ItemStack(ClayiumItems.getItem("clay_disc")), new ItemStack(ClayiumItems.getItem("raw_clay_slicer")),
//            ClayWorkTableMethod.ROLLING_PIN, 2);
        addRecipe(new ItemStack(ClayiumItems.getItem("large_clay_ball"), 6), new ItemStack(ClayiumItems.getItem("clay_disc")),
            new ItemStack(Items.CLAY_BALL, 2), ClayWorkTableMethod.ROLLING_PIN, 4);

        addRecipe(new ItemStack(ClayiumItems.getItem("clay_disc"), 6), new ItemStack(ClayiumItems.getItem("clay_plate")),
            new ItemStack(Items.CLAY_BALL, 2), ClayWorkTableMethod.CUT_PLATE, 4);

        addRecipe(new ItemStack(ClayiumItems.getItem("clay_disc"), 6), new ItemStack(ClayiumItems.getItem("clay_ring")),
            new ItemStack(ClayiumItems.getItem("clay_small_disc")), ClayWorkTableMethod.CUT_DISC, 2);
        addRecipe(new ItemStack(ClayiumItems.getItem("clay_small_disc"), 6), new ItemStack(ClayiumItems.getItem("clay_small_ring")),
            new ItemStack(ClayiumItems.getItem("clay_short_stick")), ClayWorkTableMethod.CUT_DISC, 1);

        addRecipe(new ItemStack(ClayiumItems.getItem("clay_plate")), new ItemStack(ClayiumItems.getItem("clay_stick")),
            ClayWorkTableMethod.CUT, 3);
        addRecipe(new ItemStack(ClayiumItems.getItem("clay_cylinder")), new ItemStack(ClayiumItems.getItem("clay_small_disc"), 8),
            ClayWorkTableMethod.CUT, 7);
    }

    //todo: inputとmethodが両方同じときの対処
    private static void addRecipe(ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput, ClayWorkTableMethod method, int clicks) {
        recipes.add(new ClayWorkTableRecipe(input, primaryOutput, secondaryOutput, method, clicks));
    }
    private static void addRecipe(ItemStack input, ItemStack primaryOutput, ClayWorkTableMethod method, int clicks) {
        recipes.add(new ClayWorkTableRecipe(input, primaryOutput, ItemStack.EMPTY, method, clicks));
    }

    static List<ClayWorkTableRecipe> getRecipesFor(ItemStack input) {
        return recipes.stream()
            .filter(recipe -> recipe.INPUT.isItemEqual(input))
            .filter(recipe -> recipe.INPUT.getCount() <= input.getCount())
            .collect(Collectors.toList());
    }

    @Nullable
    static ClayWorkTableRecipe getRecipeFor(ItemStack input, ClayWorkTableMethod method) {
        for (ClayWorkTableRecipe recipe : recipes) {
            if (!(recipe.INPUT.isItemEqual(input)
                && recipe.INPUT.getCount() <= input.getCount())
                && recipe.METHOD == method) {
                continue;
            }
            return recipe;
        }
        return null;
    }

    static class ClayWorkTableRecipe {
        public final ItemStack INPUT;
        public final ItemStack OUTPUT_1;
        public final ItemStack OUTPUT_2;
        public final ClayWorkTableMethod METHOD;
        public final int CLICKS;
        public ClayWorkTableRecipe(ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput, ClayWorkTableMethod method, int clicks) {
            this.INPUT = input;
            this.OUTPUT_1 = primaryOutput;
            this.OUTPUT_2 = secondaryOutput;
            this.METHOD = method;
            this.CLICKS = clicks;
        }

        public boolean hasSecondaryOutput() {
            return this.OUTPUT_2.isEmpty();
        }
    }
}