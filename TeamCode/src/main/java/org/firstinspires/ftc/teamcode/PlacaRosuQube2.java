package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Disabled
@Autonomous(name = "PlacaRosuQube2")
public class PlacaRosuQube2 extends LinearOpMode {

    //arrays of motors and servos
    DcMotor[] motors = null;
    Servo[] servos = null;

    //motors
    DcMotor MotorDF = null;
    DcMotor MotorSF = null;
    DcMotor MotorDJ = null;
    DcMotor MotorSJ = null;

    //servoMotors
    Servo Servomotor1 = null;
    Servo Servomotor2 = null;
    Servo ServomotorE = null;
    Servo ServomotorV = null;

    //Gyro
    BNO055IMU imu;

    Orientation angles;

    //Simple Singleton example
    BNO055IMU.Parameters parameters;
    BNO055IMU.Parameters getParameters() {
        if (parameters == null) {
            parameters = new BNO055IMU.Parameters();
            parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        }

        return parameters;
    }

    @Override
    public void runOpMode() {
        initMotors();
        stopMotors();
        initServo();
        setDownMotorsReverse();

        setMotorsMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        intiIMU();

        displayData(new DisplayData[]{new DisplayData("Mode","Waiting")});

        waitForStart();
        displayData(new DisplayData[]{new DisplayData("Mode","Running")});

        runProgram();
    }

    private void runProgram() {
        Servomotor2.setPosition(1);

        goSideWays();
        goFromWallToPlate();
        goBackToWall();
        goToParkingSpot();
    }

    /**
     * Inits the components used
     */

    void initMotors() {
        MotorDF = hardwareMap.dcMotor.get("Tright_drive");
        MotorDJ = hardwareMap.dcMotor.get("Bright_drive");
        MotorSF = hardwareMap.dcMotor.get("Tleft_drive");
        MotorSJ = hardwareMap.dcMotor.get("Bleft_drive");

        motors = new DcMotor[]{
                MotorDF,
                MotorDJ,
                MotorSF,
                MotorSJ
        };
    }

    void initServo() {
        Servomotor1 = hardwareMap.servo.get("Servo1");
        Servomotor2 = hardwareMap.servo.get("Servo2");
        ServomotorE = hardwareMap.servo.get("Servo_spateE");
        ServomotorV = hardwareMap.servo.get("Servo_spateV");

        servos = new Servo[]{
                Servomotor1,
                Servomotor2,
                ServomotorE,
                ServomotorV
        };
    }

    private void intiIMU() {
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(getParameters());
    }


    /**
     * Motor actions
     */

    void stopMotors() {
        for (DcMotor current : motors) {
            current.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

    void setMotorsPower(double power){
        for (DcMotor current : motors) {
            current.setPower(power);
        }
    }

    void setDownMotorsReverse(){
        MotorSF.setDirection(DcMotorSimple.Direction.REVERSE);
        MotorSJ.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private void setMotorsMode(DcMotor.RunMode modeToSet) {
        for (DcMotor current : motors) {
            current.setMode(modeToSet);
        }
    }

    private boolean motorsBusy() {
        return  MotorSF.isBusy()
                && MotorSJ.isBusy()
                && MotorDF.isBusy()
                && MotorDJ.isBusy();
    }


    /**
     *  Deplasari complexe
     */

    private void goToParkingSpot() {
        Parcare(0.3, 1650);
        //trebuie deplasat 74 de cm din est catre vest
        while (opModeIsActive() && !isStopRequested() && motorsBusy()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            displayTelemetryAngles();
        }
        setMotorsPower(0);
        sleep(500);
        Catretava1(0.3, 100);

        while (opModeIsActive() && !isStopRequested() && motorsBusy()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            displayTelemetryAngles();
        }
        setMotorsPower(0);
        sleep(500);

        Parcare(0.3, 1650);
        //trebuie deplasat 74 de cm din est catre vest
        while (opModeIsActive() && !isStopRequested() && motorsBusy()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            displayTelemetryAngles();
        }
        setMotorsPower(0);
        sleep(500);
    }

    private void goBackToWall() {
        Catretava1(0.3, -1990);
        while (opModeIsActive() && !isStopRequested() && motorsBusy()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            displayTelemetryAngles();
        }
        setMotorsPower(0);
        sleep(500);

        ServomotorE.setPosition(0.2);
        ServomotorV.setPosition(0.2);

        sleep(500);
    }

    private void goFromWallToPlate() {
        Catretava1(0.3, 2100);
        while (opModeIsActive()
                && !isStopRequested()
                && motorsBusy()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            displayTelemetryAngles();
        }
        setMotorsPower(0);
        sleep(500);

        ServomotorE.setPosition(0.5);
        ServomotorV.setPosition(0.5);
        sleep(1000);
    }

    private void goSideWays() {
        Laterala1(0.3, 600);
        //trebuie deplasat 74 de cm din est catre vest
        while (opModeIsActive()
                && !isStopRequested()
                && motorsBusy()) {

            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            displayTelemetryAngles();
        }
        setMotorsPower(0);
        sleep(500);
    }


    /** Deplasari simple
     *
     */

    public void Laterala1(double p, int t) {
        setMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        MotorSF.setTargetPosition(t);
        MotorSJ.setTargetPosition(-t);
        MotorDF.setTargetPosition(-t);
        MotorDJ.setTargetPosition(t);

        setMotorsMode(DcMotor.RunMode.RUN_TO_POSITION);

        setMotorsPower(p);
    }

    public void Catretava1(double p, int t) {
        setMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        MotorSF.setTargetPosition(t);
        MotorSJ.setTargetPosition(t);
        MotorDF.setTargetPosition(t);
        MotorDJ.setTargetPosition(t);

        setMotorsMode(DcMotor.RunMode.RUN_TO_POSITION);

        setMotorsPower(p);
    }

    public void Parcare(double p, int t) {
        setMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        MotorSF.setTargetPosition(-t);
        MotorSJ.setTargetPosition(t);
        MotorDF.setTargetPosition(t);
        MotorDJ.setTargetPosition(-t);

        setMotorsMode(DcMotor.RunMode.RUN_TO_POSITION);

        setMotorsPower(p);
    }


    /**
     * Used to display Telemetry data
     */


    //Class used to display multiple sets of telemetry data
    class DisplayData{
        String mode;
        Object data;

        public DisplayData(String mode, Object data) {
            this.mode = mode;
            this.data = data;
        }
    }

    private void displayTelemetryAngles() {
        displayData(new DisplayData[]{
                new DisplayData("Heading:",angles.firstAngle),
                new DisplayData("Roll:",angles.secondAngle),
                new DisplayData("Pitch",angles.thirdAngle)
        });
    }

    private void displayData(DisplayData[] data) {
        for (DisplayData current : data)
            telemetry.addData(current.mode, current.data);

        telemetry.update();
    }
}