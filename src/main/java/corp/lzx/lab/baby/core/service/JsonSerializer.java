package corp.lzx.lab.baby.core.service;

import com.google.gson.Gson;

public class JsonSerializer {

  private static JsonSerializer instance = new JsonSerializer();
  private final Gson gson = new Gson();

  private JsonSerializer() {
  }

  private static synchronized JsonSerializer instance() {
    if (instance == null) {
      instance = new JsonSerializer();
    }
    return instance;
  }

  public static String toJson(Object obj) {
    var json = instance().gson.toJson(obj);
    return json == null ? "{}" : json;
  }

  public static <T> T fromJson(String json, Class<T> clazz) {
    return instance().gson.fromJson(json, clazz);
  }
}
