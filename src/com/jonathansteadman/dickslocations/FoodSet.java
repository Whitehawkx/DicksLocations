
package com.jonathansteadman.dickslocations;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class FoodSet {
    private static FoodSet foodSet;

    private static ArrayList<Food> foods = new ArrayList<Food>();

    private static final String FILE_NAME = "food.json";

    private FoodJSONSerializer serializer;

    private Context context;

    private FoodSet(Context context) {
        this.context = context;
        serializer = new FoodJSONSerializer(context, FILE_NAME);

        try {
            foods = serializer.loadFoods();
        } catch (Exception e) {
            foods = new ArrayList<Food>();
            Log.e("Food", "error loading");
        }
    }

    public static FoodSet newInstance(Context context) {
        if (foodSet == null) {
            foodSet = new FoodSet(context);
        }

        return foodSet;
    }

    public ArrayList<Food> getFood() {
        return foods;
    }

    public static void addFood(Food food) {
        foods.add(food);
    }

    public static void deleteFood(Food food) {
        foods.remove(food);
    }

    public boolean saveFood() {
        try {
            serializer.saveFoods(foods);
            Log.i("Mmmhmmm", "Saving food");
            return true;
        } catch (Exception e) {
            Log.i("Mmmhmmm", "error saving food... now I'm going to starve");
            return false;
        }
    }
}
