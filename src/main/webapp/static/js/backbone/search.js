var Result = Backbone.Model.extend({});

var ResultView = Backbone.View.extend({
  tagName : "li",
  className : "tweet",
  render: function() {
    var template = this.template = '<li><h3>{{name}} {{score}}</h3></li>';
    var htm = Mustache.to_html(this.template, this.model.toJSON());
    $(this.el).html(htm);
    return this;
  }
 });

// var ResultView = Backbone.View.extend({
//   tagName : "li",
//   className : "tweet",
//   render : function() {
//     // just render the tweet text as the content of this element.
//     $(this.el).html(this.model.get("name"));
//     return this;
//   }
// });

var Results = Backbone.Collection.extend({
  model : Result,
  initialize : function(models, options) {
    this.query = options.query;
  },
  url : function() {
    return "/search?query=" + this.query;
  },
  parse : function(data) {
    return data.results;
  }
});

var ResultsView = Backbone.View.extend({
  tagName : "ul",
  className : "results",
  render : function() {
    this.collection.each(function(result) {
      var resultView = new ResultView({ 
        model : result 
      });
      $(this.el).prepend(resultView.render().el);

    }, this);
    return this;
  }
});

var CreateSearchView = Backbone.View.extend({
  initialize: function() {
    _.bindAll(this, 'updateQuery', 'submitSearchClicked');
  },
  events: {
    "click #submit-search": "submitSearchClicked",
    "keypress #search-query" : "queryKeyPress"
  },
  submitSearchClicked: function() {
    this.updateQuery();
  },
  queryKeyPress: function(event) {
    if(event.keyCode == 13) {
      this.updateQuery();
    }
  },
  updateQuery: function() {

    //get the search query from input and create collection
    var results = new Results([], {
      query: this.$('#search-query').val()
    });

    // create a view that will contain our results
    var resultsView = new ResultsView({
      collection: results
    });

    var targetSpinner = document.getElementById('loading');
    var opts = {
      lines: 13, // The number of lines to draw
      length: 7, // The length of each line
      width: 4, // The line thickness
      radius: 10, // The radius of the inner circle
      corners: 1, // Corner roundness (0..1)
      rotate: 0, // The rotation offset
      color: '#000', // #rgb or #rrggbb
      speed: 1, // Rounds per second
      trail: 60, // Afterglow percentage
      shadow: false, // Whether to render a shadow
      hwaccel: false, // Whether to use hardware acceleration
      className: 'spinner', // The CSS class to assign to the spinner
      zIndex: 2e9, // The z-index (defaults to 2000000000)
      top: 'auto', // Top position relative to parent in px
      left: 'auto' // Left position relative to parent in px
    };
    var spinner = new Spinner(opts).spin(targetSpinner);
    // on a successful fetch, update the collection.
    results.fetch({
      success: function(collection) {
        $('#example_content').html(resultsView.render().el);
        spinner.stop();
      }
    })
  }
});

var createSearchView = new CreateSearchView({
  el: $("#create-search"),
  model: Result
});