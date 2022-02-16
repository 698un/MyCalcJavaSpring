
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


let ye=-1;

function setup() {
    createCanvas(800,500,P2D);

    cmdCalc = createButton('rePaint');
    cmdCalc.position(0, 0);
    cmdCalc.mousePressed(startRepaint);

    windowResized();


    displayOpt.width = 640;
    displayOpt.height = 360;
    displayOpt.anti  = 2;
    displayOpt.fps =30;

    }//setup



function draw() {

    //определение физического времени
    tMillis = millis();

    fill(127);
    rect(100,100,100,-20);
    fill(0);
    text(frameRate(),100,100);


    tNow = tMillis*0.001;
    dt=tNow-tOld;
    tOld = tNow;


    MW_delta = 0;//reset to sero mouseWheelValue after cicle of draw
    MW_press = false;


    if (frameRate()<10) speed--;
    if (frameRate()>30) speed++;

    if (speed<1) speed =1;

    startRepaint();


    }//draw


function startRepaint() {


    //alert(displayOpt.posX);
    //speed = 1;

    for (let it=0;it<speed;it++) {


        //ye = Math.round(Math.random() * displayOpt.height);
        ye++;
        if (ye>displayOpt.height) return;

        //  alert(ye+"/"+displayOpt.width);

        //for (let ye = 0; ye < displayOpt.height; ye++) {

        //перенастраиваем параметры экрана


        let oldSpeed = speed
        speed = 10000;

        displayOpt.currentFrame=85;
        displayOpt.currentLine = ye;

        initScene(displayOpt.currentFrame, ye);


        startXe = 0;
        nextCalcIteration();

        speed = oldSpeed;

        //alert(pxArray.length+"/"+ye);
        //  alert("CALC_LINE_COMPLETTE");
        for (let xe = 0; xe < displayOpt.width; xe++) {


            stroke(
                lineResultat[xe * 3 + 0],
                lineResultat[xe * 3 + 1],
                lineResultat[xe * 3 + 2],
                )

            point(xe, ye);


        }



    }//it


    }//startRepaint


    function windowResized() {

        resizeCanvas(windowWidth, windowHeight)

        //if (layerName = "menu") menuUI.rePosition();
        //if (layerName = "game") gameUI.rePosition();
    }//windowResized

    function mouseWheel(event) {
        MW_delta = event.deltaY;
    }

    function mousePressed(event) {
        MW_press = true;
    }



