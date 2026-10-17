package org.firstinspires.ftc.teamcode.subsystems;

import static com.pedropathing.ivy.commands.Commands.instant;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Prism.Color;
import org.firstinspires.ftc.teamcode.Prism.Direction;
import org.firstinspires.ftc.teamcode.Prism.GoBildaPrismDriver;
import org.firstinspires.ftc.teamcode.Prism.PrismAnimations;
import org.firstinspires.ftc.teamcode.utils.Colors;

public class Led {
    Servo led;
    GoBildaPrismDriver prism;

    PrismState currentPrismState = null;

    public Led(){
        led = hardwareMap.get(Servo.class, "led");
        prism = hardwareMap.get(GoBildaPrismDriver.class, "prism");
    }



    // Rgb Indicator
    public Command off = instant(() -> led.setPosition(Colors.OFF));
    public Command green = instant(() -> led.setPosition(Colors.GREEN));
    public Command red = instant(() -> led.setPosition(Colors.RED));
    public Command yellow = instant(() -> led.setPosition(Colors.YELLOW));
    public Command orange = instant(() -> led.setPosition(Colors.ORANGE));


    public void setLedOff(){
        off.schedule();
    }
    public void setLedRed(){
        red.schedule();
    }
    public void setLedOrange(){
        orange.schedule();
    }
    public void setLedYellow(){
        yellow.schedule();
    }
    public void setLedGreen(){
        green.schedule();
    }

    // Prism Rgb


    public enum PrismState {
        RED_FILL,
        RED_SNAKE,
        GREEN_SNAKE,
        GREEN_SOLID,
        GREEN_BLINK,
        RED_SOLID,
        RED_BLINK,
        BLUE_BLINK,
        BLUE_SOLID,
        GREEN_YELLOW_SNAKE,
        OFF
    }

    public void setPrismState(PrismState state) {
        if (state == currentPrismState) return;

        currentPrismState = state;
        prism.clearAllAnimations();

        switch (state) {
            case RED_SNAKE:
                prism.insertAndUpdateAnimation(
                        GoBildaPrismDriver.LayerHeight.LAYER_0,
                        createSnakes(Color.RED)
                );
                break;

            case RED_FILL:
                prism.insertAndUpdateAnimation(
                        GoBildaPrismDriver.LayerHeight.LAYER_0,
                        createFill()
                );
                break;

            case GREEN_SNAKE:
                prism.insertAndUpdateAnimation(
                        GoBildaPrismDriver.LayerHeight.LAYER_0,
                        createSnakes(Color.GREEN)
                );
                break;

            case GREEN_SOLID:
                prism.insertAndUpdateAnimation(
                        GoBildaPrismDriver.LayerHeight.LAYER_0,
                        createSolid(Color.GREEN)
                );
                break;

            case GREEN_BLINK:
                prism.insertAndUpdateAnimation(
                        GoBildaPrismDriver.LayerHeight.LAYER_0,
                        createBlink(Color.GREEN)
                );
                break;

            case RED_SOLID:
                prism.insertAndUpdateAnimation(
                        GoBildaPrismDriver.LayerHeight.LAYER_0,
                        createSolid(Color.RED)
                );
                break;

            case RED_BLINK:
                prism.insertAndUpdateAnimation(
                        GoBildaPrismDriver.LayerHeight.LAYER_0,
                        createBlink(Color.RED)
                );
                break;

            case BLUE_BLINK:
                prism.insertAndUpdateAnimation(
                        GoBildaPrismDriver.LayerHeight.LAYER_0,
                        createBlink(Color.BLUE)
                );
                break;
            case BLUE_SOLID:
                prism.insertAndUpdateAnimation(
                        GoBildaPrismDriver.LayerHeight.LAYER_0,
                        createSolid(Color.BLUE)
                );
                break;
            case GREEN_YELLOW_SNAKE:
                prism.insertAndUpdateAnimation(
                        GoBildaPrismDriver.LayerHeight.LAYER_0,
                        createSnakesTwoColors(Color.GREEN, Color.YELLOW)
                );
                break;
            case OFF:
                break;
        }
    }

    private PrismAnimations.Solid createSolid(Color color) {
        PrismAnimations.Solid solid = new PrismAnimations.Solid(color);
        solid.setBrightness(100);
        solid.setStartIndex(0);
        solid.setStopIndex(24);
        return solid;
    }

    private PrismAnimations.Blink createBlink(Color color) {
        PrismAnimations.Blink blink = new PrismAnimations.Blink();
        blink.setPrimaryColor(color);
        blink.setSecondaryColor(Color.TRANSPARENT);
        blink.setBrightness(100);
        blink.setStartIndex(0);
        blink.setStopIndex(24);
        blink.setPeriod(600);
        blink.setPrimaryColorPeriod(300);
        return blink;
    }

    private PrismAnimations.Snakes createSnakes(Color color) {
        PrismAnimations.Snakes snake = new PrismAnimations.Snakes();
        snake.setSnakeLength(5);
        snake.setSpacingBetween(2);
        snake.setColors(color);
        snake.setBackgroundColor(Color.TRANSPARENT);
        snake.setSpeed(0.70f);
        snake.setDirection(Direction.Forward);
        snake.setRepeatAfter(12);
        snake.setBrightness(100);
        return snake;
    }

    private PrismAnimations.Snakes createSnakesTwoColors(Color color1, Color color2) {
        PrismAnimations.Snakes snake = new PrismAnimations.Snakes();
        snake.setSnakeLength(5);
        snake.setSpacingBetween(2);
        snake.setColors(color1);
        snake.setBackgroundColor(color2);
        snake.setSpeed(0.52f);
        snake.setDirection(Direction.Forward);
        snake.setRepeatAfter(12);
        snake.setBrightness(100);
        return snake;
    }


    private PrismAnimations.SingleFill createFill() {
        PrismAnimations.SingleFill fill = new PrismAnimations.SingleFill();
        fill.setColors(Color.WHITE, Color.RED, Color.GREEN);
        fill.setSpeed(0.65f);
        fill.setPeriod(1000);
        fill.setPixelLength(3);
        fill.setDirection(Direction.Forward);
        fill.setStyle(PrismAnimations.SingleFill.SingleFillStyle.FillIn);
        return fill;
    }

}
