package by.unil2.itstep.testSring1.controllers.webentity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResultatReportTest {

    @Test
    void getDuration() {
        ResultatReport resRep = new ResultatReport(100L);

        assertEquals(100L,resRep.getDuration());

        }

    @Test
    void setDuration() {

        ResultatReport resRep = new ResultatReport(100L);
        resRep.setDuration(123L);

        assertEquals(123L,resRep.getDuration());
        }


}