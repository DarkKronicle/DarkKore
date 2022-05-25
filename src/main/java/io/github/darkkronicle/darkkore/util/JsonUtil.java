package io.github.darkkronicle.darkkore.util;

import com.google.gson.*;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

@UtilityClass
public class JsonUtil {

    public final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public String toString(JsonElement element) {
        return GSON.toJson(element);
    }

    public JsonArray toArray(String... strings) {
        JsonArray array = new JsonArray();
        for (String string : strings) {
            array.add(string);
        }
        return array;
    }

    public JsonElement read(File file) throws IOException {
        return JsonParser.parseReader(GSON.newJsonReader(new StringReader(FileUtil.read(file))));
    }

    public void write(JsonElement element, File file) throws IOException {
        FileUtil.write(toString(element), file);
    }

}
