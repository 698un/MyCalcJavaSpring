

class MyCheckBox {

    constructor() {
        this.left = 100;
        this.top = 100;

        this.w1 = 200;
        this.h1 = 50;

        this.textColor = color(255);
        this.backColor = color(192);

        this.checkColor = color(0);
        this.checkBackColor = color(255);


        this.caption="check";
        this.checked = false;
        this.textSize = 10;

        this.mPress = false;//признак что нажата
        this.tRelease = 0;//время отпускания
        this.cnv = createGraphics(100,100);
        this.t0=Math.random()*1000;
        this.prevTime=0;//время предыдущей прорисовки
        }//constuctor

    reGraphics(){

        //if size change
        if (this.cnv.width!=this.w1 ||
            this.cnv.height!=this.h1) {

            this.cnv = createGraphics(this.w1,this.h1);
            }//if size change

    }//reGraphics


    centerPosition(){
        this.left = width*0.5-this.w1*0.5;
        this.top = height*0.5-this.h1*0.5;
        }//CenterPosition


    show() {


        let tMillis = millis();//запоминаем millis
        let dt = tMillis-this.prevTime;
        this.prevTime = tMillis;


        this.reGraphics();


        if (this.mPress==true &&
            mouseIsPressed &&
            this.inRegion()==true) this.tRelease=tMillis+250;

        if (this.tRelease<tMillis ) this.mPress = false;

        //this.mPress = true;

        //смещение текста
        var ds = 0;
        if (this.mPress==true) ds = 2;

        //серый прямоугольник
        this.cnv.strokeWeight(2);
        this.cnv.fill(this.backColor);
        //this.cnv.fill(millis()*0.5%255,millis()*0.6%255,millis()*0.7%255);
        this.cnv.noStroke();//без окантовки
        this.cnv.rect(0,0,this.w1,this.h1);

        image(this.cnv,this.left,this.top);

        //текст кнопки
        textAlign(CENTER,CENTER);
        textSize(this.textSize);
        fill(this.textColor);
        noStroke();

        text(this.caption,
            this.left+this.w1*0.5+ds,
            this.top+ this.h1*0.5+ds);




    }//show


    eventMouseDown(){
        //выход если мышь не нажата
        if (mouseIsPressed==false) return false;
        //выход если нажатие уже есть
        if (this.mPress==true) return false;
        //==============================здесь мышь уже нажата
        //если мышь нажата внутри региона (и mPress==false)
        if( this.inRegion()==true) {
            this.mPress=true;
            this.tRelease = millis()+250;
            return true;
            }

        return false;

    }//click

    inRegion(){

        var mx = mouseX;
        var my = mouseY;

        if (mx>this.left &&
            my>this.top &&
            mx<this.left+this.w1 &&
            my<this.top+this.h1) {
            return true;
             }

        return false;

    }//inRegion




}//class MyCheckBox