package com.groot.base.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchData {
    private final Map<String, Object> params = new HashMap<>();

    public SearchData() {

    }

    public Boolean isEmpty() {
        return this.params.isEmpty();
    }

    public Boolean hasKey(String key) {
        return this.params.containsKey(key);
    }

    public Boolean getBooleanValue(String key) {
        Object o = params.get(key);
        if (o == null) return null;
        if (o instanceof Boolean) {
            return (Boolean) o;
        } else {
            return Boolean.valueOf(String.valueOf(o));
        }
    }

    public Integer getIntegerValue(String key) {
        Object o = params.get(key);
        if (o == null) return null;
        if (o instanceof Integer) {
            return (Integer) o;
        } else {
            return Integer.valueOf(String.valueOf(o));
        }
    }

    public Long getLongValue(String key) {
        Object o = params.get(key);
        if (o == null) return null;
        if (o instanceof Long) {
            return (Long) o;
        } else {
            return Long.valueOf(String.valueOf(o));
        }
    }

    public Float getFloatValue(String key) {
        Object o = params.get(key);
        if (o == null) return null;
        if (o instanceof Float) {
            return (Float) o;
        } else {
            return Float.valueOf(String.valueOf(o));
        }
    }

    public Double getDoubleValue(String key) {
        Object o = params.get(key);
        if (o == null) return null;
        if (o instanceof Double) {
            return (Double) o;
        } else {
            return Double.valueOf(String.valueOf(o));
        }
    }

    public String getStringValue(String key) {
        Object o = params.get(key);
        return o == null ? null : String.valueOf(o);
    }

    public String[] getStringArrayValue(String key) {
        Object o = params.get(key);
        if (o.getClass().isArray()) {
            return (String[]) o;
        } else if (!List.class.isAssignableFrom(o.getClass())) {
            return new String[]{String.valueOf(o)};
        } else {
            List<Object> list = (List<Object>) o;
            String[] arr = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                arr[i] = (String) list.get(i);
            }
            return arr;
        }
    }

    public Long[] getLongArrayValue(String key) {
        Object o = params.get(key);
        if (o.getClass().isArray()) {
            return (Long[]) o;
        } else if (!List.class.isAssignableFrom(o.getClass())) {
            return new Long[]{Long.valueOf(String.valueOf(o))};
        } else {
            List<Object> list = (List<Object>) o;
            Long[] arr = new Long[list.size()];
            for (int i = 0; i < list.size(); i++) {
                arr[i] = (Long) list.get(i);
            }
            return arr;
        }
    }

    public Date getDateValue(String key) {
        Object o = params.get(key);
        long timestamp;
        if (o instanceof Long) {
            timestamp = (Long) o;
        } else {
            timestamp = Long.parseLong(String.valueOf(o));
        }

        return new Date(timestamp);
    }


    public void put(final String key, final Object value) {
        this.params.put(key, value);
    }

    @Override
    public String toString() {
        return "SearchData{" +
                "params=" + params +
                '}';
    }
}
