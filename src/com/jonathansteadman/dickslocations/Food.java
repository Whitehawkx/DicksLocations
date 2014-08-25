
package com.jonathansteadman.dickslocations;

import org.json.JSONException;
import org.json.JSONObject;

public class Food implements Constants {
    private String title;

    private String description;

    private String image;

    public Food() {
        this.title = "";
        this.description = "";
        this.image = "";
    }

    public Food(JSONObject json) throws JSONException {
        if (json.has(JSON_FOOD_TITLE)) {
            title = json.getString(JSON_FOOD_TITLE);
        }

        if (json.has(JSON_FOOD_DESC)) {
            description = json.getString(JSON_FOOD_DESC);
        }

        if (json.has(JSON_FOOD_IMAGE)) {
            image = json.getString(JSON_FOOD_IMAGE);
        }

    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_FOOD_TITLE, title);
        json.put(JSON_FOOD_DESC, description);
        json.put(JSON_FOOD_IMAGE, image);

        return json;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.description = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String img) {
        this.image = img;
    }

    public String toString() {
        return title;
    }
}
