

// user parameters which define its acces right
let myUser = {
    id:-1,
    login:"guest",
    hashSession:"none"
    }


//
function netLogin(loginStr,passwordStr){
    //get json string
    let sendDataStr = loginStr+";"+passwordStr;
    //getting response from server if JSON format
    let jsonResp = sendPostServer3("/command/login",sendDataStr);
    return jsonResp;
    }//netLogin


function netExit(){
    //get json string
    let sendDataStr = "{}";//jsonCommandLogin(loginStr,passwordStr);
    //getting response from server if JSON format
    let jsonResp = sendPostServer3("/command/exit",sendDataStr);
    return jsonResp;
    }//netLogin



function netRegistration(loginStr,passwordStr){
    //get json string
    let jsonReq = jsonCommandRegistration(loginStr,passwordStr);
    //getting response from server if JSON format
    let jsonResp = sendPostServer3("/command/registration",jsonReq);
    return jsonResp;
    }//netLogin



function sendPostServer3(cmdKey,dataStr){
    let xhr = new XMLHttpRequest();

    //предполагаем что ошибки нет
    let errorStr = '{"errorStr":'+'"none",';

    //xhr.responseType = 'text';
    xhr.open("POST", cmdKey, false);
    xhr.setRequestHeader('Content-type', 'text/place; charset=UTF-8');

    //маркируем отправителя команды
    //xhr.setRequestHeader('WebUser',myUser.id+"#"+myUser.login+"#"+myUser.hash);

     // Сервер должен уметь такой Content-Type принимать и раскодировать
     xhr.send("SEND_DATA:"+dataStr);


    //oXHR.statusText  - сообщение об ошибке
    //alert(xhr.statusText);

    if (xhr.statusText!="OK") {
          //alert(xhr.statusText);//text of error
          errorStr = '{"errorStr":'+'"'+xhr.statusText+'"}';
          return errorStr;
          }


    let accessCookie = xhr.getResponseHeader("ClientKey");

    if (accessCookie!=null) {
                    //alert(accessCookie);
                    //set userHash to cookie
                    setMyCookie('ClientKey', accessCookie);
                    }



    //return correct response with data from server
    return errorStr+'"data":'+xhr.responseText+"}"; //+"}" because response contain two object (error and result of request)

    }


function sendGetServer3(cmdKey,dataStr){
    let xhr = new XMLHttpRequest();

    //предполагаем что ошибки нет
    let errorStr = '{"errorStr":'+'"none",';

    //xhr.responseType = 'text';
    xhr.open("GET", cmdKey, false);
    xhr.setRequestHeader('Content-type', 'text/place; charset=UTF-8');

    //маркируем отправителя команды
    //xhr.setRequestHeader('WebUser',myUser.id+"#"+myUser.login+"#"+myUser.hash);

    // Сервер должен уметь такой Content-Type принимать и раскодировать
    xhr.send(dataStr);


    //oXHR.statusText  - сообщение об ошибке
    //alert(xhr.statusText);

    if (xhr.statusText!="OK") {
        //alert(xhr.statusText);//text of error
        errorStr = '{"errorStr":'+'"'+xhr.statusText+'"}';
        return errorStr;
         }


   // let accessCookie = xhr.getResponseHeader("ClientKey");

   // if (accessCookie!=null) {
        //alert(accessCookie);
        //set userHash to cookie
        //setMyCookie('ClientKey', accessCookie);
    //}

    //let answerString = xhr.responseText;
    /*
    alert(answerString);

    let answerObject = JSON.parse(answerString);

    if (answerObject.errorStr!="none") {
            alert(answerObject.errorStr);
            }
     */

    //return correct response with data from server
    return xhr.responseText; //+"}" because response contain two object (error and result of request)

}



function sendAnyHttp(httpType,cmdKey,dataStr){
    let xhr = new XMLHttpRequest();

    //предполагаем что ошибки нет
    //let errorStr = '{"errorStr":'+'"none",';

    //xhr.responseType = 'text';
    xhr.open(httpType, cmdKey, false);
    xhr.setRequestHeader('Content-type', 'text/place; charset=UTF-8');


    // Сервер должен уметь такой Content-Type принимать и раскодировать

    try {
        //if (httpType="GET") console.log(dataStr+" ");
        xhr.send(dataStr);
        return xhr.responseText;
        }  catch (err) {

            //if (xhr.statusText!="OK") {
            //alert(err.message);//text of error

            let errorStr = "error "+err.message;//+" "+xhr.statusText;
            console.log(errorStr);
            return errorStr;
            }


    //return correct response with data from server
    //return xhr.responseText;

}








//метод загружает данные из
    //сохраненных в cokie
   function  dataFromCookie(){
        //    	document.cookie="userId="+userIdStr;
        //    	document.cookie="userName="+userName;
        //    	document.cookie="userWork="+userWork
        }

   function  dataToCookie(){
        //document.cookie="userId="+this.userId;
    	//document.cookie="gameId="+this.gameId;
    	//document.cookie="hashSession="+this.hashSession;
        }




