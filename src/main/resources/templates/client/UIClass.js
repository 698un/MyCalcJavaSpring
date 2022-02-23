



/*
   obj =  {"width":640,"height":360}

   obj  = {errorStr:"none",data:{.......}}
   {errorStr:"notnoe" +
   "" +
   "none",data:{.......}}


   400 dsfsdf
*/








//========UI
class UIClass {
    constructor() {
        //датчик сервера
        this.UIStatus = new ServerStatus();


        //кнопка подключения
        this.cmdUnJoin = createButton('leave from test');
        //this.cmdCreateVideo = createButton('create_video');


        //this.cmdUnJoin.position(this.UIStatus.left+this.UIStatus.width/2-this.cmdUnJoin.width/2,
          //                      this.UIStatus.top+this.UIStatus.height);


        this.cmdUnJoin.mousePressed(cmdUnJoinClick);
        //this.cmdCreateVideo.mousePressed(cmdCreateVideoClick);

        //название произошедшего события
        this.eventName ="none";

        }//constuctor

    rePosition(){

        this.cmdUnJoin.position(10,this.UIStatus.h1+this.UIStatus.top);
       // this.cmdCreateVideo.position(10,this.UIStatus.h1+this.UIStatus.top+20);

        //this.cmdUnJoin.position(this.UIStatus.left+this.UIStatus.width/2-this.cmdUnJoin.width/2,
          //                      this.UIStatus.top+this.UIStatus.height);


    }//rePosition


    show(){

        this.rePosition();
        this.UIStatus.show();

        }//controlShow

    eventListen(){
        this.eventName = "none";
        if (mouseIsPressed==false) return this.eventName;


        return this.eventName;
        }//eventListen


    }//class menuUIClass

