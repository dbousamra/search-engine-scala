var $container = $('#result_content');
$container.imagesLoaded( function(){
	$container.isotope({
	  itemSelector : '.box',
	  // isAnimated : true,
	  isResizable : true
	});
});