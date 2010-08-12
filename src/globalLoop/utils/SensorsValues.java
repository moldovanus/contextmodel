package globalLoop.utils;

import model.impl.util.ModelAccess;
import model.interfaces.resources.Sensor;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Aug 9, 2010
 * Time: 12:29:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class SensorsValues implements Serializable {

    private Map<String, Double> myMap;

    public SensorsValues() {
        myMap = new HashMap<String, Double>();
    }

    public SensorsValues(ModelAccess modelAccess) {
        myMap = new HashMap<String, Double>();
        Collection<Sensor> sensors = modelAccess.getAllSensorInstances();
        for (Sensor sensor : sensors) {
            myMap.put(sensor.getLocalName(), new Double(sensor.getRecordedValue()));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SensorsValues other = (SensorsValues) obj;

        return myMap.equals(other.getMyMap());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.myMap != null ? this.myMap.hashCode() : 0);
        return hash;
    }

    public Double getValue(String sensorName) {
        return myMap.get(sensorName);
    }

    public void setValue(String sensorName, Double value) {
        myMap.put(sensorName, value);
    }

    public Map<String, Double> getMyMap() {
        return myMap;
    }

    public void setMyMap(Map<String, Double> myMap) {
        this.myMap = myMap;
    }

    public boolean equals(SensorsValues values) {
        return myMap.equals(values.getMyMap());
    }

    @Override
    public String toString() {
        String stringValue = "";
        for (String key : myMap.keySet()) {
            stringValue += "[ " + key + ": " + myMap.get(key) + "]  ";
        }
        return stringValue;
    }


//    public ArrayList<String> toLogMessage() {
//        ArrayList<String> list = new ArrayList<String>();
//        String stringValue = "";
//        Map<String,Map<String,String>> map = GlobalVars.valueMapping;
//        for (String key : myMap.keySet()) {
//            list.add("[ " + key + ": " + myMap.get(key) + "]  ");
//        }
//        return list;
//    }
//
//    public ArrayList<String[]> toArrayList() {
//        ArrayList<String[]> list = new ArrayList<String[]>();
//        Map<String, Map<String, String>> mapping = GlobalVars.valueMapping;
//
//        for (String key : myMap.keySet()) {
//            String[] entry = new String[2];
//            entry[0] = key;
//            Map<String, String> valueMapping = mapping.get(key);
//            if (valueMapping == null) {
//                entry[1] = "" + myMap.get(key);
//            } else {
//                entry[1] = valueMapping.get("" + myMap.get(key));
//            }
//            list.add(entry);
//        }
//        return list;
//    }
}
