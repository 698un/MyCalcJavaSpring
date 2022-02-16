

//+37529 5827678 Вера
class SGItem{
    constructor() {
        this.caption = "ITEM";
        this.checked=false;
        this.ID=0;//Id отображаемого поля
        this.ye=0;
        this.fld=new Array();//массив полей
        }//constructor

    }//class SGItem

class MyStringGrid {

    constructor() {
        this.left = 100;
        this.top = 100;

        this.w1 = 200;
        this.h1 = 200;

        this.backColor = color(255);//фон
        this.textColor = color(0,0,0);//фон
        this.textSize = 14;
        this.backSelectColor = color(127,255,0);//фон

        this.mPress = false;//признак что нажата
        this.tRelease = 0;//время отпускания
        this.cnv = createGraphics(100,100);
        //this.cnv.textAlign(CENTER,CENTER);

        this.items = new Array();//массив записей
        this.itemIndex = -1;
        this.itemCount = 0;
        this.colCount = 5;
        this.rowCount = 5;
        this.colWidth = new Array();//array of width of every column
        this.colPosX = new Array();

        this.firstItemIndex=0;//номер верхнего итема
       // this.oldFirstItemIndex=-1;//предыдущий верхний (для определения момента перерисовки)

        let item1;
        for (let i=0;i<40;i++){
            //создание одного пункта
            item1 = new SGItem();

            for (let j=0;j<this.colCount;j++) {
                item1.fld[j] = "fld "+j;
                }

            this.addItem(item1);
            }//next i




        this.positionItemsReCalc();//перерасчет координат

        }//constuctor

    //метод перестраивает canvas если ищменился размер
    reGraphics(){

        //if size change
        if (this.cnv.width!=this.w1 ||
            this.cnv.height!=this.h1) {

               this.cnv = createGraphics(this.w1,this.h1);
        //       console.log("resize");

            //set width of every columns
            for (let i=0;i<this.colCount;i++){
                this.colWidth[i]=this.w1/this.colCount;
                 }//next i

               }//if size change



        }//reGraphics



    centerPosition(){
        this.left = width*0.5-this.w1*0.5;
        this.top = height*0.5-this.h1*0.5;
        }//CenterPosition


    //Полная очистка списка
    clear(){
        //заново пересоздается массив
        this.items = new Array();
        this.itemCount = 0;
        this.itemIndex = -1;
        this.firstItemIndex=0;

        }

    addItem(inpItem){

        this.itemCount++;
        inpItem.ye = (this.itemCount)*this.textSize;
        this.items[this.itemCount-1]=inpItem;
        //пересчитываем кординаты всех Items
        //this.positionItemsReCalc();

        }//addItem


    positionItemsReCalc(){

            //Calculate vertical
            for (var i=0;i<this.itemCount;i++){
            this.items[i].ye=(i-this.firstItemIndex)*this.textSize;
            }//next i

            let posX = 0;

            //calculate positionX for column
            for (let i=0;i<this.colCount;i++){
                posX = 0;
                for (let j=0;j<i-1;j++) posX+=this.colWidth[j];//summary all colWidth befire this column

                this.colPosX[i]=posX;
                }//next i


        }//positionItemsRecalc


    //Method recalc quentity items on the listBox

    getItemCountVisible(){
        return this.h1/this.textSize;
        }


    show() {
        //fill(255,255,0);
        //text(tNow,100,10);
        //text(this.tRelease,100,20);
        //text(this.mPress,100,30);
        //text(MW_press,100,40);


        if (tNow>this.tRelease) this.mPress=false;

        this.cnv.textAlign(LEFT,TOP);//выравнивание текста
        //remember in region status
        let nowInRegion = this.inRegion();


        this.reGraphics();

        let wheelValue = Math.trunc(MW_delta*0.02);

        //прокручиваем список колесом
        if (nowInRegion && wheelValue!=0) {
                            //background(255,0,0);
                            //text(this.firstItemIndex,10,10);

                            this.firstItemIndex=this.firstItemIndex+wheelValue;

                            if (this.firstItemIndex<0) this.firstItemIndex=0;
                            if (this.firstItemIndex>this.itemCount-this.getItemCountVisible()) {

                                   this.firstItemIndex = this.itemCount - this.getItemCountVisible();
                                    }

                            if (this.getItemCountVisible()>=this.itemCount) this.firstItemIndex=0;

                            this.positionItemsReCalc();//перерасчитываем позиции
                            }//if mouseWheel




        //Очищаем область
        this.cnv.noStroke();
        this.cnv.fill(this.backColor);
        this.cnv.rect(0,0,this.w1,this.h1);

        //Проверяем над кем находится мышь
        this.currentMouseIndex=-1;
        if (nowInRegion) {
            for (let i = 0; i < this.itemCount; i++) {
                if ((mouseY - this.top) > this.items[i].ye &&
                    (mouseY - this.top) < this.items[i].ye + this.textSize) this.currentMouseIndex = i;

                }//next i
            }//inRegion



        //прорисовываем ITEM
        this.cnv.fill(this.textColor);
        this.cnv.textSize(this.textSize);

        for (let i=0;i<this.itemCount;i++) {

            //Color fon for selectedItem
            if (i == this.itemIndex) {
                this.cnv.noStroke();
                this.cnv.fill(this.backSelectColor);
                this.cnv.rect(0, this.items[i].ye,
                    this.w1, this.textSize);
                this.cnv.noStroke();
                this.cnv.fill(this.textColor);
            }

            //rectangle for item  under mouse
            if (i == this.currentMouseIndex) {
                this.cnv.stroke(0);
                this.cnv.noFill();
                this.cnv.rect(0, this.items[i].ye,
                    this.w1, this.textSize);
                this.cnv.noStroke();
                this.cnv.fill(this.textColor);
            }


            //draw all fields of record[i]
            for (let j = 0; j < this.colCount; j++) {
                this.cnv.text(this.items[i].fld[j].caption, this.colPosX[j], this.items[i].ye);
            }//next j

            //copy canvas to window
            image(this.cnv, this.left, this.top);
        }//next item draw

        }//show

   eventListen(){


        //выход если мышь не нажата
        if (MW_press==false) return "none";

        //выход если нажатие уже есть
        if (this.mPress==true) return "none";

        //если мышь нажата внутри региона (и mPress==false)
       if( this.inRegion()==true) {
                this.mPress=true;
                this.tRelease = tNow+0.2;
                if (this.itemIndex!=this.currentMouseIndex) {
                                this.itemIndex=this.currentMouseIndex;
                                return "item_change";
                                 }
                return "click";
                }


       return "none";

       }//eventListen



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