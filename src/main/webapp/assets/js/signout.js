$( document ).ready(function(){
	var dialog = document.getElementById('window');
	dialog.show(); 
	document.getElementById('exit').onclick = function() {  
    dialog.close();
    sessionStorage.removeItem("user_mailId");
	sessionStorage.removeItem("logInTime");
	window.location.href = "Home.html";
    } 	
})