package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@Autonomous
public class AutoTimp extends LinearOpMode {
    DcMotor TleftDrive = null;
    DcMotor BleftDrive = null;
    DcMotor TrightDrive = null;
    DcMotor BrightDrive = null;
    DcMotor Outtake6000 = null;
    DcMotor Outtake1150 = null;
    DcMotor Intake = null;
    Servo ServoStack = null;
    Servo ServoIntake = null;

    @Override
    public void runOpMode() throws InterruptedException {
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

        waitForStart();
        TrightDrive.setPower(-0.5);
        TleftDrive.setPower(-0.5);
        BleftDrive.setPower(-0.5);
        BrightDrive.setPower(-0.5);
        telemetry.addData("target Tleft: ", TleftDrive.getCurrentPosition());
        telemetry.addData("target BLeft: ", BleftDrive.getCurrentPosition());
        telemetry.addData("target Tright: ", TrightDrive.getCurrentPosition());
        telemetry.addData("target Bright: ", BrightDrive.getCurrentPosition());
        sleep(1700);
        TrightDrive.setPower(0);
        TleftDrive.setPower(0);
        BleftDrive.setPower(0);
        BrightDrive.setPower(0);
        ServoIntake.setPosition(1);
        TrightDrive.setPower(-0.5);
        TleftDrive.setPower(-0.5);
        BleftDrive.setPower(-0.5);
        BrightDrive.setPower(-0.5);
        sleep(1000);
        telemetry.addData("target Tleft: ", TleftDrive.getCurrentPosition());
        telemetry.addData("target BLeft: ", BleftDrive.getCurrentPosition());
        telemetry.addData("target Tright: ", TrightDrive.getCurrentPosition());
        telemetry.addData("target Bright: ", BrightDrive.getCurrentPosition());
        TrightDrive.setPower(0);
        TleftDrive.setPower(0);
        BleftDrive.setPower(0);
        BrightDrive.setPower(0);
        Outtake1150.setPower(0.8);
        Outtake6000.setPower(0.8);
        sleep(700);
        int Lansare;
        for (Lansare = 5; Lansare >= 0; Lansare--) {
            ServoStack.setPosition(1);
            sleep(1000);
            ServoStack.setPosition(0.2);
            sleep(1000);
        }
        sleep(100);
        TrightDrive.setPower(-0.5);
        TleftDrive.setPower(-0.5);
        BleftDrive.setPower(-0.5);
        BrightDrive.setPower(-0.5);
        sleep(900);
        telemetry.addData("target Tleft: ", TleftDrive.getCurrentPosition());
        telemetry.addData("target BLeft: ", BleftDrive.getCurrentPosition());
        telemetry.addData("target Tright: ", TrightDrive.getCurrentPosition());
        telemetry.addData("target Bright: ", BrightDrive.getCurrentPosition());
        TrightDrive.setPower(0);
        TleftDrive.setPower(0);
        BleftDrive.setPower(0);
        BrightDrive.setPower(0);
    }
}
