package jna;

import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.util.List;

public class Window {

    public List<DesktopWindow> getAllWindows() {
        return WindowUtils.getAllWindows(true);
    }

    // Getting parent window
    public WinDef.HWND getSpecifiedWindow(String windowTitle) {
        return User32.INSTANCE.FindWindow(null, windowTitle);
    }
}
