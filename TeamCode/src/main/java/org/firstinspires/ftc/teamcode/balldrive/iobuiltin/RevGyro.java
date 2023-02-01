package org.firstinspires.ftc.teamcode.balldrive.iobuiltin;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Axis;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.balldrive.OpModeExtended;
import org.firstinspires.ftc.teamcode.balldrive.structure.SensorInterface;
import org.firstinspires.ftc.teamcode.balldrive.structure.Setting;

public class RevGyro implements SensorInterface {

    private static final double OFFSET = 180;

    private BNO055IMU imu;

    private String gravity;
    private String status;
    private double heading = 0;
    private double roll = 0;
    private double pitch = 0;
    private double mag = 0;

    private Telemetry telemetry;
    private HardwareMap hardwareMap;

    private double adjust = 0;

    private final static double SCALE = 440;

    /**
     * Indicates which axis to return rotational values from
     * X - roll
     * Y - pitch
     * Z - heading
     * see https://goo.gl/AnKWEn
     */
    private Axis primaryAxis;

    public RevGyro(OpModeExtended context, Axis primaryAxis) {
        this.hardwareMap = context.hardwareMap;
        this.telemetry = context.telemetry;
        this.primaryAxis = primaryAxis;
    }

    /**
     * A constructor which defaults to the Z axis (heading) for the gyro
     * @param context
     */
    public RevGyro(OpModeExtended context) {
        this(context, Axis.Z);
    }

    @Override
    public void init() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
// Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
// on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
// and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, getConfigName());
        imu.initialize(parameters);
    }

    public void setAdjust(double adjust) {
        this.adjust = adjust;
    }

    /**
     * Updates all gyro values.
     */
    @Override
    public void update() {
        updateAngles();
        updateGravMag();
        updateStatus();
    }

    /**
     * Gets the value from the gyro in one of its axes
     * @return The angle around the preferred axis, unbounded
     */
    @Override
    public double getRawValue() {
        switch (primaryAxis) {
            case X:
                return updateRoll();
            case Y:
                return updatePitch();
            case Z:
                return updateHeading();
        }
        return -1;
    }

    /**
     * Gets the last recorded value from the gyro in one of its axes
     * @return The angle around the preferred axis, unbounded
     */
    public double getRawValueNoUpdate() {
        switch (primaryAxis) {
            case X:
                return getRoll();
            case Y:
                return getPitch();
            case Z:
                return getHeading();
        }
        return -1;
    }

    /**
     * Brings the gyro value between -180 and 180
     * @return Angle around the preferred axis, in (-180, 180]
     */
    @Override
    public double getCMValue() {
        return sum;
    }

    private double oldValue;
    private double sum;

    private double angleDiff(double angle1, double angle2) {
        double diff = angle1 - angle2;
        //Accounts for if you go from -180 degrees to 180 degrees
        // which is only a difference of one degree,
        // but the bot thinks that's 359 degree difference
        while (diff < -90) {
            diff += 180;
        } while (diff > 90) {
            diff -= 180;
        }
        telemetry.addData("diff", diff);
        return diff;
    }

    /**
     * Updates heading, roll, and pitch
     */
    public void updateAngles() {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        heading = angles.firstAngle + adjust;
        roll = angles.secondAngle + adjust;
        pitch = angles.thirdAngle + adjust;

        double newValue = getRawValueNoUpdate();
        sum += angleDiff(oldValue, newValue);
        telemetry.addData("sum", sum);
        oldValue = newValue;
    }

    public double rawHeading() {
        double head = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        while (head > 180) {
            head -= 360;
        }
        while (head < -180) {
            head += 360;
        }
        return head;
    }

    public double getAdjust() {
        return adjust;
    }


    /**
     * Updates status
     */
    public void updateStatus() {
        status = imu.getSystemStatus().toShortString();
    }

    /**
     * Updates gravity and mag
     */
    public void updateGravMag() {
        Acceleration grav = imu.getGravity();

        gravity = grav.toString();
        mag = Math.sqrt(grav.xAccel * grav.xAccel + grav.yAccel * grav.yAccel + grav.zAccel * grav.zAccel);
    }

    /**
     * Updates the heading value.
     */
    public double updateHeading() {
        updateAngles();
        return heading;
    }

    /**
     * Returns the last read heading value
     * @return Heading
     */
    public double getHeading() {
        return heading;
    }

    public double updatePitch() {
        updateAngles();
        return pitch;
    }

    public double getPitch() {
        return pitch;
    }

    public double updateRoll() {
        updateAngles();
        return roll;
    }

    public double getRoll() {
        return roll;
    }

    private double formatAngle(double oldAngle) {
        double newAngle = Math.round(oldAngle / SCALE) + OFFSET;
        newAngle = ((newAngle % 360) + 360) % 360;
        return newAngle;
    }


    public double getMag() {
        return mag;
    }

    public String getGravity() {
        return gravity;
    }

    /**
     * System Status Codes:
     * Result  Meaning
     * 0       idle
     * 1       system error
     * 2       initializing peripherals
     * 3       system initialization
     * 4       executing self-test
     * 5       sensor fusion algorithm running
     * 6       system running without fusion algorithms
     * @return The gyro's current status
     */
    public int getStatus() {
        return Integer.parseInt(status);
    }

    @Override
    public String getConfigName() {
        return "imu";
    }
}
