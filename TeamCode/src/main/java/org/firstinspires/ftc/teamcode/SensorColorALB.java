package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import java.util.Locale;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
@Disabled
@Autonomous(name = "SensorColorALB")// Comment this out to add to the opmode list
public class SensorColorALB extends LinearOpMode {
    private DcMotor ColectorE = null;
    private DcMotor ColectorV = null;
    private Servo LegoStanga1 = null;
    private  Servo LegoStanga2 = null;
    private Servo LegoDreapta1 = null;
    private  Servo LegoDreapta2 = null;
    public DcMotor MotorDF = null;
    public DcMotor MotorSF = null;
    public DcMotor MotorDJ = null;
    public DcMotor MotorSJ = null;
    double ok=0,k=0,nuvede=0;
    BNO055IMU imu;
    Orientation angles;
    ColorSensor sensorColorS;

    ///DistanceSensor sensorDistance;

    @Override
    public void runOpMode() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        //am configurat gyroul integrat si i-am definit unitatea de masura


        MotorDF = hardwareMap.dcMotor.get("Tright_drive");
        MotorDJ = hardwareMap.dcMotor.get("Bright_drive");
        MotorSF = hardwareMap.dcMotor.get("Tleft_drive");
        MotorSJ = hardwareMap.dcMotor.get("Bleft_drive");
        ColectorE = hardwareMap.get(DcMotor.class, "ColectorE");
        ColectorV = hardwareMap.get(DcMotor.class, "ColectorV");

        MotorSJ.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorSF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorDJ.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorDF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //am regasit printe elementele robotului conectate le hub cele 4 motoare


        MotorSF.setDirection(DcMotorSimple.Direction.REVERSE);
        MotorSJ.setDirection(DcMotorSimple.Direction.REVERSE);
        //am declarat ca motoarele de pe partea stanga se vormisca in sens invers

        MotorSF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorSJ.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorDF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorDJ.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        MotorSF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorSJ.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorDF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorDJ.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // get a reference to the color sensor.
        sensorColorS = hardwareMap.get(ColorSensor.class, "SensorColorS");

        ColectorE = hardwareMap.get(DcMotor.class, "ColectorE");
        ColectorV = hardwareMap.get(DcMotor.class, "ColectorV");
        LegoStanga1 = hardwareMap.servo.get("LegoStanga1");
        LegoStanga2 = hardwareMap.servo.get("LegoStanga2");
        LegoDreapta1 = hardwareMap.servo.get("LegoDreapta1");
        LegoDreapta2 = hardwareMap.servo.get("LegoDreapta2");

        // get a reference to the distance sensor that shares the same name.
        ///sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_color_distance");

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        // sometimes it helps to multiply the raw RGB values with a scale factor
        // to amplify/attentuate the measured values.
        final double SCALE_FACTOR = 255;

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        //am identificat gyroul integrat printre componentele robotului


        // wait for the start button to be pressed.
        waitForStart();

