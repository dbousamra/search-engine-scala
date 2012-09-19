$(function() {
	_.templateSettings = {
      evaluate : /\{\[([\s\S]+?)\]\}/g,
      interpolate : /\{\{([\s\S]+?)\}\}/g
	};
	window.Tweet = Backbone.Model.extend({
    });

});