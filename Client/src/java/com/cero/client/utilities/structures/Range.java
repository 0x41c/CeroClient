package com.cero.client.utilities.structures;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Range {

    public double min;
    public double max;

    public Range(final double min, final double max) {
        super();
        this.min = min;
        this.max = max;
    }

    public final JsonElement toJSON() {
        final JsonObject ret = new JsonObject();
        ret.addProperty("min", min);
        ret.addProperty("max", max);
        return ret;
    }

    public final void verify() {
        this.min = Math.min(min, max);
        this.max = Math.max(min, max);
    }

    public static Range fromJSON(final JsonObject object) {
        final double min = object.get("min").getAsDouble();
        final double max = object.get("max").getAsDouble();
        return new Range(min, max);
    }

}
