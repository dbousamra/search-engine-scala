$(function() {

	window.FormView = Backbone.View.extend({
		el: $("#search_form"),
		search_twitter: function(e) {
			e.preventDefault();
			var self = this;
			$.getJSON("http://localhost:8080/search",{
				query: $("#q").val()
			}, function(data) {
				$("#results li").fadeOut();
				for(var i in data.results) {
					var result = new Result(data.results[i]);
					var resultView = new ResultView({model: result});
					resultView.render();
				}
			});
		},
		events: {
			"submit": "search_twitter"
		}
    });

	window.ResultView = Backbone.View.extend({
		render: function() {
			var result = _.template( $("#result_template").html(), this.model.toJSON());
			$("#results").append(result);
			
		}
	});

});