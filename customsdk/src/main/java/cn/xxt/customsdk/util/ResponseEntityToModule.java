package cn.xxt.customsdk.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

public class ResponseEntityToModule {

    /**
     * 将JSONObject转换成指定的对象
     * @param jsonObject
     * @param clazz
     * @return
     */
    public static Object parseJsonObjectToModule(JSONObject jsonObject, Class<?> clazz) {
        Object moduleObject = null;
        try {
            moduleObject = clazz.newInstance();
            setFieldValue(moduleObject, jsonObject, clazz);
        } catch (Exception e) {

        }


        return moduleObject;
    }


    private static void setFieldValue(Object moduleObject, JSONObject jsonObject, Class<?> clazz)
            throws JSONException, IllegalAccessException, InstantiationException{
        if (null != clazz.getSuperclass()) {
            setFieldValue(moduleObject, jsonObject, clazz.getSuperclass());
        }

        Field[] fields = clazz.getDeclaredFields();
        Class<?> cls;
        String name;
        for (Field f : fields) {
            f.setAccessible(true);
            cls = f.getType();
            name = f.getName();
            if (!jsonObject.has(name) || jsonObject.isNull(name)) {
                continue;
            }
            if (cls.isPrimitive() || isWrappedPrimitive(cls)) {
                setPrimitiveFieldValue(f, moduleObject, jsonObject.get(name));
            } else {
                if (cls.isAssignableFrom(String.class)) {
                    f.set(moduleObject, String.valueOf(jsonObject.get(name)));
                } else if (cls.isAssignableFrom(ArrayList.class)) {
                    parseJsonArrayToList(f, name, moduleObject, jsonObject);
                } else if (cls.isAssignableFrom(RealmList.class)) {
                    parseJsonArrayToRealmList(f, name, moduleObject, jsonObject);
                } else {
                    Object obj = parseJsonObjectToModule(jsonObject.getJSONObject(name), cls.newInstance().getClass());
                    f.set(moduleObject, obj);
                }
            }
        }

    }

    private static boolean isWrappedPrimitive(Class<?> type) {
        if (type.getName().equals(Boolean.class.getName()) || type.getName().equals(Byte.class.getName())
                || type.getName().equals(Character.class.getName()) || type.getName().equals(Short.class.getName())
                || type.getName().equals(Integer.class.getName()) || type.getName().equals(Long.class.getName())
                || type.getName().equals(Float.class.getName()) || type.getName().equals(Double.class.getName())) {
            return true;
        }

        return false;
    }


    private static void setPrimitiveFieldValue(Field f, Object moduleObj, Object jsonObj) throws
            IllegalAccessException {
        if (f.getType().isAssignableFrom(jsonObj.getClass())) {
            f.set(moduleObj, jsonObj);
        } else {
            f.set(moduleObj, makeTypeSafeValue(f.getType(), jsonObj.toString()));
        }
    }

    private static final Object makeTypeSafeValue(Class<?> type, String value) throws IllegalArgumentException{
        if (int.class == type || Integer.class == type) {
            return Integer.parseInt(value);
        } else if (long.class == type || Long.class == type) {
            return Long.parseLong(value);
        } else if (short.class == type || Short.class == type) {
            return Short.parseShort(value);
        } else if (char.class == type || Character.class == type) {
            return value.charAt(0);
        } else if (byte.class == type || Byte.class == type) {
            return Byte.valueOf(value);
        } else if (float.class == type || Float.class == type) {
            return Float.parseFloat(value);
        } else if (double.class == type || Double.class == type) {
            return Double.parseDouble(value);
        } else if (boolean.class == type || Boolean.class == type) {
            return Boolean.valueOf(value);
        } else {
            return value;
        }
    }


    private static ArrayList<Object> parseJsonArrayToList(Field field, String fieldName, Object moduleObj,
                                                          JSONObject jsonObject) throws JSONException ,
    IllegalAccessException{
        ArrayList<Object> objList = new ArrayList<>();
        Type fc = field.getGenericType();
        if (fc instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) fc;
            if (pt.getActualTypeArguments()[0] instanceof Class) {
                Class<?> clazz = (Class<?>)pt.getActualTypeArguments()[0];

                if (jsonObject.get(fieldName) instanceof JSONArray) {
                    JSONArray array = jsonObject.getJSONArray(fieldName);
                    for (int i = 0; i < array.length(); i++) {
                        if (array.get(i) instanceof JSONObject) {
                            objList.add(parseJsonObjectToModule(array.getJSONObject(i), clazz));
                        } else {
                            if (clazz.isAssignableFrom(array.get(i).getClass())) {
                                objList.add(array.get(i));
                            }
                        }
                    }
                }
                field.set(moduleObj, objList);
            }
        }

        return objList;
    }


    private static RealmList<RealmObject> parseJsonArrayToRealmList(Field field, String fieldName,
                                                                    Object moduleObj, JSONObject jsonObj)
        throws JSONException, IllegalAccessException {
        RealmList<RealmObject> objList = new RealmList<>();
        Type fc = field.getGenericType();
        if (fc instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) fc;
            if (pt.getActualTypeArguments()[0] instanceof Class) {
                Class clazz = (Class<?>)pt.getActualTypeArguments()[0];

                if (jsonObj.get(fieldName) instanceof JSONArray) {
                    JSONArray array = jsonObj.getJSONArray(fieldName);
                    for (int i = 0; i < array.length(); i++) {
                        if (array.get(i) instanceof JSONObject) {
                            objList.add(parseJsonObjectToRealmModel(array.getJSONObject(i), clazz));
                        } else {
                            if (clazz.isAssignableFrom(array.get(i).getClass())) {
                                objList.add((RealmObject) array.get(i));
                            }
                        }
                    }
                }
                field.set(moduleObj, objList);
            }
        }

        return objList;
    }


    public static RealmObject parseJsonObjectToRealmModel(JSONObject jsonObj, Class<?> clazz) {
        RealmObject moduleObj = null;

        try {
            moduleObj = (RealmObject) clazz.newInstance();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return moduleObj;
    }

}
