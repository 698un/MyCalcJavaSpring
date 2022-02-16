


let loginFon;
let xe;
let ye;
let c;

let characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
var charactersLength = characters.length;

let ty = new Array();
let lCount;//количество букв
let tw;


function fonUpdate(){



    if (loginFon==null) createFon();

    if (frameCount%2==0) return;//перерисовываем каждый второй кадр


    loginFon.fill(255,255,0);


    for (var i=0;i<lCount;i++) {

        xe = i*tw;
        ye = ty[i];
        c = characters.charAt(Math.floor(Math.random() * charactersLength));
        if (ye<=loginFon.height) loginFon.text(c, xe, ye);
        ty[i]=ty[i]+tw*1.0;

        if (ty[i]>loginFon.height*2) ty[i] = 0;
        }//next i



    loginFon.fill(0,0,0,25);
    loginFon.rect(0,0,width,height);


    }//fonUpdate


function createFon(){


    loginFon = createGraphics(width/2,height/2);
    loginFon.fill(255,255,0);

    tw = loginFon.textWidth("W");

    lCount = Math.floor(width/tw);

    for (var i=0;i<lCount;i++){
        ty[i] = Math.random()*height;
        }//next i




    }//createLoginFon_byStart