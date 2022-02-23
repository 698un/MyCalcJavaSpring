
let speed = 1;

function ff(cx, cy) {

    var x = 0.0;
    var y = 0.0;
    var xx = 0;
    var yy = 0;
    var xy = 0;
    var i = 1;


    while (i<currentDeep && xx + yy <= 9) {
        i++;
        xy = x * y;
        xx = x * x;
        yy = y * y;
        x = xx - yy + cx;
        y = xy + xy + cy;
        }

    if (i>=currentDeep) i=0;

    return i;
}


let  deep =3000;

let pixelResult = new Uint16Array(3);
let currentDeep = 0;

/**
 * This methos calculate one pixel
 * @param xe
 * @param ye
 * @returns {ArrayBuffer}
 */
function calcPixel(xe,ye){
    //пиксел - триплей трех байт
    //let result = new Uint8Array(3);

    let scaleY = displayOpt.scaleX*1.0*displayOpt.height/displayOpt.width;


    //переводим экранные коорждинаты в математические
    let rxe = map( xe,0,displayOpt.width,
              displayOpt.posX-displayOpt.scaleX, displayOpt.posX+displayOpt.scaleX);

    let rye = map( ye,0,displayOpt.height,
        displayOpt.posY-scaleY, displayOpt.posY+scaleY);


    //let eRad = Math.round((xe-displayOpt.width*0.5)**2+
     //                         (ye-displayOpt.height*0.5)**2);
    //deep=3000;

    //let rDeep =deep*Math.exp(-eRad*eRad/1000000000);

    currentDeep = Math.trunc( deep);
    let iterCount1 = ff(rxe,rye);
    let cIter1 = 0;
    if (iterCount1>0) cIter1 = 1.0*iterCount1/currentDeep;


    let cr = (cIter1*255*1)%255;
    let cg = (cIter1*255*2)%255;
    let cb = (cIter1*255*3)%255;
    /*
    result[0]=cr;
    result[1]=cg;
    result[2]=cb;

     */


    pixelResult[0] =cr;
    pixelResult[1] =cg;
    pixelResult[2] =cb;



    //return result;
    }

   // let linePixel1 =  Array(5);
    //let iLine=0;
    let startXe=0;//стартовая точка следующего цикла


function nextCalcIteration() {

    //linePixel1[iLine] = new Uint8Array(displayOpt.width*3);
//    let linePixel = new Uint8Array(displayOpt.width*3);
    let antia = displayOpt.antia;


    let frameNum = displayOpt.currentFrame;
    let ye= displayOpt.currentLine;

    //alert("antia:"+displayOpt.antia+"width"+displayOpt.width+"height"+displayOpt.height);

    let rSum=0;
    let gSum=0;
    let bSum=0;

    let fxe =0.1;// XE с поправкой на антиальясинг
    let fye =0.1;
    let pxl;

    //defined range of calculate line
    let xeBegin = startXe;
    let xeEnd = startXe+speed;
    if (xeEnd>displayOpt.width) xeEnd =displayOpt.width;


    for (let xe=xeBegin; xe<xeEnd;xe++){

       // alert("xe:"+xe);

        rSum=0;
        gSum=0;
        bSum=0;

        //for antialiasing
        for (let ax=0;ax<antia;ax++){
            for (let ay=0;ay<antia;ay++) {

                fxe = xe + map(ax,0,antia,0,1);
                fye = ye + map(ay,0,antia,0,1);

                /*
                pxl = calcPixel(fxe, fye);
                rSum += pxl[0];
                gSum += pxl[1];
                bSum += pxl[2];

                 */

                calcPixel(fxe,fye);
                rSum+=pixelResult[0];
                gSum+=pixelResult[1];
                bSum+=pixelResult[2];

            //    if (pixelResult[0]+
             //       pixelResult[1]+
             //       pixelResult[2]==0) console.log("NULL___PIXEL!!!!!!")


            }}//next ax,ay

        rSum = Math.round(rSum*1.0/antia/antia);
        gSum = Math.round(gSum*1.0/antia/antia);
        bSum = Math.round(bSum*1.0/antia/antia);

       // alert("rgb"+rSum+"/"+bSum+"/"+gSum);
        /*
        linePixel[xe*3+0]=rSum;//-127;
        linePixel[xe*3+1]=gSum;//-127;
        linePixel[xe*3+2]=bSum;//-127;
        */

        if (rSum>255) rSum = 255;
        if (gSum>255) gSum = 255;
        if (bSum>255) bSum = 255;

        if (rSum<0) rSum = 0;
        if (gSum<0) gSum = 0;
        if (bSum<0) bSum = 0;



        lineResultat[xe*3+0]=rSum;
        lineResultat[xe*3+1]=gSum;
        lineResultat[xe*3+2]=bSum;
        }//next xe

    startXe = xeEnd;
    if (xeEnd>= displayOpt.width) {
                        calcTimeDuration = millis()-calcTimeStart;
                        calcStatus = 2;//mark that calculation is complette
                        }

    }