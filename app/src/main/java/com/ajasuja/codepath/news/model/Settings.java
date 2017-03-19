package com.ajasuja.codepath.news.model;

import org.parceler.Parcel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ajasuja on 3/17/17.
 */
@Parcel
public class Settings {

    private static Settings singleInstance;

    public static Settings getInstance() {
        if (singleInstance == null) {
            singleInstance = new Settings();
        }
        return singleInstance;
    }

    private String settingsName;
    private long beginDateInMillis;
    private SortOrder sortOrder = SortOrder.NEWEST;

    private boolean newsDeskArtsSelected;
    private boolean newsDeskFashionAndStyleSelected;
    private boolean newsDeskSportsSelected;

    Settings() {
        // for parcel
    }

    public String getSettingsName() {
        return settingsName;
    }

    public void setSettingsName(String settingsName) {
        this.settingsName = settingsName;
    }

    public long getBeginDateInMillis() {
        return beginDateInMillis;
    }

    public void setBeginDateInMillis(long beginDateInMillis) {
        this.beginDateInMillis = beginDateInMillis;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isNewsDeskArtsSelected() {
        return newsDeskArtsSelected;
    }

    public void setNewsDeskArtsSelected(boolean newsDeskArtsSelected) {
        this.newsDeskArtsSelected = newsDeskArtsSelected;
    }

    public boolean isNewsDeskFashionAndStyleSelected() {
        return newsDeskFashionAndStyleSelected;
    }

    public void setNewsDeskFashionAndStyleSelected(boolean newsDeskFashionAndStyleSelected) {
        this.newsDeskFashionAndStyleSelected = newsDeskFashionAndStyleSelected;
    }

    public boolean isNewsDeskSportsSelected() {
        return newsDeskSportsSelected;
    }

    public void setNewsDeskSportsSelected(boolean newsDeskSportsSelected) {
        this.newsDeskSportsSelected = newsDeskSportsSelected;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "settingsName='" + settingsName + '\'' +
                '}';
    }

    public enum SortOrder {
        OLDEST("OLDEST"),
        NEWEST("NEWEST");

        private String sortOrder;
        private static Map<String, SortOrder> string2Enum = new HashMap<>();
        static {
            for (SortOrder sortOrder : values()) {
                string2Enum.put(sortOrder.getSortOrder(), sortOrder);
            }
        }

        SortOrder(String sortOrder) {
            this.sortOrder = sortOrder;
        }

        public String getSortOrder() {
            return sortOrder;
        }

        public static SortOrder fromValue(String value) {
            return string2Enum.get(value);
        }
    }
}
