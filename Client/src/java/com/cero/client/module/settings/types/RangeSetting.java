package com.cero.client.module.settings.types;

import com.cero.client.module.settings.ModuleSetting;
import com.cero.client.utilities.structures.Range;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public class RangeSetting extends ModuleSetting {

    Range defaultRange;
    Range barriers;
    Range range;
    double steps;

    public RangeSetting(String name, Range barriers, Range defaultRange, double steps) {
        super(name, SettingType.RangeSetting);
        this.barriers = barriers;
        this.defaultRange = defaultRange;
        this.range = defaultRange;
        this.steps = steps;
    }

    @Override
    public JsonObject toJSON() {
        JsonObject retObj = new JsonObject();
        retObj.addProperty("name", name);
        retObj.add("range", range.toJSON());
        return retObj;
    }

    @Override
    public void updateFromJSON(JsonObject json) {
        if (!json.get("name").getAsString().equals(type.toString())) return;
        this.range = Range.fromJSON(json.get("range").getAsJsonObject());
    }

    @Override
    public void reset() {
        this.range = this.defaultRange;
    }

    public Range getBarriers() { return barriers; }
    public Range getRange() { return range; }
    public void setRange(@NotNull Range r) {
        r.verify();
        this.range = r;
    }
}