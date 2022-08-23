import javax.swing.*;
import java.awt.*;

public class ModWarn {
    private ModWarn() {}
    // This will be removed by proguard
    private static final String WARNING =
            "This file is not meant to be run.\n" +
            "Please place this file in your mods folder.\n" +
            "If you're looking to install Fabric, get the Fabric Installer from https://fabricmc.net/use";

    public static void main(String[] args)
            throws Throwable /* Unnecessary to handle exceptions */ {
        if (GraphicsEnvironment.isHeadless()) {
            System.err.println(WARNING);
            return;
        }
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JOptionPane.showMessageDialog(null, WARNING);
    }
}
