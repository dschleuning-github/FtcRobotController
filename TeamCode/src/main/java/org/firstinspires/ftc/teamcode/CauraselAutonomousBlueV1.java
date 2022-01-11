/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="Basic: CauraselAutonomouseBlueV1" , group="Linear Opmode")
//q1w@Disabled
public class CauraselAutonomousBlueV1 extends LinearOpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor leftDriveBack = null;
    private DcMotor rightDriveBack = null;
    private DcMotor intakeMotor = null;
    private DcMotor eyeBallMotor = null; //motor
    int Slomode = 0;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftDrive  = hardwareMap.get(DcMotor.class, "motor0");
        leftDriveBack  = hardwareMap.get(DcMotor.class, "motor 3 :]");
        rightDrive = hardwareMap.get(DcMotor.class, "motor1");
        rightDriveBack = hardwareMap.get(DcMotor.class, "motor 2 :)");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        eyeBallMotor = hardwareMap.get(DcMotor.class, "eyeballmotor");

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDriveBack.setDirection(DcMotor.Direction.FORWARD);
        eyeBallMotor.setDirection(DcMotor.Direction.FORWARD);
        leftDriveBack.setDirection(DcMotor.Direction.REVERSE);
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until thes end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double eyePower;
            double drive = .5;
            eyePower = Range.clip(drive, -1.0, 1.0) ;
            double end_time = 25.0;
            double step1 = 0.1;
            double step2 = 0.3;
            double step3 = 5.0;
            double step4 = 0.7; //add a comment
            double step5 = 1.0;
            double step6 = 0.2;
            double step7 = 3.0;

            while (runtime.time() < end_time){
                if ((runtime.time() < step1) && (runtime.time() > 0)) {
                    MoveVertical(-0.5);
                    eyeBallMotor.setPower(0);
                } else if ((runtime.time() > step1) && (runtime.time() < (step1 + step2))) {
                    MoveSide(0.5);
                } else if ((runtime.time() > (step1 + step2)) && (runtime.time() < (step1 + (step2 + step3)))) {
                    eyeBallMotor.setPower(.5);
                    StopWheels();
                } else if ((runtime.time() > (step1 + step2 + step3)) && (runtime.time() < (step1 + step2 + step3 + step4))) {
                    eyeBallMotor.setPower(0);
                    MoveVertical(-0.5);
                } else if ((runtime.time() > (step1 + step2 + step3 + step4)) && (runtime.time() < (step1 + step2 + step3 + step4 + step5))) {
                    eyeBallMotor.setPower(0);
                    StopWheels();
                } else if ((runtime.time() > (step1 + step2 + step3 + step4 + step5)) && (runtime.time() < (step1 + step2 + step3 + step4 + step5 + step6))) {
                    MoveSide(0.5);
                } else if (runtime.time() > (step1 + step2 + step3 + step4 + step5 + step6) && (runtime.time() < (step1 + step2 + step3 + step4 + step5 + step6 + step7))) {
                    intakeMotor.setPower(.5);
                } else{
                    eyeBallMotor.setPower(0);
                    StopWheels();
                }
                System.out.println(1);

                telemetry.addData("Status", "Run Time: " + runtime.toString());
                telemetry.addData("eyeball", "eye ball (%.2f)", eyePower);
                telemetry.update();
            }
            StopWheels();
            eyeBallMotor.setPower(0);
        }
    }
    public void MoveSide(double turn){
        // Positive move left, negative move right
        double leftPower;
        double rightPower;
        double leftPowerBack;
        double rightPowerBack;

        leftPower = Range.clip(turn, -.6, .6);
        rightPower = Range.clip(-turn, -.6, .6);
        leftPowerBack = Range.clip(turn, -.6, .6);
        rightPowerBack = Range.clip(-turn, -.6, .6);

        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
        leftDriveBack.setPower(leftPowerBack);
        rightDriveBack.setPower(rightPowerBack);
    }
    public void MoveVertical(double drive) {
        // Positive forward, negative backward
        double leftPower;
        double rightPower;
        double leftPowerBack;
        double rightPowerBack;

        leftPower = Range.clip(-drive, -.6, .6);
        rightPower = Range.clip(-drive, -.6, .6);
        leftPowerBack = Range.clip(drive, -.6, .6);
        rightPowerBack = Range.clip(drive, -.6, .6);

        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
        leftDriveBack.setPower(leftPowerBack);
        rightDriveBack.setPower(rightPowerBack);
    }
    public void MoveRotate(double rotate) {
        // Positive left, negative right
        double leftPower;
        double rightPower;
        double leftPowerBack;
        double rightPowerBack;

        leftPower = Range.clip(-rotate, -.6, .6);
        rightPower = Range.clip(rotate, -.6, .6);
        leftPowerBack = Range.clip(rotate, -.6, .6);
        rightPowerBack = Range.clip(-rotate, -.6, .6);

        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
        leftDriveBack.setPower(leftPowerBack);
        rightDriveBack.setPower(rightPowerBack);
    }
    public void StopWheels() {
        // Stop all motors
        double drive = 0;
        double leftPower;
        double rightPower;
        double leftPowerBack;
        double rightPowerBack;

        leftPower = Range.clip(-drive, -.6, .6);
        rightPower = Range.clip(-drive, -.6, .6);
        leftPowerBack = Range.clip(drive, -.6, .6);
        rightPowerBack = Range.clip(drive, -.6, .6);

        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
        leftDriveBack.setPower(leftPowerBack);
        rightDriveBack.setPower(rightPowerBack);

    }
}
