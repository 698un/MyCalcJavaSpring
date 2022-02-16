

//+37529 5827678 Вера
class ListItem{
    constructor() {
        this.caption = "ITEM";
        this.checked=false;
        this.ID=0;//Id отображаемого поля
        this.ye=0;
        this.level=0;
        this.currentMouseIndex=-1;//Index of item under mouse
        }//constructor


    }//class ListItem

class MyListBox {

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


        this.items = new Array();
        this.itemIndex = -1;
        this.itemCount = 0;


        this.firstItemIndex=0;//номер верхнего итема
        this.oldFirstItemIndex=-1;//предыдущий верхний (для определения момента перерисовки)

        let item1;
        for (let i=0;i<40;i++){
            //создание одного пункта
            item1 = new ListItem();
            item1.caption = "ITEMVARIANT "+i;
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

               }//if size change

        }//reGraphics




/*
    centerPosition(){
        this.left = width*0.5-this.w1*0.5;
        this.top = height*0.5-this.h1*0.5;
        }//CenterPosition
*/

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
            for (var i=0;i<this.itemCount;i++){
            this.items[i].ye=(i-this.firstItemIndex)*this.textSize;
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

        for (let i=0;i<this.itemCount;i++){

            //Color fon for selectedItem
            if (i==this.itemIndex) {
                this.cnv.noStroke();
                this.cnv.fill(this.backSelectColor);
                this.cnv.rect(0, this.items[i].ye,
                    this.w1, this.textSize);
                this.cnv.noStroke();
                this.cnv.fill(this.textColor);
                }

            //rectangle for item  under mouse
            if (i==this.currentMouseIndex) {
                    this.cnv.stroke(0);
                    this.cnv.noFill();
                    this.cnv.rect(0,this.items[i].ye,
                                   this.w1,this.textSize);
                    this.cnv.noStroke();
                    this.cnv.fill(this.textColor);
                    }

            //several TAB for items with level>0
            let title="";
            for (let j=0;j<this.items[i].level-1;j++) {title=title+"  ";}
            if (this.items[i].level>1) title=title+">";
            title=title+this.items[i].caption;

            //draw item
            this.cnv.text(title,0,this.items[i].ye);

            }

        //copy canvas to window
        image(this.cnv,this.left,this.top);
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
                                if (this.currentMouseIndex>=0) {
                                    this.itemIndex=this.currentMouseIndex;
                                    this.selectItemId = this.items[this.itemIndex].id;
                                    return "item_change";
                                    }}
                return "click";
                }


       return "none";

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