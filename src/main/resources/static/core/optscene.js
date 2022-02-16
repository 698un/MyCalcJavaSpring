

let displayOpt= {

    "width": 1280,
    "height": 720,
    "antia": 5,
    "fps": 25,


    "posX": 0.3,
    "posY": 0.1,
    "scaleX":0.003,
    "currentFrame":0,
    "currentLine":0,
    "currentSceneKey":'none',
    }//displayOpt


let lineResultat = new  Uint16Array(100*3);




//Предрасчет параметров сцены
function initScene(frameNum,lineNum){


    displayOpt.currentFrame = frameNum;
    displayOpt.currentLine = lineNum;

        let t = frameNum*1.0/displayOpt.fps;

        //function for properties of teh scene
         displayOpt.scaleX = Math.exp(-t*1.0);

         deep = 3*frameNum+16;

         //posX = 0.3*Math.sin(t*1.5);
         //posY = 0.3*Math.cos(t*1.5);

         displayOpt.posX = 0.39;
         displayOpt.posY = 0.3130205501;

         startXe = 0;

         //опционально пересоздаем массив линейки для результатов(нужного размера)
         if (displayOpt.width!=lineResultat.length){
                    lineResultat = new  Uint8Array(displayOpt.width*3);
                    }


         }//InitScene



