package hospital.java.models;

import java.util.prefs.Preferences;

public class SharedPref {
    private final Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
    String ID1 = "IPAddress";

    private SharedPref() {}

    public static SharedPref instance = new SharedPref();

    public void setPreference(String ipAddress) {
        prefs.put(ID1, ipAddress);
    }

    public String getPreference () {
        return prefs.get(ID1, "0.0.0.0");
    }

    public boolean hasIP () {
        return !getPreference().equals("0.0.0.0");
    }
}
