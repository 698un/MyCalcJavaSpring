






//========UI
class UIClass {
    constructor() {

        //датчик сервера
        this.UIStatus = new ServerStatus();


        //кнопка подключения
        this.cmdJoin = createButton('Start Test');
        this.cmdJoin.position(0, 0);
        this.cmdJoin.mousePressed(cmdJoinClick);

        this.cmdAdmin = createButton('Admin panel');
        this.cmdAdmin.position(0, 0);
        this.cmdAdmin.mousePressed(cmdAdminClick);


        //название произошедшего события
        this.eventName ="none";

        }//constuctor





    rePosition(){

        this.cmdJoin.position(width/2-this.cmdJoin.width/2,height/2-this.cmdJoin.height/2);
        this.cmdAdmin.position(width/2-this.cmdJoin.width/2,height/2+this.cmdJoin.height/2*2);


        }//rePosition


    show(){
        this.cmdJoin.show();
        this.cmdAdmin.show();

        this.UIStatus.show();

        }//controlShow

    eventListen(){

        if (this.eventName=="cmdJoinClick") {
                        return "command_join";
                        this.eventName ="none";
                        }



        this.eventName = "none";
        if (mouseIsPressed==false) return this.eventName;

        return this.eventName;
        }//eventListen


    }//class menuUIClass

