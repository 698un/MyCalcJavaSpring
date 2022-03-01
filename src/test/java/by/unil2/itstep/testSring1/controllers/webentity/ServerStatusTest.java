package by.unil2.itstep.testSring1.controllers.webentity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerStatusTest {

    @Test
    void getClientCount() {

        ServerStatus servStatus = new ServerStatus();
        servStatus.setImgAntialiasing(5);

        assertEquals(5,servStatus.getImgAntialiasing());
        }

    @Test
    void setClientCount() {
        ServerStatus servStatus = new ServerStatus();
        servStatus.setClientCount(12);

        assertEquals(12,servStatus.getClientCount());
        }

    @Test
    void getImgWidth() {
        ServerStatus servStatus = new ServerStatus();
        servStatus.setImgWidth(640);

        assertEquals(640,servStatus.getImgWidth());
        }

    @Test
    void setImgWidth() {
        ServerStatus servStatus = new ServerStatus();
        servStatus.setImgWidth(1920);

        assertEquals(1920,servStatus.getImgWidth());
        }

    @Test
    void getImgHeight() {
        ServerStatus servStatus = new ServerStatus();
        servStatus.setImgHeight(360);

        assertEquals(360,servStatus.getImgHeight());
        }

    @Test
    void setImgHeight() {
        ServerStatus servStatus = new ServerStatus();
        servStatus.setImgHeight(1080);

        assertEquals(1080,servStatus.getImgHeight());
        }

    @Test
    void getImgAntialiasing() {
        ServerStatus servStatus = new ServerStatus();
        servStatus.setImgAntialiasing(4);

        assertEquals(4,servStatus.getImgAntialiasing());
        }

    @Test
    void setImgAntialiasing() {
        ServerStatus servStatus = new ServerStatus();
        servStatus.setImgAntialiasing(8);

        assertEquals(8,servStatus.getImgAntialiasing());
        }

    @Test
    void getFps() {
        ServerStatus servStatus = new ServerStatus();
        servStatus.setFps(25);

        assertEquals(25,servStatus.getFps());
        }

    @Test
    void setFps() {
        ServerStatus servStatus = new ServerStatus();
        servStatus.setFps(30);

        assertEquals(30,servStatus.getFps());
        }
}//TEST_ServerStatus