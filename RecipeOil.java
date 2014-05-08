package com.nichtemna.saponify.models;

import android.content.ContentValues;
import android.database.Cursor;
import com.nichtemna.saponify.db.entities.RecipeEntity;
import com.nichtemna.saponify.db.entities.RecipeOilEntity;
import com.nichtemna.saponify.utils.consts.Enums;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

/**
 * Created by root on 04.05.14.
 */
public class RecipeOil extends BaseModel implements Xmlable {
    public long mId;
    public long mRecipeId;
    public long mOilId;
    public double mPercents;

    public RecipeOil() {
    }

    @Override
    public void toXml(XmlSerializer pXmlSerializer) throws IOException {
        pXmlSerializer.startTag("", RecipeOilEntity.ROW);

        pXmlSerializer.startTag("", RecipeOilEntity.RECIPE_ID).text(String.valueOf(mRecipeId)).endTag("", RecipeOilEntity.RECIPE_ID);
        pXmlSerializer.startTag("", RecipeOilEntity.OIL_ID).text(String.valueOf(mOilId)).endTag("", RecipeOilEntity.OIL_ID);
        pXmlSerializer.startTag("", RecipeOilEntity.PERCENTS).text(String.valueOf(mPercents)).endTag("", RecipeOilEntity.PERCENTS);

        pXmlSerializer.endTag("", RecipeOilEntity.ROW);
    }
}
