
//управление физическим временем
let dt=0.01;
let tOld =0;
let tNow=0;
let tMillis=0;

//значение поворота колеса мышы
let MW_delta = 0.01;
let MW_press = false;

let widthPrev=0;
let heightPrev = 0;

let firstDraw=true;
let animatedEnabled=true;
let UI;


function setup() {
    createCanvas(800,500,P2D);


    windowResized();

    UI = new UIClass();

    }//setup



function draw() {

    //определение физического времени
    tMillis = millis();

    tNow = tMillis*0.001;
    dt=tNow-tOld;
    tOld = tNow;

    if (firstDraw==true) {
        UI.rePosition();
        firstDraw=false;
          }

    //закраска фоном
    if (animatedEnabled) {
        fonUpdate();
        image(loginFon, 0, 0, width, height);
    }

    if (animatedEnabled==false) {
        background(0,0,255);
    }


    UI.show();//loginControlShow


    let controlEvent = UI.eventListen(); //прослушивание контроллера


    MW_delta = 0;//reset to sero mouseWheelValue after cicle of draw
    MW_press = false;

    if (width!=widthPrev ||
        height!=heightPrev ) {
                    windowResized();
                    widthPrev = width;
                    heightPrev = height;
                    UI.rePosition();
                    createFon();
                    }

    }//draw




function windowResized() {

    resizeCanvas(windowWidth,windowHeight)

    //if (layerName = "menu") menuUI.rePosition();
    //if (layerName = "game") gameUI.rePosition();
    }//windowResized

function mouseWheel(event){
    MW_delta = event.deltaY;
    }

function mousePressed(event){
    MW_press = true;
}







function cmdJoinClick(){
    let s1 =  sendAnyHttp("POST","/clientkey","{}");

    alert(s1);
    if (s1.indexOf("error")>-1) {
        alert(s1);
        return;
        }

    //let respJSON = JSON.parse(s1);//весь ответ как json объект

    //myUser = respJSON;

    document.location.href = '/client/clientindex.html';
}//cmdJoinClick

function cmdAdminClick(){

    var adminPassword = prompt("root", 'password');

    let s1 =  sendAnyHttp("POST","/rootkey",adminPassword);

    //alert(s1);
    if (s1.indexOf("error")>-1) {
        alert(JSON.parse(s1).errorStr);
        return;
        }


    //let respJSON = JSON.parse(s1);//весь ответ как json объект
    //myUser = respJSON;
    document.location.href = '/root/adminindex.html';


}//cmdAdminClick



