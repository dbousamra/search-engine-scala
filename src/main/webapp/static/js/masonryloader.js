var $container = $('#result_content');
$container.imagesLoaded( function(){
	$container.masonry({
	  itemSelector : '.box',
	  columnWidth: 1,
	  // isAnimated : true,
	  isResizable : true
	});
});