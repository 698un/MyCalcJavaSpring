

var dspFirst = true;
var layerName="";
layerName = "login";

let animatedEnabled= true;

//предыдущее окно чтобы отследить WindowChange
let oldLayerName="";

var cmdLight;
//UI

function dspInit(){

    textAlign(CENTER,CENTER);
   // cmdLightMaskCreate();
    createFon();

    }//dspInit
function layerDraw(){


       if (dspFirst==true){
           dspInit();
           dspFirst = false;
           }

       //if (layerName=="login") loginDraw();
       dspDraw();
       }//dspDraw






//этот метод скрывает ненужные ЭУ
function controlHide(){
    //loginUI.txtLogin.hide();
    //loginUI.txtPassword.hide();
    }



