package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
@Disabled
@Autonomous(name="Auto", group="Autonom")
public class Auto extends LinearOpMode {
    BNO055IMU imu;
    Orientation angles;

    DcMotor TleftDrive = null;
    DcMotor BleftDrive = null;
    DcMotor TrightDrive = null;
    DcMotor BrightDrive = null;
    DcMotor Outtake6000 = null;
    DcMotor Outtake1150 = null;
    DcMotor Intake = null;
    Servo ServoStack = null;
    Servo ServoIntake = null;
    double  power   = 1;
    //int nrLansari = 3;
    @Override
    public void runOpMode() throws InterruptedException {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;

        TleftDrive = hardwareMap.get(DcMotor.class, "Tleft_drive");
        TrightDrive = hardwareMap.get(DcMotor.class, "Tright_drive");
        BleftDrive = hardwareMap.get(DcMotor.class, "Bleft_drive");
        BrightDrive = hardwareMap.get(DcMotor.class, "Bright_drive");
        Intake = hardwareMap.get(DcMotor.class, "Intake");
        Outtake6000 = hardwareMap.get(DcMotor.class, "Outtake6000");
        Outtake1150 = hardwareMap.get(DcMotor.class, "Outtake1150");
        ServoStack = hardwareMap.servo.get("ServoStack");
        ServoIntake = hardwareMap.servo.get("ServoIntake");

        TleftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        TrightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BrightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BleftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Outtake6000.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Outtake1150.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        TleftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        BleftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        Outtake6000.setDirection(DcMotorSimple.Direction.REVERSE);

        TleftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        TrightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BleftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BrightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        TrightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BrightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        TleftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BleftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        waitForStart();
        telemetry.addData("Mode", "running");
        telemetry.update();
            // ServoIntake.setPosition(1);
        Outtake1150.setPower(0.8);
        Outtake6000.setPower(0.8);
        TleftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        TrightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BleftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BrightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        TrightDrive.setTargetPosition(-10);
        TleftDrive.setTargetPosition(-10);
        BrightDrive.setTargetPosition(-10);
        BleftDrive.setTargetPosition(-10);

        TleftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        TrightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BleftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BrightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        TrightDrive.setPower(0.1);
        TleftDrive.setPower(0.1);
        BrightDrive.setPower(0.1);
        BleftDrive.setPower(0.1);
           while (TleftDrive.isBusy() && TrightDrive.isBusy() && BrightDrive.isBusy() && BleftDrive.isBusy()) {
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                telemetry.addData("Heading: ", angles.firstAngle);
                telemetry.addData("Roll: ", angles.secondAngle);
                telemetry.addData("Pitch", angles.thirdAngle);
                telemetry.update();
            }
            TrightDrive.setPower(0);
            TleftDrive.setPower(0);
            BleftDrive.setPower(0);
            BrightDrive.setPower(0);
            sleep(500);
            ServoIntake.setPosition(1);
            sleep(500);
            int NrLansari;
            for (NrLansari = 3; NrLansari>=0; NrLansari--) {
                ServoStack.setPosition(1);
                ServoStack.setPosition(0);
                //nrLansari--;
            }
            sleep(100);

          /* Fata(1, 466);
           while (opModeIsActive() && !isStopRequested() &&
                    TleftDrive.isBusy() &&
                    TrightDrive.isBusy() &&
                    BrightDrive.isBusy() &&
                    BleftDrive.isBusy()) {
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                telemetry.addData("Heading: ", angles.firstAngle);
                telemetry.addData("Roll: ", angles.secondAngle);
                telemetry.addData("Pitch", angles.thirdAngle);
                telemetry.update();
            }


            TrightDrive.setPower(0);
            TleftDrive.setPower(0);
            BleftDrive.setPower(0);
            BrightDrive.setPower(0);
            Outtake1150.setPower(0);
            Outtake6000.setPower(0);
            sleep(10000);

           */
    }

    public void Fata(double p,int t) {

    }
}
