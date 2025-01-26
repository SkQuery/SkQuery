package com.skquery.skquery.skript;

import com.skquery.skquery.util.BiValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public final class SerializableDefinitions implements Serializable {

    private static final long serialVersionUID = 0L;
    private final HashSet<String> effectList = new HashSet<>();
    private final HashMap<String, BiValue<Object, Boolean>> expressionList = new HashMap<>();
    private final HashMap<BiValue<String, String>, BiValue<Object, Boolean>> propertyList = new HashMap<>();
    private final HashMap<String, ArrayList<BiValue<String, BiValue<Object, Boolean>>>> structureList = new HashMap<>();

    public void declareEffect(String name) {
        effectList.add(name);
    }

    public void declareExpression(String name, Object returnType, boolean loopable) {
        expressionList.put(name, new BiValue<>(returnType, loopable));
    }

    public void declareProperty(String target, String name, Object returnType, boolean loopable) {
        propertyList.put(new BiValue<>(name, target), new BiValue<>(returnType, loopable));
    }

    public void declareStructure(String name) {
        structureList.put(name, new ArrayList<BiValue<String, BiValue<Object, Boolean>>>());
    }

    public void bindProperty(String struct, String name, Object returnType, boolean loopable) {
        assert structureList.containsKey(struct);
        structureList.get(struct).add(new BiValue<>(name, new BiValue<>(returnType, loopable)));
    }

    public HashSet<String> getEffectList() {
        return effectList;
    }

    public HashMap<String, BiValue<Object, Boolean>> getExpressionList() {
        return expressionList;
    }

    public HashMap<BiValue<String, String>, BiValue<Object, Boolean>> getPropertyList() {
        return propertyList;
    }

    public HashMap<String, ArrayList<BiValue<String, BiValue<Object, Boolean>>>> getStructureList() {
        return structureList;
    }
}
