$(function() {
	_.templateSettings = {
      evaluate : /\{\[([\s\S]+?)\]\}/g,
      interpolate : /\{\{([\s\S]+?)\}\}/g
	};
	window.Result = Backbone.Model.extend({
    });

});