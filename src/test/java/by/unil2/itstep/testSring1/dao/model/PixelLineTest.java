package by.unil2.itstep.testSring1.dao.model;

import by.unil2.itstep.testSring1.dao.model.enums.StatusPixelLine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PixelLineTest {

    @Test
    void setStatus() {
        String clientKey= "1234567890";
        PixelLine pixLine = new PixelLine(0,0,clientKey);

        pixLine.setStatus(StatusPixelLine.COMPLETTE);
        assertEquals(StatusPixelLine.COMPLETTE,pixLine.getStatus());
        }

    @Test
    void getStatus() {
        String clientKey= "1234567890";
        PixelLine pixLine = new PixelLine(0,0,clientKey);

        pixLine.setStatus(StatusPixelLine.COMPLETTE);
        assertEquals(StatusPixelLine.COMPLETTE,pixLine.getStatus());
        }

    @Test
    void getBT() {
        String clientKey= "1234567890";
        PixelLine pixLine = new PixelLine(0,0,clientKey);

        long getBT = pixLine.getBT();

        assertEquals(getBT,System.currentTimeMillis());
        }


    @Test
    void setBT() {
        String clientKey= "1234567890";
        PixelLine pixLine = new PixelLine(0,0,clientKey);

        pixLine.setBT(100L);
        assertEquals(100L,pixLine.getBT());
        }

    @Test
    void getDT() {
        String clientKey= "1234567890";
        PixelLine pixLine = new PixelLine(0,0,clientKey);

        pixLine.setDT(100L);
        assertEquals(100L,pixLine.getDT());
        }

    @Test
    void setDT() {
        String clientKey= "1234567890";
        PixelLine pixLine = new PixelLine(0,0,clientKey);

        pixLine.setDT(100L);
        assertEquals(100L,pixLine.getDT());
        }

    @Test
    void getClientKey() {
        String clientKey= "1234567890";
        PixelLine pixLine = new PixelLine(0,0,clientKey);

        String getClientKey = pixLine.getClientKey();
        assertEquals(clientKey,getClientKey);
        }

    @Test
    void setClientKey() {
        String clientKey= "1234567890";
        PixelLine pixLine = new PixelLine(0,0);
        pixLine.setClientKey(clientKey);

        String getClientKey = pixLine.getClientKey();
        assertEquals(clientKey,getClientKey);
        }


}