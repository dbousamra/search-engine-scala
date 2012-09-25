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
    $("#result_content").empty()
    var targetSpinner = document.getElementById('loading');
    
    var spinner = new Spinner({ top: '10px' }).spin(targetSpinner);
    // on a successful fetch, update the collection.
    results.fetch({
      success: function(collection) {
        $('#result_content').html(resultsView.render().el);
        spinner.stop();
      }
    })
  }
});

var createSearchView = new CreateSearchView({
  el: $("#create-search"),
  model: Result
});