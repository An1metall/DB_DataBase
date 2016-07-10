package com.an1metall.gb_a_database;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class XmlHandler {

    private static final String ns = null;

    public static String parseToQueryString(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readTable(parser);
        } finally {
            in.close();
        }
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private static String readTable(XmlPullParser parser) throws XmlPullParserException, IOException {
        String tableName = null;
        String tableParameters = null;
        parser.require(XmlPullParser.START_TAG, ns, Manifest.XML_ENTITY);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case Manifest.XML_ENTITY_ATTRIBUTE:
                    if (tableParameters == null) {
                        tableParameters = readEntry(parser);
                    } else {
                        tableParameters = tableParameters + ", " + readEntry(parser);
                    }
                    break;
                case Manifest.XML_ENTITY_NAME:
                    tableName = readText(parser, name);
                    break;
                default:
                    skip(parser);
            }
        }
        return "CREATE TABLE " + tableName + " (" + tableParameters + ")";
    }

    private static String readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, Manifest.XML_ENTITY_ATTRIBUTE);
        String result = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case Manifest.XML_ENTITY_ATTRIBUTE_NAME:
                case Manifest.XML_ENTITY_ATTRIBUTE_TYPE:
                case Manifest.XML_ENTITY_ATTRIBUTE_PARAMETER:
                    if (result == null) {
                        result = readText(parser, name);
                    } else {
                        result = result + " " + readText(parser, name);
                    }
                    break;
                default:
                    skip(parser);
            }
        }
        return result;
    }

    private static String readText(XmlPullParser parser, String name) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, name);
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        parser.require(XmlPullParser.END_TAG, ns, name);
        return result;
    }

}