        // loop and read the RGB and distance data.
        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
        while (opModeIsActive()) {
            // convert the RGB values to HSV values.
            // multiply by the SCALE_FACTOR.
            // then cast it back to int (SCALE_FACTOR is a double)
            Color.RGBToHSV((int) (sensorColorS.red() * SCALE_FACTOR),
                    (int) (sensorColorS.green() * SCALE_FACTOR),
                    (int) (sensorColorS.blue() * SCALE_FACTOR),
                    hsvValues);

            // send the info back to driver station using telemetry function.
            // telemetry.addData("Distance (cm)",
            //             String.format(Locale.US, "%.02f", sensorDistance.getDistance(DistanceUnit.CM)));
            //telemetry.addData("Alpha", sensorColorD.alpha());























            Diagonala(0.3, -2910);
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

            LegoDreapta1.setPosition(0.5); //se lasa ala la caramida
            sleep(500);
            LegoDreapta2.setPosition(0.65); //se lasa ala mare lung
            sleep(500);

            Fata(0.3, -200);
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
            telemetry.addData("Red  ", sensorColorS.red());
            telemetry.addData("Green", sensorColorS.green());
            telemetry.addData("Blue ", sensorColorS.blue());

            double r,g,b;
            r=sensorColorS.red();
            g=sensorColorS.green();
            b=sensorColorS.blue();
            if((r*g)/(b*b)<=3)//sau <=2 depinde de senzor
            { LegoDreapta1.setPosition(-0.68);//agata caramida
                sleep(1000);
                //LegoStanga2.setPosition(0.5); //se ridica
                //sleep(2000);
                ok=1;
            }
            if(ok==1) {
                nuvede=1;
                Fata(0.3, 500);
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

                Laterala(0.8, -5400);
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

                LegoDreapta1.setPosition(0.7);
                sleep(200);
                LegoDreapta2.setPosition(0.4);//bratul mare care se ridica!!!!!;
                sleep(500);
                LegoDreapta1.setPosition(-0.7);
                sleep(200);

                Laterala(0.5, 3500);
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

                LegoDreapta1.setPosition(0.5); //se lasa ala la caramida
                sleep(500);
                LegoDreapta2.setPosition(0.7); //se lasa ala mare lung
                sleep(500);

                Fata(0.3, -700);
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

                LegoDreapta1.setPosition(-0.7); //se lasa
                sleep(500);

                Fata(0.5, 550);
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
                //sleep(200);

                Laterala(0.8, -3600);
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
                //sleep(200);

                LegoDreapta1.setPosition(0.7);
                sleep(200);
                LegoDreapta2.setPosition(0.4);//bratul mare care se ridica!!!!!;
                sleep(500);
                LegoDreapta1.setPosition(-0.7);
                sleep(200);

               // sleep(100);
                Laterala(0.6, 1000);
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                break;
            }
























            Fata(0.5, 50);
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

            Laterala(0.5, -560);
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

            telemetry.addData("Red  ", sensorColorS.red());
            telemetry.addData("Green", sensorColorS.green());
            telemetry.addData("Blue ", sensorColorS.blue());
            telemetry.addData("Hue", hsvValues[0]);

            double r1,g1,b1;
            r1=sensorColorS.red();
            g1=sensorColorS.green();
            b1=sensorColorS.blue();
            if((r1*g1)/(b1*b1)<=3)//sau <=2 depinde de senzor
            { LegoDreapta1.setPosition(-0.7);//agata caramida
                sleep(1000);
                //LegoStanga2.setPosition(0.5); //se ridica
                //sleep(2000);
                k=1;

            }

            if(k==1)
            {nuvede=1;
                Fata(0.3, 500);
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                Laterala(0.8, -4550);
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
                sleep(100);

                LegoDreapta1.setPosition(0.7);
                sleep(200);
                LegoDreapta2.setPosition(0.4);//bratul mare care se ridica!!!!!;
                sleep(500);
                LegoDreapta1.setPosition(-0.7);
                sleep(200);

                Laterala(0.5, 2850);
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
                sleep(100);

                LegoDreapta1.setPosition(0.5); //se lasa ala la caramida
                sleep(500);
                LegoDreapta2.setPosition(0.7); //se lasa ala mare lung
                sleep(500);

                Fata(0.3, -750);
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
                sleep(100);
                LegoDreapta1.setPosition(-0.7); //se lasa
                sleep(300);
                Fata(0.5, 650);
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
                sleep(100);

                Laterala(0.8, -3200);
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

                LegoDreapta1.setPosition(0.7);
                sleep(200);
                LegoDreapta2.setPosition(0.4);//bratul mare care se ridica!!!!!;
                sleep(500);
                LegoDreapta1.setPosition(-0.7);
                sleep(200);

                Laterala(0.7, 1500);
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                break;
            }

















            if(nuvede==0) {
                Laterala(0.5, -500);
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                LegoDreapta1.setPosition(-0.7);//agata caramida
                sleep(500);

                Fata(0.3, 430);
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                Laterala(0.8, -4310);
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

                LegoDreapta1.setPosition(0.7);
                sleep(200);
                LegoDreapta2.setPosition(0.4);//bratul mare care se ridica!!!!!;
                sleep(500);
                LegoDreapta1.setPosition(-0.7);
                sleep(200);

                Laterala(0.5, 2500);
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

                LegoDreapta1.setPosition(0.5); //se lasa ala la caramida
                sleep(500);
                LegoDreapta2.setPosition(0.7); //se lasa ala mare lung
                sleep(500);

                Fata(0.3, -850);
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

                LegoDreapta1.setPosition(-0.7); //se lasa
                sleep(600);

                Fata(0.5, 650);
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
                sleep(100);

                Laterala(0.8, -2800);
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
                sleep(100);

                LegoDreapta1.setPosition(0.7);
                sleep(200);
                LegoDreapta2.setPosition(0.4);//bratul mare care se ridica!!!!!;
                sleep(500);
                LegoDreapta1.setPosition(-0.7);
                sleep(200);

                Laterala(0.7, 1300);
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                Diagonala(0.3, -310);
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
                break;
            }









            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });

            telemetry.update();
        }

        // Set the panel back to the default color
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.WHITE);
            }
        });
    }
    public void Fata(double p,int t){
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
        if((t-MotorDF.getCurrentPosition()<t-150) &&(t-MotorDJ.getCurrentPosition()<t-150) && (t-MotorSF.getCurrentPosition()<t-150)&&(t-MotorSJ.getCurrentPosition()<t-150))
        {p=0;
            MotorDF.setPower(0);
            MotorDF.setPower(0);
            MotorDF.setPower(0);
            MotorDF.setPower(0);
        }
    }
    public void Diagonala(double p,int t){
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
        MotorSJ.setPower(0);
        MotorDF.setPower(0);
        MotorDJ.setPower(p);
    }
    public void Laterala(double p,int t){
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
        if(t>0) {
            if ((t - MotorDF.getCurrentPosition() > (t - 150)) && (t - MotorDJ.getCurrentPosition() < (t - 150)) && (t - MotorSF.getCurrentPosition() < (t - 150)) && (t - MotorSJ.getCurrentPosition() > (t - 150))) {
                p = 0;
                MotorDF.setPower(0);
                MotorDF.setPower(0);
                MotorDF.setPower(0);
                MotorDF.setPower(0);
            }
        }
        if(t<0) {
            if ((t - MotorDF.getCurrentPosition() < (t - 150)) && (t - MotorDJ.getCurrentPosition() > (t - 150)) && (t - MotorSF.getCurrentPosition() > (t - 150)) && (t - MotorSJ.getCurrentPosition() < (t - 150))) {
                p = 0;
                MotorDF.setPower(0);
                MotorDF.setPower(0);
                MotorDF.setPower(0);
                MotorDF.setPower(0);
            }
        }
    }
}