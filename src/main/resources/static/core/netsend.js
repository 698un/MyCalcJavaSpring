


let resultatErrorCount = 0;
let resultatCorrectCount = 0;
let errorPrc = 0;



function sendPostServerArray(urlStr,arr) {


    let xhr = new XMLHttpRequest;
    xhr.open("POST", urlStr, false);
    xhr.setRequestHeader('Content-type', 'text/place; charset=UTF-8');

    let strAll="";
    let str1="";

    //преобразование результата в строку
    for (let i=0;i<arr.length;i++) {
        str1=""+arr[i];
        while (str1.length<3) {str1="0"+str1;}
        strAll+=str1;

        //if (arr[i]==0) console.log("NULL BYTES!!!!!!!!!!!!!");

        }//next i

    //alert(strAll);

    try {
        xhr.send(strAll);//send sting of numbers
        calcStatus = 0;
        return xhr.responseText;
        }catch(e){
                calcTimeOut = tNow+1;// 5 second for timeout
                console.log("error of send array of the bytes")
                return '{"errorStr":"errorsend"}';
                }

    }//sendPostServerArray






function sendPostServerArray2(urlStr,arr) {


  //  let xhr = new XMLHttpRequest;
    //xhr.open("POST", urlStr, false);
   // xhr.setRequestHeader('Content-type', 'text/place; charset=UTF-8');

    let strAll="";
    let str1="";

    //преобразование результата в строку
    for (let i=0;i<arr.length;i++) {
        str1=""+arr[i];
        while (str1.length<3) {str1="0"+str1;}
        strAll+=str1;
        }//next i

    //alert (strAll.length);

    let answerStr = sendAnyHttp("POST",urlStr,strAll);

    return answerStr;


    /*





    return;
    httpPost(urlStr,'text',strAll,function (answer){
        //alert(answer);
        //Send Correct

        console.log(answer);
        let answerObj = JSON.parse(answer);
        if (answerObj.errorStr=="none") {
                let prc = 100.0 * calcTimeDuration / answerObj.duration;
                if (UI.UIStatus.serverStatus.netPerfomance==null) UI.UIStatus.serverStatus.netPerfomance=0;
                UI.UIStatus.serverStatus.netPerfomance =UI.UIStatus.serverStatus.netPerfomance*0.9+ prc*0.1;

                //console.log(calcTimeDuration);
                //console.log(prc + "%");
                }
            calcStatus = 0;
            resultatCorrectCount++;
            errorPrc = resultatErrorCount/(resultatErrorCount+resultatCorrectCount)*100;


              },

        //SEND_INCORRECT
                  function (errorMessage){
                        calcStatus = 0;
                        console.log(errorMessage);
                        resultatErrorCount++;
                        errorPrc = resultatErrorCount/(resultatErrorCount+resultatCorrectCount)*100;
                         }//function error
                    );

*/

}




