

var dspFirst = true;
var layerName="";

layerName = "work";

let animatedEnabled= true;

//предыдущее окно чтобы отследить WindowChange
let oldLayerName="";

//var cmdLight;
//UI

function dspInit(){

    textAlign(CENTER,CENTER);
    //cmdLightMaskCreate();
    createWorkFon();

    }//dspInit
function layerDraw(){

       //перепрятывание ненужных ЭУ
       if (oldLayerName!=layerName) {

            controlHide();
            oldLayerName = layerName;
            }


       if (dspFirst==true){
           dspInit();
           dspFirst = false;
           }

       workDraw();
       }//dspDraw






//этот метод скрывает ненужные ЭУ
function controlHide(){
    //loginUI.txtLogin.hide();
    //loginUI.txtPassword.hide();
    }




/*
function cmdLightMaskCreate(){


    //create cmdLightMask
    cmdLight = createGraphics(256,256);
    cmdLight.clear();
    cmdLight.strokeWeight(2);
    cmdLight.noFill();
    for (var i=0;i<255;i++){
        cmdLight.stroke(255,255,0,255-i);
        cmdLight.ellipse(128,128,i,i);
        }//next i

    }//cmdLightMaskCreate

 */