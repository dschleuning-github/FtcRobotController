package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="SomeKids_v1_0" , group="Linear Opmode")
//@Disabled
public class SomeKids_v1_0 extends LinearOpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor leftDriveBack = null;
    private DcMotor rightDriveBack = null;
    private DcMotor eyeBallMotor = null;
    private DcMotor intakeMotor = null;
    private DcMotor armDrive = null;

    Servo   servo;    //add servo

    int Slomode = 0;
    double velocity = .9;
    double NVelocity = -.9;
    boolean VelocityLoop = true;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftDrive = hardwareMap.get(DcMotor.class, "motor0");
        leftDriveBack = hardwareMap.get(DcMotor.class, "motor 3 :]");
        rightDrive = hardwareMap.get(DcMotor.class, "motor1");
        rightDriveBack = hardwareMap.get(DcMotor.class, "motor 2 :)");
        eyeBallMotor = hardwareMap.get(DcMotor.class, "eyeballmotor");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        armDrive = hardwareMap.get(DcMotor.class, "armMotor");

        servo = hardwareMap.get(Servo.class, "boxServo");

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        eyeBallMotor.setDirection(DcMotor.Direction.FORWARD);
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
        leftDriveBack.setDirection(DcMotor.Direction.REVERSE);
        rightDriveBack.setDirection(DcMotor.Direction.FORWARD);
        armDrive.setDirection(DcMotor.Direction.FORWARD);
        double boxDrivePower = .5;

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until thes end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double leftPower;
            double rightPower;
            double leftPowerBack;
            double rightPowerBack;
            double eyeBallMotorPower;
            double armDrivePower;
            //double boxDrivePower = .5;
            double intakePower;


            // Here is where the slowmod thing does
            if (gamepad1.right_stick_button) {
                if (Slomode == 0) {
                    Slomode = 1;
                }
                if (Slomode == 1) {
                    Slomode = 0;
                    VelocityLoop = true;
                }
            }

            double drive = gamepad1.right_stick_y;   //rename?
            double turn = gamepad1.right_stick_x;    //rename?
            double rotate = gamepad1.left_stick_x;

            double eyeBall = gamepad2.right_stick_y;
            double intakeDrive = gamepad2.right_stick_x;
//            double turn2 = gamepad2.right_stick_x;

            double arm = -gamepad2.left_stick_y;
            double box = gamepad2.left_stick_x;

            if (gamepad2.dpad_left) {
                boxDrivePower = .25;
            } else if (gamepad2.dpad_right) {
                boxDrivePower = .5;
            } else if (gamepad2.dpad_up) {
                boxDrivePower = boxDrivePower + .02;
            } else if (gamepad2.dpad_down) {
                boxDrivePower = boxDrivePower - .02;
            }

            //boxDrivePower = Range.clip(box, -1.0, 1.0);
            armDrivePower = Range.clip(arm, -.3, .5);
            intakePower = Range.clip(intakeDrive, NVelocity, 1.0);
            //armDrivePower = Range.clip(arm, -1.0, 1.0);
            //TBD...arm
            //TBD...intake

            if (Math.abs(drive) > Math.abs(turn)) {
                leftPower = Range.clip(-drive, NVelocity, .6);
                rightPower = Range.clip(-drive, NVelocity, .6);
                leftPowerBack = Range.clip(drive, NVelocity, .6);
                rightPowerBack = Range.clip(drive, NVelocity, .6);
                eyeBallMotorPower = Range.clip(eyeBall, NVelocity, .6);  //move out of block???
                //armDrivePower = Range.clip(drive2 - turn2, NVelocity, .6);
            } else {
                leftPower = Range.clip(turn, NVelocity, .6);
                rightPower = Range.clip(-turn, NVelocity, .6);
                leftPowerBack = Range.clip(turn, NVelocity, .6);
                rightPowerBack = Range.clip(-turn, NVelocity, .6);
                eyeBallMotorPower = Range.clip(eyeBall, NVelocity, .6);  //move out of block???
              //  armDrivePower = Range.clip(drive2 - turn2, NVelocity, .6);
            }
            if ((Math.abs(rotate) > Math.abs(turn)) & (Math.abs(rotate) > Math.abs(drive))) {
                leftPower = Range.clip(-rotate, NVelocity, .6);
                rightPower = Range.clip(rotate, NVelocity, .6);
                leftPowerBack = Range.clip(rotate, NVelocity, .6);
                rightPowerBack = Range.clip(-rotate, NVelocity, .6);
                eyeBallMotorPower = Range.clip(eyeBall, NVelocity, .6);  //move out of block???
            }

            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);
            leftDriveBack.setPower(leftPowerBack);
            rightDriveBack.setPower(rightPowerBack);
            eyeBallMotor.setPower(eyeBallMotorPower);
            armDrive.setPower(armDrivePower);
            intakeMotor.setPower(intakePower);
            servo.setPosition(boxDrivePower);
            //TBD intakeMotor

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
//            telemetry.update();
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "speed (%.2f)", armDrivePower);
            telemetry.update();


        }
    }
}