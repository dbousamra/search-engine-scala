var $container = $('#result_content');
$container.imagesLoaded( function(){
	$container.masonry({
	  itemSelector : '.box',
	  // isAnimated : true,
	  isResizable : true
	});
});