package by.unil2.itstep.testSring1.controllers.webpages;

import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.services.VideoService;
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



@SpringBootTest
public class RootPathControllerTest {


    @Mock
    private CalcOptions calcOpt;

    @Mock
    private ClientService clientService;

    @Mock
    private VideoService videoService;


    @InjectMocks
    private RootPathController rootPathController;


    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
        rootPathController = new RootPathController(clientService,videoService,calcOpt);
        }


    /**
     * Test access for rootFile with valid rootKEY
     */
    @Test
    public void getSomeRootFile_validKey() {

        //String myClientKey;
        //prepare
        String getUrl;// = "{somefile}";
        String myClientKey = "1234567890";

        //behavior if valid clientKey
        when (clientService.isRootKey(myClientKey)).thenReturn(true);

        //TEST_VALID_CLIENTKEY
        getUrl = "{somefile.ext}";
        String resPath = rootPathController.getSomeRootFile(getUrl,myClientKey);
        getUrl = "/root/"+getUrl;// for equals build real URL
        //validate
        verify(clientService).isRootKey(myClientKey);
        assertEquals(resPath,getUrl);
        }


    /**
     * Test access for rootFile with invalid rootKEY
     */
    @Test
    public void getSomeRootFile_invalidKey() {

        //String myClientKey;
        //prepare
        String getUrl;// = "{somefile}";
        String myClientKey = "0987654321";

        //behavior if INVALID clientKey
        when (clientService.isRootKey(myClientKey)).thenReturn(false);


        //TEST_INVALID_CLIENTKEY
        getUrl = "{somefile.ext}";
        String resPath = rootPathController.getSomeRootFile(getUrl,myClientKey);
        getUrl = "/client/"+getUrl;// for equals build real URL
        //validate
        verify(clientService).isRootKey(myClientKey);
        assertEquals("access_denied.html",resPath);
    }







    /**
     * Test access for ROOTFile in someFolder with invalid rootKEY
     */
    @Test
    public void getSomeRootFolderFile_validKey() {
        //String myClientKey;
        //prepare
        String getUrl;
        String myClientKey = "1234567890";

        //behavior if valid clientKey
        when (clientService.isRootKey(myClientKey)).thenReturn(true);

        //TEST_VALID_CLIENTKEY
        getUrl = "/folder/{somefile.ext}";
        String resPath = rootPathController.getSomeRootFile(getUrl,myClientKey);
        getUrl = "/root/"+getUrl;// for equals build real URL
        //validate
        verify(clientService).isRootKey(myClientKey);
        assertEquals(resPath,getUrl);
        }



    /**
     * Test access for rootFile in someFolder with invalid rootKEY
     */
    @Test
    public void getSomeRootFolderFile_invalidKey() {

        String getUrl;
        String myClientKey = "0987654321";

        //behavior if INVALID clientKey
        when (clientService.isRootKey(myClientKey)).thenReturn(false);

        //TEST_INVALID_CLIENTKEY
        getUrl = "/folder/{somefile.ext}";
        String resPath = rootPathController.getSomeRootFile(getUrl,myClientKey);
        getUrl = "/root/"+getUrl;// for equals build real URL
        //validate
        verify(clientService).isRootKey(myClientKey);
        assertEquals("access_denied.html",resPath);
        }



/*
    @RequestMapping(value = "/video/file/{filename}", method = RequestMethod.GET)
    public ResponseEntity<?> getVideoFile(@PathVariable(name = "filename") String fileName,
                                          @CookieValue(value="ClientKey") String rootKey){
*/







}