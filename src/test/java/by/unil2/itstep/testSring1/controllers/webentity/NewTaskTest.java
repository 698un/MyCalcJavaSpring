package by.unil2.itstep.testSring1.controllers.webentity;

import by.unil2.itstep.testSring1.dao.model.PixelLine;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewTaskTest {

    @Test
    void setSceneKey() {

        //create dummy pixelLine
        PixelLine pixLine = new PixelLine(10,10,"1234567890");

        //create Test newTask
        NewTask newTask = new NewTask(pixLine);
        newTask.setSceneKey("12345678901234567890");

        assertEquals("12345678901234567890", newTask.getSceneKey());

        }

    @Test
    void getSceneKey() {
        //create dummy pixelLine
        PixelLine pixLine = new PixelLine(10,10,"1234567890");

        //create Test newTask
        NewTask newTask = new NewTask(pixLine);
        newTask.setSceneKey("12345678901234567890");

        assertEquals("12345678901234567890", newTask.getSceneKey());

        }

    @Test
    void getFrame() {
        //create dummy pixelLine
        PixelLine pixLine = new PixelLine(10,10,"1234567890");
        //create Test newTask
        NewTask newTask = new NewTask(pixLine);

        assertEquals(10, newTask.getFrame());
        }

    @Test
    void setFrame() {
        //create dummy pixelLine
        PixelLine pixLine = new PixelLine(10,10,"1234567890");
        //create Test newTask
        NewTask newTask = new NewTask(pixLine);
        newTask.setFrame(20);

        assertEquals(20, newTask.getFrame());

    }

    @Test
    void getLine() {
        //create dummy pixelLine
        PixelLine pixLine = new PixelLine(10,10,"1234567890");
        //create Test newTask
        NewTask newTask = new NewTask(pixLine);
        newTask.setLine(12);

        assertEquals(12, newTask.getLine());

        }

    @Test
    void setLine() {
        //create dummy pixelLine
        PixelLine pixLine = new PixelLine(10,10,"1234567890");
        //create Test newTask
        NewTask newTask = new NewTask(pixLine);
        newTask.setLine(12);

        assertEquals(12, newTask.getLine());
        }
}