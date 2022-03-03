package by.unil2.itstep.testSring1.controllers.webpages;

import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



//@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ClientPathControllerTest {


    @Mock
    private CalcOptions calcOpt;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientPathController clientPathController;


    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
        clientPathController = new ClientPathController(clientService,calcOpt);

        }

    /**
     * Test access for clientFile with valid clientKEY
      */
    @Test
    public void getSomeClientFile_validKey() {

        //String myClientKey;
        //prepare
        String getUrl;// = "{somefile}";
        String myClientKey = "1234567890";

        //behavior if valid clientKey
        when (clientService.clientIsRegistration(myClientKey)).thenReturn(true);

        //TEST_VALID_CLIENTKEY
        getUrl = "{somefile.ext}";
        String resPath = clientPathController.getSomeClientFile(getUrl,myClientKey);
        getUrl = "/client/"+getUrl;// for equals build real URL
        //validate
        verify(clientService).clientIsRegistration(myClientKey);
        assertEquals(resPath,getUrl);


        }

    /**
     * Test access for clientFile with invalid clientKEY
     */
    @Test
    public void getSomeClientFile_invalidKey() {

        //String myClientKey;
        //prepare
        String getUrl;// = "{somefile}";
        String myClientKey = "0987654321";

        //behavior if INVALID clientKey
        when (clientService.clientIsRegistration(myClientKey)).thenReturn(false);


        //TEST_INVALID_CLIENTKEY
        getUrl = "{somefile.ext}";
        String resPath = clientPathController.getSomeClientFile(getUrl,myClientKey);
        getUrl = "/client/"+getUrl;// for equals build real URL
        //validate
        verify(clientService).clientIsRegistration(myClientKey);
        assertEquals("access_denied.html",resPath);
        }





    /**
     * Test access for clientFile in someFolder with invalid clientKEY
     */
    @Test
    public void getSomeClientFolderFile_validKey() {
        //String myClientKey;
        //prepare
        String getUrl;
        String myClientKey = "1234567890";

        //behavior if valid clientKey
        when (clientService.clientIsRegistration(myClientKey)).thenReturn(true);

        //TEST_VALID_CLIENTKEY
        getUrl = "/folder/{somefile.ext}";
        String resPath = clientPathController.getSomeClientFile(getUrl,myClientKey);
        getUrl = "/client/"+getUrl;// for equals build real URL
        //validate
        verify(clientService).clientIsRegistration(myClientKey);
        assertEquals(resPath,getUrl);
        }



    /**
     * Test access for clientFile in someFolder with invalid clientKEY
     */
    @Test
    public void getSomeClientFolderFile_invalidKey() {

        String getUrl;
        String myClientKey = "0987654321";

        //behavior if INVALID clientKey
        when (clientService.clientIsRegistration(myClientKey)).thenReturn(false);

        //TEST_INVALID_CLIENTKEY
        getUrl = "/folder/{somefile.ext}";
        String resPath = clientPathController.getSomeClientFile(getUrl,myClientKey);
        getUrl = "/client/"+getUrl;// for equals build real URL
        //validate
        verify(clientService).clientIsRegistration(myClientKey);
        assertEquals("access_denied.html",resPath);
        }






}