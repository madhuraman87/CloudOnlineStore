$('a').click(function (event) 
{ 
   event.preventDefault(); 
   /*var link = $(this).attr('href')
   var query1 = "?catalog=";
   var query2 = $('#link').val();*/
   var url = "http://localhost:8080/CloudOnlineStore/rest/product/listproducts?catalog=Books"
	   //link.concat(query1,query2);
   $.get(url, function(data) {
     alert(data);
    });
 });