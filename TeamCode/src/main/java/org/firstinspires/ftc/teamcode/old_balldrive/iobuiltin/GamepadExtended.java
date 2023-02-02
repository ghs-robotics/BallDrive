package org.firstinspires.ftc.teamcode.old_balldrive.iobuiltin;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamepadExtended {
    public Gamepad gamepad;

    public ButtonState a, b, x, y;
    public ButtonState dpad_right, dpad_up, dpad_left, dpad_down;
    public ButtonState back, guide, start;
    public ButtonState left_stick_button, right_stick_button;
    public ButtonState left_bumper, right_bumper;

    public float left_stick_x, left_stick_y;
    public float right_stick_x, right_stick_y;
    public float left_trigger, right_trigger;

    public final static double DEADZONE = .05;

    public enum ButtonState {
        UP, DOWN, UPPING, DOWNING
    }

    public GamepadExtended(Gamepad gamepad) {
        this.gamepad = gamepad;
    }

    public void update() {
        a = newButtonState(a, gamepad.a);
        b = newButtonState(b, gamepad.b);
        x = newButtonState(x, gamepad.x);
        y = newButtonState(y, gamepad.y);

        dpad_right = newButtonState(dpad_right, gamepad.dpad_right);
        dpad_up    = newButtonState(dpad_up,    gamepad.dpad_up);
        dpad_left  = newButtonState(dpad_left,  gamepad.dpad_left);
        dpad_down  = newButtonState(dpad_down,  gamepad.dpad_down);

        back  = newButtonState(back,  gamepad.back);
        guide = newButtonState(guide, gamepad.guide);
        start = newButtonState(start, gamepad.start);

        left_stick_button  = newButtonState(left_stick_button,  gamepad.left_stick_button);
        right_stick_button = newButtonState(right_stick_button, gamepad.right_stick_button);

        left_bumper  = newButtonState(left_bumper,  gamepad.left_bumper);
        right_bumper = newButtonState(right_bumper, gamepad.right_bumper);

        left_stick_x = gamepad.left_stick_x;
        left_stick_y = -gamepad.left_stick_y;

        right_stick_x = gamepad.right_stick_x;
        right_stick_y = -gamepad.right_stick_y;

        left_trigger  = gamepad.left_trigger;
        right_trigger = gamepad.right_trigger;
    }

    public ButtonState newButtonState(ButtonState buttonState, boolean b) {
        if (buttonState == null)
            return b ? ButtonState.DOWN : ButtonState.UP;

        switch (buttonState) {
            case DOWN: case DOWNING:
                return b ? ButtonState.DOWN : ButtonState.UPPING;
            case UP: case UPPING:
                return b ? ButtonState.DOWNING : ButtonState.UP;
        }
        return null;
    }

    public boolean down(ButtonState buttonState) {
        return buttonState == ButtonState.DOWN || buttonState == ButtonState.DOWNING;
    }

    public boolean up(ButtonState buttonState) {
        return buttonState == ButtonState.UP || buttonState == ButtonState.UPPING;
    }
}
