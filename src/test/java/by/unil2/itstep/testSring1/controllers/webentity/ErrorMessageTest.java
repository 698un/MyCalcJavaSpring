package by.unil2.itstep.testSring1.controllers.webentity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class ErrorMessageTest {

    @Test
    void getErrorStr() {
        ErrorMessage errorMsg = new ErrorMessage("someText");
        assertEquals("someText", errorMsg.getErrorStr());


        }

    @Test
    void setErrorStr() {

        ErrorMessage errorMsg = new ErrorMessage("");
        errorMsg.setErrorStr("ERROR");
        assertEquals("ERROR", errorMsg.getErrorStr());

        }
}