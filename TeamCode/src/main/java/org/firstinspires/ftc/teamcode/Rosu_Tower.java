package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import java.util.Locale;

/**
 * This 2020-2021 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the Ultimate Goal game elements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@Autonomous(name = "Rosu_Tower", group = "Concept")

public class Rosu_Tower extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";
    public DcMotor MotorDF = null;
    public DcMotor MotorSF = null;
    public DcMotor MotorDJ = null;
    public DcMotor MotorSJ = null;

    public DcMotor Outtake1=null;
    public DcMotor Outtake2=null;

    public Servo Stack=null;

    private Servo BratWobble = null;//1
    private Servo GrabWobble = null;
    private Servo ServoIntake = null;

    private DcMotor Intake = null;

    double TurnPower=0.2;
    float zAccumulated;
    int ok=0;
    double K=0;
    BNO055IMU imu;
    Orientation angles;
    Acceleration gravity;

    private static final String VUFORIA_KEY ="AaiEi8H/////AAABmUg36Q3ybkmOvEqXksHr6N5dWN3sFtoGtoxOnRdvJSW4Vx+lYcfJoNzlqaCnpnrJIKrMylKBfA0yWL3ZphMFUbAILPe0P+Lvd7ZlAqEeYAxGhOccZYp/M2WlxA6gfEORxOajiDFadVwwhvXfI5sWf1Q+avgZB9MJKUmNzqUv8m5vln0nrF4DJBAsc4P/dH4+1czeh8yfBfvssufhYoLr/wTLzLHpzm1xkz71ieM0XasBKWbcNCt4A4ZfCh5JeK87bQVgyQEtUQqpKuedUsv0UExvkCrC4frfABzSqk5t8l13p0nNqOG3zffkltbd0zoWlBcO0Ii0sHuCnU8gHnuRZL+KJCGX+pfExqqAk6U3p0jx";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {

        MotorDF = hardwareMap.dcMotor.get("Tright_drive");
        MotorDJ = hardwareMap.dcMotor.get("Bright_drive");
        MotorSF = hardwareMap.dcMotor.get("Tleft_drive");
        MotorSJ = hardwareMap.dcMotor.get("Bleft_drive");

        Outtake1 = hardwareMap.dcMotor.get("Outtake1150");
        Outtake2 = hardwareMap.dcMotor.get("Outtake6000");

        BratWobble = hardwareMap.get(Servo.class, "BratWobble");
        GrabWobble = hardwareMap.get(Servo.class, "GrabWobble");

        Stack = hardwareMap.servo.get("ServoStack");
        ServoIntake = hardwareMap.servo.get("ServoIntake");
        Intake = hardwareMap.get(DcMotor.class,"Intake");


        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        MotorSJ.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorSF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorDJ.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorDF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //am regasit printe elementele robotului conectate le hub cele 4 motoare

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
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        // Set up our telemetry dashboard
        composeTelemetry();

        // Wait until we're told to go

        // Start the logging of measured acceleration
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
        initVuforia();
        initTfod();

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 1.78 or 16/9).

            // Uncomment the following line if you want to adjust the magnification and/or the aspect ratio of the input images.
            tfod.setZoom(1.78, 3);//1.5
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();
        double X=0;
        if (opModeIsActive())
        {
            while (opModeIsActive()) {///bucla infinita!
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());// nr/string
                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        for (Recognition recognition : updatedRecognitions) {///!
                            telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                            if (recognition.getLabel() == "Quad") //QUAD
                            {
                                K = 4;
                                break;
                            } else if (recognition.getLabel() == "Single")///SINGLE
                            {
                                K = 1;
                                break;
                            }
                            telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                    recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                    recognition.getRight(), recognition.getBottom());
                            ////break;!!!!
                        }
                        telemetry.update();
                    }
                }
                X++;
                if(X==2)
                    break;
            }
            if (tfod != null) {
                tfod.shutdown();
            }


            GrabWobble.setPosition(1);//strangere
            sleep(10000);
            Deplasare();
            Fata(0.9, 400);
            Laterala(0.9, 1200);
            Rotire_GyroLELv12(0);
            Rotire_GyroLELv1(0);
            ServoIntake.setPosition(-1);
            sleep(300);
/*
            ///right down left up
            if (K == 4) {
                Fata(0.9, 3900);

                // Laterala(0.9, -2500);///2000
                //trebuie deplasat 74 de cm din est catre vest

                BratWobble.setPosition(1);///coborare
                sleep(1500);
                GrabWobble.setPosition(-1);///desfacere
                sleep(500);
                BratWobble.setPosition(-1);///ridicare
                sleep(500);
                GrabWobble.setPosition(1);//strangere
                sleep(500);

                Fata(0.9, -3400);


            }
            if (K == 1) {
                ServoIntake.setPosition(1);
                sleep(300);

                Fata(1, 1700);

                Laterala(1, -1200);///2000
                //trebuie deplasat 74 de cm din est catre vest


                BratWobble.setPosition(1);///coborare
                sleep(1500);
                GrabWobble.setPosition(-1);///desfacere
                sleep(500);

                Laterala(1, 800);///2000
                //trebuie deplasat 74 de cm din est catre vest

                //Rotire_GyroLELv12(0);
                //Rotire_GyroLELv1(0);
                //SPATE
                Fata(1, -1000);

                Laterala(1, -1500);///2000
                //trebuie deplasat 74 de cm din est catre vest


                Intake.setPower(0.6);

                Fata(1, -3500);


                Rotire_GyroLELv12(0);
                Rotire_GyroLELv1(0);

                GrabWobble.setPosition(1);//strangere
                sleep(500);

                //BratWobble.setPosition(0.7);
                //sleep(500);

                Fata(1, 2600);
                //Rotire_GyroLELv12(0);
                //Rotire_GyroLELv1(0);

                Stack.setPosition(0.5);
                sleep(500);
                Stack.setPosition(-0.2);
                sleep(500);

                Rotire_GyroLELv12(0);
                Rotire_GyroLELv1(0);

                Laterala(1, 1200);///2000
                //trebuie deplasat 74 de cm din est catre vest

                Fata(1, 2200);

                Rotire_GyroLELv12(0);
                Rotire_GyroLELv1(0);
                Laterala(1, 200);///2000
                //trebuie deplasat 74 de cm din est catre vest
                //Rotire_GyroLELv12(0);
                //Rotire_GyroLELv1(0);
                GrabWobble.setPosition(-1);///desfacere
                sleep(500);;

                Fata(1, -900);

                ///sleep(500);
                Laterala(1, -500);///2000
                //trebuie deplasat 74 de cm din est catre vest

            }
            if (K == 0) {
                //LATERALA3

                //Rotire_GyroLELv12(0);
                Fata(0.9, 500);

               // Laterala(0.9, -2500);///2000
                //trebuie deplasat 74 de cm din est catre vest

                BratWobble.setPosition(1);///coborare
                sleep(1500);
                GrabWobble.setPosition(-1);///desfacere
                sleep(500);
                BratWobble.setPosition(-1);///ridicare
                sleep(500);
                GrabWobble.setPosition(1);//strangere
                sleep(500);

/*
                Laterala(0.6, 900);///2000
                //trebuie deplasat 74 de cm din est catre vest

                //SPATE


                ///Rotire_GyroLELv12(0);

                Fata(1, -3000);

                BratWobble.setPosition(1);///coborare
                sleep(1000);
                GrabWobble.setPosition(-1);///desfacere
                sleep(500);


                //Rotire_GyroLELv12(0);

                Fata(0.4, -500);

                GrabWobble.setPosition(1);//strangere
                sleep(1000);
                BratWobble.setPosition(-1);///ridicare
                sleep(1000);


                // Rotire_GyroLELv12(0);

                Fata(1, 3350);

                Laterala(1, -500);///2000
                //trebuie deplasat 74 de cm din est catre vest

                BratWobble.setPosition(1);///coborare
                sleep(1000);
                GrabWobble.setPosition(-1);///desfacere
                sleep(500);


                Laterala(1, 500);///2000
                //trebuie deplasat 74 de cm din est catre vest
                GrabWobble.setPosition(1);//strangere
                sleep(1000);
                BratWobble.setPosition(-1);///ridicare
                sleep(1000);

            }
        */
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.7f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
    ///CLASELE:
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

    public void Diag(double p,int t){
        MotorSF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorSJ.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorDF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorDJ.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        MotorSF.setTargetPosition(-t);
        MotorSJ.setTargetPosition(0);
        MotorDF.setTargetPosition(0);
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

    public void Deplasare()
    {
        Outtake2.setPower(-0.85);
        Outtake1.setPower(0.85);
        Fata(0.9, 3900);

        /*ServoIntake.setPosition(-1);
        sleep(300);
        Stack.setPosition(0.5);
        sleep(500);
        Stack.setPosition(-0.2);
        sleep(500);

         */


        //LATERALA1
        Laterala(0.9, -900);
        //trebuie deplasat 74 de cm din est catre vest

       /* //LATERALA2
        Laterala(0.9, 600);
        //trebuie deplasat 74 de cm din est catre vest

        */

        Stack.setPosition(0.5);
        sleep(250);
        Stack.setPosition(-0.2);
        sleep(250);
        Stack.setPosition(0.5);
        sleep(250);
        Stack.setPosition(-0.2);
        sleep(250);
        Stack.setPosition(0.5);
        sleep(250);
        Stack.setPosition(-0.2);
        sleep(250);
        Stack.setPosition(0.5);
        sleep(250);
        Stack.setPosition(-0.2);
        sleep(250);
        Stack.setPosition(0.5);
        sleep(250);
        Stack.setPosition(-0.2);
        sleep(250);
        Stack.setPosition(0.5);
        sleep(250);
        Stack.setPosition(-0.2);
        sleep(250);
        Stack.setPosition(0.5);
        sleep(250);
        Stack.setPosition(-0.2);
        sleep(250);
        Stack.setPosition(0.5);
        sleep(250);
        Stack.setPosition(-0.2);
        sleep(250);


    }
    public void Rotire_GyroLELv1(double target)///rotire la stanga
    {
        while((target-angles.firstAngle)>4)
        {

            MotorDF.setPower(-TurnPower);
            MotorDJ.setPower(-TurnPower);
            MotorSF.setPower(TurnPower);
            MotorSJ.setPower(TurnPower);
            telemetry.addData(" zacc:",angles.firstAngle);
            telemetry.addData(" target", target);
            telemetry.update();
        }
    }
    public void Rotire_GyroLELv12(double target)///rotire la dreapta
    {
        while((target-angles.firstAngle)<-4)
        {

            MotorDF.setPower(-TurnPower);
            MotorDJ.setPower(-TurnPower);
            MotorSF.setPower(TurnPower);
            MotorSJ.setPower(TurnPower);
            telemetry.addData(" zacc:",angles.firstAngle);
            telemetry.addData(" target", target);
            telemetry.update();
        }
    }

    void composeTelemetry() {

        // At the beginning of each telemetry update, grab a bunch of data
        // from the IMU that we will then display in separate lines.
        telemetry.addAction(new Runnable() { @Override public void run()
        {
            // Acquiring the angles is relatively expensive; we don't want
            // to do that in each of the three items that need that info, as that's
            // three times the necessary expense.
            angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            gravity  = imu.getGravity();
        }
        });

        telemetry.addLine()
                .addData("status", new Func<String>() {
                    @Override public String value() {
                        return imu.getSystemStatus().toShortString();
                    }
                })
                .addData("calib", new Func<String>() {
                    @Override public String value() {
                        return imu.getCalibrationStatus().toString();
                    }
                });

        telemetry.addLine()
                .addData("heading", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.firstAngle);
                    }
                })
                .addData("roll", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.secondAngle);
                    }
                })
                .addData("pitch", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.thirdAngle);
                    }
                });

        telemetry.addLine()
                .addData("grvty", new Func<String>() {
                    @Override public String value() {
                        return gravity.toString();
                    }
                })
                .addData("mag", new Func<String>() {
                    @Override public String value() {
                        return String.format(Locale.getDefault(), "%.3f",
                                Math.sqrt(gravity.xAccel*gravity.xAccel
                                        + gravity.yAccel*gravity.yAccel
                                        + gravity.zAccel*gravity.zAccel));
                    }
                });
        //double varx=(double).formatAngle(angles.angleUnit, angles.secondAngle);

    }

    //----------------------------------------------------------------------------------------------
    // Formatting
    //----------------------------------------------------------------------------------------------

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
}