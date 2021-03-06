$( document ).ready(function() {
	var user_mailId = sessionStorage.getItem('user_mailId');
	$.getJSON("http://localhost:8080/CloudOnlineStore/rest/users/displayCart?mailId="+user_mailId, function(data) {
		//fill dropdown menu for remove from cart
		$.each(data, function(index, product) {
			var productId = product.productId;
			$("#productId_selector")
			.append($('<option>', { productId : index }).text(productId));
		});

		//display cart table
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