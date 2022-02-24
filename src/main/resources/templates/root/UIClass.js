









//========UI
class UIClass {
    constructor() {
        //датчик сервера
        //this.UIStatus = new ServerStatus();


        //кнопка подключения
        this.cmdAdminExit = createButton('Exit');
        this.cmdCreateVideo = createButton('create_video');
        this.cmdVideoList = createButton('videoList');
        this.cmdGetVideoFile = createButton('getVideoFile');
        this.cmdReset = createButton('ResetCalculation');


        this.cmdAdminExit.mousePressed(cmdExitClick);
        this.cmdCreateVideo.mousePressed(cmdCreateVideoClick);
        this.cmdVideoList.mousePressed(cmdVideoListClick);
        this.cmdGetVideoFile.mousePressed(cmdGetVideoFileClick);
        this.cmdReset.mousePressed(cmdResetClick);




        //название произошедшего события
        this.eventName ="none";

        }//constuctor

    rePosition(){

        this.cmdAdminExit.position(10,10);
        this.cmdCreateVideo.position(10,50);
        this.cmdVideoList.position(10,100);
        this.cmdGetVideoFile.position(10,150);
        this.cmdReset.position(10,200);

        //this.cmdUnJoin.position(this.UIStatus.left+this.UIStatus.width/2-this.cmdUnJoin.width/2,
          //                      this.UIStatus.top+this.UIStatus.height);


    }//rePosition


    show(){

        this.rePosition();


        }//controlShow

    eventListen(){
        this.eventName = "none";
        if (mouseIsPressed==false) return this.eventName;

       // if (this.cmdLogin.eventMouseDown()==true)        this.eventName = "command_login";
      //  if (this.cmdRegistration.eventMouseDown()==true) this.eventName = "command_registration";

        return this.eventName;
        }//eventListen


    }//class menuUIClass

