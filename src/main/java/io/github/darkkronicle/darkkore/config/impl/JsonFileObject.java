package io.github.darkkronicle.darkkore.config.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.darkkronicle.darkkore.DarkKore;
import io.github.darkkronicle.darkkore.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.IOException;

public class JsonFileObject implements FileObject {

    @Getter @Setter private JsonConfigObject object;

    @Getter @Setter private File file;

    public JsonFileObject(File file) {
        this(file, new JsonConfigObject());
    }

    public JsonFileObject(File file, JsonConfigObject object) {
        this.file = file;
        this.object = object;
    }

    @Override
    public void save() {
        try {
            JsonUtil.write(object.getObject(), file);
        } catch (IOException e) {
            DarkKore.LOGGER.log(Level.ERROR, "Error saving JSON file!", e);
        }
    }

    @Override
    public void load() {
        try {
            JsonElement object = JsonUtil.read(file);
            if (!object.isJsonObject()) {
                DarkKore.LOGGER.log(Level.ERROR, "JSON file is not a json object! " + file);
                return;
            }
            this.object = new JsonConfigObject((JsonObject) object);
        } catch (IOException e) {
            DarkKore.LOGGER.log(Level.ERROR, "Error loading JSON file!", e);
        }
    }

    @Override
    public void close() {

    }

    @Override
    public ConfigObject getConfig() {
        return object;
    }

}
