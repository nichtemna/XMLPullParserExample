package com.nichtemna.saponify.models;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by root on 06.05.14.
 */
public interface Xmlable {

    public void toXml(XmlSerializer pXmlSerializer) throws IOException;

}
