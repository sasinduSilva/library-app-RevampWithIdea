package com.library.app.author.resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.library.app.author.model.Author;
import com.library.app.common.json.EntityJsonConverter;
import com.library.app.common.json.JsonReader;

import javax.faces.bean.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class AuthorJsonConverter implements EntityJsonConverter<Author>  {

    @Override
    public  Author convertFrom(String json) {
        JsonObject jsonObject = JsonReader.readAsJsonObject(json);

        Author author = new Author();
        author.setName(JsonReader.getStringOrNull(jsonObject,"name"));

        return author;
    }

    @Override
    public JsonElement convertToJsonElement(Author authors) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", authors.getId());
        jsonObject.addProperty("name", authors.getName());
        return jsonObject;
    }



}
