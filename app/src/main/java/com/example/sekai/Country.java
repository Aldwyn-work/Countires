package com.example.sekai;

import android.os.Parcel;
import android.os.Parcelable;

public class Country implements Parcelable  {
    private String name;
    private String capital;
    private String alpha2code;
    private String region;
    private String callingCodes;
    private int population;
    private String lnglat;
    private String borders;
    private String flag;
    private String languages;
    private String currencies;

    public Country(){

    }


    protected Country(Parcel in) {
        name = in.readString();
        capital = in.readString();
        alpha2code = in.readString();
        region = in.readString();
        callingCodes = in.readString();
        population = in.readInt();
        lnglat = in.readString();
        borders = in.readString();
        flag = in.readString();
        languages = in.readString();
        currencies = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(capital);
        dest.writeString(alpha2code);
        dest.writeString(region);
        dest.writeString(callingCodes);
        dest.writeInt(population);
        dest.writeString(lnglat);
        dest.writeString(borders);
        dest.writeString(flag);
        dest.writeString(languages);
        dest.writeString(currencies);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {

        return capital;
    }

    public void setCapital(String capital) {

        this.capital = capital;
    }

    public String getAlpha2code() {
        return alpha2code;
    }

    public void setAlpha2code(String alpha2code) {
        this.alpha2code = alpha2code;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCallingCodes() {
        return callingCodes;
    }

    public void setCallingCodes(String callingCodes) {
        this.callingCodes = callingCodes;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getLnglat() {
        return lnglat;
    }

    public void setLnglat(String lnglat) {
        this.lnglat = lnglat;
    }

    public String getBorders() {
        return borders;
    }

    public void setBorders(String borders) {
        this.borders = borders;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getCurrencies() {
        return currencies;
    }

    public void setCurrencies(String currencies) {
        this.currencies = currencies;
    }

}
