$( document ).ready(function() {
	var user_mailId = sessionStorage.getItem('user_mailId');
	$.getJSON("http://localhost:8080/CloudOnlineStore/rest/users/displayOrderHistory?mailId="+user_mailId, function(data) {
		//fill table with order items
		$.each(data, function(index,item){
		$("#cartTable").append("Order Number: " + (index+1));
		$("#cartTable").mrjsontable({	
            columns: [
                new $.fn.mrjsontablecolumn({
                    heading: "Product Id",
                    data: "productId",
                    sortable: true	
                }),
                new $.fn.mrjsontablecolumn({
                    heading: "Name",
                    data: "name",
                    sortable: true
                }),
                new $.fn.mrjsontablecolumn({
                    heading: "Description",
                    data: "desc",
                    sortable: true
                }),
                new $.fn.mrjsontablecolumn({
                    heading: "Price",
                    data: "price",
                    sortable: true
                }),                
                new $.fn.mrjsontablecolumn({
                    heading: "Catalog",
                    data: "catalog",
                    sortable: true
                }),
                new $.fn.mrjsontablecolumn({
                    heading: "Quantity",
                    data: "quantity",
                    sortable: true
                })
            ],
            data: item
        	});
		});
	});
});