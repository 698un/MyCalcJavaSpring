package by.unil2.itstep.testSring1.controllers.webpages;

import by.unil2.itstep.testSring1.dao.repository.ClientRepository;
import by.unil2.itstep.testSring1.services.ClientService;
import by.unil2.itstep.testSring1.services.VideoService;
import by.unil2.itstep.testSring1.utilits.CalcOptions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ClientPathControllerTest {


    @Mock
    private CalcOptions calcOpt;

    @Mock
    private ClientService clientService;




    @InjectMocks
    private ClientPathController clientPathController;

    //@Mock
    //private ClientRepository clientRepository;



    private String myClientKey;

/*
    private ClientRepository clientRep;
    private ClientService clientService;
    private String myClientKey;
    private ClientPathController clientPathController;
    private VideoService videoService;
    private CalcOptions calcOpt;
*/


    @BeforeEach
    void initService(){

       // clientService  = mock(ClientService.class);



        }



    @Test
    public void getSomeClientFile() {

        //prepare
        String getUrl = "/client/some.path";
        myClientKey = "1234567890";

        //clientService = new ClientService(null,null,null);


        when (clientService.clientIsRegistration(myClientKey)).thenReturn(true);

        System.out.println("when");

        String resPath = clientPathController.getSomeClientFile(getUrl,myClientKey);

        System.out.println(getUrl);
        System.out.println(resPath);

        //validate
        verify(clientService.clientIsRegistration(myClientKey));

        assertEquals(resPath,getUrl);
        }



    @Test
    void getSomeClientFolderFile() {
    }

    @Test
    void testGetSomeClientFile() {
    }

    @Test
    void testGetSomeClientFolderFile() {
    }
}