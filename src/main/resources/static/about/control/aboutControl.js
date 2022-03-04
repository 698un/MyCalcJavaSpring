


class AboutControl{

    constructor(){

        this.btnSize = 30.0;

        this.cmdNext = createButton('>>');
        this.cmdPrev = createButton('<<');
        this.cmdNext.position(200,60);
        this.cmdPrev.position(0,20);


        this.cmdPrev.size(this.btnSize,this.btnSize/1.5);
        this.cmdNext.size(this.btnSize,this.btnSize/1.5);


        this.cmdNext.show();
        this.cmdPrev.show();

        this.cmdNext.mousePressed(pressNext);
        this.cmdPrev.mousePressed(pressPrev);


        }//constructor


    rePositionControl(){

        this.cmdPrev.position(0,height-this.btnSize);
        this.cmdNext.position(width-this.btnSize, height-this.btnSize);



        //text(this.cmdNext.style.width,100,250);

        //this.cmdPrev.position(0,height-this.cmdNext.sizeY-200);
        //this.cmdNext.position(width-this.cmdPrev.sizeX, height-this.cmdNext.sizeY-200);
        }//rePosition

    }//AboutControl

