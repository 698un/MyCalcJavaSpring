


class Label {

    constructor() {
        this.left = 100;
        this.top = 100;

        this.w1 = 100;
        this.h1 = 50;

        this.bColor = color(127);//фон
        this.tColor = color(0);//текст
        this.textSize = 10;
        this.caption = "caption";

        }//constuctor

    show() {

        //смещение текста
        var ds = 0;

        //серый прямоугольник
        strokeWeight(2);
        stroke(64);
        fill(this.bColor);
        rect(this.left,this.top,this.w1,this.h1);

    //текст надписи
       textAlign(CENTER,CENTER);
       textSize(this.textSize);
       fill(this.tColor);
       noStroke();

       text(this.caption,
            this.left+this.w1*0.5+ds,
            this.top+ this.h1*0.5+ds);

        }//show

}//class Label