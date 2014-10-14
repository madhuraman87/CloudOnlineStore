$("#removeFromCartButton").click(
		function() {
			var productIdToBeRemoved = $("#productId_selector").val();
			var user_mailId = sessionStorage.getItem("user_mailId");
			var removeFromCartURL = "http://localhost:8080/CloudOnlineStore/rest/users/removeFromCart?mailId=" + user_mailId + "&productId=" + productIdToBeRemoved;

			$.ajax({
				type: "POST",
				url: removeFromCartURL,
				contentType: "application/json",
				success: function(data, textStatus, jqXHR) {
					alert("Product removed from cart");
					location.reload();
				},
				error: function(textStatus, jqXHR, errorThrown) {
					alert(textStatus + " " + jqXHR);
				}
				});
		});