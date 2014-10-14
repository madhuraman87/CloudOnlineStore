$( document ).ready(function() {
	var user_mailId = sessionStorage.getItem('user_mailId');
	$.getJSON("http://localhost:8080/CloudOnlineStore/rest/users/displayCart?mailId="+user_mailId, function(data) {	
		//display total number of items		
		$("#numProducts").html(data.length);
		
		//calculate total amount and display
		var totalAmount = 0;
		$.each(data, function(index, product) {
			totalAmount += product.price*product.quantity;
		});
		$("#amountDisplay").html(totalAmount);
		
		//display cart table
		//TODO: move this portion out to a function that is then called from cart_info.js and here
		$("#cartTable").mrjsontable({	
            columns: [
                new $.fn.mrjsontablecolumn({
                    heading: "Product Id",
                    data: "productId"
                }),
                new $.fn.mrjsontablecolumn({
                    heading: "Name",
                    data: "name"
                }),
                new $.fn.mrjsontablecolumn({
                    heading: "Description",
                    data: "desc"
                }),
                new $.fn.mrjsontablecolumn({
                    heading: "Price",
                    data: "price"
                }),                
                new $.fn.mrjsontablecolumn({
                    heading: "Catalog",
                    data: "catalog"
                }),
                new $.fn.mrjsontablecolumn({
                    heading: "Quantity",
                    data: "quantity"
                })
            ],
            data: data
        });
	});
});