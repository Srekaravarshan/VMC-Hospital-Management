package hospital.java.helpers;

import hospital.java.models.Patient;

import java.util.ArrayList;
import java.util.HashMap;

public class HelperMethods {

    public static String getKeyValueString(ArrayList<String> keys, ArrayList<String> values) {
        StringBuilder string = new StringBuilder();
        for (int i=0; i<keys.size(); i++) {
            if (values.get(i).trim().isEmpty())
                string.append(keys.get(i)).append("\n");
            else
                string.append(keys.get(i)).append(": ").append(values.get(i)).append("\n");
        }
        return string.toString();
    }

    public static void getKeysValues(ArrayList<String> keys, ArrayList<String> values, String primary, HashMap<String, String> additional, String field) {
        keys.clear();
        values.clear();

        keys.add(primary);
        additional.forEach((k, v) -> keys.add(k));

        values.addAll(Patient.getValueString(field, additional));
    }
}
