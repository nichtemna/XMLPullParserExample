package com.nichtemna.saponify.models;

import android.util.Log;
import android.util.Xml;
import com.nichtemna.saponify.R;
import com.nichtemna.saponify.SaponifyApp;
import com.nichtemna.saponify.db.entities.RecipeEntity;
import com.nichtemna.saponify.db.entities.RecipeOilEntity;
import com.nichtemna.saponify.utils.DateUtils;
import com.nichtemna.saponify.utils.consts.Const;
import com.nichtemna.saponify.utils.consts.Enums;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Created by root on 06.05.14.
 */
public class RestoreBackupModel {
    public ArrayList<Recipe> mRecipes;
    public ArrayList<RecipeOil> mRecipeOils;
    public String mName;
    public String mVersion;

    public RestoreBackupModel() {
    }

    public RestoreBackupModel(InputStream pInputStream) {
        try {
            fromXml(pInputStream);
        } catch (IOException e) {
            Log.e(Const.SAPONIFY, "Error parsing xml");
        }
    }

    public RestoreBackupModel(ArrayList<Recipe> pRecipes, ArrayList<RecipeOil> pRecipeOils) {
        mRecipes = pRecipes;
        mRecipeOils = pRecipeOils;
        mName = DateUtils.getBackUpFileName();
        mVersion = SaponifyApp.getAppContext().getString(R.string.version_name);
    }

    private void fromXml(InputStream pInputStream) throws IOException {
        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(pInputStream, null);

            int eventType = parser.getEventType();
            Recipe recipe = null;
            RecipeOil recipeOil = null;
            String name;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        name = parser.getName();

                        if (name.equalsIgnoreCase(Const.EXPORT_DATABASE)) {
                            mName = parser.getAttributeValue("", Const.NAME);
                            mVersion = parser.getAttributeValue("", Const.APP_VERSION);

                        } else if (name.equalsIgnoreCase(RecipeEntity.TABLE_NAME)) {
                            mRecipes = new ArrayList<Recipe>();

                        } else if (name.equalsIgnoreCase(RecipeEntity.ROW)) {
                            recipe = new Recipe();

                        } else if (recipe != null) {
                            if (name.equalsIgnoreCase(RecipeEntity._ID)) {
                                recipe.mId = Long.valueOf(parser.nextText());
                            } else if (name.equalsIgnoreCase(RecipeEntity.TITLE)) {
                                recipe.mTitle = parser.nextText();
                            } else if (name.equalsIgnoreCase(RecipeEntity.NOTES)) {
                                recipe.mNotes = parser.nextText();
                            } else if (name.equalsIgnoreCase(RecipeEntity.TIMESTAMP)) {
                                recipe.mTimestamp = Long.valueOf(parser.nextText());
                            } else if (name.equalsIgnoreCase(RecipeEntity.LYE_TYPE)) {
                                recipe.mLyeType = Enums.Lye.values()[Integer.valueOf(parser.nextText())];
                            } else if (name.equalsIgnoreCase(RecipeEntity.SAP)) {
                                recipe.mSap = Double.valueOf(parser.nextText());
                            } else if (name.equalsIgnoreCase(RecipeEntity.TOTAL_WEIGHT)) {
                                recipe.mTotalOilsWeightInGrams = Double.valueOf(parser.nextText());
                            } else if (name.equalsIgnoreCase(RecipeEntity.LIQUID_TYPE)) {
                                Enums.Liquid liquid = Enums.Liquid.values()[Integer.valueOf(parser.nextText())];
                                recipe.mLiquidInRecipe = new LiquidInRecipe(liquid);
                            } else if (name.equalsIgnoreCase(RecipeEntity.LIQUID_FIRST_VALUE)) {
                                recipe.mLiquidInRecipe.mLiquidFirstValue = Double.valueOf(parser.nextText());
                            } else if (name.equalsIgnoreCase(RecipeEntity.LIQUID_SECOND_VALUE)) {
                                recipe.mLiquidInRecipe.mLiquidSecondValue = Double.valueOf(parser.nextText());
                            } else if (name.equalsIgnoreCase(RecipeEntity.LIQUID_ID)) {
                                recipe.mLiquidInRecipe.mLiquidTypeId = Long.valueOf(parser.nextText());
                            } else if (name.equalsIgnoreCase(RecipeEntity.SUPER_FAT_PERCENTS)) {
                                recipe.mSuperFatPercents = Double.valueOf(parser.nextText());
                            } else if (name.equalsIgnoreCase(RecipeEntity.RATING)) {
                                recipe.mRating = Integer.valueOf(parser.nextText());
                            }
                        } else if (name.equalsIgnoreCase(RecipeOilEntity.TABLE_NAME)) {
                            mRecipeOils = new ArrayList<RecipeOil>();

                        } else if (name.equalsIgnoreCase(RecipeOilEntity.ROW)) {
                            recipeOil = new RecipeOil();

                        } else if (recipeOil != null) {
                            if (name.equalsIgnoreCase(RecipeOilEntity.RECIPE_ID)) {
                                recipeOil.mRecipeId = Long.valueOf(parser.nextText());
                            } else if (name.equalsIgnoreCase(RecipeOilEntity.OIL_ID)) {
                                recipeOil.mOilId = Long.valueOf(parser.nextText());
                            } else if (name.equalsIgnoreCase(RecipeOilEntity.PERCENTS)) {
                                recipeOil.mPercents = Double.valueOf(parser.nextText());
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        name = parser.getName();

                        if (name.equalsIgnoreCase(RecipeEntity.ROW) && recipe != null) {
                            mRecipes.add(recipe);
                            recipe = null;

                        } else if (name.equalsIgnoreCase(RecipeOilEntity.ROW) && recipeOil != null) {
                            for (Recipe recipeSelected : mRecipes) {
                                if (recipeOil.mRecipeId == recipeSelected.mId) {
                                    Oil oil = new Oil(recipeOil.mOilId, recipeOil.mPercents);
                                    recipeSelected.mOils.add(oil);
                                }
                            }
                            mRecipeOils.add(recipeOil);
                            recipeOil = null;
                        }
                }
                eventType = parser.next();
            }

        } catch (Exception ex) {
            Log.e(Const.SAPONIFY, "Error parsing xml");
            pInputStream.close();
        }
    }

    public String toXmlString() throws IOException {
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        xmlSerializer.setOutput(writer);
        xmlSerializer.startDocument("UTF-8", true);

        xmlSerializer.startTag("", Const.EXPORT_DATABASE)
                .attribute("", Const.NAME, DateUtils.getBackUpFileName())
                .attribute("", Const.APP_VERSION, SaponifyApp.getAppContext().getString(R.string.version_name));


        xmlSerializer.startTag("", RecipeEntity.TABLE_NAME);
        for (Recipe recipe : mRecipes) {
            recipe.toXml(xmlSerializer);
        }
        xmlSerializer.endTag("", RecipeEntity.TABLE_NAME);

        xmlSerializer.startTag("", RecipeOilEntity.TABLE_NAME);
        for (RecipeOil oil : mRecipeOils) {
            oil.toXml(xmlSerializer);
        }
        xmlSerializer.endTag("", RecipeOilEntity.TABLE_NAME);

        xmlSerializer.endTag("", Const.EXPORT_DATABASE);
        xmlSerializer.endDocument();

        return writer.toString();
    }
}
