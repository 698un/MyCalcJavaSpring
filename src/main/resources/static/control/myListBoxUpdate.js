

//+37529 5827678 Вера
class MyListBoxUpdate extends MyListBox {



    constructor() {
        super();

        this.updateEnabled = false;
        this.updateInterval =10;//интервал проверки/обновления 10 сек
        this.updateLastTime = -100;//время последнего обновления
        this.updateHash = "sdfsd";//Hash версии данных
        this.updateURL = "/command/razdel"; //    +"/hash" /  +"data"
        this.fldName =["id","title","level"];

        }

    setWidth(){
        super.w1 = arguments[0];

    }

    //функция проверки/выполнения обновления
    timerUpdate(){

        if (this.updateEnabled==false) return;

        //verify moment of update
        if (tNow<this.updateLastTime+this.updateInterval) return;

        //get hash version data from server
        //body of request is not important
        let newHash = sendPostServer3(
                      this.updateURL+"/hash",
                     '{"none"}'
                             );

        this.updateLastTime = tNow;//reSet last time of verify

        //alert(newHash);
        //verify equals Hash from server and inner hash value
        //if equals server hash equals inner hash then exit

        if ((this.updateHash==newHash)==true) return;

        //update inner hash(necessarily!!!!!)
        this.updateHash = newHash;

        //loading from server actual Data
        //body of request is not important
        let newData = sendPostServer3(
                        this.updateURL+"/data",
                        '{"none"}'
                                );

       // console.log(newData);

       // alert("список:"+newData);
        this.itemsFromJSON(newData);



        }//timerUpdate

    /**
     * This method load items to listbox from JSON_String
     * @param JSONStr  - String  representation of JSON object
     */
    itemsFromJSON(x){

       //alert("обновляем список:"+x);

        let objArray =JSON.parse(x);

        //выход если произошла ошибка
        if (objArray.errorStr!="none")  {
                        alert(objArray.errorStr);
                        return;
                        }

        objArray = objArray.data;//get only data
        super.clear();
        for (let i=0;i<objArray.length;i++){
            let itemOne = new ListItem();
            itemOne.id = objArray[i][this.fldName[0]];
            itemOne.caption = objArray[i][this.fldName[1]];

            //Optional add level
            if (objArray[i].hasOwnProperty('level'))  itemOne.level = objArray[i][this.fldName[2]];

            super.addItem(itemOne);
            }//next i

        super.itemCount=objArray.length;//переопределение размера
        super.positionItemsReCalc();//перерасчитываем координаты items

        }//itemsFromJSON


    }//class MyListBoxUpdate