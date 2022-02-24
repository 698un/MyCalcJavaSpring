
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







}//draw



//function of leave from calculation
function cmdExitClick(){
    let s1 =  sendAnyHttp("POST","/api/exit","{}");

     document.location.href = '/index.html';

}//cmdUnJoinClick





//function of leave from calculation
function cmdCreateVideoClick(){
    let fileName =  prompt("Enter filename:", 'fileName');
    let s1 =  sendAnyHttp("POST","/api/createvideo/"+fileName,"{}");
    console.log(s1);
    if (s1.indexOf("error")>-1){
        alert(s1);
        return;
        }
    }//cmdCreateVideo


function cmdVideoListClick(){
    let s1 =  sendAnyHttp("GET","/video/all","{}");
    alert(s1);
    }



///////////////////////
function cmdGetVideoFileClick(){
    let fileName =  prompt("Enter filename:", 'fileName');
    //let s1 =  sendAnyHttp("GET","/video/file/"+fileName,"{}");
    document.location.href = '/video/file/'+fileName;
    }

function cmdResetClick(){
    let s1 =  sendAnyHttp("POST","/api/reset","{}");
    alert(s1);

    }












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

