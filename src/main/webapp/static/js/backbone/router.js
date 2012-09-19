$(function() {

	var App = Backbone.Router.extend({
		routes: {
		    "": "index"
		},
		index: function() {
			var formView = new window.FormView;
		}
	});
	var app = new App();

	Backbone.history.start();

});