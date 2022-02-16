





function getMyCookie ( cookie_name ){
      var results = document.cookie.match ( '(^|;) ?' + cookie_name + '=([^;]*)(;|$)' );
      if ( results )    return ( unescape ( results[2] ) );
	    	else  return null;
   	  }//get_cookie

function setMyCookie(cookieName,cookieValue){
    document.cookie=cookieName+"="+cookieValue;
    }


