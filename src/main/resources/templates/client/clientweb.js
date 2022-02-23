
//управление физическим временем
let dt=0.01;
let tOld =0;
let tNow=0;
//значение поворота колеса мышы
let MW_delta = 0.01;
let MW_press = false;
let widthPrev = 0;
let heightPrev = 0;
//признак что пора пересчитать UI
let firstDraw=true;
let animatedEnabled = true;

let taskNumber=0;

let UI;

function setup() {
    createCanvas(800,500,P2D);

    //создание элементов управления
    UI = new UIClass();

    windowResized();
    animatedEnabled = false;
    }//setup

let screenFps = 10;

function draw() {

    //SYSTEM_FUNCTION
    if (firstDraw == true) {
        UI.rePosition();
        firstDraw = false;
        background(0);
        }


    //определение физического времени
    tNow = millis()*0.001;
    dt=tNow-tOld;
    tOld = tNow;
    MW_delta = 0;//reset to sero mouseWheelValue after cicle of draw
    MW_press = false;
    if (width!=widthPrev || height!=heightPrev ) windowResized();

    //закраска фоном
    if (animatedEnabled) {
 //       fonUpdate();
      //  image(loginFon, 0, 0, width, height);
        } else   background(0);



    UI.show();//loginControlShow

    fill(255)
    //text(speed,100,100);
    //text(startXe,100,125);
    //text(frameRate(),100,150);
   // text(displayOpt.width,100,175);

   screenFps=screenFps*0.5+frameRate()*0.5;


    //выход если пока не получили настройки с серверСтатус
    if (UI.UIStatus.serverStatus=="none") return;


    displayOpt.width =  UI.UIStatus.serverStatus.imgWidth;
    displayOpt.height = UI.UIStatus.serverStatus.imgHeight;
    displayOpt.antia =  UI.UIStatus.serverStatus.imgAntialiasing;
    displayOpt.fps =    UI.UIStatus.serverStatus.fps;

    //alert(displayOpt.width+" / "+displayOpt.height+" / "+displayOpt.antia);

    //forward containt only calculated function

    if (tNow<calcTimeOut) return;// abort cicle if timeout



    //если свободен то спрашиваем новую задачу
    if (calcStatus==0) {


                        getNewTask();



                    }

    //если процесс расчетов идет

    if (calcStatus==1) {
                    nextCalcIteration();


                    let fps = frameRate();
                    //поправка на SPEED
                    if (fps<20) speed--;
                    if (fps<15) speed=speed-10;
                    if (fps<10) speed=Math.round(speed/2);
                    if (fps>20) speed++;
                    //if (fps>30) speed=speed+10;

                    if (speed<1) speed = 1;
                    }

    if (calcStatus==2){
                    let answerStr = sendResultat();


                    if (answerStr.indexOf("error")>-1){
                        resultatErrorCount++;
                        errorPrc = resultatErrorCount/(resultatErrorCount+resultatCorrectCount)*100;
                        calcStatus =0;
                        return;
                        }

                    let answerObj = JSON.parse(answerStr);

                    resultatCorrectCount++;
                    let prc = 100.0 * calcTimeDuration / answerObj.duration;
                    if (UI.UIStatus.serverStatus.netPerfomance==null) UI.UIStatus.serverStatus.netPerfomance=0;
                    UI.UIStatus.serverStatus.netPerfomance =UI.UIStatus.serverStatus.netPerfomance*0.9+ prc*0.1;

                    calcStatus = 0;
                    }


}//draw



//function of leave from calculation
function cmdUnJoinClick(){
    let s1 =  sendAnyHttp("POST","/api/exit","{}");

    alert(s1);

    //if not error then relink to start page
    if (s1.indexOf("error")>-1){
           document.location.href = '/index.html';
           return;
           }
    //finally return to startPage
    document.location.href = '/index.html';
    return;



}//cmdUnJoinClick


















function windowResized() {

    resizeCanvas(windowWidth,windowHeight);
    widthPrev = windowWidth;
    heightPrev = windowHeight;
    UI.rePosition();
    createFon();

    //if (layerName = "menu") menuUI.rePosition();
    //if (layerName = "game") gameUI.rePosition();
    }//windowResized

function mouseWheel(event){
    MW_delta = event.deltaY;
    }

function mousePressed(event){
    MW_press = true;
}

