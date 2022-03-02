package by.unil2.itstep.testSring1.dao.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {





    @Test
    void getKey() {

        String clientKey ="1234567890";

        Client client = new Client();
        client.setKey(clientKey);

        String getKey =client.getKey();
        assertEquals(getKey,clientKey);

        }

    @Test
    void setKey() {
        String clientKey ="1234567890";

        Client client = new Client();
        client.setKey(clientKey);

        String getKey =client.getKey();
        assertEquals(getKey,clientKey);
        }

    @Test
    void updateLastTime() throws InterruptedException {

        Client client = new Client();
        long oldTime =  client.getLastTimeConnect();
        Thread.sleep(100);
        client.updateLastTime();
        long newTime =  client.getLastTimeConnect();

        long deltaTime =newTime-oldTime;
        if (deltaTime>=100L) deltaTime = 100L; else deltaTime=0;

        assertEquals(deltaTime,100L);
        }



    @Test
    void getLastTimeConnect() throws InterruptedException {
        Client client = new Client();
        long oldTime =  client.getLastTimeConnect();
        Thread.sleep(100);
        client.updateLastTime();
        long newTime =  client.getLastTimeConnect();

        long deltaTime =newTime-oldTime;
        if (deltaTime>=100L) deltaTime = 100L; else deltaTime=0;

        assertEquals(deltaTime,100L);
        }




}//ClientTest