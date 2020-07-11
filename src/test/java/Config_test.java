import Config.GUIConfigs;

import java.awt.*;

public class Config_test {
    GUIConfigs guiConfigs=new GUIConfigs();
    public Config_test() {
        Rectangle newRec = guiConfigs.getBounds("login.properties","menuBounds" );
        System.out.println(newRec.toString());
    }

    public static void main(String[] args) {
        new Config_test();
    }
}