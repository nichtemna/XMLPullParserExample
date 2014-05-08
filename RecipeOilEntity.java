package com.nichtemna.saponify.db.entities;

import android.provider.BaseColumns;

/**
 * Created by Shishova Galina on 09.04.14.
 * nichtemna@gmail.com
 */
public interface RecipeOilEntity extends BaseColumns {
    String TABLE_NAME = "recipe_oils";
    String RECIPE_ID = "recipe_id";
    String OIL_ID = "oil_id";
    String PERCENTS = "oil_in_recipe_percents";
    String ROW = "row_recipe_oil";
}
