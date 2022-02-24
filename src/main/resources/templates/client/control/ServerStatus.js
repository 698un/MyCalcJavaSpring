

//+37529 5827678 Вера


class ServerStatus {

    constructor() {
        this.left = 5;
        this.top = 5;

        this.w1 = 200;
        this.h1 = 200;

        this.bColor = color(127,127,127,255);//фон
        this.tColor = color(0);//текст
        this.textSize = 10;
        this.caption = "caption";
        this.mPress = false;//признак что нажата
        this.tRelease = 0;//время отпускания
        this.cnv = createGraphics(100,100);

        this.cmdKeyUpdate="/server/status";
        this.lastUpdateTime = tNow-100;
        this.updateInterval = 25;

        this.serverStatus="none";//object of server status
        this.rotate_angle=0;
        this.rotate_w = 0;

        }//constuctor

    //метод перестраивает canvas если ищменился размер
    reGraphics(){

        //if size change
        if (this.cnv.width!=this.w1 ||
           this.cnv.height!=this.h1) {

               this.cnv = createGraphics(this.w1,this.h1);
               //console.log("resize");

               }//if size change



        }//reGraphics




    update(){
        if (tNow<this.lastUpdateTime+this.updateInterval) return;




        let answerStr = sendAnyHttp("GET",this.cmdKeyUpdate,"{}");

        //alert(answerStr)

        //Verifyng errors
        if (answerStr.indexOf("error")>-1) {
                 alert("ERROR="+answerStr);
                 this.lastUpdateTime=tNow;
                 return;
                 }

         console.log(answerStr);

        let answerObject = JSON.parse(answerStr);

        let oldPerfomance = this.serverStatus.netPerfomance;

        this.serverStatus = answerObject;
        this.lastUpdateTime = tNow;

        this.serverStatus.netPerfomance = oldPerfomance;

        }//update


    show() {

        this.update();


        //this.rotate_w = this.serverStatus.clientCount;
        //this.rotate_angle+=this.rotate_w*dt*2*Math.PI*0.1;

        this.reGraphics();

        //title
        this.cnv.fill(this.tColor);
        this.cnv.noStroke();
        this.cnv.text("ServerStatus:",this.w1/2-this.cnv.textWidth("ServerStatus:")/2,this.textSize*1.5);


        //серый прямоугольник
        this.cnv.strokeWeight(2);
        this.cnv.fill(this.bColor);
        //this.cnv.fill(millis()*0.5%255,millis()*0.6%255,millis()*0.7%255);
        this.cnv.stroke(0);//черная окантовка кнопки
        this.cnv.rect(0,0,this.w1,this.h1);


        let xc = this.w1/2;
        let yc = this.h1/2;

        this.rotate_angle+=this.serverStatus.clientCount;

        this.cnv.fill(0);
        this.cnv.rect(15,1*this.textSize*1.5,
                         this.cnv.width-15*2,1*this.textSize*1.5);


        for (let i=1;i<10;i++){
            xe=(0.25*this.rotate_angle/i)%(this.cnv.width-15*2);
            this.cnv.stroke(i/10.0*255.0);
            this.cnv.line(15+xe,1*this.textSize*1.5,
                          15+xe,2*this.textSize*1.5);

            }//next i


        //ClientCount
        this.cnv.noStroke();
        this.cnv.fill(255,255,0);
        this.cnv.text("ClientCount:"+this.serverStatus.clientCount,15,2*this.textSize*1.5);

        //netPerfomance
        if (this.serverStatus.netPerfomance!=null) {
            let ntPerf = Math.round(this.serverStatus.netPerfomance*100.0)/100;

            if (ntPerf>100) ntPerf = 99.0;

            this.cnv.fill(0);
            this.cnv.rect(15,3*this.textSize*1.5,
                this.cnv.width-15*2,1*this.textSize*1.5);

            this.cnv.fill(255,0,0);
            xe= (this.cnv.width-15*2)*ntPerf/100.0;
            this.cnv.rect(15,3*this.textSize*1.5,
                            xe,1*this.textSize*1.5);


            this.cnv.fill(255,255,0);
            this.cnv.text("netPerfomance:" + ntPerf, 15, 4 * this.textSize * 1.5);



         //TaskStatus

             let taskCompletteW = (this.cnv.width-15*2)*displayOpt.currentLine/displayOpt.height;
             let taskStatusStr = Math.round(100*displayOpt.currentLine/displayOpt.height);

             this.cnv.fill(0);
             this.cnv.rect(15,5*this.textSize*1.5,
                       this.cnv.width-15*2,1*this.textSize*1.5);

                this.cnv.fill(255,0,0);
                xe= taskCompletteW;
                this.cnv.rect(15,5*this.textSize*1.5,
                                 xe,1*this.textSize*1.5);

                this.cnv.fill(255,255,0);
                this.cnv.text("TaskComplette:" + taskStatusStr+" %", 15, 6 * this.textSize * 1.5);

            //TestComplette
            let testCompletteW = (this.cnv.width-15*2)*displayOpt.currentFrame/1024;
            let testCompletteStr = Math.round(100*displayOpt.currentFrame/1024);

            this.cnv.fill(0);
            this.cnv.rect(15,7*this.textSize*1.5,
                this.cnv.width-15*2,1*this.textSize*1.5);

            this.cnv.fill(255,0,0);
            xe= testCompletteW;
            this.cnv.rect(15,7*this.textSize*1.5,
                xe,1*this.textSize*1.5);

            this.cnv.fill(255,255,0);
            this.cnv.text("TestComplette:" + testCompletteStr+" %", 15, 8 * this.textSize * 1.5);

         //ErrorPrc
         /*
         let errorW = (this.cnv.width-15*2)*errorPrc/100.0;

         this.cnv.fill(0);
         this.cnv.rect(15,9*this.textSize*1.5,
                   this.cnv.width-15*2,1*this.textSize*1.5);

        this.cnv.fill(255,0,0);
        xe= errorW;
        this.cnv.rect(15,9*this.textSize*1.5,
                         xe,1*this.textSize*1.5);

        this.cnv.fill(255,255,0);
        this.cnv.text("error :" + Math.round(errorPrc*100)/100+" %", 15, 10 * this.textSize * 1.5);
        */




            }


        image(this.cnv,this.left,this.top);


        }//show


   eventMouseDown(){
        return false;

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

        let mx = mouseX;
        let my = mouseY;


        if (mx>this.left &&
            my>this.top &&
            mx<this.left+this.w1 &&
            my<this.top+this.h1) {
                      return true;
                      }

         return false;

        }//inRegion




}//class cmd