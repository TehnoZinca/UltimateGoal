package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
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
@Autonomous(name = "AutTOWER", group = "Concept")
public class NewAutTOWER extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";
    public DcMotor MotorDF = null;
    public DcMotor MotorSF = null;
    public DcMotor MotorDJ = null;
    public DcMotor MotorSJ = null;
    private DcMotor Ridicare=null;

    private Servo ServoShot;

    public DcMotor Outtake1=null;
    public DcMotor Outtake2=null;

    public Servo Stack=null;

    private Servo BratWobble = null;//1
    private Servo GrabWobble = null;
    private Servo ServoIntake = null;

    private DcMotor Intake = null;

    double K=0;
    BNO055IMU imu;
    Orientation angles;

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

        Ridicare = hardwareMap.dcMotor.get("Ridicare");

        Outtake1 = hardwareMap.dcMotor.get("Outtake1150");
        Outtake2 = hardwareMap.dcMotor.get("Outtake6000");

        ServoShot = hardwareMap.get(Servo.class, "ServoShot");

        BratWobble = hardwareMap.get(Servo.class, "BratWobble");
        GrabWobble = hardwareMap.get(Servo.class, "GrabWobble");

        Stack = hardwareMap.servo.get("ServoStack");
        ServoIntake = hardwareMap.servo.get("ServoIntake");
        Intake = hardwareMap.get(DcMotor.class,"Intake");


        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        MotorSJ.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorSF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorDJ.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorDF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Ridicare.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
            tfod.setZoom(3, 1.78);//1.5
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

            ServoShot.setPosition(0.5);
            sleep(1000);

            Deplasare();
            ServoIntake.setPosition(0.5);
            sleep(500);
            if(K==0)
            {
                Fata(0.9, -1000);
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

                Laterala(0.9, 500);///2000
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

                BratWobble.setPosition(0.7);
                sleep(1000);
                GrabWobble.setPosition(0.5);
                sleep(1000);
                BratWobble.setPosition(-1);
                sleep(1000);

                Laterala(0.6, -900);///2000
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

                BratWobble.setPosition(0.7);
                sleep(800);

                Fata(1, 4300);
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

                Laterala(0.6, -300);///2000
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


                Fata(0.4, 500);
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

                GrabWobble.setPosition(-0.7);
                sleep(500);
                BratWobble.setPosition(0.7);
                sleep(500);

                Laterala(0.6, 100);///2000
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
                Fata(1, 3350);
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
                Laterala(1, -500);///2000
                //trebuie deplasat 74 de cm din est catre vest
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                BratWobble.setPosition(-1);
                sleep(500);
                GrabWobble.setPosition(0.5);
                sleep(500);
                BratWobble.setPosition(0.7);
                sleep(500);

                Laterala(1, 500);///2000
                //trebuie deplasat 74 de cm din est catre vest
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();

                }
            }
            if(K==1)
            {
                Laterala(0.9, 800);///2000
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

                Fata(0.9, -500);
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

                Laterala(0.9, -200);///2000
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

                BratWobble.setPosition(-1);
                sleep(500);
                GrabWobble.setPosition(0.5);
                sleep(500);
                BratWobble.setPosition(0.7);
                sleep(500);

                Laterala(0.9, 800);///2000
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
                Fata(0.9, -900);
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
                Laterala(0.6, -1500);///2000
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

                Intake.setPower(0.6);

                Fata(1, -4300);
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


                BratWobble.setPosition(-1);
                sleep(500);
                Fata(0.4, 500);
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

                GrabWobble.setPosition(-0.7);
                sleep(500);
                //BratWobble.setPosition(0.7);
                //sleep(500);

                Fata(1, 2900);
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }

                Ridicare.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                Vadimu(0.6,-940);
                while (opModeIsActive() && !isStopRequested() && Ridicare.isBusy() ) { }
                //Ridicare.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                Ridicare.setPower(0);
                sleep(1000);

                ServoShot.setPosition(0.5);
                sleep(500);
                ServoShot.setPosition(-0.5);

                Vadimu(0.6,940);
                while (opModeIsActive() && !isStopRequested() && Ridicare.isBusy() ) { }

                Laterala(1, 1000);///2000
                //trebuie deplasat 74 de cm din est catre vest
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                Fata(1, 1900);
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                Laterala(1, 200);///2000
                //trebuie deplasat 74 de cm din est catre vest
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                BratWobble.setPosition(-1);
                sleep(500);
                GrabWobble.setPosition(0.5);
                sleep(500);
                BratWobble.setPosition(0.7);
                sleep(500);
                Fata(1, -900);
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
                ///sleep(500);
                Laterala(1, -300);///2000
                //trebuie deplasat 74 de cm din est catre vest
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
            }
            else if(K==4)
            {
                Fata(0.9, -1700);
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

                Intake.setPower(0.7);

                Ridicare.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                Vadimu(0.6,-940);
                while (opModeIsActive() && !isStopRequested() && Ridicare.isBusy() ) { }
                //Ridicare.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                Ridicare.setPower(0);
                sleep(1000);

                ServoShot.setPosition(0.5);
                sleep(500);
                ServoShot.setPosition(-0.5);

                ServoShot.setPosition(0.5);
                sleep(700);
                ServoShot.setPosition(-0.5);

                ServoShot.setPosition(0.5);
                sleep(700);
                ServoShot.setPosition(-0.5);

                Vadimu(0.6,940);
                while (opModeIsActive() && !isStopRequested() && Ridicare.isBusy() ) { }

                Fata(0.9, 1400);
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

                Fata(0.9, 3500);
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }

                LateralaInapoi(0.4, 825);
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
                BratWobble.setPosition(-1);
                sleep(500);
                GrabWobble.setPosition(0.5);
                sleep(500);
                BratWobble.setPosition(0.7);
                sleep(500);
                LateralaInapoi(0.4, -825);
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
            /*if (K == 4) {
                //LATERALA3
                Laterala(0.9, 2100);///2000
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
                Fata(0.9, 1200);
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
                Intake.setPower(0.6);
                Fata(0.7, 250);
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

                Fata(0.9, -1050);
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

                Stack.setPosition(0.7);
                sleep(500);
                Stack.setPosition(-0.7);
                sleep(500);

                Stack.setPosition(0.7);
                sleep(500);
                Stack.setPosition(0.7);
                sleep(500);
                Stack.setPosition(-0.7);
                sleep(500);

                Stack.setPosition(0.7);
                sleep(500);
                Stack.setPosition(-0.7);
                sleep(500);

                Fata(0.9, -1700);
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

                Intake.setPower(0.7);
                Fata(0.9, 1400);
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
                sleep(500);
                Stack.setPosition(-0.7);
                sleep(500);

                Stack.setPosition(0.7);
                sleep(500);
                Stack.setPosition(-0.7);

                sleep(500);
                Stack.setPosition(0.7);
                sleep(500);
                Stack.setPosition(-0.7);
                sleep(500);

                Stack.setPosition(0.7);
                sleep(500);

                Fata(0.9, 3500);
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }

                LateralaInapoi(0.4, 825);
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
                BratWobble.setPosition(-1);
                sleep(500);
                GrabWobble.setPosition(0.5);
                sleep(500);
                BratWobble.setPosition(0.7);
                sleep(500);
                LateralaInapoi(0.4, -825);
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
            if (K == 1) {
                Fata(0.9, 1600);
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
                Laterala(0.9, -1200);///2000
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

                BratWobble.setPosition(-1);
                sleep(500);
                GrabWobble.setPosition(0.5);
                sleep(500);
                BratWobble.setPosition(0.7);
                sleep(500);

                Laterala(0.9, 800);///2000
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
                Fata(0.9, -900);
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
                Laterala(0.6, -1500);///2000
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

                Intake.setPower(0.6);

                Fata(1, -4300);
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


                BratWobble.setPosition(-1);
                sleep(500);
                Fata(0.4, 500);
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

                GrabWobble.setPosition(-0.7);
                sleep(500);
                //BratWobble.setPosition(0.7);
                //sleep(500);

                Fata(1, 2900);
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }

                Stack.setPosition(0.7);
                sleep(500);
                Stack.setPosition(-0.7);
                sleep(500);

                Laterala(1, 1000);///2000
                //trebuie deplasat 74 de cm din est catre vest
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                Fata(1, 1900);
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                Laterala(1, 200);///2000
                //trebuie deplasat 74 de cm din est catre vest
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                BratWobble.setPosition(-1);
                sleep(500);
                GrabWobble.setPosition(0.5);
                sleep(500);
                BratWobble.setPosition(0.7);
                sleep(500);
                Fata(1, -900);
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
                ///sleep(500);
                Laterala(1, -300);///2000
                //trebuie deplasat 74 de cm din est catre vest
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
            }
            if (K == 0) {
                //LATERALA3
                Fata(0.9, -500);
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
                Laterala(0.9, -2700);///2000
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

                BratWobble.setPosition(-1);
                sleep(500);
                GrabWobble.setPosition(0.5);
                sleep(500);
                BratWobble.setPosition(0.7);
                sleep(500);
                Laterala(0.6, 900);///2000
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

                Fata(1, -4300);
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

                Laterala(0.6, -200);///2000
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
                BratWobble.setPosition(-1);
                sleep(800);

                Fata(0.4, 500);
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

                GrabWobble.setPosition(-0.7);
                sleep(500);
                BratWobble.setPosition(0.7);
                sleep(500);

                Laterala(0.6, 100);///2000
                //trebuie deplasat 74 de cm din est catre vest
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = i.mu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
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
                Fata(1, 3350);
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
                Laterala(1, -500);///2000
                //trebuie deplasat 74 de cm din est catre vest
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();
                }
                BratWobble.setPosition(-1);
                sleep(500);
                GrabWobble.setPosition(0.5);
                sleep(500);
                BratWobble.setPosition(0.7);
                sleep(500);

                Laterala(1, 500);///2000
                //trebuie deplasat 74 de cm din est catre vest
                while (opModeIsActive() && !isStopRequested() && MotorSF.isBusy() && MotorSJ.isBusy() && MotorDF.isBusy() && MotorDJ.isBusy()) {
                    angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                    telemetry.addData("Heading: ", angles.firstAngle);
                    telemetry.addData("Roll: ", angles.secondAngle);
                    telemetry.addData("Pitch", angles.thirdAngle);
                    telemetry.update();

                }
            }*/
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
        Outtake2.setPower(-0.8);
        Outtake1.setPower(0.7);
        Spate(0.7, 3500);
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
        sleep(700);

        Laterala(0.5, 1200);
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

        Ridicare.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Vadimu(0.6,-950);
        while (opModeIsActive() && !isStopRequested() && Ridicare.isBusy() ) { }
        //Ridicare.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Ridicare.setPower(0);
        sleep(1000);

        ServoShot.setPosition(1);
        sleep(1000);
        ServoShot.setPosition(0.5);
        sleep(1000);

        //LATERALA1

        ServoShot.setPosition(1);
        sleep(1000);
        ServoShot.setPosition(0.5);
        sleep(1000);

        ServoShot.setPosition(1);
        sleep(1000);
        ServoShot.setPosition(0.5);
        sleep(1000);

        Vadimu(0.6,950);
        while (opModeIsActive() && !isStopRequested() && Ridicare.isBusy() ) { }
    }
    public void Vadimu ( double p, int t){
        Ridicare.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Ridicare.setTargetPosition(t);

        Ridicare.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Ridicare.setPower(p);
    }
}