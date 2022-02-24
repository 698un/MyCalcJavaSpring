




let calcStatus = 0;  //0-empty  1-calcProcess 2-sendData
let calcTimeDuration = -100;//time for the end of timeout (paused(if error ))
let calcTimeStart = -100.0;//time for the end of timeout (paused(if error ))
let timeAllDuration  =  0;//время расчета и транзакции
let calcTimeOut =-1;

 function  getNewTask() {

    let answerStr = sendAnyHttp("GET","/newtask", "{}");

   //alert(answerStr);


    if (answerStr.indexOf("error")>-1) {

                                    //если ключ устарел спрашиваем новый
                                    //if (answerStr.indexOf("actual client")>-1) {
                                    //    sendAnyHttp("POST","/clientkey","{}");
                                    //    }




                                    calcTimeOut = tNow+1.0;// 1 second for timeout
                                    calcStatus = 0;
                                    console.log(answerStr);
                                    console.log("pause 1 sec");

                                    //if clientKey not valid get to startPage
                                    if (answerStr.indexOf("invalid client")>-1) document.location.href = '/index.html';

                                    return;
                                    }
    let answerObj = JSON.parse(answerStr);



    //if (answerObj.frame==0) alert (answerObj.frame);

    //alert(answerObj.data);
    let curentFrame = answerObj.frame;
    let curentLine = answerObj.line;

    //если сцена сменилась то не переходим в расчет
    // а переспрашиваем параметры сцены
    if (answerObj.sceneKey!=displayOpt.currentSceneKey) {

            displayOpt.currentSceneKey=answerObj.sceneKey;
            calcStatus=0;
            UI.UIStatus.lastUpdateTime=-1;
            UI.UIStatus.update();
            }

    displayOpt.currentSceneKey = answerObj.sceneKey;
    //alert(answerStr);

    initScene(curentFrame,curentLine)//init parameters
    startXe = 0;//начинаем с левого пикселя
    calcStatus = 1;//признак что можно приступать к задаче
    calcTimeStart = millis();//запоминаем когда начали

    //alert(timeCalcStart);





}//getNewTask



function sendResultat(){

        if (calcStatus!=2) return;
        calcStatus = 3;

        let resultatUrl = "/resultat/"+
                           displayOpt.currentSceneKey+"/"+
                           displayOpt.currentFrame+"/"+
                           displayOpt.currentLine;


        //alert(calcResult.length);
        return sendPostServerArray2(resultatUrl,lineResultat);

        //console.log(answerStr);


        }//sendResultat

