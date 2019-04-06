package jna;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

public class Mouse {


    public static final int MOUSEEVENTF_MOVE = 1;
    public static final int MOUSEEVENTF_LEFTDOWN = 2;
    public static final int MOUSEEVENTF_LEFTUP = 4;
    public static final int MOUSEEVENTF_RIGHTDOWN = 8;
    public static final int MOUSEEVENTF_RIGHTUP = 16;
    public static final int MOUSEEVENTF_MIDDLEDOWN = 32;
    public static final int MOUSEEVENTF_MIDDLEUP = 64;
    public static final int MOUSEEVENTF_WHEEL = 2048;

    // Sends a left-click input
    public static void mouseLeftClick() {
        mouseButton(MOUSEEVENTF_LEFTDOWN);
        mouseButton(MOUSEEVENTF_LEFTUP);
    }

    // Sends a right-click input
    public static void mouseRightClick() {
        mouseButton(MOUSEEVENTF_RIGHTDOWN);
        mouseButton(MOUSEEVENTF_RIGHTUP);
    }

    // Sends a middle-click input
    public static void mouseMiddleClick() {
        mouseButton(MOUSEEVENTF_MIDDLEDOWN);
        mouseButton(MOUSEEVENTF_MIDDLEUP);
    }


    // Moves the mouse relative to it's current position.
    public static void moveMouse(int x, int y) {
        WinUser.INPUT input = new WinUser.INPUT();

        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_MOUSE);
        input.input.setType("mi");

        input.input.mi.dx = new WinDef.LONG(x);
        input.input.mi.dy = new WinDef.LONG(y);

        input.input.mi.time = new WinDef.DWORD(0);
        input.input.mi.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
        input.input.mi.dwFlags = new WinDef.DWORD(MOUSEEVENTF_MOVE);
        User32.INSTANCE.SendInput(new WinDef.DWORD(1), new WinUser.INPUT[] { input }, input.size());
    }

    // "action" - action for a specific mouse button
    private static void mouseButton(int action) {
        WinUser.INPUT input = new WinUser.INPUT();

        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_MOUSE);
        input.input.setType("mi");

        input.input.mi.time = new WinDef.DWORD(0);
        input.input.mi.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
        input.input.mi.dwFlags = new WinDef.DWORD(action);
        User32.INSTANCE.SendInput(new WinDef.DWORD(1), new WinUser.INPUT[] { input }, input.size());
    }
}
