package com.nichtemna.saponify.db.entities;

import android.provider.BaseColumns;
import com.nichtemna.saponify.utils.consts.Const;

/**
 * Created by Shishova Galina
 * nichtemna@gmail.com
 */
public interface OilEntity extends BaseColumns {
    String TABLE_NAME = "oils";
    String ID = "oils_id"; // false id title
    String TITLE = "oil_title";
    String AVATAR = "avatar";
    String DESCRIPTION = "description";
    String HARDNESS = "hardness";
    String CLEANSING = "cleansing";
    String CONDITION = "condition";
    String BUBBLY = "bubbly";
    String CREAMY = "creamy";
    String IODINE = "iodine";
    String INS = "ins";
    String LAURIC = "lauric";
    String MYRISTIC = "myristic";
    String PALMITIC = "palmitic";
    String STEARIC = "stearic";
    String RICINOLEIC = "ricinoleic";
    String OLEIC = "oleic";
    String LINOLEIC = "linoleic";
    String LINOLENIC = "linolenic";
    String SAP_NAOH = "sap_naoh";
    String SAP_KOH = "sap_koh";

}
