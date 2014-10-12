function signUpfun(){
	
jQuery.ajax({
    type: "POST",
    url: "http://localhost:8080/CloudOnlineStore/rest/users/signup",
    data: formToJSON(),
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    success: function (data, status, jqXHR) {
    	alert("user created successfully");
    	window.location = "SignIn.html";
    },

    error: function (jqXHR, status) {            
    	alert(jqXHR+" "+status);
    }

});
}

function formToJSON() {
    return JSON.stringify({
    "firstName": $('#firstName').val(),
    "lastName": $('#lastName').val(),
    "mailId": $('#mailId').val(),
    "passwd": $('#passwd').val(),
    });
}