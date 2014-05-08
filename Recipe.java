package com.nichtemna.saponify.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import com.nichtemna.saponify.db.entities.RecipeEntity;
import com.nichtemna.saponify.db.entities.RecipeOilEntity;
import com.nichtemna.saponify.utils.consts.Enums;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shishova Galina on 09.04.14.
 * nichtemna@gmail.com
 */
public class Recipe extends BaseModel implements Parcelable, Xmlable {
    public long mId;
    public long mTimestamp;
    public String mTitle;
    public String mNotes;
    public Enums.Lye mLyeType;
    public LiquidInRecipe mLiquidInRecipe;
    public double mTotalOilsWeightInGrams;
    public double mSuperFatPercents;
    public double mSap;
    public List<Oil> mOils = new ArrayList<Oil>();
    public int mRating;

    public Recipe() {
    }

    @Override
    public void toXml(XmlSerializer pXmlSerializer) throws IOException {
        pXmlSerializer.startTag("", RecipeEntity.ROW);

        pXmlSerializer.startTag("", RecipeEntity._ID).text(String.valueOf(mId)).endTag("", RecipeEntity._ID);
        pXmlSerializer.startTag("", RecipeEntity.TITLE).text(mTitle).endTag("", RecipeEntity.TITLE);
        pXmlSerializer.startTag("", RecipeEntity.NOTES).text(mNotes).endTag("", RecipeEntity.NOTES);
        pXmlSerializer.startTag("", RecipeEntity.TIMESTAMP).text(String.valueOf(mTimestamp)).endTag("", RecipeEntity.TIMESTAMP);
        pXmlSerializer.startTag("", RecipeEntity.LYE_TYPE).text(String.valueOf(mLyeType.ordinal())).endTag("", RecipeEntity.LYE_TYPE);
        pXmlSerializer.startTag("", RecipeEntity.SAP).text(String.valueOf(mSap)).endTag("", RecipeEntity.SAP);
        pXmlSerializer.startTag("", RecipeEntity.TOTAL_WEIGHT).text(String.valueOf(mTotalOilsWeightInGrams)).endTag("", RecipeEntity.TOTAL_WEIGHT);
        pXmlSerializer.startTag("", RecipeEntity.LIQUID_TYPE).text(String.valueOf(mLiquidInRecipe.mLiquid.ordinal())).endTag("", RecipeEntity.LIQUID_TYPE);
        pXmlSerializer.startTag("", RecipeEntity.LIQUID_FIRST_VALUE).text(String.valueOf(mLiquidInRecipe.mLiquidFirstValue)).endTag("", RecipeEntity.LIQUID_FIRST_VALUE);
        pXmlSerializer.startTag("", RecipeEntity.LIQUID_SECOND_VALUE).text(String.valueOf(mLiquidInRecipe.mLiquidSecondValue)).endTag("", RecipeEntity.LIQUID_SECOND_VALUE);
        pXmlSerializer.startTag("", RecipeEntity.LIQUID_ID).text(String.valueOf(mLiquidInRecipe.mLiquidTypeId)).endTag("", RecipeEntity.LIQUID_ID);
        pXmlSerializer.startTag("", RecipeEntity.SUPER_FAT_PERCENTS).text(String.valueOf(mSuperFatPercents)).endTag("", RecipeEntity.SUPER_FAT_PERCENTS);
        pXmlSerializer.startTag("", RecipeEntity.RATING).text(String.valueOf(mRating)).endTag("", RecipeEntity.RATING);

        pXmlSerializer.endTag("", RecipeEntity.ROW);
    }

}
