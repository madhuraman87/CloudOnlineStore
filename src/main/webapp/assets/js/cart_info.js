$( document ).ready(function() {
	var user_mailId = sessionStorage.getItem('user_mailId');
	$.getJSON("http://localhost:8080/CloudOnlineStore/rest/users/displayCart?mailId="+user_mailId, function(data) {
		var product_details_display = []
		$.each(data, function(i, obj) {
			var productId = obj.productId;
			$.getJSON("http://localhost:8080/CloudOnlineStore/rest/product/productInfo/"+productId,function(product_data) {
				
			});			
		});
	});
});