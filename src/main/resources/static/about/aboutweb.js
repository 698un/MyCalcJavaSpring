
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
let currentScene = "first";


const sceneTitle = ['first','second'];
let currentSceneIndex = 0;
let sceneCount = 2;


//create UIControl
let aboutUI;

function setup() {
    createCanvas(800,500,P2D);



    dspSetup();//переустановка параметров текущей сцены
    windowResized();

    aboutUI = new AboutControl();
    aboutUI.rePositionControl();



    }//setup



function draw() {

    //определение физического времени
    tMillis = millis();
    tNow = tMillis*0.001;
    dt=tNow-tOld;
    tOld = tNow;

    if (firstDraw==true) {
        dspSetup();//переустановка перемеров сцены
        firstDraw=false;
        }


    dspRedraw();



    text(currentSceneIndex,100,100);
    text(sceneTitle[currentSceneIndex],100,100);
    text(width,100,200);

    MW_delta = 0;//reset to sero mouseWheelValue after cicle of draw
    MW_press = false;



    if (width!=widthPrev ||
        height!=heightPrev ) {
                    windowResized();
                    widthPrev = width;
                    heightPrev = height;
                    dspSetup();
                    aboutUI.rePositionControl();
                    }

    }//draw




function windowResized() {


    resizeCanvas(windowWidth,windowHeight)


    }//windowResized

function mouseWheel(event){
    MW_delta = event.deltaY;
    }

function mousePressed(event){
    MW_press = true;
}



function pressPrev(){
    let oldIndex = currentSceneIndex;
    currentSceneIndex--;
    if (currentSceneIndex<0)  currentSceneIndex=0;
    if (oldIndex!=currentSceneIndex) firstDraw = true;
    }

function pressNext(){
    let oldIndex = currentSceneIndex;
    currentSceneIndex++;
    if (currentSceneIndex>sceneCount-1)  currentSceneIndex=sceneCount-1;
    if (oldIndex!=currentSceneIndex) firstDraw = true;
    }





function dspRedraw(){

    if (currentScene=="first") dspFirstRedraw();
    }

function dspSetup(){

    if (currentScene=="first") dspFirstSetup();
    }




 class MyCam{
     constructor() {
        this.pos = createVector(0,0,0);
        this.dir = createVector(0,0,0);
        this.trg = createVector(0,0,0);
        }//constructor

 }  //class myCam