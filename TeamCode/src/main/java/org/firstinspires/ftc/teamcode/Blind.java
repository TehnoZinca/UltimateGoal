package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Disabled
@Autonomous(name= "ORB TEST")
//program cu a doua extragere scurta
public class Blind extends LinearOpMode {

    public DcMotor MotorDF = null;
    public DcMotor MotorSF = null;
    public DcMotor MotorDJ = null;
    public DcMotor MotorSJ = null;

    private DcMotor Intake = null;

    public DcMotor Outtake1=null;
    public DcMotor Outtake2=null;

    public Servo Stack=null;
    // public Servo Servomotor1 = null;
    // public Servo Servomotor2 = null;
    BNO055IMU imu;
    Orientation angles;

    @Override
    public void runOpMode() {

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        //am configurat gyroul integrat si i-am definit unitatea de masura

        MotorDF = hardwareMap.dcMotor.get("Tright_drive");
        MotorDJ = hardwareMap.dcMotor.get("Bright_drive");
        MotorSF = hardwareMap.dcMotor.get("Tleft_drive");
        MotorSJ = hardwareMap.dcMotor.get("Bleft_drive");

        Outtake1 = hardwareMap.dcMotor.get("Outtake1150");
        Outtake2 = hardwareMap.dcMotor.get("Outtake6000");

        Intake = hardwareMap.get(DcMotor.class,"Intake");

        MotorSJ.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorSF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorDJ.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorDF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Stack = hardwareMap.servo.get("ServoStack");
        //am regasit printe elementele robotului conectate le hub cele 4 motoare

//        Servomotor1 = hardwareMap.servo.get("Servo1");
        //       Servomotor2 = hardwareMap.servo.get("Servo2");
        //servomotorul
        MotorDF.setDirection(DcMotorSimple.Direction.REVERSE);
        MotorDJ.setDirection(DcMotorSimple.Direction.REVERSE);
        //am declarat ca motoarele de pe partea stanga se vormisca in sens invers

        MotorSF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorSJ.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorDF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorDJ.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        MotorSF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorSJ.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorDF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorDJ.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //se reseteaza valorile encoderelor pentru cele doua motoare de stanga la 0

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        //am identificat gyroul integrat printre componentele robotului


        telemetry.addData("Mode", "Waiting");
        telemetry.update();
        //am actuaizat datele din telemetrie

        waitForStart();
        telemetry.addData("Mode", "running");
        telemetry.update();

        //FATA
        Outtake2.setPower(-0.5);
        Outtake1.setPower(0.6);
        Fata(0.9,3500);
        while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("Heading: ", angles.firstAngle);
            telemetry.addData("Roll: ", angles.secondAngle);
            telemetry.addData("Pitch", angles.thirdAngle);
            telemetry.update();
        }
        MotorSF.setPower(0);
        MotorSJ.setPower(0);
        MotorDF.setPower(0);
        MotorDJ.setPower(0);
        sleep(200);

        Stack.setPosition(0.8);
        sleep(1000);
        Stack.setPosition(-0.1);
        sleep(1000);

