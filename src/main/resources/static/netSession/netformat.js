




//command to server which login
function jsonCommandLogin(loginStr,passwordStr){
    //parameters for command_login
    /*
    let jsonReq = '{'+getUserJson()+','+
                        '{"cmdName":"login",'+
                        '"inlogin":"'+loginStr+'",'+
                        '"inpassword":"'+passwordStr+'"}'+
                '}';
    */
    jsonReq = loginStr+";"+passwordStr;
    return jsonReq;
    }//netLogin


//command to server which login
function jsonCommandRegistration(loginStr,passwordStr){
    /*
    let jsonReq = '{'+getUserJson()+','+

        '{"cmdName":"registration",'+
        '"inlogin":"'+loginStr+'",'+
        '"inpassword":"'+passwordStr+'"}'+
        '}';
    */
    jsonReq = loginStr+";"+passwordStr;
    return jsonReq;
    }//netLogin


//coding user parameters to JSON string
function getUserJson(){
    return JSON.stringify(myUser);
    }//getUserJson
