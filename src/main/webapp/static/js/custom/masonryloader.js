jQuery.expr[':'].between = function(a, b, c) { 
   var args = c[3].split(',');
   var val = parseInt(jQuery(a).attr("id"));
   return val >= parseInt(args[0]) && val <= parseInt(args[1]);
};

var $container = $('#result_content');
$container.imagesLoaded( function(){
	$container.isotope({
	  itemSelector : '.box',
	  // isAnimated : true,
	  isResizable : true
	});
});