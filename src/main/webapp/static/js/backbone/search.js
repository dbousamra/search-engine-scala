var Result = Backbone.Model.extend({});

_.templateSettings = {
  evaluate : /\{\[([\s\S]+?)\]\}/g,
  interpolate : /\{\{([\s\S]+?)\}\}/g
};

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
  template : _.template($("#result_template").html()),
  render : function() {
    this.collection.each(function(result) {
      var $output = $(this.template(result.toJSON()));
      var $container = $('#result_content');
      $container.append($output).masonry('reload');
      // $container.masonry( 'appended', $output );
      $output.imagesLoaded( function(){
        $container.masonry('reload')
      });
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
      el : "#result_content",
      collection: results
    });
    $('#result_content .result').empty()
    var targetSpinner = document.getElementById('loading');
    
    var spinner = new Spinner({ top: '10px' }).spin(targetSpinner);
    // on a successful fetch, update the collection.
    results.fetch({
      success: function(collection) {
        resultsView.render();
        spinner.stop();
        var $container = $('#result_content');

        // $container.imagesLoaded( function(){
        //   $container.masonry('reload')
        // });
      // $container.imagesLoaded( function(){
      //   $container.append( $output ).masonry( 'appended', $output );
      // });
      
      }
    })
  }
});

var createSearchView = new CreateSearchView({
  el: $("#create-search"),
  model: Result
});