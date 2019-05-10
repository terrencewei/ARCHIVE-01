package io.aaxis.core.assets.imageupload.util;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonNull;

/**
 * The utils to process json
 *
 * Created by terrence on 2019/03/29.
 */
public class JsonUtils {

    private static final Gson   GSON  = new GsonBuilder().create();
    private static       String SPACE = "    ";



    /**
     * convert json array string to list
     *
     * @param jsonArr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> fromJsonArray(String jsonArr, Class<T[]> clazz) {
        if (StringUtils.isBlank(jsonArr) || clazz == null) {
            return null;
        }
        T[] arr = GSON.fromJson(jsonArr, clazz);
        return Arrays.asList(arr);
    }



    /**
     * convert object to json
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return GSON.toJson(JsonNull.INSTANCE);
        }
        return GSON.toJson(obj);
    }



    /**
     * parse json to object
     *
     * @param json
     * @param classOfT
     * @return
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return GSON.fromJson(json, classOfT);
    }



    /**
     * format the json string
     *
     * @param pObj
     * @return
     */
    public static String formatJson(Object pObj) {
        return formatJson(toJson(pObj));
    }



    /**
     * format the json string
     *
     * @param json
     * @return
     */
    public static String formatJson(String json) {
        if (StringUtils.isBlank(json)) {
            return "null";
        }
        StringBuffer result = new StringBuffer();

        int length = json.length();
        int number = 0;
        char key = 0;

        for (int i = 0; i < length; i++) {
            key = json.charAt(i);

            if ((key == '[') || (key == '{')) {
                if ((i - 1 > 0) && (json.charAt(i - 1) == ':')) {
                    result.append('\n');
                    result.append(indent(number));
                }

                result.append(key);

                result.append('\n');

                number++;
                result.append(indent(number));

                continue;
            }

            if ((key == ']') || (key == '}')) {
                result.append('\n');

                number--;
                result.append(indent(number));

                result.append(key);

                if (((i + 1) < length) && (json.charAt(i + 1) != ',')) {
                    result.append('\n');
                }

                continue;
            }

            if ((key == ',')) {
                result.append(key);
                result.append('\n');
                result.append(indent(number));
                continue;
            }

            result.append(key);
        }

        return result.toString();
    }



    private static String indent(int number) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < number; i++) {
            result.append(SPACE);
        }
        return result.toString();
    }

}