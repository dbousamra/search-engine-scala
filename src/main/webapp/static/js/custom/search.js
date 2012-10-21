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
    this.resultsLength = data.resultsLength
    this.startDate = data.startDate
    this.endDate = data.endDate
    return data.results;
  }
});

jQuery.expr[':'].between = function(a, b, c) { 
   var args = c[3].split(',');
   var val = parseInt(jQuery(a).attr("id"));
   return val >= parseInt(args[0]) && val <= parseInt(args[1]);
};


var ResultsView = Backbone.View.extend({
  template : _.template($("#result_template").html()),
  render : function() {
    $('#length').html("<h4>" + this.collection.resultsLength + " results found</h4>")

    $('#slider').slider({
      range: true,
      min: this.collection.startDate,
      max: this.collection.endDate,
      values: [1857, 2012],
      slide: function (event, ui) {
        console.log();
        var selector = $(".result:between(" + ui.values[0] + "," + + ui.values[1] + "), .home-tile")
        $container.isotope({ filter: selector });
        $(".year0").html(ui.values[0])
        $(".year1").html(ui.values[1])
      }
    });

    this.collection.each(function(result) {
      var $output = $(this.template(result.toJSON()));
      var $container = $('#result_content');
      $container.append( $output ).isotope( 'reloadItems' ).isotope({ sortBy: 'original-order' });
      $output.imagesLoaded( function(){
        $container.isotope( 'reloadItems' ).isotope({ sortBy: 'original-order' });
      });
    }, this);
    $('.modal').on('show', function (e) {
    })
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

    $('#result_content .result').remove()

    // create a view that will contain our results
    var resultsView = new ResultsView({
      el : "#result_content",
      collection: results
    });
    
    var targetSpinner = document.getElementById('loading');
    
    var spinner = new Spinner({ top: '10px' }).spin(targetSpinner);
    // on a successful fetch, update the collection.
    results.fetch({
      success: function(collection) {
        resultsView.render();
        spinner.stop();
        var $container = $('#result_content');
      }
    })
  }
});

var createSearchView = new CreateSearchView({
  el: $("#create-search"),
  model: Result
});