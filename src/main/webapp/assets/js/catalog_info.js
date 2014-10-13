$( document ).ready(function() {
	var pageURL = purl();
	var catalog_name = pageURL.param('catalog');
	var catalogInfoURL = 'http://localhost:8080/CloudOnlineStore/rest/product/listproducts?catalog=';
	$.getJSON(catalogInfoURL+catalog_name, function(data) {
		$("#mydiv").mrjsontable({	
            columns: [
                new $.fn.mrjsontablecolumn({
                    heading: "Product Id",
                    data: "prodId"
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
                    heading: "Inventory",
                    data: "inventory"
                }),
                new $.fn.mrjsontablecolumn({
                    heading: "Catalog",
                    data: "catalog"
                })
            ],
            data: data
        });
	});
});

/*$.each(data, function(key, val) {
    var tr=$('<tr></tr>');
    $.each(val, function(k, v){
        $('<td>'+v+'</td>').appendTo(tr);
    });
tr.appendTo('#display');
}); 
});
});*/