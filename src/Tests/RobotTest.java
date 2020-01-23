package Tests;

import gameClient.Robot;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import utils.Point3D;

import static org.junit.Assert.*;

public class RobotTest {
    static JSONObject str;

    static {
        try {
            str = new JSONObject("{\"src\":9,\"pos\":\"35.19597880064568,32.10154696638656,0.0\",\"id\":0,\"dest\":-1,\"value\":0,\"speed\":1}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static Robot r;

    static {
        try {
            r = new Robot(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public RobotTest() throws JSONException {
    }

    @Test
    public void getSource() {
        assertEquals(r.getSource(),9);
    }

    @Test
    public void getDest() {
        assertEquals(r.getDest(),-1);
    }

    @Test
    public void getKey() {
        assertEquals(r.getKey(),0);
    }

    @Test
    public void getLocation() {
        assertEquals(r.getLocation(),new Point3D("35.19597880064568,32.10154696638656,0.0"));
    }

    @Test
    public void getSpeed() {
        assertEquals(r.getSpeed(),1,1);
    }

    @Test
    public void getScore() {
        assertEquals(r.getScore(),0,0);
    }

   }