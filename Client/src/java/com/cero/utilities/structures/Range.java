package com.cero.utilities.structures;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Range {

    public double min;
    public double max;

    public Range(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public JsonElement toJSON() {
        JsonObject ret = new JsonObject();
        ret.addProperty("min", min);
        ret.addProperty("max", max);
        return ret;
    }

    public void verify() {
        this.min = Math.min(min, max);
        this.max = Math.max(min, max);
    }

    public static Range fromJSON(JsonObject object) {
        double min = object.get("min").getAsDouble();
        double max = object.get("max").getAsDouble();
        return new Range(min, max);
    }

}
