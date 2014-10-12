function LogInfun(){

	var URL = "http://localhost:8080/CloudOnlineStore/rest/users/signin";

	//alert("signin button clicked"+formToJSON());
	
	$.ajax({
			type: "POST",
			url: URL,
            headers: { 'Access-Control-Allow-Origin': '*' },
			contentType: "application/json",
			dataType: 'json',
			data : formToJSON(),
				//success: function () { //success(data); }
			success: function(data, textStatus, jqXHR){
					alert("you are logged in");
                    window.location = "../../Catalog.html";
				},
			error: function(textStatus, jqXHR,errorThrown){
				alert(textStatus+" "+jqXHR);
			}

		});
}

function formToJSON() {
    return JSON.stringify({
        "mailId": $('#mailId').val(),
        "passwd": $('#passwd').val()
    });
}
    //$.ajax({
      //  url : '/OnlineShopping/addToCart/'+sessionStorage.userId+'?cata='+catalogName+'&product_id=' + productID+'&quan=1',
//}