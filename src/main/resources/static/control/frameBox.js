


class FrameBox {

    constructor() {
        this.left = 100;
        this.top = 100;

        this.w1 = 100;
        this.h1 = 50;

        this.backColor = color(127);//фон
        this.limitColor = color(0);//фон
        this.textColor = color(0);//текст
        this.textSize = 10;
        this.caption = " ";

        }//constuctor

    show() {


        //серый прямоугольник
        strokeWeight(2);
        stroke(this.limitColor);
        fill(this.backColor);
        rect(this.left,this.top,this.w1,this.h1);

    //текст надписи
       textAlign(CENTER,TOP);
       textSize(this.textSize);
       fill(this.tColor);
       noStroke();

       text(this.caption,
            this.left+this.w1*0.5,
            this.top+ this.h1*0.5);

            }//show





}//class Label