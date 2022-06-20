package ru.hogwarts.school;

import org.json.JSONException;
import org.json.JSONObject;
import ru.hogwarts.school.model.Faculty;

public class TestConstants {
    public static String DEFAULTURL;
    public final static String DEFAULTREQUESTPARAMETERSTUDENTID = "?studentId=100";
    public final static String DEFAULTREQUESTPARAMETERBYAGE = "/filter/by-age?studentAge=33";
    public final static String DEFAULTREQUESTPARAMETERBETWEENAGES = "/filter/by-age?minAge=18&maxAge=40";
    public final static String DEFAULTREQUESTPARAMETERFACULTYID = "/filter/by-faculty?facultyId=100";

    public final static Long ID_DEFAULT = 1L;
    public final static String NAME_DEFAULT = "ANewOne";
    public final static String COLOR_DEFAULT = "Green";
    public final static Faculty FACULTY_DEFAULT = new Faculty(ID_DEFAULT, NAME_DEFAULT, COLOR_DEFAULT);
    public final static JSONObject facultyObject = new JSONObject();

    static {
        try {
            facultyObject.put("id", ID_DEFAULT);
            facultyObject.put("name", NAME_DEFAULT);
            facultyObject.put("color", COLOR_DEFAULT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