        //LATERALA1
        Laterala(0.5,600);
        //trebuie deplasat 74 de cm din est catre vest
        while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("Heading: ", angles.firstAngle);
            telemetry.addData("Roll: ", angles.secondAngle);
            telemetry.addData("Pitch", angles.thirdAngle);
            telemetry.update();
        }
        MotorSF.setPower(0);
        MotorSJ.setPower(0);
        MotorDF.setPower(0);
        MotorDJ.setPower(0);
        sleep(500);

        Stack.setPosition(0.8);
        sleep(1000);
        Stack.setPosition(-0.1);
        sleep(1000);

        //LATERALA2
        Laterala(0.5,600);
        //trebuie deplasat 74 de cm din est catre vest
        while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("Heading: ", angles.firstAngle);
            telemetry.addData("Roll: ", angles.secondAngle);
            telemetry.addData("Pitch", angles.thirdAngle);
            telemetry.update();
        }
        MotorSF.setPower(0);
        MotorSJ.setPower(0);
        MotorDF.setPower(0);
        MotorDJ.setPower(0);
        sleep(200);

        Stack.setPosition(0.8);
        sleep(1000);
        Stack.setPosition(-0.1);
        sleep(1000);

        //LATERALA3
        Intake.setPower(0.6);
        Laterala(0.5,-2100);
        //trebuie deplasat 74 de cm din est catre vest
        while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("Heading: ", angles.firstAngle);
            telemetry.addData("Roll: ", angles.secondAngle);
            telemetry.addData("Pitch", angles.thirdAngle);
            telemetry.update();
        }
        MotorSF.setPower(0);
        MotorSJ.setPower(0);
        MotorDF.setPower(0);
        MotorDJ.setPower(0);
        sleep(500);
        //SPATE

        Fata(0.4,-1800);
        while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("Heading: ", angles.firstAngle);
            telemetry.addData("Roll: ", angles.secondAngle);
            telemetry.addData("Pitch", angles.thirdAngle);
            telemetry.update();
        }
        MotorSF.setPower(0);
        MotorSJ.setPower(0);
        MotorDF.setPower(0);
        MotorDJ.setPower(0);
        sleep(1000);

        Fata(0.9,1100);
        while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("Heading: ", angles.firstAngle);
            telemetry.addData("Roll: ", angles.secondAngle);
            telemetry.addData("Pitch", angles.thirdAngle);
            telemetry.update();
        }
        MotorSF.setPower(0);
        MotorSJ.setPower(0);
        MotorDF.setPower(0);
        MotorDJ.setPower(0);
        sleep(200);


        Stack.setPosition(0.7);
        sleep(1000);
        Stack.setPosition(-0.7);
        sleep(1000);

        Stack.setPosition(0.7);
        sleep(1000);
        Stack.setPosition(-0.7);
        sleep(1000);

        Stack.setPosition(0.7);
        sleep(200);
        Stack.setPosition(-0.7);
        sleep(200);

        Fata(0.9,1300);
        while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("Heading: ", angles.firstAngle);
            telemetry.addData("Roll: ", angles.secondAngle);
            telemetry.addData("Pitch", angles.thirdAngle);
            telemetry.update();
        }


    }

    public void Laterala(double p,int t){
        MotorSF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorSJ.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorDF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorDJ.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        MotorSF.setTargetPosition(-t);
        MotorSJ.setTargetPosition(t);
        MotorDF.setTargetPosition(t);
        MotorDJ.setTargetPosition(-t);

        MotorSF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorSJ.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorDF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorDJ.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        MotorSF.setPower(p);
        MotorSJ.setPower(p);
        MotorDF.setPower(p);
        MotorDJ.setPower(p);
    }

    public void LateralaInapoi(double p,int t){
        MotorSF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorSJ.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorDF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorDJ.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        MotorSF.setTargetPosition(t);
        MotorSJ.setTargetPosition(-t);
        MotorDF.setTargetPosition(-t);
        MotorDJ.setTargetPosition(t);

        MotorSF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorSJ.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorDF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorDJ.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        MotorSF.setPower(p);
        MotorSJ.setPower(p);
        MotorDF.setPower(p);
        MotorDJ.setPower(p);
    }

    public void Fata(double p,int t){
        MotorSF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorSJ.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorDF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorDJ.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        MotorSF.setTargetPosition(t);
        MotorSJ.setTargetPosition(t);
        MotorDF.setTargetPosition(t);
        MotorDJ.setTargetPosition(t);

        MotorSF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorSJ.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorDF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorDJ.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        MotorSF.setPower(p);
        MotorSJ.setPower(p);
        MotorDF.setPower(p);
        MotorDJ.setPower(p);
    }

    public void Parcare(double p,int t){
        MotorSF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorSJ.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorDF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorDJ.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        MotorSF.setTargetPosition(-t);
        MotorSJ.setTargetPosition(-t);
        MotorDF.setTargetPosition(-t);
        MotorDJ.setTargetPosition(-t);

        MotorSF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorSJ.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorDF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorDJ.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        MotorSF.setPower(p);
        MotorSJ.setPower(p);
        MotorDF.setPower(p);
        MotorDJ.setPower(p);
    }

    public void Spate(double p,int t){
        MotorSF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorSJ.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorDF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorDJ.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        MotorSF.setTargetPosition(-t);
        MotorSJ.setTargetPosition(-t);
        MotorDF.setTargetPosition(-t);
        MotorDJ.setTargetPosition(-t);

        MotorSF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorSJ.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorDF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorDJ.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        MotorSF.setPower(p);
        MotorSJ.setPower(p);
        MotorDF.setPower(p);
        MotorDJ.setPower(p);
    }

    public void Aproape(double p,int t){
        MotorSF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorSJ.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorDF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorDJ.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        MotorSF.setTargetPosition(-t);
        MotorSJ.setTargetPosition(t);
        MotorDF.setTargetPosition(t);
        MotorDJ.setTargetPosition(-t);

        MotorSF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorSJ.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorDF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        MotorDJ.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        MotorSF.setPower(p);
        MotorSJ.setPower(p);
        MotorDF.setPower(p);
        MotorDJ.setPower(p);
    }
}