

//+37529 5827678 Вера


class Cmd {

    constructor() {
        this.left = 100;
        this.top = 100;

        this.w1 = 200;
        this.h1 = 50;

        this.bColor = color(127);//фон
        this.tColor = color(0);//текст
        this.textSize = 10;
        this.caption = "caption";
        this.mPress = false;//признак что нажата
        this.tRelease = 0;//время отпускания
        this.cnv = createGraphics(100,100);
        this.t0=Math.random()*1000;
        this.prevTime=0;//время предыдущей прорисовки


        }//constuctor

    //метод перестраивает canvas если ищменился размер
    reGraphics(){

        //if size change
        if (this.cnv.width!=this.w1 ||
           this.cnv.height!=this.h1) {

               this.cnv = createGraphics(this.w1,this.h1);
               console.log("resize");

               }//if size change



        }//reGraphics





    centerPosition(){
        this.left = width*0.5-this.w1*0.5;
        this.top = height*0.5-this.h1*0.5;
        }//CenterPosition

    //этот метод размещаетт кнопку внизу указанной
    bottomCmd(cmdUp,dHeight){
        this.top = cmdUp.top+cmdUp.h1+dHeight;
        }//CenterPosition



    show() {


        var tMillis = millis();//запоминаем millis
        var dt = tMillis-this.prevTime;
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
        this.cnv.fill(this.bColor);
        //this.cnv.fill(millis()*0.5%255,millis()*0.6%255,millis()*0.7%255);
        this.cnv.stroke(0);//черная окантовка кнопки
        this.cnv.rect(0,0,this.w1,this.h1);


        //рельефные линии по периметру
        this.cnv.stroke(255);
        if (this.mPress==false) {
             this.cnv.line(0,0, 0+this.w1,0);
             this.cnv.line(0,0,0,0+this.h1);
             }//mPress = false

        if (this.mPress==true) {
             this.cnv.line(0+this.w1,0+this.h1,
                           0+this.w1,0);
             this.cnv.line(0+this.w1,0+this.h1,
                           0,0+this.h1);
             }//mPress=  true







        image(this.cnv,this.left,this.top);

    //текст кнопки
       textAlign(CENTER,CENTER);
       textSize(this.textSize);
       fill(this.tColor);
       noStroke();

       text(this.caption,
            this.left+this.w1*0.5+ds,
            this.top+ this.h1*0.5+ds);




        }//show


   eventMouseDown(){
        //выход если мышь не нажата
        if (MW_press==false) return false;

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




}//class cmd